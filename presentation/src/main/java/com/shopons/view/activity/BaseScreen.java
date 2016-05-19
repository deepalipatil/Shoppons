package com.shopons.view.activity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.shopons.domain.User;
import com.shopons.presenter.GeneralPresenter;
import com.shopons.presenter.LoginPresenter;
import com.shopons.utils.DialogsHelper;

import rx.Subscriber;

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
    protected String mLastTag;
    private static final String TAG = "##BaseScreen";
    protected static boolean mIsLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGeneralPresenter = new GeneralPresenter();
        mLoginPresenter = new LoginPresenter();
    }

    protected void addFragment(int id, Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(id, fragment, tag).commit();
        mLastTag = tag;
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

    public void setLoginStatus(boolean value)
    {
        mIsLoggedIn=value;
    }
    public boolean isUserLoggedIn()
    {
        return mIsLoggedIn;
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
    protected void onStart(){
        super.onStart();

        mLoginPresenter.getUserInfo(new Subscriber<User>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(final Throwable e) {}

            @Override
            public void onNext(final User user) {
                if (user != null) {
                    if (!user.getIs_info_pushed()) {
                        user.setIs_info_pushed(true);
                        mLoginPresenter.saveUserInfo(user, new Subscriber<User>() {
                            @Override
                            public void onCompleted() {
                                Log.d(TAG, "REalm done");
                            }

                            @Override
                            public void onError(final Throwable e) {
                            }

                            @Override
                            public void onNext(User u) {

                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGeneralPresenter.destroy();
    }

   /* @Override
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
    }*/
}
