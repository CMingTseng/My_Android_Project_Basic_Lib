package tseng.min.c.store;


import tseng.min.c.AbstractInterface;
import tseng.min.c.AbstractSetting;

/**
 * Created by Neo on 2016/2/25.
 */
public abstract class PseudoStoreSubItem extends AbstractSetting implements AbstractInterface {
	private static final String TAG = PseudoStoreSubItem.class.getSimpleName();
	protected String mCompanyName;
	protected String mStoreAddress;
	protected String mStorePhone;
	protected String mBusinessRegistrationNumber;
	protected String mPrincipal;

	public void setCompanyName(String company) {
		synchronized (mUpdateLock) {
			mCompanyName = company;
		}
		mObservable.dispatchChange(false);
	}

	public abstract String getCompanyName();

	public void setStoreAddress(String storeAddress) {
		synchronized (mUpdateLock) {
			mStoreAddress = storeAddress;
		}
		mObservable.dispatchChange(false);
	}

	public abstract String getStoreAddress();

	public void setStorePhone(String storePhone) {
		synchronized (mUpdateLock) {
			mStorePhone = storePhone;
		}
		mObservable.dispatchChange(false);
	}

	public abstract String getStorePhone();

	public void setBusinessRegistrationNumber(String number) {
		synchronized (mUpdateLock) {
			mBusinessRegistrationNumber = number;
		}
		mObservable.dispatchChange(false);
	}

	public abstract String getBusinessRegistrationNumber();

	public void setPrincipal(String principal) {
		synchronized (mUpdateLock) {
			mPrincipal = principal;
		}
		mObservable.dispatchChange(false);
	}

	public abstract String getPrincipal();
}
