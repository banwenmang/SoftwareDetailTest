package com.example.administrator.softwaredetailtest.two.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.administrator.softwaredetailtest.R;
import com.example.administrator.softwaredetailtest.two.RecycleViewAdapter;

import java.util.ArrayList;



public class RecycleViewFragment extends BaseFragment {


    RecyclerView mRecycleView;

    public static RecycleViewFragment newInstance() {
        RecycleViewFragment fragment = new RecycleViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycle_view, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecycleView = (RecyclerView) view.findViewById(R.id.id_stickynavlayout_innerscrollview);

        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false));
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("RecycleView----item-->>>>" + i);
        }

        final RecycleViewAdapter adapter = new RecycleViewAdapter(list);
        mRecycleView.setAdapter(adapter);
        adapter.setOnItemOnClickListener(new RecycleViewAdapter.OnItemOnClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Toast.makeText(getActivity(), "click " + position, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onItemLongClickListener(View view, int position) {
                Toast.makeText(getActivity(), "Long-click " + position, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public String getTitle() {
        return "RecycleView";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
