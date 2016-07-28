package com.shopons.view.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.shopons.R;

/**
 * Created by komal on 20/5/16.
 */


public class CouponList extends BaseScreen {

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coupon_list);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
    }
}
