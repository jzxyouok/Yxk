package com.huilianonline.yxk.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.huilianonline.yxk.R;
import com.huilianonline.yxk.view.refresh.NoScrollGridView;

import java.util.Objects;

/**
 * Created by admin on 2017/3/2.
 */
public class MineFragment extends BaseFragment{

    private Activity mActivity;
    private TextView title;
    private ImageView imgRight;
    private NoScrollGridView gridView;
    private MineListDataAdapter adapter;
    private int[] resourses = {R.drawable.img_mine_daishouhuo,R.drawable.img_mine_yishouhuo,R.drawable.img_mine_shoubaozhang,
            R.drawable.img_mine_daifukuan,R.drawable.img_mine_xiaoxi};
    private String[] names = {"待收货","已收货","售后保障","待付款","消息"};

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initView(View view) {
        gridView = (NoScrollGridView) view.findViewById(R.id.gridview_mine);
        title = (TextView) view.findViewById(R.id.txt_title);
        imgRight = (ImageView) view.findViewById(R.id.img_saoyisao);
        title.setText("我的");
        imgRight.setImageResource(R.drawable.img_mine_telphone);
        adapter = new MineListDataAdapter();
        gridView.setAdapter(adapter);

    }

    public class MineListDataAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return resourses.length;
        }

        @Override
        public Objects getItem(int position) {
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
                convertView = LayoutInflater.from(mActivity).inflate(R.layout.item_class, null);
                holder.imgClassIcon = (ImageView) convertView.findViewById(R.id.img_class_icon);
                holder.txtClassIcon = (TextView) convertView.findViewById(R.id.img_class_name);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            Glide.with(mActivity)
                    .load(resourses[position])
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade()
                    .dontAnimate()
                    .into(holder.imgClassIcon);
            holder.txtClassIcon.setText(names[position]);
            return convertView;
        }

        class Holder {
            private ImageView imgClassIcon;
            private TextView txtClassIcon;
        }
    }
}