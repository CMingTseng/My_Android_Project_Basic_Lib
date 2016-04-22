package tseng.min.c;

import android.database.ContentObserver;

/**
 * Created by Neo on 2016-02-29 0029.
 */
public interface AbstractInterface {
    void dispatchChange(boolean selfChange, String property);

    void registerContentObserver(ContentObserver observer);

    void unregisterContentObserver(ContentObserver observer);
}
