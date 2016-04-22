package tseng.min.c.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import tseng.min.c.R;


/**
 * Created by Neo on 2016/3/16.
 */
public class NonSwipableViewPager extends ViewPager {
	private boolean mSwipable;

	public NonSwipableViewPager(Context context) {
		this(context, null);
	}

	public NonSwipableViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NonSwipableViewPager);
		try {
			mSwipable = a.getBoolean(R.styleable.NonSwipableViewPager_swipeable, true);
		} finally {
			a.recycle();
		}
	}

	public boolean isSwipable() {
		return mSwipable;
	}

	public void setSwipable(boolean swipable) {
		mSwipable = swipable;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		return mSwipable && super.onInterceptTouchEvent(event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return mSwipable && super.onTouchEvent(event);
	}
}
