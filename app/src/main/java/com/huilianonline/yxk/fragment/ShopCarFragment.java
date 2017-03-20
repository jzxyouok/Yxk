package com.huilianonline.yxk.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.huilianonline.yxk.R;
import com.huilianonline.yxk.activity.CaptureActivity;
import com.huilianonline.yxk.activity.ConfirmOrderActivity;
import com.huilianonline.yxk.view.refresh.PullToRefreshBase;
import com.huilianonline.yxk.view.refresh.PullToRefreshListView;

import java.util.Objects;

/**
 * Created by admin on 2017/3/2.
 */
public class ShopCarFragment extends BaseFragment implements View.OnClickListener {

    private Activity mActivity;
    private TextView title;
    private PullToRefreshListView mPulllistView;
    private ListView mListView;
    private ShopCarListDataAdapter adapter;
    private TextView txtJieSuan;
    private ImageView imgSaoSao;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopcar, container, false);
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
        title = (TextView) view.findViewById(R.id.txt_title);
        title.setText("购物车");
        mPulllistView = (PullToRefreshListView) view.findViewById(R.id.list_shopcar_list_data);
        mListView = mPulllistView.getRefreshableView();
        mPulllistView.setMode(PullToRefreshBase.Mode.BOTH);
        adapter = new ShopCarListDataAdapter();
        mPulllistView.setAdapter(adapter);

        mPulllistView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            }
        });
        txtJieSuan = (TextView) view.findViewById(R.id.txt_jiesuan);
        txtJieSuan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == txtJieSuan) {
            Intent intent = new Intent();
            intent.setClass(mActivity, ConfirmOrderActivity.class);
            startActivity(intent);
        } else if (v == imgSaoSao) {
            Intent intent = new Intent(mActivity, CaptureActivity.class);
            startActivityForResult(intent, 0);
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

    public class ShopCarListDataAdapter extends BaseAdapter {

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
                convertView = LayoutInflater.from(mActivity).inflate(R.layout.item_shopcar_data, null);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            return convertView;
        }

        class Holder {
        }
    }
}
