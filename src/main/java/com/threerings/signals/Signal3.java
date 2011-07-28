//
// java-signals - Simple, type-safe event dispatching
// Copyright (c) 2011, Three Rings Design, Inc. - All rights reserved.
// https://github.com/threerings/java-signals/blob/master/LICENSE

package com.threerings.signals;

/** Dispatches events to listeners with three accompanying arguments. */
public class Signal3<A, B, C>
    implements SignalConnector3<A, B, C>
{
    /** Calls apply on all connected listeners. */
    public void dispatch (A a, B b, C c)
    {
        _signaller.dispatch(a, b, c);
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
    public Connection connect (Listener1<? super A> listener)
    {
        return connect(listener, Signals.DEFAULT_PRIORITY);
    }

    /**
     * Adds <code>listener</code> at <code>priority</code>. Listeners with a higher priority will
     * have their apply called before listeners with a lower priority. Listeners with equal priority
     * are applied in the order they're added to the signal.
     */
    public Connection connect (Listener1<? super A> listener, int priority)
    {
        return _signaller.connect(listener, priority);
    }

    /** Adds <code>listener</code> at {@link Signals#DEFAULT_PRIORITY}. */
    public Connection connect (Listener2<? super A, ? super B> listener)
    {
        return connect(listener, Signals.DEFAULT_PRIORITY);
    }

    /**
     * Adds <code>listener</code> at <code>priority</code>. Listeners with a higher priority will
     * have their apply called before listeners with a lower priority. Listeners with equal priority
     * are applied in the order they're added to the signal.
     */
    public Connection connect (Listener2<? super A, ? super B> listener, int priority)
    {
        return _signaller.connect(listener, priority);
    }

    /** Adds <code>listener</code> at {@link Signals#DEFAULT_PRIORITY}. */
    public Connection connect (Listener3<? super A, ? super B, ? super C> listener)
    {
        return connect(listener, Signals.DEFAULT_PRIORITY);
    }

    /**
     * Adds <code>listener</code> at <code>priority</code>. Listeners with a higher priority will
     * have their apply called before listeners with a lower priority. Listeners with equal priority
     * are applied in the order they're added to the signal.
     */
    public Connection connect (Listener3<? super A, ? super B, ? super C> listener, int priority)
    {
        return _signaller.connect(listener, priority);
    }

    /** Removes <code>listener</code> from this signal if it's present.*/
    public void disconnect (Listener listener)
    {
        _signaller.disconnect(listener);
    }

    protected final Signaller _signaller = new Signaller();
}
