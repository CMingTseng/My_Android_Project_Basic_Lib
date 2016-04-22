package tseng.min.c.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by Chiatai on 2016/3/27.
 */
public class AddTabByViewPagerTabLayout extends TabLayout {
    public interface ConsumingIconPagerAdapter {
        void bindTab(@NonNull Tab tab, int position, @NonNull ViewGroup parent);
    }

    // because this is private in super class and there's no getAdapter() nor getViewPager(),
    // thus we store it during setupWithViewPager();
    protected ConsumingIconPagerAdapter mAdapter;

    public AddTabByViewPagerTabLayout(Context context) {
        super(context);
    }

    public AddTabByViewPagerTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AddTabByViewPagerTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void addTab(@NonNull Tab tab, boolean setSelected) {
        super.addTab(tab, setSelected);
        if (mAdapter == null)
            throw new IllegalStateException("are you trying to addTab() manually? call setupWithViewPager() before doing so.");
        mAdapter.bindTab(tab, tab.getPosition(), this);
    }

    @Override
    public void addTab(@NonNull Tab tab, int position, boolean setSelected) {
        super.addTab(tab, position, setSelected);
        if (mAdapter == null)
            throw new IllegalStateException("are you trying to addTab() manually? call setupWithViewPager() before doing so.");
        // we have to re-bind the tabs else all subsequent tabs would have wrong color
        for (int p = position; p < getTabCount(); ++p) {
            mAdapter.bindTab(getTabAt(p), p, this);
        }
    }

    @Override
    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        // go through the check before calling super, because super.setupWithViewPager()
        // does addTab() and the overridden addTab() needs mAdapter.
        if (viewPager == null)
            return;

        final PagerAdapter pagerAdapter = viewPager.getAdapter();
        if (pagerAdapter == null)
            return;

        if (!(pagerAdapter instanceof ConsumingIconPagerAdapter))
            throw new IllegalArgumentException("attached ViewPager should have a " + ConsumingIconPagerAdapter.class.getCanonicalName() + " set.");

        mAdapter = (ConsumingIconPagerAdapter) pagerAdapter;

        super.setupWithViewPager(viewPager);
    }
}
