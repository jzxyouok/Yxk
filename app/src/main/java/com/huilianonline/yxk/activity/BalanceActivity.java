package com.huilianonline.yxk.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.huilianonline.yxk.R;

/**
 * 结算页
 * Created by admin on 2017/3/13.
 */
public class BalanceActivity extends BaseActivity implements View.OnClickListener{

    private TextView title;
    private View back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        initView();

    }

    private void initView() {
        title = (TextView) findViewById(R.id.tv_title_text);
        title.setText("结算");
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
