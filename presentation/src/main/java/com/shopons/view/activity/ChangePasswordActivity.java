package com.shopons.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.shopons.R;
import com.shopons.view.fragment.ChangePasswordFragment;

/**
 * Created by deepali on 8/3/16.
 */
public class ChangePasswordActivity extends BaseScreen {

    public static final String IS_CHANGE_PASSWORD = "is_change_password";
    private boolean imIsChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        displayHomeAsUpEnabled(true);
        imIsChangePassword = getIntent().getBooleanExtra(IS_CHANGE_PASSWORD, true);
        addFragment(R.id.activity_change_password, ChangePasswordFragment.getInstance(imIsChangePassword),
                ChangePasswordFragment.TAG);
    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        goBack();
    }

    public void goBack() {
        if(imIsChangePassword) {
            Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
            startActivity(intent);
            //overridePendingTransition(R.anim.in_right, R.anim.out_left);
            finish();
        } else {
            Intent intent = new Intent(getApplicationContext(), OtpActivity.class);
            startActivity(intent);
            //overridePendingTransition(R.anim.in_right, R.anim.out_left);
            finish();
        }
    }
}

