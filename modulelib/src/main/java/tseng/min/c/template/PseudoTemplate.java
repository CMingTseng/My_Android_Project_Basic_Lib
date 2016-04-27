package tseng.min.c.template;

import tseng.min.c.AbstractInterface;
import tseng.min.c.AbstractSetting;
/**
 * Created by Neo on 2016/2/25.
 */
public abstract class PseudoTemplate extends AbstractSetting implements AbstractInterface {
	private static final String TAG = PseudoTemplate.class.getSimpleName();
	protected String mPrinterModel;

	public void setPrinterModel(String model) {
		synchronized (mUpdateLock) {
			mPrinterModel = model;
		}
		mObservable.dispatchChange(false);
	}

	public abstract String getPrinterModel();
}
