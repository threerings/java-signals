package com.threerings.signals;

public class Signal0
{
    public void dispatch ()
    {
        _signaller.dispatch();
    }

    public Connection add (Listener0 l)
    {
        return _signaller.connect(l);
    }

    public void remove (Object l)
    {
        _signaller.disconnect(l);
    }

    protected final Signaller _signaller = new Signaller();
}
