package com.shopons.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.shopons.R;
import com.shopons.view.fragment.AppLoginFragment;

/**
 * Created by deepali on 8/3/16.
 */
public class LoginActivity extends BaseScreen {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        displayHomeAsUpEnabled(true);
        if(savedInstanceState == null){
            addFragment(R.id.activity_login, AppLoginFragment.getInstance(), AppLoginFragment.TAG);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                goBack();
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    private void goBack(){
        Intent intent = new Intent(getApplicationContext(),CallSocialLoginActivity.class);
        startActivity(intent);
        finish();
        //overridePendingTransition(R.anim.in_left, R.anim.out_right);
    }
}

