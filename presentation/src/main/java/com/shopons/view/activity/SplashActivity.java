package com.shopons.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.shopons.R;
import com.shopons.domain.AppVersion;
import com.shopons.presenter.GeneralPresenter;
import com.shopons.utils.DialogsHelper;
import com.shopons.view.fragment.BaseFragment;
import com.shopons.view.fragment.MainFragment;

import rx.Subscriber;

public class SplashActivity extends AppCompatActivity {

    GeneralPresenter mGeneralPresenter=new GeneralPresenter();
    int version;
    private BroadcastReceiver mInternetReceiver;
    public static final String TAG="####Splashactivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Crashlytics.start(this);
        setContentView(R.layout.activity_splash);

        mInternetReceiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "Inside Internet BroadCast receiver");
                ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();
                // Check for network connections
                if (activeNetwork != null && activeNetwork.isConnected()) {
                    Log.d("###isNetworkAvailable", "true");
                    Intent in = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(in);
                    finish();
                    //Toast.makeText(getContext(), "Connected", Toast.LENGTH_LONG).show();

                    /*  try {
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
                                DialogsHelper.showInteractiveDialog(SplashActivity.this, "UPDATE NOW", "", "Update Available", "Update App to latest version", new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                                        materialDialog.dismiss();
                                    }
                                });
                            } else {
                                DialogsHelper.showInteractiveDialog(SplashActivity.this, "Update", "Not Now", "Update Available", "Please update app to the latest version",
                                        new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                                                materialDialog.dismiss();
                                            }
                                        }, new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                                                materialDialog.dismiss();
                                                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                            }

                        }

                    });
                }
                else*/

                    Log.d("###isNetworkAvailable", "false");
                    // Toast.makeText(getContext(), "Not Connected", Toast.LENGTH_LONG).show();

                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_layout,
                            (ViewGroup) findViewById(R.id.toast_layout_root));

                    TextView text = (TextView) layout.findViewById(R.id.text);
                    BaseFragment.setLightFont(text);
                    text.setText("Please Check network connectivity");

                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();

                }
            }

        };

        registerReceiver(mInternetReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        registerReceiver(mInternetReceiver, new IntentFilter("android.net.wifi.WIFI_STATE_CHANGED"));
        registerReceiver(mInternetReceiver, new IntentFilter("ConnectivityManager.CONNECTIVITY_ACTION"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mInternetReceiver);
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