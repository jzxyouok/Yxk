package com.huilianonline.yxk.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.huilianonline.yxk.R;
import com.huilianonline.yxk.view.refresh.PullToRefreshBase;
import com.huilianonline.yxk.view.refresh.PullToRefreshListView;

/**
 * 订单记录
 * Created by admin on 2017/3/13.
 */
public class OrderListActivity extends BaseActivity implements View.OnClickListener{

    private TextView title;
    private View back;
    private PullToRefreshListView mPulllistView;
    private ListView mListView;
    private OrderAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        initView();
    }

    private void initView() {
        title = (TextView) findViewById(R.id.tv_title_text);
        title.setText("订单记录");
        back = findViewById(R.id.ll_title_common_back);
        back.setOnClickListener(this);

        mPulllistView = (PullToRefreshListView) findViewById(R.id.list_order_list_data);
        mListView = mPulllistView.getRefreshableView();
        mPulllistView.setMode(PullToRefreshBase.Mode.BOTH);
        adapter = new OrderAdapter();
        mPulllistView.setAdapter(adapter);
        mPulllistView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v == back){
            finish();
        }
    }

    private class OrderAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
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
                convertView = LayoutInflater.from(OrderListActivity.this).inflate(R.layout.item_order_list, parent,false);
                holder.txtOldPrice = (TextView) convertView.findViewById(R.id.txt_old_price);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.txtOldPrice.getPaint().setAntiAlias(true);
            holder.txtOldPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG); //中划线
            return convertView;
        }

        class Holder {
            TextView txtOldPrice;
        }
    }

}
