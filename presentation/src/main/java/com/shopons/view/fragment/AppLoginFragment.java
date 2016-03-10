package com.shopons.view.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.shopons.R;
import com.shopons.data.utils.Validation;
import com.shopons.domain.User;
import com.shopons.presenter.LoginPresenter;
import com.shopons.utils.DialogsHelper;
import com.shopons.utils.ExceptionTypes;
import com.shopons.view.activity.ChangePasswordActivity;
import com.shopons.view.activity.SignUpActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by deepali on 7/3/16.
 */
public class AppLoginFragment extends BaseFragment {

    public static final String TAG = "AppLoginFragment";

    public AppLoginFragment() {
        // Required empty public constructor
    }

    public static AppLoginFragment getInstance() {
        return new AppLoginFragment();
    }

    @Bind(R.id.email)
    TextInputLayout mEmailTil;

    @Bind(R.id.password)
    TextInputLayout mPasswordTil;

    @Bind(R.id.email_edit_text)
    EditText mEmailEditText;

    @Bind(R.id.password_edit_text)
    EditText mPasswordEditText;

    @Bind(R.id.sign_up)
    TextView mSignUpTextView;

    @Bind(R.id.login_button)
    Button mLoginButton;

    @Bind(R.id.forgot_password)
    TextView mForgotPassword;

    @OnClick(R.id.login_button)
    void onLoginClick() {
        if(!isConnected()){
            DialogsHelper.showSuccessDialog(getActivity(), "Please check your internet connection!");
            return;
        }
        if (!isLoginFormValid()) {
            return;
        }
        doLogin();
    }

    @OnClick(R.id.sign_up)
    void onSignUpClick() {
        Intent intent = new Intent(getActivity(), SignUpActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @OnClick(R.id.forgot_password)
    void onForgotClick() {
        final Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
        intent.putExtra(ChangePasswordActivity.IS_CHANGE_PASSWORD, false);
        startActivity(intent);
        //getActivity().overridePendingTransition(R.anim.in_right, R.anim.out_left);
        getActivity().finish();
    }

    private LoginPresenter mLoginPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        mLoginPresenter = new LoginPresenter();
        setupFonts();
        return view;
    }

    // as we are extending BaseLoginFragment
    // just call super
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void doLogin() {
        hideSoftKeyboard();
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Logging in..");
        progressDialog.setCancelable(false);
        mLoginPresenter.loginUserByEmail(new User(mEmailEditText.getText().toString().trim(),
                mPasswordEditText.getText().toString().trim()), new Subscriber<User>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (ExceptionTypes.NOINTERNET == getExceptionType(e)) {
                    DialogsHelper.showInteractiveDialog(getActivity(), "OK", "", "Oops", "Please check internet connection", new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                            materialDialog.dismiss();
                        }
                    }, new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                            materialDialog.dismiss();
                        }
                    });
                } else {
                    DialogsHelper.showInteractiveDialog(getActivity(), "OK", "", "Sorry", DialogsHelper.getFormattedError(e.getMessage()), new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                            materialDialog.dismiss();
                        }
                    }, new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                            materialDialog.dismiss();
                        }
                    });
                }
                progressDialog.dismiss();
            }

            @Override
            public void onNext(User user) {
                Log.d(TAG, " Login Success " + user.toString());
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLoginPresenter != null)
            mLoginPresenter.destroy();
    }

    /**
     * Helper function which checks whether login form is valid or not
     *
     * @return
     */
    private boolean isLoginFormValid() {
        try {
            Validation.validateEmailAddress(mEmailEditText.getText().toString().trim());
        } catch (Exception e) {
            mEmailEditText.setError(e.getMessage());
            return false;
        }
        try {
            Validation.validatePassword(mPasswordEditText.getText().toString());
        } catch (Exception e) {
            mPasswordEditText.setError(e.getMessage());
            return false;
        }
        return true;
    }

    private void setupFonts() {
        mPasswordEditText.setTransformationMethod(new PasswordTransformationMethod());
        mPasswordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_DONE:
                        onLoginClick();
                        break;
                }
                return true;
            }
        });
    }
}
