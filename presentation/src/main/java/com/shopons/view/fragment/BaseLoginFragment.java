package com.shopons.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shopons.domain.User;
import com.shopons.domain.constants.Constants;
import com.shopons.view.activity.SocialLoginActivity;

/**
 * Created by deepali on 29/3/16.
 */
public abstract class BaseLoginFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private static final String TAG = "##BaseLoginFragment";

    public abstract void googlePlusLogin(final User user);
    public abstract void facebookLogin(final User user);

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null)
            return;
        int response = data.getIntExtra("result", 0);
        if (response == SocialLoginActivity.LOGIN_SUCCESS) {
            final User user = new User();
            // final SocialLoginFragment socialLoginFragment = new SocialLoginFragment();
            switch (data.getIntExtra("social_network", 0)){
                case SocialLoginActivity.FACEBOOK:{
                    user.setName(data.getStringExtra(Constants.NAME));
                    user.setEmail(data.getStringExtra(Constants.EMAIL));
                    user.setFb_token(data.getStringExtra(Constants.TOKEN));
                    user.setFb_id(data.getLongExtra(Constants.ID, 0));
                    Log.d(TAG, "Name" + user.getName());
                    facebookLogin(user);
                    break;
                }


                case SocialLoginActivity.GOOGLE_PLUS:{
                    user.setEmail(data.getStringExtra(Constants.EMAIL));
                    user.setName(data.getStringExtra(Constants.NAME));
                    user.setGoogle_token(data.getStringExtra(Constants.TOKEN));
                    googlePlusLogin(user);
                    Log.d(TAG, "Name" + user.getName());
                    break;}
            }

        } else {
            //TODO change this
            Toast.makeText(getActivity(), "There was some issue with social login, " +
                    "please try again !!", Toast.LENGTH_LONG).show();
        }
    }
}
