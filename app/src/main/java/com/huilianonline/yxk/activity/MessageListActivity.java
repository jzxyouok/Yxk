package com.huilianonline.yxk.activity;

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
 * Created by admin on 2017/3/13.
 */
public class MessageListActivity extends BaseActivity implements View.OnClickListener{

    private TextView title;
    private View back;
    private PullToRefreshListView mPulllistView;
    private ListView mListView;
    private MessageAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        initView();
    }

    private void initView() {
        title = (TextView) findViewById(R.id.tv_title_text);
        title.setText("消息列表");
        back = findViewById(R.id.ll_title_common_back);
        back.setOnClickListener(this);

        mPulllistView = (PullToRefreshListView) findViewById(R.id.list_message_list_data);
        mListView = mPulllistView.getRefreshableView();
        mPulllistView.setMode(PullToRefreshBase.Mode.BOTH);
        adapter = new MessageAdapter();
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

    private class MessageAdapter extends BaseAdapter{

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
                convertView = LayoutInflater.from(MessageListActivity.this).inflate(R.layout.item_message_data, parent,false);
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
