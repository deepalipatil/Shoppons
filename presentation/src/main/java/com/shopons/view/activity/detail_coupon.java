package com.shopons.view.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shopons.R;
import com.shopons.domain.Coupon;
import com.shopons.domain.User;
import com.shopons.presenter.CouponPresenter;
import com.shopons.presenter.LoginPresenter;
import com.shopons.presenter.StorePresenter;
import com.shopons.view.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by Win7 on 02-May-16.
 */
public class detail_coupon extends BaseScreen {
    StorePresenter mCouponPresenter;
    LoginPresenter mLoginPresenter;
    private String authkey;
    private String dealId,shop_name,deal;
    final String TAG="###Detail_Coupon";
    private ActionBar actionBar;

    @Bind(R.id.info_label)
    TextView info;

    @Bind(R.id.discount)
    TextView discount;

    @Bind(R.id.shop_name)
    TextView txt_shopname;

    @Bind(R.id.validity)
    TextView validity;

    @Bind(R.id.code)
    TextView code;

    @Bind(R.id.progress_bar)
    ProgressBar progressBar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coupons);
        ButterKnife.bind(this);



        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        //actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.light_blue), android.graphics.PorterDuff.Mode.SRC_IN);

        Intent intent=getIntent();
        info.setTextColor(getResources().getColor(R.color.white));
        BaseFragment.setTypeFace(info);
        BaseFragment.setRegularFont(validity);
        BaseFragment.setRegularFont(code);
        BaseFragment.setRegularFont(txt_shopname);
        BaseFragment.setRegularFont(discount);


        mCouponPresenter=new StorePresenter();
        mLoginPresenter=new LoginPresenter();

        dealId= getIntent().getStringExtra("dealId");
        shop_name= getIntent().getStringExtra("shop_name");
        deal= getIntent().getStringExtra("deal");

        txt_shopname.setText(shop_name);
        discount.setText(deal);
    }

    @Override
    protected void onResume() {
        super.onResume();

        getUserInfo();
        //getCouponCode();
    }

    void getUserInfo()
    {
        mLoginPresenter.getUserInfo(new Subscriber<User>() {
            @Override
            public void onCompleted() {
                getCouponCode();
                Log.d(TAG,"On completed User");
                if(progressBar.getVisibility()==View.VISIBLE)
                    progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();

            }

            @Override
            public void onNext(User user) {
                Log.d(TAG,"Inside User On Next");
                getUserAuthKey(user);

            }
        });
    }
     void getUserAuthKey(User user)
     {
         Log.d(TAG,"Inside Auth key");
         authkey=user.getAuth_key();
         //getCouponCode();
     }


    void getCouponCode()
    {
        Log.d(TAG,"Inside get coupon code");
        mCouponPresenter.generateCoupon(authkey,dealId, new Subscriber<Coupon>() {
            @Override
            public void onCompleted() {
               Log.d("Detail Coupon","Coupon on completed");

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Coupon coupon) {
                Log.d(TAG,"Inside On next coupon");
                code.setText(coupon.getCoupon_code());
                //Txt_shop_name.setText(shop_name);
                Log.d(TAG,""+coupon.getCoupon_code());
                Log.d(TAG,""+shop_name);
            }
        });
    }


    @OnClick(R.id.back)
    void closeActivity()
    {
        onBackPressed();
    }

    public void pay_now(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(getApplicationContext());
        edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
        //alert.setMessage("Enter Your Message");
        alert.setTitle("Enter Amount");
        edittext.setTextColor(getResources().getColor(R.color.black));
        alert.setView(edittext);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String textvalue = edittext.getText().toString();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Intent intent=new Intent(getApplicationContext(),detail_coupon.class);
                startActivity(intent);
            }
        });
        alert.show();
    }

}
