package tseng.min.c.table;


import tseng.min.c.AbstractInterface;
import tseng.min.c.AbstractSetting;

/**
 * Created by Neo on 2016/2/25.
 */
public abstract class PseudoTable extends AbstractSetting implements AbstractInterface {
	private static final String TAG = PseudoTable.class.getSimpleName();
	protected int mSize;
	protected int mlimitPeople;
	protected int mPeople;
	protected double mPrice;
	protected double mCost;
	protected int mlimitTime;
	protected int mTime;

	public void setSize(int size) {
		synchronized (mUpdateLock) {
			mSize = size;
		}
		mObservable.dispatchChange(false);
	}

	public abstract int getSize();

	public void setlimitPeople(int limitpeople) {
		synchronized (mUpdateLock) {
			mlimitPeople = limitpeople;
		}
		mObservable.dispatchChange(false);
	}

	public abstract int getlimitPeople();

	public void setPeople(int people) {
		synchronized (mUpdateLock) {
			mPeople = people;
		}
		mObservable.dispatchChange(false);
	}

	public abstract int getPeople();

	public void setlimitTime(int limittime) {
		synchronized (mUpdateLock) {
			mlimitTime = limittime;
		}
		mObservable.dispatchChange(false);
	}

	public abstract int getlimitTime();

	public void setTime(int time) {
		synchronized (mUpdateLock) {
			mTime = time;
		}
		mObservable.dispatchChange(false);
	}

	public abstract int getTime();

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
