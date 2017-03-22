package com.huilianonline.yxk.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.huilianonline.yxk.R;
import com.huilianonline.yxk.global.ConstantValues;
import com.huilianonline.yxk.utils.Json_U;
import com.huilianonline.yxk.utils.ToastUtils;
import com.huilianonline.yxk.view.refresh.PullToRefreshBase;
import com.huilianonline.yxk.view.refresh.PullToRefreshListView;
import com.huilianonline.yxk.vo.MessageBean;
import com.huilianonline.yxk.vo.RequestBean;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 消息列表
 * Created by admin on 2017/3/13.
 */
public class MessageListActivity extends BaseActivity implements View.OnClickListener{

    private TextView title;
    private View back;
    private PullToRefreshListView mPulllistView;
    private ListView mListView;
    private MessageAdapter adapter;
    private MessageBean messageBean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        initView();
        getMsgList();
    }

    private void initView() {
        title = (TextView) findViewById(R.id.tv_title_text);
        title.setText("消息列表");
        back = findViewById(R.id.ll_title_common_back);
        back.setOnClickListener(this);

        mPulllistView = (PullToRefreshListView) findViewById(R.id.list_message_list_data);
        mListView = mPulllistView.getRefreshableView();
        mPulllistView.setMode(PullToRefreshBase.Mode.BOTH);
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

    private void getMsgList() {
        RequestParams params = new RequestParams();
        RequestBean bean = new RequestBean();
        bean.setUserID("2");
        bean.setPage("1");
        bean.setPageSize("10");
        String json = Json_U.toJson(bean);
        params.addQueryStringParameter("json", json);
        HttpUtils http = new HttpUtils();
        showWaitingDialog();
        http.send(HttpRequest.HttpMethod.POST,
                ConstantValues.GET_MSG_LIST_URL,
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        stopWaitingDialog();
                        String json = responseInfo.result;
                        Log.e("tag==", json);
                        try {
                            JSONObject object = new JSONObject(json);
                            int Code = object.getInt("Code");
                            if (Code == 0){
                                messageBean = Json_U.fromJson(json,MessageBean.class);
                                adapter = new MessageAdapter(messageBean.getData());
                                mPulllistView.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        stopWaitingDialog();
                        ToastUtils.showShort(MessageListActivity.this, "网络异常，加载数据失败");
                    }
                });
    }

    private class MessageAdapter extends BaseAdapter{

        private List<MessageBean.DataBean> mlists;

        public MessageAdapter( List<MessageBean.DataBean> lists){
            this.mlists = lists;
        }

        @Override
        public int getCount() {
            return mlists.size();
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
                holder.txtTime = (TextView) convertView.findViewById(R.id.txt_msg_create_time);
                holder.txtContent = (TextView) convertView.findViewById(R.id.txt_msg_create_content);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
                holder.txtTime.setText(mlists.get(position).getCreateTime());
                holder.txtContent.setText(mlists.get(position).getIntro());
            }
            return convertView;
        }

        class Holder {
            TextView txtTime;
            TextView txtContent;
        }
    }
}
