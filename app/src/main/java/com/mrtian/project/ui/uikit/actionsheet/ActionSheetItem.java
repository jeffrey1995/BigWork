package com.mrtian.project.ui.uikit.actionsheet;

/**
 * Created by tianxiying on 2017/3/22.
 */

public class ActionSheetItem {
    public enum Type {
        NORMAL,
        LINK,
        WARN,
    }

    private Type mType;

    private String mName;

    public ActionSheetItem() {

    }

    public ActionSheetItem(Type type, String name) {
        mType = type;
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Type getType() {
        return mType;
    }

    public void setType(Type type) {
        mType = type;
    }


}
