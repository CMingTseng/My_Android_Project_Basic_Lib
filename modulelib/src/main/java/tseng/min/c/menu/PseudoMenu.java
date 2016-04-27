package tseng.min.c.menu;

import tseng.min.c.AbstractInterface;
import tseng.min.c.AbstractSetting;

/**
 * Created by Neo on 2016-02-28 0028.
 */
public abstract class PseudoMenu extends AbstractSetting implements AbstractInterface {
	private static final String TAG = PseudoMenu.class.getSimpleName();
	protected int mUID;

	public void setUID(int uid) {
		synchronized (mUpdateLock) {
			mUID = uid;
		}
		mObservable.dispatchChange(false);
	}

	public abstract int getUID();
}
