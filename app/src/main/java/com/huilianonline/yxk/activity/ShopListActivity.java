package com.huilianonline.yxk.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.huilianonline.yxk.R;
import com.huilianonline.yxk.global.ConstantValues;
import com.huilianonline.yxk.utils.Json_U;
import com.huilianonline.yxk.utils.PhoneInfoUtil;
import com.huilianonline.yxk.utils.ToastUtils;
import com.huilianonline.yxk.view.refresh.PullToRefreshBase;
import com.huilianonline.yxk.view.refresh.PullToRefreshListView;
import com.huilianonline.yxk.vo.ClassBean;
import com.huilianonline.yxk.vo.ProductListBean;
import com.huilianonline.yxk.vo.RequestBean;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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
    private ProductListBean productListBean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        initView();
        getProductList();

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

    /**
     * 获取商品列表
     */
    private void getProductList() {
        RequestParams params = new RequestParams();
        RequestBean bean = new RequestBean();
        bean.setParentClassID("0");
        bean.setClassID("2");
        bean.setProductName("");
        bean.setTagID("0");
        bean.setKeyID("0");
        bean.setPage("1");
        bean.setPageSize("10");
        String json = Json_U.toJson(bean);
        params.addQueryStringParameter("json", json);
        HttpUtils http = new HttpUtils();
        showWaitingDialog();
        http.send(HttpRequest.HttpMethod.POST,
                ConstantValues.GET_PRODUCT_LIST_URL,
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String json = responseInfo.result;
                        stopWaitingDialog();
                        Log.e("tag==", json);
                        try {
                            JSONObject object = new JSONObject(json);
                            int Code = object.getInt("Code");
                            if (Code == 0) {
                                productListBean = Json_U.fromJson(json,ProductListBean.class);
                                adapter = new ShopListAdapter(productListBean.getData());
                                mPulllistView.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        stopWaitingDialog();
                        ToastUtils.showShort(ShopListActivity.this, "网络异常，加载数据失败");
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

        private List<ProductListBean.DataBean> mlists;

        public ShopListAdapter(List<ProductListBean.DataBean> lists){
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
                convertView = LayoutInflater.from(ShopListActivity.this).inflate(R.layout.item_shop_list, parent,false);
                holder.imgPic = (ImageView) convertView.findViewById(R.id.img_product_pic);
                holder.txtName = (TextView) convertView.findViewById(R.id.img_product_name);
                holder.txtStyle = (TextView) convertView.findViewById(R.id.img_product_style);
                holder.txtPrice = (TextView) convertView.findViewById(R.id.img_product_price);
                holder.txtOldPrice = (TextView) convertView.findViewById(R.id.txt_old_price);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.txtOldPrice.getPaint().setAntiAlias(true);
            holder.txtOldPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG); //中划线
            holder.txtOldPrice.setText("¥"+mlists.get(position).getPrice());
            Glide.with(ShopListActivity.this)
                    .load(mlists.get(position).getPic())
                    .placeholder(R.drawable.shape_pic_loaderr_bg)
                    .error(R.drawable.shape_pic_loaderr_bg)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade()
                    .dontAnimate()
                    .into(holder.imgPic);
            holder.txtName.setText(mlists.get(position).getProductName());
            holder.txtStyle.setText(mlists.get(position).getProductName2());
            holder.txtPrice.setText("¥"+mlists.get(position).getSalePrice());

            return convertView;
        }

        class Holder {
            ImageView imgPic;
            TextView txtName;
            TextView txtStyle;
            TextView txtPrice;
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
