//
// Copyright (c) 2011 Three Rings Design, Inc.

// Permission is hereby granted, free of charge, to any person
// obtaining a copy of this software and associated documentation
// files (the "Software"), to deal in the Software without
// restriction, including without limitation the rights to use,
// copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the
// Software is furnished to do so, subject to the following
// conditions:

// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of the Software.

// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
// OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
// NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
// HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
// WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
// FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
// OTHER DEALINGS IN THE SOFTWARE.

package com.threerings.signals;

import java.lang.RuntimeException;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.common.primitives.Ints;

/**
 * The workhorse that does all the connection management and dispatching for the arity types. Not
 * part of the public API.
 */
class Signaller
{
    @SuppressWarnings("rawtypes")
    public Connection connect (Object listener, int priority)
    {
        if (listener instanceof Listener3) {
            return new Connection3Impl((Listener3)listener, priority);
        } else if (listener instanceof Listener2) {
            return new Connection2Impl((Listener2)listener, priority);
        } else if (listener instanceof Listener1) {
            return new Connection1Impl((Listener1)listener, priority);
        } else {
            return new Connection0Impl((Listener0)listener, priority);
        }
    }

    public void disconnect (Object listener)
    {
        for (ConnectionImpl<?> conn : _observers) {
            if (conn.get() == listener) {
                conn.disconnect();
                return;
            }
        }
    }

    public void dispatch (Object...args)
    {
        for (ConnectionImpl<?> conn : _observers) {
            if (!conn.apply(args)) {
                conn.disconnect();
            }
        }
    }

    protected abstract class ConnectionImpl<L>
        implements Connection, Comparable<ConnectionImpl<?>> {
        public ConnectionImpl (L listener, int priority) {
            Signaller.this.disconnect(listener);
            _priority = priority;
            _listener = listener;
            _ref = new WeakReference<L>(_listener);
            int idx = Collections.binarySearch(_observers, this);
            if (idx < 0) {
                // Nothing with this priority in the list, so use binarySearch's insertionPoint
                idx = -idx - 1;
            } else {
                // Found something with the priority, so sort this past items at the same priority
                while (idx < _observers.size() && _priority == _observers.get(idx)._priority) {
                    idx++;
                }
            }
            _observers.add(idx, this);
        }

        public L get () {
            return _listener == null ? _ref.get() : _listener;
        }

        public void disconnect () {
            _connected = false;
            _observers.remove(this);
        }

        public Connection once () {
            _stayInList = false;
            return this;
        }

        public Connection makeWeak () {
            _listener = null;
            return this;
        }

        public boolean apply (Object...args) {
            if (!_connected) { return true; }
            // Get a reference to listener before calling apply in case we're made weak
            L listener = _listener;
            if (listener != null) {
                applyToArity(listener, args);
            } else {
                listener = _ref.get();
                if (listener != null) {
                    applyToArity(listener, args);
                } else {
                    return true;// We've been collected; remove us from the dispatch list
                }
            }
            return _stayInList;
        }

        public int compareTo (ConnectionImpl<?> other) {
            return -Ints.compare(_priority, other._priority);
        }

        protected abstract void applyToArity(L listener, Object...args);

        protected L _listener;
        protected boolean _stayInList = true;
        protected boolean _connected = true;
        protected final WeakReference<L> _ref;
        protected final int _priority;
    }

    protected class Connection0Impl extends ConnectionImpl<Listener0> {
        public Connection0Impl (Listener0 listener, int priority) {
            super(listener, priority);
        }
        @Override protected void applyToArity (Listener0 listener, Object...args) {
            listener.apply();
        }
    }
    protected class Connection1Impl extends ConnectionImpl<Listener1<?>> {
        public Connection1Impl (Listener1<?> listener, int priority) {
            super(listener, priority);
        }
        @Override @SuppressWarnings({"unchecked", "rawtypes"})
        protected void applyToArity (Listener1 listener, Object...args) {
            listener.apply(args[0]);
        }
    }

    protected class Connection2Impl extends ConnectionImpl<Listener2<?, ?>> {
        public Connection2Impl (Listener2<?, ?> listener, int priority) {
            super(listener, priority);
        }
        @Override @SuppressWarnings({"unchecked", "rawtypes"})
        protected void applyToArity (Listener2 listener, Object...args) {
            listener.apply(args[0], args[1]);
        }
    }

    protected class Connection3Impl extends ConnectionImpl<Listener3<?, ?, ?>> {
        public Connection3Impl (Listener3<?, ?, ?> listener, int priority) {
            super(listener, priority);
        }
        @Override @SuppressWarnings({"unchecked", "rawtypes"})
        protected void applyToArity (Listener3 listener, Object...args) {
            listener.apply(args[0], args[1], args[2]);
        }
    }

    /** Connections to the signal sorted by priority and then insertion order. */
    protected final CopyOnWriteArrayList<ConnectionImpl<?>> _observers =
        new CopyOnWriteArrayList<Signaller.ConnectionImpl<?>>();
}
