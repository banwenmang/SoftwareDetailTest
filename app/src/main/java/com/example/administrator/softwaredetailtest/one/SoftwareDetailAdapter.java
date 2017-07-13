package com.example.administrator.softwaredetailtest.one;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.softwaredetailtest.R;

import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */

public class SoftwareDetailAdapter extends RecyclerView.Adapter {

    private static final int KEY_HEAD = 0XFF;           // 顶部描述
    private static final int KEY_FUNCTION_INTO = 0XFD;  // 功能入口
    private static final int KEY_APP_INSTRUCTION = 0XFC;// 软件不会用
    private static final int KEY_TAG = 0XFB;            // 话题标签
    private static final int KEY_TOPIC_ITEM = 0XFA;     // 话题条目

    private List<NullData> mDatas;
    private OnSoftwareDetailClickListener mOnSoftwareDetailClickListener;

    public void setData(List<NullData> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 4 : mDatas.size() + 4;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return KEY_HEAD;
        } else if (position == 1) {
            return KEY_FUNCTION_INTO;
        } else if (position == 2) {
            return KEY_APP_INSTRUCTION;
        } else if (position == 3) {
            return KEY_TAG;
        } else {
            return KEY_TOPIC_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == KEY_HEAD) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_software_detail_header, parent, false);
            viewHolder = new HeaderHolder(view);
        } else if (viewType == KEY_FUNCTION_INTO) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_software_detail_function_into, parent, false);
            viewHolder = new FunctionIntoHolder(view);
        } else if (viewType == KEY_APP_INSTRUCTION) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_software_detail_app_instruction, parent, false);
            viewHolder = new AppInstructionHolder(view);
        } else if (viewType == KEY_TAG) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_software_detail_tag, parent, false);
            viewHolder = new TagHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_software_detail_topic, parent, false);
            viewHolder = new TopicItemAdapter(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int itemType = getItemViewType(position);
        switch (itemType) {
            case KEY_HEAD:
                break;
            case KEY_FUNCTION_INTO:
                break;
            case KEY_APP_INSTRUCTION:
                break;
            case KEY_TAG:
                TagHolder tagHolder = (TagHolder) holder;
                tagHolder.mTvCalculateFunction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnSoftwareDetailClickListener != null)
                            mOnSoftwareDetailClickListener.onCalculateFuctionClick();
                    }
                });

                tagHolder.mTvGetFunction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnSoftwareDetailClickListener != null)
                            mOnSoftwareDetailClickListener.onGetFunctionClick();
                    }
                });

                break;
            case KEY_TOPIC_ITEM:
                break;
        }
    }

    static class HeaderHolder extends RecyclerView.ViewHolder {

        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }

    static class FunctionIntoHolder extends RecyclerView.ViewHolder {

        public FunctionIntoHolder(View itemView) {
            super(itemView);
        }
    }

    static class AppInstructionHolder extends RecyclerView.ViewHolder {

        public AppInstructionHolder(View itemView) {
            super(itemView);
        }
    }

    static class TagHolder extends RecyclerView.ViewHolder {

        TextView mTvCalculateFunction;
        TextView mTvGetFunction;

        public TagHolder(View itemView) {
            super(itemView);

            mTvCalculateFunction = (TextView) itemView.findViewById(R.id.tv_calculate_function);
            mTvGetFunction = (TextView) itemView.findViewById(R.id.tv_get_function);
        }
    }

    static class TopicItemAdapter extends RecyclerView.ViewHolder {

        public TopicItemAdapter(View itemView) {
            super(itemView);
        }
    }

    public void setOnSoftwareDetailClickListener(OnSoftwareDetailClickListener onSoftwareDetailClickListener) {
        this.mOnSoftwareDetailClickListener = onSoftwareDetailClickListener;
    }

    interface OnSoftwareDetailClickListener {
        /**
         * 新功能预测标签 点击事件
         */
        void onCalculateFuctionClick();

        /**
         * 我要新功能标签 点击事件
         */
        void onGetFunctionClick();
    }
}
