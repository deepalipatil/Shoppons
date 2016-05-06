package com.shopons.model;

import com.shopons.domain.Store;

/**
 * This could be either place or restaurant
 */
public class SearchResult
{
    public static final int RESTAURANT = 1;
    private int mType;
    private Store mStore;
    public int getType() {
        return mType;
    }


    public Store getName() {
        return mStore;
    }


    private SearchResult(final Store store) {
        mType = RESTAURANT;
        mStore = store;
    }

    public static SearchResult Get(final Store store) {
        return new SearchResult(store);
    }


}
