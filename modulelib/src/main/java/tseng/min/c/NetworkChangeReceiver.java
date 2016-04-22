package tseng.min.c;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Neo on 2016/2/25.
 */
public abstract class NetworkChangeReceiver extends BroadcastReceiver {
    @SuppressWarnings("UnusedDeclaration")
    private static final String TAG = NetworkChangeReceiver.class.getSimpleName();
    private static final ArrayList<ConnectivityObserver> sObservers = new ArrayList<>();

    public synchronized static void registerConnectivityObserver(ConnectivityObserver observer) {
        if (!sObservers.contains(observer))
            sObservers.add(observer);
    }

    public synchronized static boolean unregisterConnectivityObserver(ConnectivityObserver observer) {
        return sObservers.remove(observer);
    }

    public synchronized static void unregisterAllConnectivityObserver() {
        sObservers.clear();
        sObservers.trimToSize();
    }

    protected void dispatchConnectivityState(NetworkInfo info) {
        if (info == null || !info.isConnected())
            for (int i = sObservers.size() - 1; i >= 0; --i) {
                sObservers.get(i).onDisconnected();
            }
        else
            for (int i = sObservers.size() - 1; i >= 0; --i) {
                sObservers.get(i).onConnected(info);
            }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive()");
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        dispatchConnectivityState(cm.getActiveNetworkInfo());
    }

    public interface ConnectivityObserver {
        public void onConnected(NetworkInfo info);

        public void onDisconnected();
    }
}
