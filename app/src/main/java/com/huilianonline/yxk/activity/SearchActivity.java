package com.huilianonline.yxk.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.huilianonline.yxk.R;

/**
 * Created by admin on 2017/3/13.
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener{

    private TextView txtCancle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
    }

    private void initView() {
        txtCancle = (TextView) findViewById(R.id.tv_search_cancle);
        txtCancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == txtCancle){
            finish();
        }
    }
}
