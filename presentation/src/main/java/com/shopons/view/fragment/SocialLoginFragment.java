package com.shopons.view.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shopons.R;
import com.shopons.domain.User;
import com.shopons.presenter.GeneralPresenter;
import com.shopons.presenter.LoginPresenter;
import com.shopons.utils.DialogsHelper;
import com.shopons.view.activity.SocialLoginActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by deepali on 5/3/16.
 */
public class SocialLoginFragment extends BaseFragment {

    public static final String TAG = "LoginFragment";
    private GeneralPresenter mGeneralPresenter;
    private User mUser;

    public SocialLoginFragment(){}

    public static SocialLoginFragment getInstance() {
        return new SocialLoginFragment();
    }

    @OnClick(R.id.facebook_login_button)
    void onFacebookClick() {
        if(!isConnected()){
            DialogsHelper.showSuccessDialog(getActivity(), "Please check your internet connection!");
            return;
        }
        Log.d(TAG, "Reg Id............. Fb login " );
        Intent intent = new Intent(getActivity(),SocialLoginActivity.class);
        startActivity(intent);
        startActivityForResult(intent.putExtra("type", SocialLoginActivity.FACEBOOK), SocialLoginActivity.FACEBOOK);
    }

    @OnClick(R.id.google_login_button)
    void onGoogleClick() {
        if(!isConnected()){
            DialogsHelper.showSuccessDialog(getActivity(), "Please check your internet connection!");
            return;
        }
        Log.d(TAG, "Reg Id.............. GPlus login");
        Intent intent = new Intent(getActivity(),SocialLoginActivity.class);
        startActivity(intent);
        startActivityForResult(intent.putExtra("type", SocialLoginActivity.GOOGLE_PLUS), SocialLoginActivity.GOOGLE_PLUS);
    }

    private LoginPresenter mLoginPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_social_login, container, false);
        ButterKnife.bind(this, view);
        mGeneralPresenter = new GeneralPresenter();
        mLoginPresenter = new LoginPresenter();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void facebookLogin(final User user) {
        // make API call here
        Log.d(TAG, "User " + user.toString());
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "connecting");
        progressDialog.setCancelable(false);

        mLoginPresenter.fbLogin(user, new Subscriber<User>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (e != null && e.getMessage() != null) {

                } else {
                    DialogsHelper.showErrorDialog(getActivity(), new Throwable("Please check internet connection!"));
                }
                progressDialog.dismiss();
            }

            @Override
            public void onNext(final User registeredUser) {
                mUser = registeredUser;
            }
        });
    }

    public void googlePlusLogin(final User user) {
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "connecting");
        progressDialog.setCancelable(false);

        mLoginPresenter.gPlusLogin(user, new Subscriber<User>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (e != null && e.getMessage() != null) {

                } else {
                    DialogsHelper.showErrorDialog(getActivity(), new Throwable("Please check internet connection!"));
                }
                progressDialog.dismiss();
            }

            @Override
            public void onNext(final User registeredUser) {
                progressDialog.dismiss();
                mUser = registeredUser;
            }
        });
    }

}
