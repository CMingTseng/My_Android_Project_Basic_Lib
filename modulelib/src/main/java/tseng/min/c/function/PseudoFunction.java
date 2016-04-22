package tseng.min.c.function;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import tseng.min.c.AbstractInterface;
import tseng.min.c.ContentObservableCompat;
import tseng.min.c.utils.ThrowingRunnable;

/**
 * Created by Neo on 2016/2/25.
 */
public abstract class PseudoFunction implements AbstractInterface {
	private static final String TAG = PseudoFunction.class.getSimpleName();
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
			Log.d(TAG, "BasicFunction onChange");
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

//    public abstract void dispatchChange(boolean selfChange, String property);
//        Log.d(TAG, "Function  : " + this + "_dispatchChange : " + property, new Throwable());
//        mObservable.dispatchChangeCompat(
//                selfChange, Uri.parse("content://huafu.com.tw/function?app=")
//        );
//    }

//    public abstract void registerContentObserver(ContentObserver observer);
//    {
//        try {
//            mObservable.registerObserver(observer);
//        } catch (IllegalStateException ignored) {
//        }
//    }

//    public abstract void unregisterContentObserver(ContentObserver observer);
//    {
//        try {
//            mObservable.unregisterObserver(observer);
//        } catch (IllegalStateException ignored) {
//        }
//    }

	//    @Expose(serialize = false, deserialize = false)
	protected String mName;
	protected int mUID;
	protected int mIconRes;
	protected String mIconPath;
	protected int mNavigationIconRes;
	protected int mColor;
	protected int mBgColor;
	protected int mFirstLayoutRes;
	protected int mSecondLayoutRes;
	//FIXME  UUID use random ? timestamp ?
	protected UUID mUUID;
	protected int mType;

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

	public abstract void setUUID(UUID uuid);

	public abstract UUID getUUID();
	//    {
//        synchronized (mUpdateLock) {
//            Log.d(TAG, "Show UUID :  " + mUUID);
//            return mUUID;
//        }
//    }

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

	public void setNavigationIconRes(int res) {
		synchronized (mUpdateLock) {
			mNavigationIconRes = res;
		}
		mObservable.dispatchChange(false);
	}

	public abstract int getNavigationIconRes();
//    {
//        synchronized (mUpdateLock) {
//            return mIconRes;
//        }
//    }

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

	//    public void setFirstLayoutRes(@LayoutRes int resource) {
	public void setFirstLayoutRes(int resource) {
		synchronized (mUpdateLock) {
			mFirstLayoutRes = resource;
		}
		mObservable.dispatchChange(false);
	}

	public abstract int getFirstLayoutRes();
//    {
//        synchronized (mUpdateLock) {
//            return mFirstLayoutRes;
//        }
//    }

	//    public void setSecondLayoutRes(@LayoutRes int resource) {
	public void setSecondLayoutRes(int resource) {
		synchronized (mUpdateLock) {
			mSecondLayoutRes = resource;
		}
		mObservable.dispatchChange(false);
	}

	public abstract int getSecondLayoutRes();
//    {
//        synchronized (mUpdateLock) {
//            return mFirstLayoutRes;
//        }
//    }

	public abstract Fragment getFirstFragment();

	public abstract Fragment getSecondFragment();

	public abstract Bundle getArguments();
//    {
//        final Bundle arguments = new Bundle();
//        arguments.putString(C.ARGUMENT_FUNCTION_UUID, getUUID().toString());
//        return arguments;
//    }

	public abstract Future<?>[] fetchEverything();

	public Future<?> executeNetworkTask(Runnable task) {
		return mNetworkTaskExecutor.submit(task);
	}

	public abstract Future<?> executeNetworkTaskWithGuard(ThrowingRunnable task);

	public abstract void saveToSharedPreferences();

	public abstract void restoreFromSharedPreferences();
}
