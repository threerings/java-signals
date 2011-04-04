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
        final Connection firstConn = sig.add(new Listener0 () {
            public void apply () {
                calls.incrementAndGet();
            }});
        final Listener0 thirdLis = new Listener0 () {
            public void apply () {
                calls.incrementAndGet();
            }};
        sig.add(new Listener0() {
            public void apply () {
                firstConn.disconnect();
                sig.remove(thirdLis);
            }});
        sig.add(thirdLis);
        sig.dispatch();
        assertEquals(1, calls.get());
    }

    @Test
    public void dispatchMultipleTimes ()
    {
        final AtomicInteger calls = new AtomicInteger();
        ConnectionGroup group = new ConnectionGroup();
        Connection conn = group.add(_stringInt.add(new Listener3<String, Integer, Integer> () {
            public void apply (String str, Integer i, Integer other) {
                assertEquals(_first, str);
                assertEquals(_second, i);
                assertEquals(_third, other);
                calls.incrementAndGet();
            }
        }));

        group.add(_stringInt.add(new Listener1<String> () {
            public void apply (String str) {
                assertEquals(_first, str);
                _stringInt.remove(this);
                calls.incrementAndGet();
            }
        }));

        group.add(_stringInt.add(new Listener0 () {
            public void apply () {
                calls.incrementAndGet();
            }
        }).once());

        group.add(_stringInt.add(new Listener0 () {
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
