package com.huilianonline.yxk.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.huilianonline.yxk.R;
import com.huilianonline.yxk.utils.PhoneInfoUtil;
import com.huilianonline.yxk.view.refresh.PullToRefreshBase;
import com.huilianonline.yxk.view.refresh.PullToRefreshListView;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

/**
 * Created by admin on 2017/3/13.
 */
public class ShopListActivity extends BaseActivity implements View.OnClickListener{

    private View back;
    private ImageView saosao;
    private PullToRefreshListView mPulllistView;
    private ListView mListView;
    private ShopListAdapter adapter;
    private View layoutListShaixuan;
    public View swichView;
    private PopupWindow mSwichPop;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        initView();

    }

    private void initView() {
        back = findViewById(R.id.ll_title_common_back);
        saosao = (ImageView) findViewById(R.id.img_saoyisao);
        back.setOnClickListener(this);
        saosao.setOnClickListener(this);
        layoutListShaixuan = findViewById(R.id.ll_list_shaixuan);
        layoutListShaixuan.setOnClickListener(this);
        swichView = LayoutInflater.from(ShopListActivity.this).inflate(R.layout.alert_dailog_holiday, null);
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
            Intent intent = new Intent(ShopListActivity.this, CaptureActivity.class);
            startActivityForResult(intent, 0);
        }else if (v == layoutListShaixuan){
            showPop();
        }
    }

    private void showPop() {
        startAnim(swichView);
        mSwichPop = new PopupWindow(swichView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mSwichPop.setFocusable(true);
        mSwichPop.setBackgroundDrawable(new ColorDrawable());
        mSwichPop.showAsDropDown(layoutListShaixuan, 0, 0);
        mSwichPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
    }

    private void startAnim(View popView) {
        popView.measure(0, 0);
        ViewHelper.setTranslationY(popView, -(PhoneInfoUtil.getInstance().getDisplayHeight(ShopListActivity.this)));
        ViewPropertyAnimator.animate(popView).translationY(0f).setDuration(500).start();
        ViewPropertyAnimator.animate(popView).translationY(0f).setDuration(500).setInterpolator(new DecelerateInterpolator()).start();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ShopListActivity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            Toast.makeText(ShopListActivity.this, scanResult, Toast.LENGTH_SHORT).show();

        }
    }

}
