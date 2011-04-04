package com.threerings.signals;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class SignalTest
{
    @Test
    public void dispatchMultipleTime ()
    {
        final AtomicInteger calls = new AtomicInteger();
        ConnectionGroup group = new ConnectionGroup();
        Connection conn = group.add(_stringInt.add(new Listener3<String, Integer, Integer> () {
            public void apply (String str, Integer i, Integer other) {
                System.out.println("Listener3 - Str: " + str + " i: " + i + " other: " + other);
                calls.incrementAndGet();
            }
        }));

        group.add(_stringInt.add(new Listener1<String> () {
            public void apply (String str) {
                System.out.println("Listener1 - Str: " + str);
                _stringInt.remove(this);
                calls.incrementAndGet();
            }
        }));

        group.add(_stringInt.add(new Listener0 () {
            public void apply () {
                System.out.println("Listener0");
                calls.incrementAndGet();
            }
        }).once());

        group.add(_stringInt.add(new Listener0 () {
            public void apply () {
                System.out.println("Grouped listener");
                calls.incrementAndGet();
            }}));



        _stringInt.dispatch("MyString", 5, 6);
        _stringInt.dispatch("AnotherString", 999, 7);
        conn.disconnect();
        conn.disconnect();
        _stringInt.dispatch("My, could it be another string?", 333, 8);
        group.disconnect();
        _stringInt.dispatch("Last one", 1, 2);
        assertEquals(7, calls.get());
    }

    protected Signal3<String, Integer, Integer> _stringInt = Signals.newSignal3();
}
