package tseng.min.c.employee;


import tseng.min.c.AbstractInterface;
import tseng.min.c.AbstractSetting;

/**
 * Created by Neo on 2016/2/25.
 */
public abstract class PseudoEmployee extends AbstractSetting implements AbstractInterface {
	private static final String TAG = PseudoEmployee.class.getSimpleName();
	protected String mPhoneNumber;
	protected String mAccount;
	protected String mPW;
	protected boolean mAdmin;
	protected boolean mIsFree;

	public void setPhoneNumber(String phone) {
		synchronized (mUpdateLock) {
			mPhoneNumber = phone;
		}
		mObservable.dispatchChange(false);
	}

	public abstract String getPhoneNumber();

	public void setAccount(String account) {
		synchronized (mUpdateLock) {
			mAccount = account;
		}
		mObservable.dispatchChange(false);
	}

	public abstract String getAccount();

	public void setPassword(String pw) {
		synchronized (mUpdateLock) {
			mPW = pw;
		}
		mObservable.dispatchChange(false);
	}

	public abstract String getPassword();

	public void setAdmin(boolean admin) {
		synchronized (mUpdateLock) {
			mAdmin = admin;
		}
		mObservable.dispatchChange(false);
	}

	public abstract boolean isAdmin();

	public void setEntertain(boolean free) {
		synchronized (mUpdateLock) {
			mIsFree = free;
		}
		mObservable.dispatchChange(false);
	}

	public abstract boolean isEntertain();
}
