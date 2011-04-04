package com.threerings.signals;

public interface Connection
{
    /**
     * Prevent the Listener from receiving any further apply calls.
     */
    void disconnect ();

    /**
     * Make the next apply call on the listener the last it'll receive.
     * @return the Connection
     */
    Connection once ();
}
