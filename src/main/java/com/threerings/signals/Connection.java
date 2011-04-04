package com.threerings.signals;

public interface Connection
{
    /**
     * Prevent the Listener from receiving apply calls from dispatches after the disconnect.
     */
    void disconnect ();

    /**
     * Make the next apply call on the listener the last it'll receive.
     * @return the Connection
     */
    Connection once ();
}
