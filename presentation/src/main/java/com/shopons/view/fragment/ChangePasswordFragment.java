package com.shopons.view.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.shopons.R;
import com.shopons.data.utils.Validation;
import com.shopons.domain.MsgFromServer;
import com.shopons.domain.User;
import com.shopons.presenter.LoginPresenter;
import com.shopons.utils.DialogsHelper;
import com.shopons.view.activity.CallSocialLoginActivity;
import com.shopons.view.activity.ChangePasswordActivity;
import com.shopons.view.activity.OtpActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * Created by deepali on 8/3/16.
 */
public class ChangePasswordFragment extends BaseFragment {

    public static final String TAG = "##ChangePassword";

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    public boolean mIsChangePassword;

    @Bind(R.id.password)
    TextInputLayout mPassword;

    @Bind(R.id.email)
    TextInputLayout mEmail;

    @Bind(R.id.email_edit_text)
    EditText mEmailEditText;

    @Bind(R.id.password_edit_text)
    EditText mPasswordEditText;

    private LoginPresenter mLoginPresenter;

    private User mUser;

    public static ChangePasswordFragment getInstance(final boolean isChangePassword) {
        final Bundle bundle = new Bundle();
        final ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
        bundle.putBoolean("is_change_password", isChangePassword);
        changePasswordFragment.setArguments(bundle);
        return changePasswordFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        ButterKnife.bind(this, view);
        mIsChangePassword = getArguments().getBoolean("is_change_password");
        if (mIsChangePassword) {
            mEmail.setVisibility(View.GONE);
            mPassword.setVisibility(View.VISIBLE);
        } else {
            mEmail.setVisibility(View.VISIBLE);
            mPassword.setVisibility(View.GONE);
        }
        mEmail.setHintTextAppearance(R.style.TextInputLayoutPrimary);
        mPassword.setHintTextAppearance(R.style.TextInputLayoutPrimary);

        mLoginPresenter = new LoginPresenter();
        setHasOptionsMenu(true);
        setFonts();
        return view;
    }

    /**
     * Initialize the contents of the Activity's standard options menu.
     *
     * @param menu     The options menu in which you place your items.
     * @param inflater
     * @see #setHasOptionsMenu
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.menu_signup, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ((ChangePasswordActivity) getActivity()).goBack();
                break;

            case R.id.okay:
                if (mIsChangePassword) {
                    // call change password API
                    changePassword();
                } else {
                    // call forgot password API
                    forgotPassword();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFonts() {
        mPasswordEditText.setTransformationMethod(new PasswordTransformationMethod());
        mEmailEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_DONE:
                        forgotPassword();
                        break;
                }
                return true;
            }
        });
    }

    private void forgotPassword() {
        try {
            Validation.validateEmailAddress(mEmailEditText.getText().toString().trim());
        } catch (Exception e) {
            e.printStackTrace();
            mEmailEditText.setError(e.getMessage());
            return;
        }
        updatePassword();
    }

    private void updatePassword() {
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Updating Password");
        progressDialog.setCancelable(false);

        mLoginPresenter.getForgotPasswordOtp(mEmailEditText.getText().toString(), new Subscriber<MsgFromServer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(final Throwable e) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(),"Plz check internet",Toast.LENGTH_SHORT);
            }

            @Override
            public void onNext(final MsgFromServer msgFromServer) {
                progressDialog.dismiss();
                Intent intent = new Intent(getActivity(), OtpActivity.class);
                intent.putExtra("should_go_to_change_password", true);
                intent.putExtra("email", mEmailEditText.getText().toString());
                startActivity(intent);
                //getActivity().overridePendingTransition(R.anim.in_right, R.anim.out_left);
                getActivity().finish();
            }
        });
    }

    private void changePassword() {
        try {
            Validation.validatePassword(mPasswordEditText.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
            mPasswordEditText.setError(e.getMessage());
            return;
        }

        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Updating Password");
        progressDialog.setCancelable(false);
        mUser.setPassword(mPasswordEditText.getText().toString());
        mLoginPresenter.changePassword(mUser, new Subscriber<MsgFromServer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Plz check internet", Toast.LENGTH_SHORT);
            }

            @Override
            public void onNext(MsgFromServer msgFromServer) {
                progressDialog.dismiss();
                DialogsHelper.showInteractiveDialog(getActivity(), getString(R.string.ok), "",
                        "Password changed successfully",
                        "You will be logged out. Please login again", new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                logout();
                            }
                        },
                        new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {

                            }
                        });
            }
        });
    }

    private void logout() {
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", getString(R.string.logging_out));
        progressDialog.setCancelable(false);

        mLoginPresenter.logoutUser(new Subscriber<Void>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(final Throwable e) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error while logging out!",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Void aVoid) {
                progressDialog.dismiss();
                Intent intent = new Intent(getActivity(), CallSocialLoginActivity.class);
                startActivity(intent);
                //getActivity().overridePendingTransition(R.anim.in_right, R.anim.out_left);
                getActivity().finish();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLoginPresenter != null)
            mLoginPresenter.destroy();
    }
}

