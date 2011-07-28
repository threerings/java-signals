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

import static org.junit.Assert.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;

public class SignalTest
{
    @Test
    public void disconnectDuringDispatch ()
    {
        final AtomicInteger calls = new AtomicInteger();
        final Signal0 sig = Signals.newSignal0();
        final Connection firstConn = sig.connect(new Listener0 () {
            public void apply () {
                calls.incrementAndGet();
            }});
        final Listener0 thirdLis = new Listener0 () {
            public void apply () {
                calls.incrementAndGet();
            }};
        sig.connect(new Listener0() {
            public void apply () {
                firstConn.disconnect();
                sig.disconnect(thirdLis);
            }});
        sig.connect(thirdLis);
        sig.dispatch();
        assertEquals(1, calls.get());
    }

    @Test
    public void dispatchMultipleTimes ()
    {
        final AtomicInteger calls = new AtomicInteger();
        ConnectionGroup group = new ConnectionGroup();
        Connection conn = group.add(_stringInt.connect(new Listener3<String, Integer, Integer> () {
            public void apply (String str, Integer i, Integer other) {
                assertEquals(_first, str);
                assertEquals(_second, i);
                assertEquals(_third, other);
                calls.incrementAndGet();
            }
        }));

        group.add(_stringInt.connect(new Listener1<String> () {
            public void apply (String str) {
                assertEquals(_first, str);
                _stringInt.disconnect(this);
                calls.incrementAndGet();
            }
        }));

        group.add(_stringInt.connect(new Listener0 () {
            public void apply () {
                calls.incrementAndGet();
            }
        }).once());

        group.add(_stringInt.connect(new Listener0 () {
            public void apply () {
                calls.incrementAndGet();
            }}));

        dispatch("MyString", 5, 6);
        dispatch("AnotherString", 999, 7);
        conn.disconnect();
        conn.disconnect();
        dispatch("My, could it be another string?", 333, 8);
        group.disconnect();
        dispatch("Last one", 1, 2);
        assertEquals(7, calls.get());
    }

    protected void dispatch (String first, int second, int third)
    {
        _first = first;
        _second = second;
        _third = third;
        _stringInt.dispatch(_first, second, third);
    }

    protected String _first;
    protected Integer _second;
    protected Integer _third;
    protected Signal3<String, Integer, Integer> _stringInt = Signals.newSignal3();
}
