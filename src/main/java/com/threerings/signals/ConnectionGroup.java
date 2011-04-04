package com.threerings.signals;

import java.lang.ref.WeakReference;
import java.util.Map;

import com.google.common.collect.MapMaker;

/**
 * Collects connections to allow mass operations on them. The group holds a {@link WeakReference}
 * to a Connection, so if nothing else is holding onto the connection, it'll be garbage collected.
 */
public class ConnectionGroup
{
    /**
     * Disconnects all connections in the group.
     */
    public void disconnect ()
    {
        for (Connection c : _connections.keySet()) {
            c.disconnect();
        }
        _connections.clear();
    }

    /** Adds a connection to the group. */
    public Connection add (Connection c)
    {
        _connections.put(c, DUMMY_VALUE);
        return c;
    }

    /** Removes a connection from the group while leaving its connected status unchanged. */
    public void remove (Connection c)
    {
        _connections.remove(c);
    }

    protected Map<Connection, Object> _connections = new MapMaker().weakKeys().makeMap();

    // ConcurrentHashMap can't store null, so we use this for all of our values
    protected static final Object DUMMY_VALUE = new Object();
}
