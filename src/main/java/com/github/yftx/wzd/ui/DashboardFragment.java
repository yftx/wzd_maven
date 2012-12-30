package com.github.yftx.wzd.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;
import com.github.yftx.wzd.R;
import com.github.yftx.wzd.WZDApp;


/**
 * User: yftx
 * Mail: yftx.net@gmail.com
 * Date: 12-8-22
 */
public class DashboardFragment extends SherlockFragment implements View.OnClickListener {
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dashboard_info, null);
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button btnClose = (Button) view.findViewById(R.id.btn_close);
        Button btnSetting = (Button) view.findViewById(R.id.btn_setting);
        btnClose.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close:
                //kill process
                System.exit(0);
                break;
            case R.id.btn_setting:
                Intent launchPreferencesIntent = new Intent().setClass(getActivity(), PrefsActivity.class);
                //TODO 此处的请求码为什么在退出配置界面的时候,在MainActivity中的onActivityResult方法中回调的值与传入的不同，
                startActivityForResult(launchPreferencesIntent, WZDApp.REQUEST_CODE_PREFERENCES);
                break;
            default:
                break;
        }
    }


    private void showMsg(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

}
