package com.threerings.signals;

import java.util.ArrayList;
import com.threerings.signals.Signaller.ConnectionImpl;

class SnapshotArrayList extends ArrayList<ConnectionImpl<?>>
{
    public ConnectionImpl<?>[] snapshot()
    {
        if (modCount != _snapshotModCount) {
            _snapshotModCount = modCount;
            _snapshot = toArray(new ConnectionImpl<?>[size()]);
        }
        return _snapshot;
    }

    protected ConnectionImpl<?>[] _snapshot;
    protected int _snapshotModCount = modCount - 1;
}
