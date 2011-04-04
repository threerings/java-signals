package com.threerings.signals;

public interface Listener2<Type1, Type2>
{
    void apply (Type1 arg1, Type2 arg2);
}
