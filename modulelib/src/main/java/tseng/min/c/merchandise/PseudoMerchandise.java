package tseng.min.c.merchandise;

import tseng.min.c.AbstractInterface;
import tseng.min.c.AbstractSetting;

/**
 * Created by Neo on 2016/2/25.
 */
public abstract class PseudoMerchandise extends AbstractSetting implements AbstractInterface {
	private static final String TAG = PseudoMerchandise.class.getSimpleName();
	protected int mSize;
	protected int mPrice;
	protected int mCost;

	public void setSize(int size) {
		synchronized (mUpdateLock) {
			mSize = size;
		}
		mObservable.dispatchChange(false);
	}

	public abstract int getSize();

	public void setPrice(int price) {
		synchronized (mUpdateLock) {
			mPrice = price;
		}
		mObservable.dispatchChange(false);
	}

	public abstract int getPrice();

	public void setCost(int cost) {
		synchronized (mUpdateLock) {
			mCost = cost;
		}
		mObservable.dispatchChange(false);
	}

	public abstract int getCost();
}
