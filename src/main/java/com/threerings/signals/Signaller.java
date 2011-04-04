package com.threerings.signals;

import java.util.List;
import com.google.common.collect.Lists;

class Signaller
{
    @SuppressWarnings("rawtypes")
    public Connection connect (Object listener)
    {
        if (listener instanceof Listener3) {
            return new Connection3Impl((Listener3)listener);
        } else if (listener instanceof Listener2) {
            return new Connection2Impl((Listener2)listener);
        } else if (listener instanceof Listener1) {
            return new Connection1Impl((Listener1)listener);
        } else {
            return new Connection0Impl((Listener0)listener);
        }
    }

    public void disconnect (final Object listener)
    {
        for (ConnectionImpl<?> conn : _observers) {
            if (conn._listener == listener) {
                conn.disconnect();
                return;
            }
        }
    }

    public void dispatch(final Object...args)
    {
        ConnectionImpl<?>[] snapshot = _observers.toArray(new ConnectionImpl<?>[_observers.size()]);
        for (ConnectionImpl<?> conn : snapshot) {
            if (!conn.apply(args)) {
                conn.disconnect();
            }
        }
    }

    protected abstract class ConnectionImpl<L> implements Connection {
        public ConnectionImpl (L listener) {
            _listener = listener;
            _observers.add(this);
        }

        public void disconnect () {
            _connected = false;
            _observers.remove(this);
        }

        public Connection once () {
            _stayInList = false;
            return this;
        }

        public boolean apply (Object...args) {
            if (!_connected) { return true; }
            applyToArity(args);
            return _stayInList;
        }

        protected abstract void applyToArity(Object...args);

        protected boolean _stayInList = true;
        protected boolean _connected = true;
        protected final L _listener;
    }

    protected class Connection0Impl extends ConnectionImpl<Listener0> {
        public Connection0Impl (Listener0 listener) {
            super(listener);
        }
        protected void applyToArity (Object...args) {
            _listener.apply();
        }
    }
    protected class Connection1Impl extends ConnectionImpl<Listener1<?>> {
        public Connection1Impl (Listener1<?> listener) {
            super(listener);
        }
        @SuppressWarnings({"unchecked", "rawtypes"}) protected void applyToArity (Object...args) {
            ((Listener1)_listener).apply(args[0]);
        }
    }

    protected class Connection2Impl extends ConnectionImpl<Listener2<?, ?>> {
        public Connection2Impl (Listener2<?, ?> listener) {
            super(listener);
        }
        @SuppressWarnings({"unchecked", "rawtypes"}) protected void applyToArity (Object...args) {
            ((Listener2)_listener).apply(args[0], args[1]);
        }
    }

    protected class Connection3Impl extends ConnectionImpl<Listener3<?, ?, ?>> {
        public Connection3Impl (Listener3<?, ?, ?> listener) {
            super(listener);
        }
        @SuppressWarnings({"unchecked", "rawtypes"}) protected void applyToArity (Object...args) {
            ((Listener3)_listener).apply(args[0], args[1], args[2]);
        }
    }

    protected final List<ConnectionImpl<?>> _observers = Lists.newArrayList();
}
