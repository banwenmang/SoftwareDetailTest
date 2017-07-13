package com.example.administrator.softwaredetailtest.one;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.softwaredetailtest.R;
import com.example.administrator.softwaredetailtest.two.SwipeRefreshLayoutActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout mRlMain;
    private RelativeLayout mRlTopHeader;
    private RelativeLayout mRlAddTopicTag;
    private TextView mTvBack;
    private TextView mTvShare;

    private LinearLayoutManager mLiManager;
    private SwipeRefreshLayout mSrlSoftDetail;
    private RecyclerView mRlvSoftDetail;

    private LinearLayout mLlFirstBottomWrap;
    private TextView mTvOpenApp;
    private TextView mTvFunctionInto;

    private LinearLayout mLlPublishFunctionWrap;
    private TextView mTvPublishFunction;

    private SoftwareDetailAdapter mSoftwareDetailAdapter;
    private List<NullData> mDatas;

    private LinearLayout mChildAt;      // 需悬浮的话题标签条目
    private RelativeLayout mRlChildAt;  // 话题标签的父条目 -- 同时是 recycleview 的第 4 个子条目
    private boolean isShowHead;

    /**
     * recyleview 第一个条目 距底部的高度
     */
    private float mFirstBottom;

    /**
     * 底部条目的 淡入淡出
     */
    private float mAlpha;
    private float mFirstDefaultHeight;


    /**
     * recycleview 标签条目 距底部的高度
     */
    private float mSecondBottom;
    private float mSecondDefaultHeight;


    private float mRLMainHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRlMain = (RelativeLayout) findViewById(R.id.rl_main);
        mRlMain.post(new Runnable() {
            @Override
            public void run() {
                mRLMainHeight = mRlMain.getHeight();
            }
        });
        mRlTopHeader = (RelativeLayout) findViewById(R.id.rl_top_header);
        mRlAddTopicTag = (RelativeLayout) findViewById(R.id.rl_add_topic_tag);
        mTvBack = (TextView) findViewById(R.id.tv_back);
        mTvShare = (TextView) findViewById(R.id.tv_share);

        mLiManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mSrlSoftDetail = (SwipeRefreshLayout) findViewById(R.id.srl_software_detail);
        mRlvSoftDetail = (RecyclerView) findViewById(R.id.rlv_software_detail);
        mRlvSoftDetail.setLayoutManager(mLiManager);

        mSoftwareDetailAdapter = new SoftwareDetailAdapter();
        mRlvSoftDetail.setAdapter(mSoftwareDetailAdapter);

        mLlFirstBottomWrap = (LinearLayout) findViewById(R.id.ll_first_bottom_wrap);
        mTvOpenApp = (TextView) findViewById(R.id.tv_open_app);
        mTvOpenApp.setOnClickListener(this);

        mTvFunctionInto = (TextView) findViewById(R.id.tv_function_into);
        mTvFunctionInto.setOnClickListener(this);

        mLlPublishFunctionWrap = (LinearLayout) findViewById(R.id.ll_publish_function_wrap);
        mTvPublishFunction = (TextView) findViewById(R.id.tv_publish_function);

        mDatas = new ArrayList<>();
        initData(20);
        mSoftwareDetailAdapter.setData(mDatas);

        mFirstDefaultHeight = dp2px(this, 48);
        mSecondDefaultHeight = dp2px(this, 60);
        initListener();

        startActivity(new Intent(this, SwipeRefreshLayoutActivity.class));
    }

    private void initListener() {
        mSoftwareDetailAdapter.setOnSoftwareDetailClickListener(new SoftwareDetailAdapter.OnSoftwareDetailClickListener() {
            @Override
            public void onCalculateFuctionClick() {
                if (mLlPublishFunctionWrap.getVisibility() != View.GONE)
                    mLlPublishFunctionWrap.setVisibility(View.GONE);

                Toast.makeText(MainActivity.this, "新功能预测", Toast.LENGTH_SHORT).show();
                initData(20);
                mSoftwareDetailAdapter.notifyDataSetChanged();
            }

            @Override
            public void onGetFunctionClick() {
                if (mLlPublishFunctionWrap.getVisibility() != View.VISIBLE)
                    mLlPublishFunctionWrap.setVisibility(View.VISIBLE);

                Toast.makeText(MainActivity.this, "我要新功能", Toast.LENGTH_SHORT).show();
                initData(3);
                mSoftwareDetailAdapter.notifyDataSetChanged();
            }
        });

        mRlvSoftDetail.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                try {
                    if (mLlFirstBottomWrap.getVisibility() == View.VISIBLE) {
                        mFirstBottom = mRLMainHeight - mLiManager.getChildAt(0).getBottom();
                        mAlpha = mFirstBottom / mFirstDefaultHeight;
                        mLlFirstBottomWrap.setAlpha(1 - mAlpha);
                    }

                    if (mLlPublishFunctionWrap.getVisibility() == View.VISIBLE && mRLMainHeight - mLiManager.getChildAt(3).getBottom() < mSecondDefaultHeight) {
                        mLlPublishFunctionWrap.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (mLlPublishFunctionWrap.getVisibility() == View.VISIBLE)
                        mLlPublishFunctionWrap.setVisibility(View.GONE);
                }

                if (mLiManager.findFirstVisibleItemPosition() == 0 && mLiManager.findLastVisibleItemPosition() == 2) {
                    if (mLlFirstBottomWrap.getVisibility() != View.GONE) {
                        mLlFirstBottomWrap.setVisibility(View.GONE);
                    }
                } else if ((mLiManager.findFirstVisibleItemPosition() == 0 && mLiManager.findLastVisibleItemPosition() == 1) || mLiManager.findLastCompletelyVisibleItemPosition() == 0) {
                    if (mLlFirstBottomWrap.getVisibility() != View.VISIBLE) {
                        mLlFirstBottomWrap.setVisibility(View.VISIBLE);
                    }

                    if (mLiManager.findLastCompletelyVisibleItemPosition() == 0) {
                        mLlFirstBottomWrap.setAlpha(1);
                    }
                }

                if (mChildAt == null) {
                    try {
                        mRlChildAt = (RelativeLayout) mLiManager.getChildAt(3);
                        mChildAt = (LinearLayout) mRlChildAt.getChildAt(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (null == mChildAt) return;
                if (mLiManager.findFirstVisibleItemPosition() > 1 && !isShowHead) {
                    ((ViewGroup) mChildAt.getParent()).removeView(mChildAt);
                    mRlTopHeader.setVisibility(View.VISIBLE);
                    mRlAddTopicTag.addView(mChildAt);
                    isShowHead = true;
                } else if (mLiManager.findFirstVisibleItemPosition() <= 1 && isShowHead) {
                    mRlTopHeader.setVisibility(View.GONE);
                    ((ViewGroup) mChildAt.getParent()).removeView(mChildAt);
                    mRlChildAt.addView(mChildAt);
                    isShowHead = false;
                }
            }
        });
    }

    private void initData(int size) {
        mDatas.clear();
        for (int i = 0; i < size; i++) {
            mDatas.add(new NullData());
        }
    }

    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp值
     * @return px值
     */
    public float dp2px(Context context, float dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }


    @Override
    public void onClick(View v) {
        // 打开应用
        if (v == mTvOpenApp) {

        }

        // 功能入口
        else if (v == mTvFunctionInto) {

        }

        // 我要新功能
        else if (v == mTvPublishFunction) {

        }
    }
}
