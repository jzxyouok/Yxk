package com.huilianonline.yxk.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.huilianonline.yxk.R;
import com.huilianonline.yxk.activity.AlertAddressActivity;
import com.huilianonline.yxk.activity.CaptureActivity;
import com.huilianonline.yxk.activity.PurchaseHistoryActivity;
import com.huilianonline.yxk.activity.ShopDetailsActivity;
import com.huilianonline.yxk.activity.ShopListActivity;
import com.huilianonline.yxk.utils.GlideRoundTransform;
import com.huilianonline.yxk.view.refresh.NoScrollGridView;
import com.huilianonline.yxk.view.refresh.PullToRefreshBase;
import com.huilianonline.yxk.view.refresh.PullToRefreshListView;

import java.util.Objects;

/**
 * Created by admin on 2017/3/2.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private Activity mActivity;
    private PullToRefreshListView mPulllistView;
    private ListView mListView;
    private HomeListDataAdapter adapter;
    private ImageView imgSaoSao;
    private View header;
    private NoScrollGridView gridView;
    private ClassListDataAdapter adapterClass;
    private TextView txtAlertAddress;
//    private ImageView imghead;
    private TextView txtYuE;
    private int[] resourses = {R.drawable.img_class_shenghuochaoshi, R.drawable.img_class_zhongdigongju, R.drawable.img_class_jiayongdianqi,
            R.drawable.img_class_zhongzihuafei, R.drawable.img_class_jujiashenghuo, R.drawable.img_class_yiyaobaojian,
            R.drawable.img_class_huwaiyundong, R.drawable.img_class_shoujishuma, R.drawable.img_class_wanjuyuqi};
    private String[] names = {"生活超市", "种地工具", "家用电器", "种子化肥", "居家生活", "医药保健", "户外运动", "手机数码", "玩具乐器"};

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initView(View view) {
        imgSaoSao = (ImageView) view.findViewById(R.id.img_saoyisao);
        imgSaoSao.setOnClickListener(this);
        mPulllistView = (PullToRefreshListView) view.findViewById(R.id.list_home_list_data);
        mListView = mPulllistView.getRefreshableView();
        mPulllistView.setMode(PullToRefreshBase.Mode.BOTH);
        adapter = new HomeListDataAdapter();
        header = LayoutInflater.from(mActivity).inflate(R.layout.header_home_shop, null);
        txtAlertAddress = (TextView) header.findViewById(R.id.txt_alert_address);
        txtAlertAddress.setOnClickListener(this);
        txtYuE = (TextView) header.findViewById(R.id.txt_zhanghuyue);
        txtYuE.setOnClickListener(this);
        gridView = (NoScrollGridView) header.findViewById(R.id.grid_home);
//        imghead = (ImageView) header.findViewById(R.id.img_header_icon);
//        Glide.with(mActivity)
//                .load(R.drawable.logo)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .transform(new GlideRoundTransform(mActivity, 20))
//                .crossFade()
//                .dontAnimate()
//                .into(imghead);
        adapterClass = new ClassListDataAdapter();
        gridView.setAdapter(adapterClass);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent();
                mIntent.setClass(mActivity, ShopListActivity.class);
                startActivity(mIntent);
            }
        });
        mListView.removeHeaderView(header);
        mListView.addHeaderView(header);
        mPulllistView.setAdapter(adapter);

        mPulllistView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            }
        });

        mPulllistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent();
                mIntent.setClass(mActivity, ShopDetailsActivity.class);
                startActivity(mIntent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v == imgSaoSao) {
            Intent intent = new Intent(mActivity, CaptureActivity.class);
            startActivityForResult(intent, 0);
        } else if (v == txtAlertAddress) {
            Intent mIntent = new Intent();
            mIntent.setClass(mActivity, AlertAddressActivity.class);
            startActivity(mIntent);
        } else if (v == txtYuE) {
            Intent mIntent = new Intent();
            mIntent.setClass(mActivity, PurchaseHistoryActivity.class);
            startActivity(mIntent);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == mActivity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            Toast.makeText(mActivity, scanResult, Toast.LENGTH_SHORT).show();

        }
    }

    public class HomeListDataAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 10;
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
                convertView = LayoutInflater.from(mActivity).inflate(R.layout.item_home_data, null);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            return convertView;
        }

        class Holder {
        }
    }


    public class ClassListDataAdapter extends BaseAdapter {

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
