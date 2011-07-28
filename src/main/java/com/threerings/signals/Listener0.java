//
// java-signals - Simple, type-safe event dispatching
// Copyright (c) 2011, Three Rings Design, Inc. - All rights reserved.
// https://github.com/threerings/java-signals/blob/master/LICENSE

package com.threerings.signals;

public interface Listener0 extends Listener
{
    /** Called when any signals this listener is connected to dispatch. */
    void apply ();
}
