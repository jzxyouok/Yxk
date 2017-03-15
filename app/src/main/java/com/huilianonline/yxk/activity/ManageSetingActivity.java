package com.huilianonline.yxk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.huilianonline.yxk.MainActivity;
import com.huilianonline.yxk.R;

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

    }

    private void initView() {
        txtSave = (TextView) findViewById(R.id.txt_save_message);
        txtSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == txtSave){
            Intent mIntent = new Intent();
            mIntent.setClass(ManageSetingActivity.this, MainActivity.class);
            startActivity(mIntent);
        }
    }
}
