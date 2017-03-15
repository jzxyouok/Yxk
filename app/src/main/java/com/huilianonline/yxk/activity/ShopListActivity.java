package com.huilianonline.yxk.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.huilianonline.yxk.R;
import com.huilianonline.yxk.view.refresh.PullToRefreshBase;
import com.huilianonline.yxk.view.refresh.PullToRefreshListView;

/**
 * Created by admin on 2017/3/13.
 */
public class ShopListActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back;
    private ImageView saosao;
    private PullToRefreshListView mPulllistView;
    private ListView mListView;
    private ShopListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        initView();

    }

    private void initView() {
        back = (ImageView) findViewById(R.id.img_shoplist_back);
        saosao = (ImageView) findViewById(R.id.img_saoyisao);
        back.setOnClickListener(this);
        saosao.setOnClickListener(this);

        mPulllistView = (PullToRefreshListView) findViewById(R.id.list_shop_list_data);
        mListView = mPulllistView.getRefreshableView();
        mPulllistView.setMode(PullToRefreshBase.Mode.BOTH);
        adapter = new ShopListAdapter();
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
                Intent intent = new Intent();
                intent.setClass(ShopListActivity.this,ShopDetailsActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v == back){
            finish();
        }else if (v == saosao){

        }
    }

    private class ShopListAdapter extends BaseAdapter {

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
                convertView = LayoutInflater.from(ShopListActivity.this).inflate(R.layout.item_shop_list, parent,false);
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
