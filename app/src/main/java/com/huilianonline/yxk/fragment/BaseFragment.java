package com.huilianonline.yxk.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huilianonline.yxk.R;


/**
 * Fragment基类
 *
 * @author dewyze
 *
 */
public  class BaseFragment extends Fragment {

	private View mPreserveView;
	private LayoutInflater mLayoutInflater;
	private Dialog dialog;
	private Activity mActivity;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		return super.onCreateView(inflater, container, savedInstanceState);

		mLayoutInflater = inflater;
		if (mPreserveView != null) {
			Log.d("onCreateView", "onCreateView");
			return mPreserveView;
		} else {
			Log.d("onCreateView", "onCreateView");
			return onInitView(inflater, container);
		}
	}

	// Fragment初次载入时
	public View onInitView(LayoutInflater inflater, ViewGroup container) {
		return null;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.d("onDestroyView", "onDestroyView");
		mPreserveView = getView();
	}

	public LayoutInflater getLayoutInflater() {
		return mLayoutInflater;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
	}


	@Override
	public void onStop() {
		super.onStop();
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	/**
	 * 显示加载的对话框
	 *
	 * @param cancelable 是否可被返回键取消
	 */
	public void showWaitingDialog(boolean cancelable, final String msg) {
		if (dialog == null) {
			dialog = new Dialog(mActivity, R.style.custom_dialog_transparent) {
				@Override
				protected void onCreate(Bundle savedInstanceState) {
					super.onCreate(savedInstanceState);
					((TextView) dialog.findViewById(R.id.tv_msg)).setText(msg);
				}
			};
			dialog.setContentView(R.layout.lay_pro_loading_dialog);
			dialog.setCancelable(cancelable);
			dialog.setCanceledOnTouchOutside(cancelable);
		}
		dialog.show();
	}

	public void showWaitingDialog() {
		if (dialog == null) {
			dialog = new Dialog(mActivity, R.style.custom_dialog_transparent) {
				@Override
				protected void onCreate(Bundle savedInstanceState) {
					super.onCreate(savedInstanceState);
					((TextView) dialog.findViewById(R.id.tv_msg)).setText("加载中...");
				}
			};
			dialog.setContentView(R.layout.lay_pro_loading_dialog);
			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(true);
		}
		dialog.show();
	}

	/**
	 * 显示默认的对话框
	 */
	public void stopWaitingDialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}
	public void onResume() {
		super.onResume();
	}
	public void onPause() {
		super.onPause();
	}

}
