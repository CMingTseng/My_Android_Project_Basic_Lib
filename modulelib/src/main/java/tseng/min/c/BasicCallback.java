package tseng.min.c;

/**
 * Created by Neo on 2015/12/10.
 */
public interface BasicCallback {
    static final String TAG = BasicCallback.class.getSimpleName();

    void removeCallbacks(Runnable runnable);

    void post(Runnable runnable);

    void postDelayed(Runnable runnable, int delayMillis);
}