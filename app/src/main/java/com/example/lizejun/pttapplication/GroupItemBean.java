package com.example.lizejun.pttapplication;

/**
 * create by lizejun
 * date 2018/9/3
 */
public class GroupItemBean {


    public GroupItemBean(String groupName, boolean isSelected) {
        this.groupName = groupName;
        this.isSelected = isSelected;
    }


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String groupName;

    public boolean isSelected;


}
