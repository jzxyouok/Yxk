package com.huilianonline.yxk.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.huilianonline.yxk.R;
import com.huilianonline.yxk.view.refresh.PullToRefreshBase;
import com.huilianonline.yxk.view.refresh.PullToRefreshListView;

import java.util.Objects;

/**
 * Created by admin on 2017/3/2.
 */
public class HomeFragment extends BaseFragment {

    private Activity mActivity;
    private PullToRefreshListView mPulllistView;
    private ListView mListView;
    private HomeListDataAdapter adapter;

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
        mPulllistView = (PullToRefreshListView) view.findViewById(R.id.list_home_list_data);
        mListView = mPulllistView.getRefreshableView();
        mPulllistView.setMode(PullToRefreshBase.Mode.BOTH);
        adapter = new HomeListDataAdapter();
        View header = LayoutInflater.from(mActivity).inflate(R.layout.header_home_shop,null);
        mListView.removeHeaderView(header);
        mListView.addHeaderView(header);
        mPulllistView.setAdapter(adapter);

        mPulllistView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//                curPage = 1;
//                getMessageListsData(curPage, "10");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//                curPage++;
//                getMessageListsData(curPage, "10");
            }
        });

    }

    public class HomeListDataAdapter extends BaseAdapter {

//        private List<MessageBean.DataBeanA.DataBean> mdatalists;
//
//        public MessageAdapter(List<MessageBean.DataBeanA.DataBean> datalistst) {
//            this.mdatalists = datalistst;
//        }

        @Override
        public int getCount() {
//            return mdatalists.size();
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
//                holder.textSender = (TextView) convertView.findViewById(R.id.tv_message_sender);
//                holder.textSenderTime = (TextView) convertView.findViewById(R.id.tv_message_sender_time);
//                holder.textSendDes = (TextView) convertView.findViewById(R.id.tv_message_send_shot_des);
//                holder.textSendConte = (TextView) convertView.findViewById(R.id.tv_message_send_shot_content);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
//            holder.textSender.setText(mdatalists.get(position).getOperaUserName());
//            holder.textSenderTime.setText(mdatalists.get(position).getPushTime());
//            holder.textSendDes.setText(mdatalists.get(position).getTitle());
//            holder.textSendConte.setText(mdatalists.get(position).getContent());
            return convertView;
        }

        class Holder {
//            private TextView textSender;
//            private TextView textSenderTime;
//            private TextView textSendDes;
//            private TextView textSendConte;
        }
    }
}
