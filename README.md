This is a Java reinterpretation of [Robert Penner's as3-signals](https://github.com/robertpenner/as3-signals). It keeps the simple event declaration and dispatching, while adding a dash of type-safety afforded by Java's generics.

Usage
-----
Using a signal is a 3 step process. First, create a signal on the class that will be firing
events:

```java
public class Dispatcher {
    /** Dispatches the old value and the new value after it changes. */
    public final Signal2<String, String> onChanged = Signals.newSignal2();
}
```

Then, add listeners to that signal as appropriate:

```java
public class InterestedParty {
    public void connectToDispatcher (Dispatcher d)
    {
        d.onChanged.add(new Listener1<String>() {
            public void apply (String oldValue) {
                System.out.println("Dispatcher changed from " + oldValue);
            }});
    }
}
```

or

```java
d.onChanged.add(new Listener0() {
    public void apply () {
        System.out.println("I'll only be called for one dispatch!");
    }
    }).once();
```

if the listener only wants to receive the next dispatch.

Finally, dispatch that signal when the event occurs:

```java
onChanged.dispatch("oldValue", "newValue");
```

That'll call apply on all the added listeners.

If a listener is no longer interested in a signal, it can be removed from the signal, or
`Connection.disconnect` can be called.

Connecting, disconnecting, and dispatching is thread-safe. After `Signal.disconnect(Listener)` or
`Connection.disconnect` returns, a listener is guaranteed to never receive another call to its apply
method.

Getting
-------

The 0.3 release of signals is available via Maven Central. Just add this to your POM:

```
<dependency>
  <groupId>com.threerings</groupId>
  <artifactId>signals</artifactId>
  <version>0.3</version>
</dependency>
```

You can also download it directly from
[Maven Central](http://repo2.maven.org/maven2/com/threerings/signals/0.3/signals-0.3.jar).
