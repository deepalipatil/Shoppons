package com.shopons.view.activity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.shopons.presenter.GeneralPresenter;
import com.shopons.presenter.LoginPresenter;
import com.shopons.utils.DialogsHelper;

/**
 * Base screen
 *
 * @author : Kaustubh Deshmukh
 * @date : 01/10/15 : 8:54 PM
 * @email : akshay@betacraft.co
 */
public class BaseScreen extends AppCompatActivity{

    private GeneralPresenter mGeneralPresenter;
    protected int mLastMenuItemId;
    private LoginPresenter mLoginPresenter;

    private static final String TAG = "##BaseScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGeneralPresenter = new GeneralPresenter();
        mLoginPresenter = new LoginPresenter();
    }

    protected void addFragment(int id, Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(id, fragment, tag).commit();
    }

    protected void displayHomeAsUpEnabled(boolean homeAsEnabled) {
        if (getSupportActionBar() == null)
            return;
        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsEnabled);
    }

    protected void removeFragment(String tag) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .remove(fragment).commit();
        }
    }

    /*protected void changeStatusBarColor(final int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(color);
        }
    }*/

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGeneralPresenter.destroy();
        mLoginPresenter.destroy();
    }

    @Override
    public void onBackPressed() {

        DialogsHelper.showInteractiveDialog(this, "OK", "CANCEL", "Close Application?", "Do you want to close application?",
                new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        materialDialog.dismiss();
                        finish();
                    }
                }, new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        materialDialog.dismiss();
                    }
                });
    }
}
