package com.shopons.view.activity;

import android.content.pm.PackageManager;
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

public class MainActivity extends AppCompatActivity {

    GeneralPresenter mGeneralPresenter=new GeneralPresenter();
    int version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
