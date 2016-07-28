package com.shopons.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.shopons.R;
import com.shopons.view.fragment.BaseFragment;
import com.shopons.view.fragment.NewSearchFragment;
import com.shopons.view.fragment.SearchFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by deeps on 17/5/16.
 */
public class About extends AppCompatActivity {

    @Bind(R.id.about_Us)
    TextView about_Us;

    @Bind(R.id.info_label)
    TextView info;

    private ActionBar actionBar;

    public void BackActivity(View view)
    {
        //Intent intent = new Intent(getBaseContext(),MainActivity.class);
        //startActivity(intent);
        //finish();
        onBackPressed();
    }

    public void callStorePerson(View view) {
        Intent intent =
                new Intent(Intent.ACTION_DIAL);    //ACTION_CALL -to initiate call,add to manifest CALL_PHONE
        intent.setData(Uri.parse("tel:" + "+(91)-20-39548358"));
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        //actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);

        info.setTextColor(getResources().getColor(R.color.white));
        BaseFragment.setTypeFace(info);
        Intent intent = getIntent();
        BaseFragment.setRegularFont(about_Us);
        return;

    }

    public void share_method(View view)
    {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "extra text that you want to put");
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }


    @Override
    public void onBackPressed() {
        finish();

    }
}