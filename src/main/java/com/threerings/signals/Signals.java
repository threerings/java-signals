package com.threerings.signals;

public class Signals
{
    public static Signal0 newSignal0 ()
    {
        return new Signal0();
    }

    public static <Type1> Signal1<Type1> newSignal1 ()
    {
        return new Signal1<Type1>();
    }

    public static <Type1, Type2> Signal2<Type1, Type2> newSignal2 ()
    {
        return new Signal2<Type1, Type2>();
    }

    public static <Type1, Type2, Type3> Signal3<Type1, Type2, Type3> newSignal3 ()
    {
        return new Signal3<Type1, Type2, Type3>();
    }
}
