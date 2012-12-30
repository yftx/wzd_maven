package com.github.yftx.wzd.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.github.yftx.wzd.R;
import com.github.yftx.wzd.domain.Bid;
import com.github.yftx.wzd.ui.adapter.InfoAdapter;
import com.github.yftx.wzd.ui.base.Refreshable;
import com.github.yftx.wzd.ui.custom.PullToRefreshListView;
import com.github.yftx.wzd.ui.custom.RefreshableListView;
import com.github.yftx.wzd.utils.*;


/**
 * 表信息显示ui
 * User: yftx
 * Mail: yftx.net@gmail.com
 * Date: 12-8-11
 */
public class InfoFragment extends SherlockFragment implements ActionBar.OnNavigationListener, SharedPreferences.OnSharedPreferenceChangeListener {
    PullToRefreshListView plv;
    Refreshable refreshable;
    InfoAdapter infoAdapter;
    ActionBar ab;
    MainActivity activity;

    public static InfoFragment newInstance() {
        InfoFragment fragment = new InfoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.info, container, false);
        plv = (PullToRefreshListView) v.findViewById(R.id.lv_info);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refreshable = (Refreshable) getActivity();
        infoAdapter = new InfoAdapter(getActivity());
        plv.setAdapter(infoAdapter);
        Gfan.sendEvent(getActivity(), "open app|-->" + DateTimeHelper.getNowTime());
        plv.setOnUpdateTask(new RefreshableListView.OnUpdateTask() {
            public void updateBackground() {
                Gfan.sendEvent(getActivity(), "refresh|-->" + DateTimeHelper.getNowTime());
                refreshData();
            }

            public void updateUI() {
                refreshable.freshUI();
                infoAdapter.notifyDataSetChanged();
            }

            public void onUpdateStart() {
            }
        });


        plv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bid bid = (Bid) view.getTag();
                Logger.d("当前点击的item的url为" + bid.getLink_url());
                openUrl(bid.getLink_url());
            }
        });
        plv.startUpdateImmediate();
        Preferences.getPrefs(ContextManager.getContext()).
                registerOnSharedPreferenceChangeListener(this);

        configureActionBar();
    }

    private void configureActionBar() {
        activity = (MainActivity) getActivity();
        ArrayAdapter<CharSequence> listAdapter = ArrayAdapter.createFromResource(activity, R.array.classify, R.layout.sherlock_spinner_item);
        listAdapter.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);
        ab = activity.getSupportActionBar();
        ab.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        ab.setListNavigationCallbacks(listAdapter, this);
        ab.setSelectedNavigationItem(Preferences.getInt(Preferences.SORT_BID_KEY, 0));
    }


    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    protected void refreshData() {
        try {
            refreshable.doRetrieve();
        } catch (Exception e) {
            Logger.d("请求数据失败  失败原因" + e.toString());
        }
    }


    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        Preferences.setInt(Preferences.SORT_BID_KEY, itemPosition);
        return true;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(Preferences.SORT_BID_KEY)) {
            activity.sortData();
            infoAdapter.notifyDataSetChanged();
        }
    }
}
