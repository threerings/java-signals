//
// java-signals - Simple, type-safe event dispatching
// Copyright (c) 2011, Three Rings Design, Inc. - All rights reserved.
// https://github.com/threerings/java-signals/blob/master/LICENSE

package com.threerings.signals;

/**
 * The link between a signal and a listener. Can be added to a <code>ConnectionGroup</code> to
 * perform bulk operations on Connections.
 */
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

    /**
     * Only weakly reference the listener side of this connection. If there are no other references
     * to the listener, it will eventually be garbage collected. By default Connections keep
     * a strong reference to their listeners and will keep them resident in memory as long as the
     * signal exists.
     */
    Connection makeWeak ();
}
