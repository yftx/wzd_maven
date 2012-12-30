package com.github.yftx.wzd.ui.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;
import com.actionbarsherlock.app.SherlockFragment;
import com.github.yftx.wzd.ui.InfoFragment;
import com.github.yftx.wzd.utils.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * User: yftx
 * Mail: yftx.net@gmail.com
 * Date: 12-8-11
 */

public class ContentAdapter extends FragmentStatePagerAdapter {
    protected static final String[] CONTENT = new String[]{"温州贷", "月标"};
    private Map<Integer, SherlockFragment> mFragmentReference = new HashMap<Integer, SherlockFragment>();
    private int mCount = CONTENT.length;

    public ContentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Logger.d("getItem  pos --> " + position);
        InfoFragment infoFragment = InfoFragment.newInstance();
        mFragmentReference.put(position, infoFragment);
        return infoFragment;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ContentAdapter.CONTENT[position % CONTENT.length];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        mFragmentReference.remove(position);
    }


    public SherlockFragment getFragment(int key) {
        return mFragmentReference.get(key);
    }


}
