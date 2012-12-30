package com.github.yftx.wzd.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.yftx.wzd.R;
import com.github.yftx.wzd.domain.Bid;
import com.github.yftx.wzd.ui.MainActivity;

/**
 * User: yftx
 * Mail: yftx.net@gmail.com
 * Date: 12-8-11
 */
public class InfoAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private MainActivity mainActivity;

    public InfoAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        mainActivity = (MainActivity) context;
    }

    @Override
    public int getCount() {
        return mainActivity.bids.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.info_item, null);
        }

        Bid bid = mainActivity.bids.get(position);
        convertView.setTag(bid);


        TextView tvApr = (TextView) convertView.findViewById(R.id.tv_apr);
        TextView tvAccountFormat = (TextView) convertView.findViewById(R.id.tv_account_format);
        TextView tvName = (TextView) convertView.findViewById(R.id.tv_name);
        TextView tvScale = (TextView) convertView.findViewById(R.id.tv_scale);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tv_userName);
        TextView tvBidTime = (TextView) convertView.findViewById(R.id.tv_bidTime);
        ImageView ivIco = (ImageView) convertView.findViewById(R.id.iv_ico);

        tvApr.setText("年化: " + bid.getApr() + "%");
        tvAccountFormat.setText("总额: " + bid.getAccount_format());
        tvName.setText(bid.getName());
        tvScale.setText("完成度: "+bid.getScale() + "%");
        tvUserName.setText(bid.getUsername());
        tvBidTime.setText(bid.getAddtime());
        ivIco.setImageResource(getImgId(bid.getBorrow_type()));
        return convertView;
    }

    private int getImgId(String borrow_type) {
        if (borrow_type.equals("净值标"))
            return R.drawable.jing;
        else if (borrow_type.equals("给力标"))
            return R.drawable.li;
        else if(borrow_type.equals("秒标"))
            return R.drawable.miao;

        return 0;


    }


}
