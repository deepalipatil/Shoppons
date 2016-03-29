package com.shopons.model;

import com.shopons.domain.Store;

/**
 * This could be either place or restaurant
 */
public class SearchResult
{
    public static final int RESTAURANT = 1;
    private int mType;
    private Store mStoar;
    public int getType() {
        return mType;
    }


    public Store getName() {
        return mStoar;
    }


    private SearchResult(final Store store) {
        mType = RESTAURANT;
        mStoar = store;
    }

    public static SearchResult Get(final Store stoar) {
        return new SearchResult(stoar);
    }


}
