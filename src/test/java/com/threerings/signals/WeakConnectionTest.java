//
// java-signals - Simple, type-safe event dispatching
// Copyright (c) 2011, Three Rings Design, Inc. - All rights reserved.
// https://github.com/threerings/java-signals/blob/master/LICENSE

package com.threerings.signals;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;

public class WeakConnectionTest
{
    @Test
    public void makeWeak ()
    {
        final AtomicInteger calls = new AtomicInteger();
        Signal0 sig = Signals.newSignal0();
        Listener0 listener = new Listener0() {
            public void apply () {
                calls.incrementAndGet();
            }
        };
        Connection conn = sig.connect(listener);
        sig.dispatch();
        assertEquals(1, calls.get());
        conn.makeWeak();
        System.gc();
        sig.dispatch();
        assertEquals(2, calls.get());
        listener = null;
        System.gc();
        sig.dispatch();
        assertEquals(2, calls.get());
    }
}

