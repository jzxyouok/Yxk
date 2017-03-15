package com.huilianonline.chinagrassland.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.huilianonline.chinagrassland.R;
import com.huilianonline.chinagrassland.global.ConstantValues;
import com.huilianonline.chinagrassland.utils.GlideImageLoader;
import com.huilianonline.chinagrassland.utils.Json_U;
import com.huilianonline.chinagrassland.utils.PhoneInfoUtil;
import com.huilianonline.chinagrassland.utils.ToastUtils;
import com.huilianonline.chinagrassland.view.refresh.PullToRefreshBase;
import com.huilianonline.chinagrassland.view.refresh.PullToRefreshListView;
import com.huilianonline.chinagrassland.vo.NewsListBean;
import com.huilianonline.chinagrassland.vo.SearchHotBean;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by liujiqing on 2016/9/18.
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private PullToRefreshListView mListSearch;
    private TextView mtxtCancle;
    private EditText mEdtInput;
    private ImageView mIvCleanAllInputSearch;
    private ListView mListView;
    private int curPage = 1;
    private SearchHotListAdapter adapter;
    private List<SearchHotBean.DataBeanA.DataBean> datalists = new ArrayList<>();
    private String mCurrentKw = "";

    //搜索结果相关
    private View layoutKey;
    private View layoutResult;
    private PullToRefreshListView mResultListSearch;
    private ListView mResultListView;
    private int curResultPage = 1;
    private NewsListAdapter mResultadapter;
    private List<NewsListBean.DataBeanA.DataBean> mResultdatalists = new ArrayList<>();
    private String kw;
    private View layoutEmputy;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initData();
    }


    private void initView() {
        mListSearch = (PullToRefreshListView) findViewById(R.id.list_search_hot_tip);
        mListView = mListSearch.getRefreshableView();
        mListSearch.setMode(PullToRefreshBase.Mode.BOTH);
        mListSearch.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                curPage = 1;
                getSearchHotListsData(curPage, "10", mCurrentKw);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                curPage++;
                getSearchHotListsData(curPage, "10", mCurrentKw);
            }
        });

        mListSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                layoutKey.setVisibility(View.GONE);
                layoutResult.setVisibility(View.VISIBLE);
                kw = datalists.get(position - 1).getText();
                mEdtInput.setText(kw);
                getMessageListsData(curResultPage, "10", kw);
            }
        });

        mListSearch.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem > 2) {
                    PhoneInfoUtil.closeSoftWareInput(SearchActivity.this);
                }
            }
        });

        mtxtCancle = (TextView) findViewById(R.id.tv_search_cancle);
        mtxtCancle.setOnClickListener(this);

        mEdtInput = (EditText) findViewById(R.id.edt_search_text);
        mIvCleanAllInputSearch = (ImageView) this.findViewById(R.id.iv_cancle_input_search);
        mIvCleanAllInputSearch.setOnClickListener(this);

        mEdtInput.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mEdtInput.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        mEdtInput.setSingleLine(true);

        mEdtInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    String input = v.getText().toString().trim();
                    if (TextUtils.isEmpty(input)) {
                        ToastUtils.showShort(SearchActivity.this, "输入不能为空！");
                    } else {
//                        PhoneInfoUtil.closeSoftWareInput(SearchActivity.this);
                        layoutKey.setVisibility(View.GONE);
                        layoutResult.setVisibility(View.VISIBLE);
                        kw = v.getText().toString();
                        getMessageListsData(curResultPage, "10", kw);
                    }
                    return true;
                }
                return false;
            }
        });

        mEdtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                mIvCleanAllInputSearch.setVisibility(View.VISIBLE);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //s:变化后的所有字符
                if (s.toString().trim().equals("")) {
                    mIvCleanAllInputSearch.setVisibility(View.INVISIBLE);
                    mCurrentKw = "";
                } else {
                    mIvCleanAllInputSearch.setVisibility(View.VISIBLE);
                    mCurrentKw = s.toString().trim();
                }
            }
        });


        //搜索结果相关----------------------------------------------------------------------------------------------
        layoutKey = findViewById(R.id.layout_search_keyword);
        layoutResult = findViewById(R.id.layout_search_result);
        layoutEmputy = findViewById(R.id.layout_emputy_view);
        layoutResult.setVisibility(View.GONE);
        layoutKey.setVisibility(View.VISIBLE);
        mResultListSearch = (PullToRefreshListView) findViewById(R.id.list_search_hot_result);
        mResultListView = mResultListSearch.getRefreshableView();
        mResultListSearch.setMode(PullToRefreshBase.Mode.BOTH);
        mResultListSearch.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    curResultPage = 1;
                    getMessageListsData(curResultPage, "10", kw);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    curResultPage++;
                    getMessageListsData(curResultPage, "10", kw);
            }
        });

        mResultListSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent mIntent = new Intent();
                    mIntent.setClass(SearchActivity.this, ArticleDetailsActivity.class);
                    mIntent.putExtra("GeneralID", mResultdatalists.get(position - 1).getGeneralID());
                    Log.e("tag==", mResultdatalists.get(position - 1).getGeneralID() + "");
                    startActivity(mIntent);

            }
        });

    }

    private void initData() {
        getSearchHotListsData(curPage, "10", mCurrentKw);
    }

    @Override
    public void onClick(View v) {
        if (v == mtxtCancle) {
            finish();
            PhoneInfoUtil.closeSoftWareInput(SearchActivity.this);
        } else if (v == mIvCleanAllInputSearch) {
            mEdtInput.setText("");
            mEdtInput.setCursorVisible(true);
            layoutResult.setVisibility(View.GONE);
            layoutKey.setVisibility(View.VISIBLE);
            curPage = 1;
            getSearchHotListsData(curPage, "10", mCurrentKw);
        }
    }

    private void getSearchHotListsData(int pi, String pc, String words) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("pi", String.valueOf(pi));
        params.addQueryStringParameter("pc", pc);
        params.addQueryStringParameter("words", words);
        HttpUtils http = new HttpUtils();
        showWaitingDialog();
        http.send(HttpRequest.HttpMethod.POST,
                ConstantValues.SEARCH_HOT_KEYWORD,
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        stopWaitingDialog();
                        String json = responseInfo.result;
                        Log.e("tag==", json);
                        int totalPage = -1;
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            JSONObject object = jsonObject.getJSONObject("data");
                            totalPage = object.getInt("totalPage");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("NewsListActivity", json);
                        if (totalPage == 0) {
                            datalists.clear();
                            adapter = new SearchHotListAdapter(datalists);
                            mListSearch.setAdapter(adapter);
                            mListSearch.onRefreshComplete();
                            adapter.notifyDataSetChanged();
                            ToastUtils.showShort(SearchActivity.this, "暂无数据");
                        } else {
                            SearchHotBean newsListBean = Json_U.fromJson(json, SearchHotBean.class);
                            if (newsListBean != null) {
                                List<SearchHotBean.DataBeanA.DataBean> temp = newsListBean.getData().getData();
                                if (temp != null && temp.size() > 0) {
                                    if (datalists.contains(temp)) {
                                        ToastUtils.showShort(SearchActivity.this, "无更多数据");
                                        return;
                                    }
                                    if (curPage > totalPage) {
                                        ToastUtils.showShort(SearchActivity.this, "无更多数据");
                                        mListSearch.onRefreshComplete();
                                    } else {
                                        datalists.addAll(temp);
                                        if (curPage == 1) {
                                            datalists.clear();
                                            datalists.addAll(temp);
                                            adapter = new SearchHotListAdapter(datalists);
                                            mListSearch.setAdapter(adapter);
                                            mListSearch.onRefreshComplete();
                                            adapter.notifyDataSetChanged();

                                        } else {
                                            if (adapter == null) {
                                                adapter = new SearchHotListAdapter(datalists);
                                                mListSearch.setAdapter(adapter);
                                            } else {
                                                adapter.notifyDataSetChanged();
                                            }
                                            mListSearch.onRefreshComplete();

                                        }
                                    }
                                } else {
                                    ToastUtils.showShort(SearchActivity.this, "无更多数据");
                                }
                            } else {
                                ToastUtils.showShort(SearchActivity.this, "无更多数据");
                            }
                        }

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        stopWaitingDialog();
                        ToastUtils.showShort(SearchActivity.this, "网络异常，加载数据失败");
                    }
                });
    }

    public class SearchHotListAdapter extends BaseAdapter {

        private List<SearchHotBean.DataBeanA.DataBean> mdatalists;

        public SearchHotListAdapter(List<SearchHotBean.DataBeanA.DataBean> datalistst) {
            this.mdatalists = datalistst;
        }

        @Override
        public int getCount() {
            return mdatalists.size();
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
                convertView = LayoutInflater.from(SearchActivity.this).inflate(R.layout.item_search_hot, null);
                holder.textContent = (TextView) convertView.findViewById(R.id.tv_search_hot_text);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.textContent.setText(mdatalists.get(position).getText());

            return convertView;
        }

        class Holder {
            private TextView textContent;

        }
    }



    //-----------------------------搜索结果相关---------------------------------------------------------------------------------

    private void getMessageListsData(int pi, String pc, String mkw) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("pi", String.valueOf(pi));
        params.addQueryStringParameter("pc", pc);
        params.addQueryStringParameter("kw", mkw);
        HttpUtils http = new HttpUtils();
        showWaitingDialog();
        http.send(HttpRequest.HttpMethod.POST,
                ConstantValues.NEWS_LISTS_URL,
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        stopWaitingDialog();
                        String json = responseInfo.result;
                        int totalPage = -1;
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            JSONObject object = jsonObject.getJSONObject("data");
                            totalPage = object.getInt("totalPage");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("NewsListActivity", json);
                        if (totalPage == 0) {
                            layoutEmputy.setVisibility(View.VISIBLE);
                            mResultListSearch.setVisibility(View.GONE);
                            ToastUtils.showShort(SearchActivity.this, "暂无数据");
                        } else {
                            layoutEmputy.setVisibility(View.GONE);
                            mResultListSearch.setVisibility(View.VISIBLE);
                            NewsListBean newsListBean = Json_U.fromJson(json, NewsListBean.class);
                            if (newsListBean != null) {
                                List<NewsListBean.DataBeanA.DataBean> temp = newsListBean.getData().getData();
                                if (temp != null && temp.size() > 0) {
                                    if (mResultdatalists.contains(temp)) {
                                        ToastUtils.showShort(SearchActivity.this, "无更多数据");
                                        return;
                                    }
                                    if (curResultPage > totalPage) {
                                        ToastUtils.showShort(SearchActivity.this, "无更多数据");
                                        mResultListSearch.onRefreshComplete();
                                    } else {
                                        mResultdatalists.addAll(temp);
                                        if (curResultPage == 1) {
                                            mResultdatalists.clear();
                                            mResultdatalists.addAll(temp);
                                            mResultadapter = new NewsListAdapter(mResultdatalists);
                                            mResultListSearch.setAdapter(mResultadapter);
                                            mResultListSearch.onRefreshComplete();
                                            mResultadapter.notifyDataSetChanged();

                                        } else {
                                            if (mResultadapter == null) {
                                                mResultadapter = new NewsListAdapter(mResultdatalists);
                                                mResultListSearch.setAdapter(mResultadapter);
                                            } else {
                                                mResultadapter.notifyDataSetChanged();
                                            }
                                            mResultListSearch.onRefreshComplete();

                                        }
                                    }
                                } else {
                                    ToastUtils.showShort(SearchActivity.this, "无更多数据");
                                }
                            } else {
                                ToastUtils.showShort(SearchActivity.this, "无更多数据");
                            }
                        }

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        stopWaitingDialog();
                        ToastUtils.showShort(SearchActivity.this, "网络异常，加载数据失败");
                        layoutEmputy.setVisibility(View.VISIBLE);
                        mResultListSearch.setVisibility(View.GONE);
                    }
                });
    }


    public class NewsListAdapter extends BaseAdapter {

        private List<NewsListBean.DataBeanA.DataBean> mresultdatalists;
        private GlideImageLoader loader;
        private int width;

        public NewsListAdapter(List<NewsListBean.DataBeanA.DataBean> datalistst) {
            this.mresultdatalists = datalistst;
            loader = new GlideImageLoader(SearchActivity.this);
            width = PhoneInfoUtil.getInstance().getDisplayWidth(SearchActivity.this);
        }

        @Override
        public int getCount() {
            return mresultdatalists.size();
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
                convertView = LayoutInflater.from(SearchActivity.this).inflate(R.layout.item_news_list, null);
                holder.textNewsContent = (TextView) convertView.findViewById(R.id.tv_news_list_shotcontent);
                holder.textNewsTime = (TextView) convertView.findViewById(R.id.tv_news_list_time);
                holder.textNewsFrom = (TextView) convertView.findViewById(R.id.tv_news_list_from);
                holder.mImgPic = (ImageView) convertView.findViewById(R.id.img_news_list_pic);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.textNewsContent.setText(mresultdatalists.get(position).getTitle());
            holder.textNewsTime.setText(mresultdatalists.get(position).getInputTime());
            holder.textNewsFrom.setText(mresultdatalists.get(position).getNodeName());
            String img_url = mresultdatalists.get(position).getDefaultPicUrl();
            if (!img_url.equals("")) {
                if (!holder.mImgPic.isShown()){
                    holder.mImgPic.setVisibility(View.VISIBLE);
                }
                try {
                    loader.display(holder.mImgPic, img_url, R.drawable.shape_def_pic);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                holder.mImgPic.setVisibility(View.GONE);
            }
            return convertView;
        }

        class Holder {
            private TextView textNewsContent;
            private TextView textNewsTime;
            private TextView textNewsFrom;
            private ImageView mImgPic;
        }
    }

    //-----------------------------搜索结果相关---------------------------------------------------------------------------------

}
