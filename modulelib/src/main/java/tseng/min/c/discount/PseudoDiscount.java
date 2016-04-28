package tseng.min.c.discount;


import tseng.min.c.AbstractInterface;
import tseng.min.c.AbstractSetting;

/**
 * Created by Neo on 2016/2/25.
 */
public abstract class PseudoDiscount extends AbstractSetting implements AbstractInterface {
	private static final String TAG = PseudoDiscount.class.getSimpleName();
	protected int mSubType;
	protected boolean mEffective;
	protected boolean mForever;

	public void setEffective(boolean effective) {
		synchronized (mUpdateLock) {
			mEffective = effective;
		}
		mObservable.dispatchChange(false);
	}

	public abstract boolean isEffective();

	public void setForever(boolean forever) {
		synchronized (mUpdateLock) {
			mForever = forever;
		}
		mObservable.dispatchChange(false);
	}

	public abstract boolean isForever();

	public void setSubType(int subtype) {
		synchronized (mUpdateLock) {
			mSubType = subtype;
		}
		mObservable.dispatchChange(false);
	}

	public abstract int getSubType();

}
