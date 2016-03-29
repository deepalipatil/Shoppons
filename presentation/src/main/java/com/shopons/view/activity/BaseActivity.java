package com.shopons.view.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

import com.shopons.R;

/**
 *
 */
public class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor();
       //overridePendingTransition(R.anim.in_right,R.anim.out_left);
    }


    @Override
    protected void onStart() {
        super.onStart();
       // Appboy.getInstance(this).openSession(this);
    }

    protected void changeStatusBarColor(final int colorId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(colorId));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.black));
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
       // Appboy.getInstance(this).closeSession(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @TargetApi(value = Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.black));
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // overridePendingTransition(R.anim.in_left, R.anim.out_right);
    }
}
