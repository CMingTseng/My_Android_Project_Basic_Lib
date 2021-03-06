package tseng.min.c;


import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import tseng.min.c.utils.ThrowingRunnable;

/**
 * Created by Neo on 2016/2/25.
 */
public abstract class AbstractSetting {
	private static final String TAG = AbstractSetting.class.getSimpleName();
	//    @Expose(serialize = false, deserialize = false)
	protected ContentObservableCompat mObservable = new ContentObservableCompat();
	//    @Expose(serialize = false, deserialize = false)
	protected ContentObserver mObserver = new ContentObserver(null) {
		@Override
		public void onChange(boolean selfChange) {
			onChange(selfChange, null);
		}

		@Override
		public void onChange(boolean selfChange, Uri uri) {
			Log.d(TAG, "BasicOrder onChange");
			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
				mObservable.dispatchChangeCompat(selfChange, uri);
			} else {
				mObservable.dispatchChange(selfChange);
			}
		}
	};
	//    @Expose(serialize = false, deserialize = false)
	protected final Object mUpdateLock = new Object();
	//    @Expose(serialize = false, deserialize = false)
	protected ExecutorService mNetworkTaskExecutor = Executors.newSingleThreadExecutor();//FIXME Do Funtion from Account profile

	//    @Expose(serialize = false, deserialize = false)
	protected String mName;
	protected int mIconRes;
	protected String mIconPath;
	protected int mColor;
	protected int mBgColor;
	protected int mFirstLayoutRes;
	protected int mSecondLayoutRes;
	//FIXME  UUID use random ? timestamp ?
	protected UUID mUUID;
	protected int mType;
	protected boolean mActivity;
	protected String mAnnotation;

	public void setName(String name) {
		synchronized (mUpdateLock) {
			mName = name;
		}
		mObservable.dispatchChange(false);
	}

	public abstract String getName();

	public void setAnnotation(String annotation) {
		synchronized (mUpdateLock) {
			mAnnotation = annotation;
		}
		mObservable.dispatchChange(false);
	}

	public abstract String getAnnotation();

	public void setIconRes(int res) {
		synchronized (mUpdateLock) {
			mIconRes = res;
		}
		mObservable.dispatchChange(false);
	}

	public abstract int getIconRes();

	public void setIconPath(String path) {
		synchronized (mUpdateLock) {
			mIconPath = path;
		}
		mObservable.dispatchChange(false);
	}

	public abstract String getIconPath();

	public void setisActivity(boolean activity) {
		synchronized (mUpdateLock) {
			mActivity = activity;
		}
		mObservable.dispatchChange(false);
	}

	public abstract boolean getisActivity();

	public abstract void setUUID(UUID uuid);

	public abstract UUID getUUID();

	public void setType(int type) {
		synchronized (mUpdateLock) {
			mType = type;
		}
		mObservable.dispatchChange(false);
	}

	public abstract int getType();

	public void setColor(int color) {
		synchronized (mUpdateLock) {
			mColor = color;
		}
		mObservable.dispatchChange(false);
	}

	public abstract int getColor();

	public void setBgColor(int color) {
		synchronized (mUpdateLock) {
			mBgColor = color;
		}
		mObservable.dispatchChange(false);
	}

	public abstract int getBgColor();

	//    public void setFirstLayoutRes(@LayoutRes int resource) {
	public void setFirstLayoutRes(int resource) {
		synchronized (mUpdateLock) {
			mFirstLayoutRes = resource;
		}
		mObservable.dispatchChange(false);
	}

	public abstract int getFirstLayoutRes();

	//    public void setSecondLayoutRes(@LayoutRes int resource) {
	public void setSecondLayoutRes(int resource) {
		synchronized (mUpdateLock) {
			mSecondLayoutRes = resource;
		}
		mObservable.dispatchChange(false);
	}

	public abstract int getSecondLayoutRes();

	public abstract Bundle getArguments();

	public abstract Future<?>[] fetchEverything();

	public Future<?> executeNetworkTask(Runnable task) {
		return mNetworkTaskExecutor.submit(task);
	}

	public abstract Future<?> executeNetworkTaskWithGuard(ThrowingRunnable task);

	public abstract void saveToSharedPreferences();

	public abstract void restoreFromSharedPreferences();
}
