package com.shopons.model;

/**
 * Created by deepali on 29/3/16.
 */
public class DrawerMenu {

    private String mText;
    private int mIconId;
    private int mSelectedIconId;
    private String mIconString;
    private boolean mIsSelected;

    public DrawerMenu(final String text, final int iconId, final int selectedIconId) {
        mText = text;
        mIconId = iconId;
        mSelectedIconId = selectedIconId;
    }

    public DrawerMenu() {

    }

    public String getIconString() {
        return mIconString;
    }

    public void setIconString(String iconString) {
        mIconString = iconString;
    }

    public int getIconId() {
        return mIconId;
    }

    public void setIconId(int iconId) {
        mIconId = iconId;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public void setIsSelected(boolean isSelected) {
        mIsSelected = isSelected;
    }

    public int getSelectedIconId() {
        return mSelectedIconId;
    }

    public void setSelectedIconId(int selectedIconId) {
        mSelectedIconId = selectedIconId;
    }
}

