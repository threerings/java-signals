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

/** Dispatches events to listeners with one accompanying argument. */
public class Signal1<A>
    implements SignalConnector1<A>
{
    /** Calls apply on all connected listeners. */
    public void dispatch (A a)
    {
        _signaller.dispatch(a);
    }

    /** Adds <code>listener</code> at {@link Signals#DEFAULT_PRIORITY}. */
    public Connection connect (Listener0 listener)
    {
        return connect(listener, Signals.DEFAULT_PRIORITY);
    }
    /**
     * Adds <code>listener</code> at <code>priority</code>. Listeners with a higher priority will
     * have their apply called before listeners with a lower priority. Listeners with equal priority
     * are applied in the order they're added to the signal.
     */
    public Connection connect (Listener0 listener, int priority)
    {
        return _signaller.connect(listener, priority);
    }

    /** Adds <code>listener</code> at {@link Signals#DEFAULT_PRIORITY}. */
    public Connection connect (Listener1<A> listener)
    {
        return connect(listener, Signals.DEFAULT_PRIORITY);
    }

    /**
     * Adds <code>listener</code> at <code>priority</code>. Listeners with a higher priority will
     * have their apply called before listeners with a lower priority. Listeners with equal priority
     * are applied in the order they're added to the signal.
     */
    public Connection connect (Listener1<A> listener, int priority)
    {
        return _signaller.connect(listener, priority);
    }

    /** Removes <code>listener</code> from this signal if it's present.*/
    public void disconnect(Object listener)
    {
        _signaller.disconnect(listener);
    }

    protected final Signaller _signaller = new Signaller();
}
