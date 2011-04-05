package com.threerings.signals;

public interface SignalConnector2<A, B>
{
    /** Adds <code>listener</code> at {@link Signals#DEFAULT_PRIORITY}. */
    Connection connect (Listener0 listener);

    /**
     * Adds <code>listener</code> at <code>priority</code>. Listeners with a higher priority will
     * have their apply called before listeners with a lower priority. Listeners with equal priority
     * are applied in the order they're added to the signal.
     */
    Connection connect (Listener0 listener, int priority);

    /** Adds <code>listener</code> at {@link Signals#DEFAULT_PRIORITY}. */
    Connection connect (Listener1<A> listener);

    /**
     * Adds <code>listener</code> at <code>priority</code>. Listeners with a higher priority will
     * have their apply called before listeners with a lower priority. Listeners with equal priority
     * are applied in the order they're added to the signal.
     */
    Connection connect (Listener1<A> listener, int priority);

    /** Adds <code>listener</code> at {@link Signals#DEFAULT_PRIORITY}. */
    Connection connect (Listener2<A, B> listener);

    /**
     * Adds <code>listener</code> at <code>priority</code>. Listeners with a higher priority will
     * have their apply called before listeners with a lower priority. Listeners with equal priority
     * are applied in the order they're added to the signal.
     */
    Connection connect (Listener2<A, B> listener, int priority);

    /** Removes <code>listener</code> from this signal if it's present.*/
    void disconnect (Listener listener);
}
