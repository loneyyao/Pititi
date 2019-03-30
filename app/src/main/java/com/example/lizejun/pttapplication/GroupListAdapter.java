package com.example.lizejun.pttapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * create by lizejun
 * date 2018/9/3
 */
public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.ViewHolder> {

    private Context context;

    private List<GroupItemBean> data;

    public void setOnItemClickListener(PttRecyclerviewItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private PttRecyclerviewItemClickListener onItemClickListener;

    private int mSelectedPos = -1;//变量保存当前选中的position

    public GroupListAdapter(Context context, List<GroupItemBean> data) {
        this.context = context;
        this.data = data;
        //实现单选方法二： 设置数据集时，找到默认选中的pos
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).isSelected()) {
                mSelectedPos = i;
            }
        }
    }

    public void setData(List<GroupItemBean> data) {
        this.data = data;
        //实现单选方法二： 设置数据集时，找到默认选中的pos
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).isSelected()) {
                mSelectedPos = i;
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rc_ptt_group_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.groupName.setText(data.get(position).getGroupName());
        holder.checkBox.setChecked(data.get(position).isSelected());
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener!=null) {
                    //如果勾选的不是已经勾选状态的Item
                    if (mSelectedPos!=position){
                        //先取消上个item的勾选状态
                        data.get(mSelectedPos).setSelected(false);
                        notifyItemChanged(mSelectedPos);
                        //设置新Item的勾选状态
                        mSelectedPos = position;
                        data.get(mSelectedPos).setSelected(true);
                        notifyItemChanged(mSelectedPos);
                    }


                    onItemClickListener.onClick(position);
                }
            }
        });




    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView avatar;
        TextView groupName;
        CheckBox checkBox;
        RelativeLayout itemLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            groupName = itemView.findViewById(R.id.ptt_group_name);
            avatar = itemView.findViewById(R.id.img_group_avatar);
            checkBox = itemView.findViewById(R.id.ptt_checkbox);
            itemLayout = itemView.findViewById(R.id.ptt_group_layout);
        }
    }
}
