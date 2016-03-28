package com.shopons.view.activity;

import android.os.Bundle;

import com.shopons.R;
import com.shopons.view.fragment.SocialLoginFragment;

/**
 * Created by deepali on 6/3/16.
 */
public class CallSocialLoginActivity extends BaseScreen  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_social_login);

        if(savedInstanceState == null){
            addFragment(R.id.activity_call_social_login, SocialLoginFragment.getInstance(),
                    SocialLoginFragment.TAG);
        }
    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        finish();
    }
}
