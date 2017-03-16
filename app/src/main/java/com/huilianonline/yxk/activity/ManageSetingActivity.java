package com.huilianonline.yxk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.huilianonline.yxk.MainActivity;
import com.huilianonline.yxk.R;
import com.huilianonline.yxk.global.ConstantValues;
import com.huilianonline.yxk.utils.Json_U;
import com.huilianonline.yxk.utils.ToastUtils;
import com.huilianonline.yxk.vo.RequestBean;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * Created by admin on 2017/3/13.
 */
public class ManageSetingActivity extends BaseActivity implements View.OnClickListener{

    private TextView txtSave;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_seting);
        initView();
        getData();
    }

    private void initView() {
        txtSave = (TextView) findViewById(R.id.txt_save_message);
        txtSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == txtSave){
            Intent mIntent = new Intent();
            mIntent.putExtra("flag_main",0);
            mIntent.setClass(ManageSetingActivity.this, MainActivity.class);
            startActivity(mIntent);
        }
    }

    private void getData(){
        RequestParams params = new RequestParams();
        RequestBean bean = new RequestBean();
        bean.setTel("18563788999");
        String json = Json_U.toJson(bean);
        params.addQueryStringParameter("json", json);
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST,
                ConstantValues.GET_USERINFO_URL,
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String json = responseInfo.result;
                        Log.e("tag==", json);

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        ToastUtils.showShort(ManageSetingActivity.this, "网络异常，加载数据失败");

                    }
                });
    }

}
