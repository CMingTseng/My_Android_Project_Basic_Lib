package tseng.min.c.menu;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import tseng.min.c.ContentObservableCompat;
import tseng.min.c.utils.ThrowingRunnable;

/**
 * Created by Neo on 2016-02-28 0028.
 */
public abstract class PseudoMenu {
	private static final String TAG = PseudoMenu.class.getSimpleName();
	protected ContentObservableCompat mObservable = new ContentObservableCompat();
	protected ContentObserver mObserver = new ContentObserver(null) {
		@Override
		public void onChange(boolean selfChange) {
			onChange(selfChange, null);
		}

		@Override
		public void onChange(boolean selfChange, Uri uri) {
			Log.d(TAG, "BasicMenu onChange");
			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {

				mObservable.dispatchChangeCompat(selfChange, uri);
			} else {
				mObservable.dispatchChange(selfChange);
			}
		}
	};
	protected final Object mUpdateLock = new Object();
	protected ExecutorService mNetworkTaskExecutor = Executors.newSingleThreadExecutor();

	protected abstract void dispatchChange(boolean selfChange, String property);
//    {
//        Log.d(TAG, "menu  : " + this + "_dispatchChange : " + property, new Throwable());
//        mObservable.dispatchChangeCompat(
//                selfChange, Uri.parse("content://huafu.com.tw/menu?app=")
//        );
//    }

	public abstract void registerContentObserver(ContentObserver observer);
//    {
//        try {
//            mObservable.registerObserver(observer);
//        } catch (IllegalStateException ignored) {
//        }
//    }

	public abstract void unregisterContentObserver(ContentObserver observer);

	//    {
//        try {
//            mObservable.unregisterObserver(observer);
//        } catch (IllegalStateException ignored) {
//        }
//    }

	protected int mUID;
	protected int mType;
	protected String mName;
	protected int mIconRes;
	protected String mIconPath;
	protected int mColor;
	protected int mBgColor;

	public void setType(int type) {
		synchronized (mUpdateLock) {
			mType = type;
		}
		mObservable.dispatchChange(false);
	}

	public abstract int getType();
//    {
//        synchronized (mUpdateLock) {
//            return mType;
//        }
//    }

	public void setName(String name) {
		synchronized (mUpdateLock) {
			mName = name;
		}
		mObservable.dispatchChange(false);
	}

	public abstract String getName();
//    {
//        synchronized (mUpdateLock) {
//            return mName;
//        }
//    }

	public void setUID(int uid) {
		synchronized (mUpdateLock) {
			mUID = uid;
		}
		mObservable.dispatchChange(false);
	}

	public abstract int getUID();

	public void setIconRes(int res) {
		synchronized (mUpdateLock) {
			mIconRes = res;
		}
		mObservable.dispatchChange(false);
	}

	public abstract int getIconRes();
//    {
//        synchronized (mUpdateLock) {
//            return mIconRes;
//        }
//    }

	public void setIconPath(String path) {
		synchronized (mUpdateLock) {
			mIconPath = path;
		}
		mObservable.dispatchChange(false);
	}

	public abstract String getIconPath();

	public void setColor(int color) {
		synchronized (mUpdateLock) {
			mColor = color;
		}
		mObservable.dispatchChange(false);
	}

	public abstract int getColor();
//    {
//        synchronized (mUpdateLock) {
//            return mColor;
//        }
//    }

	public void setBgColor(int color) {
		synchronized (mUpdateLock) {
			mBgColor = color;
		}
		mObservable.dispatchChange(false);
	}

	public abstract int getBgColor();
//    {
//        synchronized (mUpdateLock) {
//            return mBgColor;
//        }
//    }

	public abstract Fragment getFirstFragment();

	public abstract Fragment getSecondFragment();

	public abstract Bundle getArguments();

	public abstract Future<?>[] fetchEverything();

	public Future<?> executeNetworkTask(Runnable task) {
		return mNetworkTaskExecutor.submit(task);
	}

	public abstract Future<?> executeNetworkTaskWithGuard(ThrowingRunnable task);

	public abstract void saveToSharedPreferences();

	public abstract void restoreFromSharedPreferences();
}
