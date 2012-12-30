package com.github.yftx.wzd.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.github.yftx.wzd.R;
import com.github.yftx.wzd.WZDApp;
import com.github.yftx.wzd.domain.Bid;
import com.github.yftx.wzd.engine.Sort;
import com.github.yftx.wzd.ui.adapter.ContentAdapter;
import com.github.yftx.wzd.ui.base.Refreshable;
import com.github.yftx.wzd.ui.custom.DrawerGarment;
import com.github.yftx.wzd.utils.ContextManager;
import com.github.yftx.wzd.utils.Gfan;
import com.github.yftx.wzd.utils.Logger;
import com.github.yftx.wzd.utils.Preferences;
import com.viewpagerindicator.TitlePageIndicator;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: yftx
 * Mail: yftx.net@gmail.com
 * Date: 12-8-9
 */
public class MainActivity extends SherlockFragmentActivity implements Refreshable {
    private ContentAdapter mAdapter;
    private ViewPager mPager;
    public List<Bid> bids = new ArrayList<Bid>();
    private WZDApp app;
    ActionBar ab;
    TextView tv_bidCount;
    DrawerGarment mDrawerGarment;
    Boolean isOpenDashBoard = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ContextManager.setContext(this);
        initDashboard();
        app = (WZDApp) getApplication();
        configureActionBar();
        initView();
    }

    /**
     * 初始化设置面板
     */
    private void initDashboard() {
        mDrawerGarment = new DrawerGarment(this, R.layout.dashboard);
        mDrawerGarment.setDrawerCallbacks(new DrawerGarment.IDrawerCallbacks() {
            @Override
            public void onDrawerOpened() {
                isOpenDashBoard = true;
            }

            @Override
            public void onDrawerClosed() {
                isOpenDashBoard = false;
            }
        });

    }

    private void initView() {
        //内容部分
        mAdapter = new ContentAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        //Title 部分
        TitlePageIndicator indicator = (TitlePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
        indicator.setFooterIndicatorStyle(TitlePageIndicator.IndicatorStyle.Triangle);

        tv_bidCount = (TextView) findViewById(R.id.tv_bidCount);
    }

    private void configureActionBar() {
        ab = getSupportActionBar();
        if (ab == null)
            return;
        ab.setCustomView(R.layout.title_count);
        ab.setDisplayShowCustomEnabled(true);
        ab.setLogo(R.drawable.app_ico);
        ab.setHomeButtonEnabled(true);
    }

    /**
     * 向网络请求数据。
     */
    private void getData() throws IOException, XmlPullParserException {
        bids = app.wzd.retrieveData();
    }

    /**
     * 向fragment中填充数据
     */
    public void freshUI() {
        Logger.d("当前标的数量" + bids.size());
        tv_bidCount.setText(bids.size() + "");
    }

    @Override
    public void doRetrieve() throws IOException, XmlPullParserException {
        getData();
        sortData();
    }

    public void sortData() {
        int sortFlag = Preferences.getInt(Preferences.SORT_BID_KEY, -1);
        switch (sortFlag) {
            case 0:
                Collections.sort(bids, new Sort.ComparatorByApr());
                break;
            case 1:
                Collections.sort(bids, new Sort.ComparatorByTime());
                break;
            case 2:
                Collections.sort(bids, new Sort.ComparatorByAmount());
                break;
            default:
                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (!isOpenDashBoard) {
                    mDrawerGarment.openDrawer();
                } else {
                    mDrawerGarment.closeDrawer();
                }
                return true;
            default:
                if(item.getTitle().equals("提醒")){
                    Logger.d("点击开启service");
//                    WZDService.scheduleService(this);
                }
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerGarment.isDrawerMoving())
            return;
        if (!isOpenDashBoard)
            mDrawerGarment.openDrawer();
        else
            super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Gfan.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Gfan.onPause(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.d("返回码" + resultCode);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add("提醒").setIcon(R.drawable.tixing).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }
}
