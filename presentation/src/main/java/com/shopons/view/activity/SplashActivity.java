package com.shopons.view.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;


import com.shopons.R;
import com.shopons.domain.AppVersion;
import com.shopons.presenter.GeneralPresenter;
import com.afollestad.materialdialogs.MaterialDialog;

import rx.Subscriber;

public class SplashActivity extends AppCompatActivity {

    GeneralPresenter mGeneralPresenter=new GeneralPresenter();
    int version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        if(!isNetworkAvailable())
        {
            Toast.makeText(SplashActivity.this,"Please Check network connectivity",Toast.LENGTH_LONG).show();
        }
        else {


            try {
                version = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                Log.d("####SplashActivity", "Unable to find package");
            }
            mGeneralPresenter.checkForUpdates(6, new Subscriber<AppVersion>() {
                @Override
                public void onCompleted() {
                    Log.d("#####SplashActvity", "OnCompleted");
                }

                @Override
                public void onError(Throwable e) {
                    Log.d("#####SplashActvity", "OnError");
                }

                @Override
                public void onNext(AppVersion appVersion) {

                    if (appVersion.isForceUpdate() && appVersion.isUpdateAvailable()) {
                        MaterialDialog.Builder materialDialog = new MaterialDialog.Builder(SplashActivity.this)
                                .title("Update Available").content("Update App to latest version")
                                .positiveText("Update Now").negativeText("")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                                        materialDialog.dismiss();

                                    }
                                });

                        materialDialog.show();
                    } else {
                        MaterialDialog.Builder materialDialog = new MaterialDialog.Builder(SplashActivity.this)
                                .title("Update Available").content("Update App to latest version")
                                .positiveText("Update").negativeText("Not Now")
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                                        materialDialog.dismiss();
                                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }
                                }).onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                                        materialDialog.dismiss();
                                    }
                                });
                        materialDialog.show();

                    }

                }

            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mGeneralPresenter!=null)
            mGeneralPresenter.destroy();
    }

    boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork= connectivityManager.getActiveNetworkInfo();
        // Check for network connections
        if(activeNetwork !=null && activeNetwork.isConnected())
        {   Log.d("###isNetworkAvailable","true");return true;}
        else
        {   Log.d("###isNetworkAvailable","false");return false;}

    }

}
