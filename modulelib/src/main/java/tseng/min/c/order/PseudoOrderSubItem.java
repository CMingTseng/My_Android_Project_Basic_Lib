package tseng.min.c.order;


import tseng.min.c.AbstractInterface;
import tseng.min.c.AbstractSetting;

/**
 * Created by Neo on 2016/2/25.
 */
public abstract class PseudoOrderSubItem extends AbstractSetting implements AbstractInterface {
	private static final String TAG = PseudoOrderSubItem.class.getSimpleName();
	protected int mSize;
	protected double mPrice;
	protected double mCost;

	public void setSize(int size) {
		synchronized (mUpdateLock) {
			mSize = size;
		}
		mObservable.dispatchChange(false);
	}

	public abstract int getSize();

	public void setPrice(double price) {
		synchronized (mUpdateLock) {
			mPrice = price;
		}
		mObservable.dispatchChange(false);
	}

	public abstract double getPrice();

	public void setCost(double cost) {
		synchronized (mUpdateLock) {
			mCost = cost;
		}
		mObservable.dispatchChange(false);
	}

	public abstract double getCost();
}
