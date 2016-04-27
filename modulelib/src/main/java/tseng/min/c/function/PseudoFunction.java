package tseng.min.c.function;

import android.support.v4.app.Fragment;

import tseng.min.c.AbstractInterface;
import tseng.min.c.AbstractSetting;

/**
 * Created by Neo on 2016/2/25.
 */
public abstract class PseudoFunction extends AbstractSetting implements AbstractInterface {
	private static final String TAG = PseudoFunction.class.getSimpleName();
	protected int mUID;
	protected int mNavigationIconRes;

	public void setUID(int uid) {
		synchronized (mUpdateLock) {
			mUID = uid;
		}
		mObservable.dispatchChange(false);
	}

	public abstract int getUID();


	public void setNavigationIconRes(int res) {
		synchronized (mUpdateLock) {
			mNavigationIconRes = res;
		}
		mObservable.dispatchChange(false);
	}

	public abstract int getNavigationIconRes();

	public abstract Fragment getFirstFragment();

	public abstract Fragment getSecondFragment();

//    public abstract void dispatchChange(boolean selfChange, String property);
//        Log.d(TAG, "Function  : " + this + "_dispatchChange : " + property, new Throwable());
//        mObservable.dispatchChangeCompat(
//                selfChange, Uri.parse("content://c.min.tseng/function?app=")
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
}
