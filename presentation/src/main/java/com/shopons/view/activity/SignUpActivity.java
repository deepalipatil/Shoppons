package com.shopons.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.shopons.R;
import com.shopons.domain.constants.Constants;
import com.shopons.view.fragment.SignUpFragment;

/**
 * Created by deepali on 8/3/16.
 */
public class SignUpActivity extends BaseScreen {

    public static final String OKAY = "okay";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        displayHomeAsUpEnabled(true);
        final String name = getIntent().getStringExtra(Constants.NAME);
        final String email = getIntent().getStringExtra(Constants.EMAIL);
        addFragment(R.id.activity_signup, SignUpFragment.getInstance(name, email),
                SignUpFragment.TAG);
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    private void goBack() {
        Intent intent = new Intent(getApplicationContext(),CallSocialLoginActivity.class);
        startActivity(intent);
        finish();
        //overridePendingTransition(R.anim.in_left, R.anim.out_right);
    }

}
