package com.threerings.signals;

import java.util.List;

import com.google.common.collect.Lists;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SignalPriorityTest
{
    protected class NotingListener implements Listener0 {
        public void apply () {
            _callOrder.add(this);
        }
    }

    @Test
    public void callInPriorityOrder ()
    {
        NotingListener defPriAddedFirst = new NotingListener();
        Connection firstConn = _sig.connect(defPriAddedFirst);
        checkOrdering(defPriAddedFirst);

        NotingListener defPriAddedSecond = new NotingListener();
        _sig.connect(defPriAddedSecond);
        checkOrdering(defPriAddedFirst, defPriAddedSecond);

        NotingListener medPriAddedThird = new NotingListener();
        _sig.connect(medPriAddedThird, Signals.DEFAULT_PRIORITY + 1);
        checkOrdering(medPriAddedThird, defPriAddedFirst, defPriAddedSecond);

        NotingListener highPriAddedFourth = new NotingListener();
        _sig.connect(highPriAddedFourth, Signals.DEFAULT_PRIORITY + 2);
        checkOrdering(highPriAddedFourth, medPriAddedThird, defPriAddedFirst, defPriAddedSecond);

        NotingListener lowPriAddedFifth = new NotingListener();
        _sig.connect(lowPriAddedFifth, Signals.DEFAULT_PRIORITY - 1);
        checkOrdering(highPriAddedFourth, medPriAddedThird, defPriAddedFirst, defPriAddedSecond,
            lowPriAddedFifth);

        NotingListener defPriAddedLast = new NotingListener();
        _sig.connect(defPriAddedLast);
        checkOrdering(highPriAddedFourth, medPriAddedThird, defPriAddedFirst, defPriAddedSecond,
            defPriAddedLast, lowPriAddedFifth);

        // Check that the sort is stable
        _sig.disconnect(defPriAddedSecond);
        checkOrdering(highPriAddedFourth, medPriAddedThird, defPriAddedFirst, defPriAddedLast,
            lowPriAddedFifth);

        // Reconnecting a listener removes its previous connection and adds a new one
        firstConn = _sig.connect(defPriAddedFirst);
        checkOrdering(highPriAddedFourth, medPriAddedThird, defPriAddedLast, defPriAddedFirst,
            lowPriAddedFifth);

        firstConn.disconnect();
        checkOrdering(highPriAddedFourth, medPriAddedThird, defPriAddedLast, lowPriAddedFifth);
    }

    protected void checkOrdering (Listener0...listeners)
    {
        _sig.dispatch();
        assertEquals(Lists.newArrayList(listeners), _callOrder);
        _callOrder.clear();
    }

    protected final Signal0 _sig = Signals.newSignal0();
    protected final List<Listener0> _callOrder = Lists.newArrayList();
}
