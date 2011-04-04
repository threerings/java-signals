package com.threerings.signals;

public class Signal2<Type1, Type2>
{
    public void dispatch (Type1 arg1, Type2 arg2)
    {
        _signaller.dispatch(arg1, arg2);
    }

    public Connection add (Listener0 l)
    {
        return _signaller.connect(l);
    }

    public Connection add (Listener1<Type1> l)
    {
        return _signaller.connect(l);
    }

    public Connection add (Listener2<Type1, Type2> l)
    {
        return _signaller.connect(l);
    }

    public void remove (Object l)
    {
        _signaller.disconnect(l);
    }

    protected final Signaller _signaller = new Signaller();
}
