//
// java-signals
// Copyright (c) 2011 Three Rings Design, Inc.
// http://github.com/threerings/java-signals
//
// Permission is hereby granted, free of charge, to any person
// obtaining a copy of this software and associated documentation
// files (the "Software"), to deal in the Software without
// restriction, including without limitation the rights to use,
// copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the
// Software is furnished to do so, subject to the following
// conditions:
//
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
// OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
// NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
// HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
// WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
// FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
// OTHER DEALINGS IN THE SOFTWARE.

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
