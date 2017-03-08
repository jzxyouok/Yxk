package com.huilianonline.yxk;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huilianonline.yxk.activity.BaseActivity;
import com.huilianonline.yxk.fragment.ClassFragment;
import com.huilianonline.yxk.fragment.HomeFragment;
import com.huilianonline.yxk.fragment.MineFragment;
import com.huilianonline.yxk.fragment.ShopCarFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private FragmentManager fragmentManager;
    private HomeFragment homeFragment;
    private ClassFragment classFragment;
    private ShopCarFragment shopCarFragment;
    private MineFragment mineFragment;
    private View layoutHome;
    private ImageView imgHome;
    private TextView txtHome;

    private View layoutClass;
    private ImageView imgClass;
    private TextView txtClass;

    private View layoutShopcar;
    private ImageView imgShopcar;
    private TextView txtShopcar;

    private View layoutMine;
    private ImageView imgMine;
    private TextView txtMine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        initViews();
        setTabSelection(0);
    }

    private void initViews() {
        layoutHome = findViewById(R.id.ll_tab_home);
        imgHome = (ImageView) findViewById(R.id.img_tab_home);
        txtHome = (TextView) findViewById(R.id.txt_tab_home);
        layoutHome.setOnClickListener(this);

        layoutClass = findViewById(R.id.ll_tab_class);
        imgClass = (ImageView) findViewById(R.id.img_tab_class);
        txtClass = (TextView) findViewById(R.id.txt_tab_class);
        layoutClass.setOnClickListener(this);

        layoutShopcar = findViewById(R.id.ll_tab_shopcar);
        imgShopcar = (ImageView) findViewById(R.id.img_tab_shopcar);
        txtShopcar = (TextView) findViewById(R.id.txt_tab_shopcar);
        layoutShopcar.setOnClickListener(this);

        layoutMine = findViewById(R.id.ll_tab_mine);
        imgMine = (ImageView) findViewById(R.id.img_tab_mine);
        txtMine = (TextView) findViewById(R.id.txt_tab_mine);
        layoutMine.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == layoutHome){
            setTabSelection(0);
        }else if (v == layoutClass){
            setTabSelection(1);
        }else if (v == layoutShopcar){
            setTabSelection(2);
        }else if (v == layoutMine){
            setTabSelection(3);
        }
    }

    private void setTabSelection(int index) {
        clearSelection();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                imgHome.setImageResource(R.drawable.img_tab_home_press);
                txtHome.setTextColor(Color.parseColor("#D53C32"));
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.fragment_content, homeFragment);
                } else {
                    transaction.show(homeFragment);
                }
                break;
            case 1:
                imgClass.setImageResource(R.drawable.img_tab_class_press);
                txtClass.setTextColor(Color.parseColor("#D53C32"));
                if (classFragment == null) {
                    classFragment = new ClassFragment();
                    transaction.add(R.id.fragment_content, classFragment);
                } else {
                    transaction.show(classFragment);
                }
                break;
            case 2:
                imgShopcar.setImageResource(R.drawable.img_tab_shopcar_press);
                txtShopcar.setTextColor(Color.parseColor("#D53C32"));
                if (shopCarFragment == null) {
                    shopCarFragment = new ShopCarFragment();
                    transaction.add(R.id.fragment_content, shopCarFragment);
                } else {
                    transaction.show(shopCarFragment);
                }
                break;
            case 3:
                imgMine.setImageResource(R.drawable.img_tab_mine_press);
                txtMine.setTextColor(Color.parseColor("#D53C32"));
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    transaction.add(R.id.fragment_content, mineFragment);
                } else {
                    transaction.show(mineFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (classFragment != null) {
            transaction.hide(classFragment);
        }
        if (shopCarFragment != null) {
            transaction.hide(shopCarFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }
    }

    private void clearSelection() {
        imgHome.setImageResource(R.drawable.img_tab_home_normal);
        txtHome.setTextColor(Color.parseColor("#666666"));

        imgClass.setImageResource(R.drawable.img_tab_class_normal);
        txtClass.setTextColor(Color.parseColor("#666666"));

        imgShopcar.setImageResource(R.drawable.img_tab_shopcar_normal);
        txtShopcar.setTextColor(Color.parseColor("#666666"));

        imgMine.setImageResource(R.drawable.img_tab_mine_normal);
        txtMine.setTextColor(Color.parseColor("#666666"));
    }
}
