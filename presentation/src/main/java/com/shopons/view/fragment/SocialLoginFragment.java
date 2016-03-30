package com.shopons.view.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crashlytics.android.Crashlytics;
import com.shopons.R;
import com.shopons.domain.User;
import com.shopons.presenter.GeneralPresenter;
import com.shopons.presenter.LoginPresenter;
import com.shopons.utils.DialogsHelper;
import com.shopons.view.activity.MainActivity;
import com.shopons.view.activity.SocialLoginActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by deepali on 5/3/16.
 */
public class SocialLoginFragment extends BaseLoginFragment  {

    public static final String TAG = "LoginFragment";
    private GeneralPresenter mGeneralPresenter;
    private User mUser;

    public SocialLoginFragment(){}

    public static SocialLoginFragment getInstance() {
        return new SocialLoginFragment();
    }

    @OnClick(R.id.btn_back)
    void onBackClick()
    {

        getActivity().finish();
    }

    @OnClick(R.id.facebook_login_button)
    void onFacebookClick() {
        if(!isConnected()){
            DialogsHelper.showSuccessDialog(getActivity(), "Please check your internet connection!");
            return;
        }
        Log.d(TAG, "Reg Id............. Fb login " );
        Intent intent = new Intent(getActivity(),SocialLoginActivity.class);
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
        Log.d(TAG,"Inside on CreateView");
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
       public void facebookLogin(final User user) {
        // make API call here
        Log.d(TAG, "User " + user.toString());
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "connecting");
        progressDialog.setCancelable(false);
        mLoginPresenter.loginWithFacebook(user, new Subscriber<User>() {
            @Override
            public void onCompleted() {
                Log.d(TAG,"Saving user");
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
                mLoginPresenter.saveUserInfo(mUser, new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG,"realm");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.logException(e);
                        DialogsHelper.showErrorDialog(getActivity(), e);
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onNext(User u) {
                        mUser.set_is_logged_in(true);
                        mLoginPresenter.saveUserInfo(mUser, new Subscriber<User>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(final Throwable e) {
                                progressDialog.dismiss();
                                DialogsHelper.showErrorDialog(getActivity(), e);
                            }

                            @Override
                            public void onNext(final User user) {
                                progressDialog.dismiss();
                                Intent intent = new Intent(getContext(),MainActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public void googlePlusLogin(final User user) {
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", getString(R.string.connecting));
        progressDialog.setCancelable(false);
        mLoginPresenter.loginWithGooglePlus(user, new Subscriber<User>() {
            @Override
            public void onCompleted() {

                Log.d(TAG,"Completed!!!!!!");

                Intent intent=new Intent(getActivity(), MainActivity.class);
                intent.putExtra("userLoginStatus",true);
                startActivity(intent);
                getActivity().finish();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
               /* if (e != null && e.getMessage() != null) {

                } else {
                    e.printStackTrace();
                    DialogsHelper.showErrorDialog(getActivity(), new Throwable("Please check internet connection!"));
                }*/
              //  progressDialog.dismiss();
            }

            @Override
            public void onNext(final User registeredUser) {
                progressDialog.dismiss();
                mUser = registeredUser;
                mLoginPresenter.saveUserInfo(mUser, new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG,"Saving user");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.logException(e);
                        DialogsHelper.showErrorDialog(getActivity(), e);
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onNext(User u) {
                        mUser.set_is_logged_in(true);
                        mLoginPresenter.saveUserInfo(mUser, new Subscriber<User>() {
                            @Override
                            public void onCompleted() {
                                Log.d(TAG,"Saving user");
                            }

                            @Override
                            public void onError(final Throwable e) {
                                progressDialog.dismiss();
                                DialogsHelper.showErrorDialog(getActivity(), e);
                            }

                            @Override
                            public void onNext(final User user) {
                                progressDialog.dismiss();
                                Intent intent = new Intent(getContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public void onDetach() {
        Log.d("####SocialLoginFragment","Detached :((((((((");
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGeneralPresenter.destroy();
        if (mLoginPresenter != null) {
            mLoginPresenter.destroy();
        }
    }
}
