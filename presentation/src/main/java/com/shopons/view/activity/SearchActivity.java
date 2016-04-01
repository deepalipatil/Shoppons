package com.shopons.view.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.shopons.R;
import com.shopons.view.fragment.NewSearchFragment;
import com.shopons.view.fragment.SearchFragment;

public class SearchActivity extends BaseScreen {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, NewSearchFragment.GetInstance(),
                SearchFragment.TAG);
        transaction.commit();
    }
}
