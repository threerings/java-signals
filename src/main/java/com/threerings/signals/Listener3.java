package com.threerings.signals;

public interface Listener3<Type1, Type2, Type3>
{
    void apply (Type1 arg1, Type2 arg2, Type3 arg3);
}
