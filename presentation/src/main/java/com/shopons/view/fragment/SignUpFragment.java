package com.shopons.view.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.shopons.R;
import com.shopons.data.utils.Validation;
import com.shopons.domain.User;
import com.shopons.domain.constants.Constants;
import com.shopons.mapper.UserMapper;
import com.shopons.model.UserModel;
import com.shopons.presenter.LoginPresenter;
import com.shopons.utils.DialogsHelper;
import com.shopons.utils.ExceptionTypes;
import com.shopons.view.activity.CallSocialLoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * Created by deepali on 9/3/16.
 */
public class SignUpFragment extends BaseFragment {

    public static final String TAG = "SignUpFragment";
    private static final int SELECT_PICTURE = 1;
    private static final int CAPTURE_IMAGE = 2;

    private String name;
    private String email;

    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment getInstance(final String name, final String email) {
        final SignUpFragment signUpFragment = new SignUpFragment();
        final Bundle arguments = new Bundle();
        arguments.putString(Constants.NAME, name);
        arguments.putString(Constants.EMAIL, email);
        signUpFragment.setArguments(arguments);
        return signUpFragment;
    }

    @Bind(R.id.name)
    TextInputLayout mName;

    @Bind(R.id.email)
    TextInputLayout mEmail;

    @Bind(R.id.phone)
    TextInputLayout mPhone;

    @Bind(R.id.password)
    TextInputLayout mPassword;

    @Bind(R.id.name_edit_text)
    EditText mNameEditText;

    @Bind(R.id.email_edit_text)
    EditText mEmailEditText;

    @Bind(R.id.phone_edit_text)
    EditText mPhoneEditText;

    @Bind(R.id.password_edit_text)
    EditText mPasswordEditText;

    private LoginPresenter mLoginPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this, view);
        name = getArguments().getString(Constants.NAME);
        email = getArguments().getString(Constants.EMAIL);
        mName.setHintTextAppearance(R.style.TextInputLayoutPrimary);
        mEmail.setHintTextAppearance(R.style.TextInputLayoutPrimary);
        mPhone.setHintTextAppearance(R.style.TextInputLayoutPrimary);
        mPassword.setHintTextAppearance(R.style.TextInputLayoutPrimary);
        // if we had got the values then just pre-fill the values
        if (!TextUtils.isEmpty(email)) {
            //mEmailEditText.setText(Preferences.GetEmailAddress(getActivity()));
            mEmailEditText.setEnabled(false);
        }

        if (!TextUtils.isEmpty(name)) {
            //mNameEditText.setText(Preferences.GetUserName(getActivity()));
            mNameEditText.setEnabled(false);
        }
        mLoginPresenter = new LoginPresenter();

        setHasOptionsMenu(true);
        return view;
    }

    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.  For this method
     * to be called, you must have first called {@link #setHasOptionsMenu}.
     *
     * @param menu     The options menu in which you place your items.
     * @param inflater
     * @see #setHasOptionsMenu
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_signup, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(getActivity(), CallSocialLoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                //getActivity().overridePendingTransition(R.anim.in_left, R.anim.out_right);
                break;

            case R.id.okay:
                if (!isSignUpFormValid()) {
                    return true;
                }
                break;
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected User mUser;
    protected void updateUser() {
        final UserModel userModel = new UserModel("", mNameEditText.getText().toString().trim(),
                mEmailEditText.getText().toString().trim(),
                mPhoneEditText.getText().toString().trim(),
                mPasswordEditText.getText().toString().trim(),"");
        doSignUp(UserMapper.transform(userModel));
    }

    private void doSignUp(final User user) {
        hideSoftKeyboard();
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Signing up");
        progressDialog.setCancelable(false);
        mLoginPresenter = new LoginPresenter();
        mLoginPresenter.registerUserByEmail(user, new Subscriber<User>() {
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
                }
                progressDialog.dismiss();
            }

            @Override
            public void onNext(User user) {
                progressDialog.dismiss();
                Log.d(TAG, "User registration successful " + user.toString());
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
     * Function which checks if sign up form is valid or not
     *
     * @return boolean
     */
    private boolean isSignUpFormValid() {
        try {
            Validation.validateName(mNameEditText.getText().toString().trim());
        } catch (Exception e) {
            mNameEditText.setError(e.getMessage());
            return false;
        }
        try {
            Validation.validateEmailAddress(mEmailEditText.getText().toString().trim());
        } catch (Exception e) {
            mEmailEditText.setError(e.getMessage());
            return false;
        }
        try {
            Validation.validatePhoneNumber(mPhoneEditText.getText().toString().trim());
        } catch (Exception e) {
            mPhoneEditText.setError(e.getMessage());
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


    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on {@link #requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link PackageManager#PERMISSION_GRANTED}
     *                     or {@link PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
