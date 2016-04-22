package tseng.min.c;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Neo on 2016/2/25.
 */
public abstract class DefaultApplication extends Application implements Thread.UncaughtExceptionHandler {
    private static final String TAG = DefaultApplication.class.getSimpleName();
    protected static DefaultApplication sContext;
    protected static Toast sToast = null;
    protected boolean isReporting = false;
    protected Handler mHandler = new Handler();
    public Runnable mStartDiscoveryRunnable;
    public Runnable mStopDiscoveryRunnable;
    protected Thread.UncaughtExceptionHandler mPreviousHandler;
    protected String RECIPIENT = BuildConfig.AUTHOR_EMAIL;

    public static Context getContext() {
        return sContext.getApplicationContext();
    }

    public static Application getApplication() {
        return sContext;
    }

    public static Toast makeToast(int duration, String format, Object... objects) {
        if (sToast != null) {
            sToast.cancel();
        }
        sToast = Toast.makeText(sContext, String.format(format, objects), duration);
        return sToast;
    }

    public static Toast makeToast(int duration, int resId, Object... objects) {
        if (sToast != null) {
            sToast.cancel();
        }
        sToast = Toast.makeText(sContext, sContext.getString(resId, objects), duration);
        return sToast;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.UncaughtExceptionHandler previousHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (!(previousHandler instanceof DefaultApplication)) {
            mPreviousHandler = previousHandler;
            Thread.setDefaultUncaughtExceptionHandler(this);
        }
        sContext = this;

        NetworkChangeReceiver.registerConnectivityObserver(new NetworkChangeReceiver.ConnectivityObserver() {
            @Override
            public void onConnected(NetworkInfo info) {
                Log.d(TAG, "NetworkChangeReceiver startDiscovery() ");
                startDiscovery();
            }

            @Override
            public void onDisconnected() {
                stopDiscovery();
            }
        });

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null) {
            Log.d(TAG, " ConnectivityManager startDiscovery() ");
            startDiscovery();
        }
    }

    public static void startDiscovery() {
        Log.d(TAG, "Run Start Discovery");
        sContext.mHandler.removeCallbacks(sContext.mStartDiscoveryRunnable);
        sContext.mHandler.removeCallbacks(sContext.mStopDiscoveryRunnable);
        sContext.mHandler.post(sContext.mStartDiscoveryRunnable);
    }

    public static void stopDiscovery() {
//        Log.d(TAG, "Run Stop Discovery");
        sContext.mHandler.removeCallbacks(sContext.mStartDiscoveryRunnable);
        sContext.mHandler.removeCallbacks(sContext.mStopDiscoveryRunnable);
        sContext.mHandler.postDelayed(sContext.mStopDiscoveryRunnable,
                Settings.isDebugNetworkChangeReceiver() ? 500 : 5000);
    }

    @Override
    public void onTerminate() {
        sContext = null;
        mHandler.removeCallbacks(mStartDiscoveryRunnable);
        mHandler.removeCallbacks(mStopDiscoveryRunnable);
        super.onTerminate();
    }

    @Override
    public void uncaughtException(final Thread thread, final Throwable ex) {
        if (isReporting) {
            mPreviousHandler.uncaughtException(thread, ex);
            return;
        }
        isReporting = true;
        try {
            final StringBuilder report = new StringBuilder();
            report.append("------------ Version ----------\n");
            report.append("Package: ").append(BuildConfig.APPLICATION_ID).append("\n");
            report.append("Version Name: ").append(BuildConfig.VERSION_NAME).append("\n");
            report.append("Version Code: ").append(BuildConfig.VERSION_CODE).append("\n");
            report.append("Build Type: ").append(BuildConfig.BUILD_TYPE).append("\n");
            report.append("Flavor: ").append(BuildConfig.FLAVOR).append("\n");
            StringWriter strWriter = new StringWriter();
            PrintWriter writer = new PrintWriter(strWriter);
            ex.printStackTrace(writer);
            writer.flush();

            report.append("---------- Exception ----------\n");
            report.append(strWriter.toString());

            report.append("------------ Device -----------\n");
            report.append("Brand: ").append(Build.BRAND).append("\n");
            report.append("Device: ").append(Build.DEVICE).append("\n");
            report.append("Model: ").append(Build.MODEL).append("\n");
            report.append("Id: ").append(Build.ID).append("\n");
            report.append("Product: ").append(Build.PRODUCT).append("\n");

            report.append("----------- Firmware ----------\n");
            report.append("SDK: ").append(Build.VERSION.SDK_INT).append("\n");
            report.append("Release: ").append(Build.VERSION.RELEASE).append("\n");
            report.append("Incremental: ").append(Build.VERSION.INCREMENTAL).append("\n");

            report.append("------ Extra Information ------\n");

            final Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TITLE, "Send crash report");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.fromParts("mailto", RECIPIENT, null));
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, RECIPIENT.split(","));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Crash Report: " + BuildConfig.APPLICATION_ID + " " + BuildConfig.VERSION_NAME);
            intent.putExtra(Intent.EXTRA_TEXT, report.toString());
            startActivity(intent);
            // If you don't kill the VM here the app goes into limbo
            mPreviousHandler.uncaughtException(thread, ex);
        } catch (Throwable ex2) {
            Log.e(TAG, "uncaughtException(): Error while sending error e-mail", ex2);
        }
    }

    public static class Settings {
        public static boolean isDebugNetworkChangeReceiver() {
            return sContext
                    .getSharedPreferences(C.SHAREDPREFERENCES_DEBUG, Context.MODE_MULTI_PROCESS)
                    .getBoolean(C.PREFS_DEBUG_NETWORK_CHANGE_RECEIVER, false);
        }

        public static void setDebugNetworkChangeReceiver(boolean enabled) {
            sContext.getSharedPreferences(C.SHAREDPREFERENCES_DEBUG, MODE_MULTI_PROCESS)
                    .edit()
                    .putBoolean(C.PREFS_DEBUG_NETWORK_CHANGE_RECEIVER, enabled)
                    .apply();
        }
    }
}


