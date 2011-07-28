//
// java-signals
// Copyright (c) 2011 Three Rings Design, Inc.
// http://github.com/threerings/java-signals
//
// Permission is hereby granted, free of charge, to any person
// obtaining a copy of this software and associated documentation
// files (the "Software"), to deal in the Software without
// restriction, including without limitation the rights to use,
// copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the
// Software is furnished to do so, subject to the following
// conditions:
//
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
// OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
// NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
// HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
// WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
// FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
// OTHER DEALINGS IN THE SOFTWARE.

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
