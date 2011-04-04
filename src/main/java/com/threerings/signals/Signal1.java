package com.threerings.signals;

public class Signal1<Type1>
{
    public void dispatch (Type1 arg1)
    {
        _signaller.dispatch(arg1);
    }

    public Connection add (Listener0 l)
    {
        return _signaller.connect(l);
    }

    public Connection add (Listener1<Type1> l)
    {
        return _signaller.connect(l);
    }

    public void remove (Object l)
    {
        _signaller.disconnect(l);
    }

    protected final Signaller _signaller = new Signaller();
}
