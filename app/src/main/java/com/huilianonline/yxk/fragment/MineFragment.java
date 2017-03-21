package com.huilianonline.yxk.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.huilianonline.yxk.R;
import com.huilianonline.yxk.activity.AlertAddressActivity;
import com.huilianonline.yxk.activity.MessageListActivity;
import com.huilianonline.yxk.activity.OrderListActivity;
import com.huilianonline.yxk.activity.PurchaseHistoryActivity;
import com.huilianonline.yxk.global.Config;
import com.huilianonline.yxk.utils.PrefUtils;
import com.huilianonline.yxk.view.refresh.NoScrollGridView;

import java.util.Objects;

/**
 * Created by admin on 2017/3/2.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private Activity mActivity;
    private TextView title;
    private ImageView imgRight;
    private NoScrollGridView gridView;
    private MineListDataAdapter adapter;
    private int[] resourses = {R.drawable.img_mine_daishouhuo, R.drawable.img_mine_yishouhuo, R.drawable.img_mine_shoubaozhang,
            R.drawable.img_mine_daifukuan, R.drawable.img_mine_xiaoxi, R.drawable.img_mine_yifukuan};
    private String[] names = {"待收货", "已收货", "售后保障", "待付款", "消息", "已付款"};
    private TextView txtAlertAddress;
    private TextView txtCallPhone;
    private TextView txtName;
    private TextView txtAddress;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initView(View view) {
        gridView = (NoScrollGridView) view.findViewById(R.id.gridview_mine);
        title = (TextView) view.findViewById(R.id.txt_title);
        imgRight = (ImageView) view.findViewById(R.id.img_saoyisao);
        title.setText("我的");
        imgRight.setImageResource(R.drawable.img_mine_telphone);
        imgRight.setOnClickListener(this);
        txtCallPhone = (TextView) view.findViewById(R.id.txt_call_phone);
        txtCallPhone.setOnClickListener(this);
        adapter = new MineListDataAdapter();
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 4) {
                    Intent mIntent = new Intent();
                    mIntent.setClass(mActivity, MessageListActivity.class);
                    startActivity(mIntent);
                } else if (position == 2) {
                    Toast.makeText(mActivity, "暂未开通！", Toast.LENGTH_SHORT).show();
                } else {
                    Intent mIntent = new Intent();
                    mIntent.setClass(mActivity, OrderListActivity.class);
                    startActivity(mIntent);
                }
            }
        });
        txtAlertAddress = (TextView) view.findViewById(R.id.txt_alert_address);
        txtAlertAddress.setOnClickListener(this);
        txtName = (TextView) view.findViewById(R.id.txt_user_name);
        txtAddress = (TextView) view.findViewById(R.id.txt_user_address);
        txtName.setText((String) PrefUtils.getParam(mActivity,"RealName",""));
        txtCallPhone.setText((String)PrefUtils.getParam(mActivity,"Tel",""));
        txtAddress.setText((String)PrefUtils.getParam(mActivity,"Address",""));
    }

    @Override
    public void onClick(View v) {
        if (v == imgRight || v == txtCallPhone) {
            new AlertDialog.Builder(mActivity).setTitle("拨打电话").setMessage(Config.TEL_SERVER)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Config.TEL_SERVER)));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    })
                    .setNegativeButton("取消", null).create().show();
        } else if (v == txtAlertAddress) {
            Intent mIntent = new Intent();
            mIntent.setClass(mActivity, AlertAddressActivity.class);
            startActivity(mIntent);
        }
    }

    public class MineListDataAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return resourses.length;
        }

        @Override
        public Objects getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder = null;
            if (convertView == null) {
                holder = new Holder();
                convertView = LayoutInflater.from(mActivity).inflate(R.layout.item_class, null);
                holder.imgClassIcon = (ImageView) convertView.findViewById(R.id.img_class_icon);
                holder.txtClassIcon = (TextView) convertView.findViewById(R.id.img_class_name);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            Glide.with(mActivity)
                    .load(resourses[position])
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade()
                    .dontAnimate()
                    .into(holder.imgClassIcon);
            holder.txtClassIcon.setText(names[position]);
            return convertView;
        }

        class Holder {
            private ImageView imgClassIcon;
            private TextView txtClassIcon;
        }
    }
}
