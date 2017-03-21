package com.huilianonline.yxk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.huilianonline.yxk.MainActivity;
import com.huilianonline.yxk.R;
import com.huilianonline.yxk.global.ConstantValues;
import com.huilianonline.yxk.utils.Json_U;
import com.huilianonline.yxk.utils.PhoneInfoUtil;
import com.huilianonline.yxk.utils.PrefUtils;
import com.huilianonline.yxk.utils.ToastUtils;
import com.huilianonline.yxk.vo.RequestBean;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 2017/3/13.
 */
public class ManageSetingActivity extends BaseActivity implements View.OnClickListener {

    private TextView txtSave;
    private EditText edtPhone;
    private EditText edtEbank;
    private EditText edtRealName;
    private EditText edtIdCard;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_seting);
        initView();
    }

    private void initView() {
        txtSave = (TextView) findViewById(R.id.txt_save_message);
        txtSave.setOnClickListener(this);
        edtPhone = (EditText) findViewById(R.id.edt_input_phone);
        edtEbank = (EditText) findViewById(R.id.edt_input_ebank);
        edtRealName = (EditText) findViewById(R.id.edt_input_real_name);
        edtIdCard = (EditText) findViewById(R.id.edt_input_idcard);
    }

    @Override
    public void onClick(View v) {
        if (v == txtSave) {
            if (TextUtils.isEmpty(edtPhone.getText().toString().trim())) {
                ToastUtils.showShort(ManageSetingActivity.this, "请输入手机号码");
            } else if (TextUtils.isEmpty(edtEbank.getText().toString().trim())) {
                ToastUtils.showShort(ManageSetingActivity.this, "请输入银行卡号");
            } else if (TextUtils.isEmpty(edtRealName.getText().toString().trim())) {
                ToastUtils.showShort(ManageSetingActivity.this, "请输入真实姓名");
            } else if (TextUtils.isEmpty(edtIdCard.getText().toString().trim())) {
                ToastUtils.showShort(ManageSetingActivity.this, "请输入身份证号");
            } else {
                getUserInfo(edtPhone.getText().toString().trim());
            }
        }
    }

    private void getUserInfo(String phone) {
        RequestParams params = new RequestParams();
        RequestBean bean = new RequestBean();
//        bean.setTel("18563788999");
        bean.setTel(phone);
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
                        try {
                            JSONObject object = new JSONObject(json);
                            int Code = object.getInt("Code");
                            if (Code == 0){
                                JSONObject objectDate = object.getJSONObject("Data");
                                PrefUtils.setParam(ManageSetingActivity.this,"UserID",objectDate.getString("UserID"));
                                PrefUtils.setParam(ManageSetingActivity.this,"UserKey",objectDate.getString("UserKey"));
                                PrefUtils.setParam(ManageSetingActivity.this,"Address",objectDate.getString("Address"));
                                PrefUtils.setParam(ManageSetingActivity.this,"RealName",objectDate.getString("RealName"));
                                PrefUtils.setParam(ManageSetingActivity.this,"Tel",objectDate.getString("Tel"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        updateUserInfo();
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        ToastUtils.showShort(ManageSetingActivity.this, "网络异常，加载数据失败");
                    }
                });
    }


    private void updateUserInfo() {
        RequestParams params = new RequestParams();
        RequestBean bean = new RequestBean();
        bean.setTel(edtPhone.getText().toString().trim());
        bean.setBankCode(edtEbank.getText().toString().trim());
        bean.setRealName(edtRealName.getText().toString().trim());
        bean.setPapersCode(edtIdCard.getText().toString().trim());
        bean.setIMEI(PhoneInfoUtil.getInstance().getPhoneIMEI(this));
        bean.setSIM(PhoneInfoUtil.getInstance().getPhoneIMEI(this));
        bean.setSystemCode(PhoneInfoUtil.getInstance().getAndroidSDKVersion()+"");
        String json = Json_U.toJson(bean);
        params.addQueryStringParameter("json", json);
        HttpUtils http = new HttpUtils();
        showWaitingDialog();
        http.send(HttpRequest.HttpMethod.POST,
                ConstantValues.UPDATE_USERINFO_URL,
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
                            if (Code == 0){
                                ToastUtils.showShort(ManageSetingActivity.this, "修改成功");
                                Intent mIntent = new Intent();
                                mIntent.putExtra("flag_main", 0);
                                mIntent.setClass(ManageSetingActivity.this, MainActivity.class);
                                startActivity(mIntent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        stopWaitingDialog();
                        ToastUtils.showShort(ManageSetingActivity.this, "网络异常，加载数据失败");

                    }
                });
    }

}
