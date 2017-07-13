package com.example.administrator.softwaredetailtest.two;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;


import com.example.administrator.softwaredetailtest.R;
import com.example.administrator.softwaredetailtest.three.SecondActivity;
import com.example.administrator.softwaredetailtest.two.fragment.BaseFragment;
import com.example.administrator.softwaredetailtest.two.fragment.ListViewFragment;
import com.example.administrator.softwaredetailtest.two.fragment.RecycleViewFragment;
import com.example.administrator.softwaredetailtest.view.StickyNavLayout;

import java.util.ArrayList;

/**
 * @author 顾修忠-guxiuzhong@youku.com/gfj19900401@163.com
 * @Title: SwipeRefreshLayoutActivity
 * @Package com.gxz.stickynavlayout.activity
 * @Description:
 * @date 16/3/27
 * @time 下午9:50
 */
public class SwipeRefreshLayoutActivity extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;

    ViewPager viewPager;
    TabLayout tabLayout;

    StickyNavLayout stickyNavLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swip_pull_to_refresh);


        ArrayList<BaseFragment> fragments = new ArrayList<>();
        fragments.add(ListViewFragment.newInstance());
        fragments.add(RecycleViewFragment.newInstance());

        FragmentsViewPagerAdapter adapter = new FragmentsViewPagerAdapter(getSupportFragmentManager(), fragments);

        viewPager = (ViewPager) findViewById(R.id.id_stickynavlayout_viewpager);
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.id_stickynavlayout_indicator);

        tabLayout.addTab(tabLayout.newTab().setText("新功能预测"));
        tabLayout.addTab(tabLayout.newTab().setText("我要新功能"));
        tabLayout.setupWithViewPager(viewPager);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.swipe_color_5,
                R.color.swipe_color_6);

        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.swipe_color_1);

        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        stickyNavLayout = (StickyNavLayout) findViewById(R.id.id_stick);
        stickyNavLayout.setOnStickStateChangeListener(onStickStateChangeListener);

        //startActivity(new Intent(this, SecondActivity.class));
    }

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {

        @Override
        public void onRefresh() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
        }
    };
    private StickyNavLayout.onStickStateChangeListener onStickStateChangeListener = new StickyNavLayout.onStickStateChangeListener() {
        @Override
        public void isStick(boolean isStick) {

        }

        @Override
        public void scrollPercent(float percent) {
            if (percent == 0) {
                swipeRefreshLayout.setEnabled(true);
                swipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
            } else {
                swipeRefreshLayout.setEnabled(false);
                swipeRefreshLayout.setOnRefreshListener(null);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
