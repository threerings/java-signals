//
// java-signals - Simple, type-safe event dispatching
// Copyright (c) 2011, Three Rings Design, Inc. - All rights reserved.
// https://github.com/threerings/java-signals/blob/master/LICENSE

package com.threerings.signals;

/** Creates signals. */
public class Signals
{
    /** The priority at which a listener is added if a priority isn't given. */
    public static final int DEFAULT_PRIORITY = 0;

    /** Creates a signal that dispatches no arguments. */
    public static Signal0 newSignal0 ()
    {
        return new Signal0();
    }

    /** Creates a signal that dispatches one argument. */
    public static <A> Signal1<A> newSignal1 ()
    {
        return new Signal1<A>();
    }

    /** Creates a signal that dispatches two arguments. */
    public static <A, B> Signal2<A, B> newSignal2 ()
    {
        return new Signal2<A, B>();
    }

    /** Creates a signal that dispatches three arguments. */
    public static <A, B, C> Signal3<A, B, C> newSignal3 ()
    {
        return new Signal3<A, B, C>();
    }
}
