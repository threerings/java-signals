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
