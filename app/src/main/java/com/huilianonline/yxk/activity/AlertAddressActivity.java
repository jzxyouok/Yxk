package com.huilianonline.yxk.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.huilianonline.yxk.R;

/**
 * Created by admin on 2017/3/13.
 */
public class AlertAddressActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;
    private View back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_address);
        initView();

    }

    private void initView() {
        title = (TextView) findViewById(R.id.tv_title_text);
        title.setText("修改收货地址");
        back = findViewById(R.id.ll_title_common_back);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == back){
            finish();
        }
    }
}
