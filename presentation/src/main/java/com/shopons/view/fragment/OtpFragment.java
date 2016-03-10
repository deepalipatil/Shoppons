package com.shopons.view.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shopons.R;
import com.shopons.data.utils.Validation;
import com.shopons.domain.MsgFromServer;
import com.shopons.domain.User;
import com.shopons.presenter.LoginPresenter;
import com.shopons.presenter.PhoneVerifyPresenter;
import com.shopons.presenter.UserPresenter;
import com.shopons.utils.DialogsHelper;
import com.shopons.utils.ExceptionTypes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by deepali on 9/3/16.
 */
public class OtpFragment extends PermissionFragment {

    public static final String TAG = "##OtpFragment";

    public static OtpFragment getInstance(final boolean shouldShowReviewScreen,
                                                 final String email) {
        final OtpFragment receiveOtpFragment = new OtpFragment();
        final Bundle args = new Bundle();
        args.putBoolean("should_show_review_screen", shouldShowReviewScreen);
        args.putString("email", email);
        //OtpFragment.setArguments(args);
        return receiveOtpFragment;
    }

    public OtpFragment() {
        // Required empty public constructor
    }

    private BroadcastReceiver mSmsBroadCastReceiver;
    private int mPermissionCount;
    private LoginPresenter mLoginPresenter;
    private boolean mShouldShowReviewScreen;
    private String mEmail;

    @Bind(R.id.resend_otp)
    TextView mResendOtp;

    @OnClick(R.id.resend_otp)
    void onResendClicked() {
        if (TextUtils.isEmpty(mEmail)) {
            resendForChangePassword();
        } else {
            resendForForgotPassword();
        }
    }

    @Bind(R.id.otp)
    TextInputLayout mOtpTil;

    @Bind(R.id.otp_edit_text)
    EditText mOtpEditText;

    @Bind(R.id.validate_your_phone)
    TextView mValidateYourPhone;

    @Bind(R.id.insert_code)
    TextView mInsertCode;

    @OnClick(R.id.verify)
    void onVerifyClicked() {
        try {
            Validation.validateOtp(mOtpEditText.getText().toString().trim());
        } catch (final Exception e) {
            e.printStackTrace();
            mOtpEditText.setError(e.getMessage());
            return;
        }
        if (TextUtils.isEmpty(mEmail)) {
            verify();
        } else {
            verifyForForgotPassword();
        }

    }

    private User mUser;
    private PhoneVerifyPresenter mPhoneVerifyPresenter;
    private UserPresenter mUserPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_otp, container, false);
        ButterKnife.bind(this, view);
        mEmail = getArguments().getString("email");
        mShouldShowReviewScreen = getArguments().getBoolean("should_show_review_screen");
        mPermissionCount = 0;
        mPhoneVerifyPresenter = new PhoneVerifyPresenter();
        mUserPresenter = new UserPresenter();
        mLoginPresenter = new LoginPresenter();

        checkForPermission(Manifest.permission.RECEIVE_SMS);
        mResendOtp.setTextColor(getResources().getColor(R.color.colorPrimary));
        return view;
    }

    /**
     * Called when the Fragment is no longer resumed.  This is generally
     * Activity's lifecycle.
     */
    @Override
    public void onPause() {
        super.onPause();
        //mUpdateProfilePresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSmsBroadCastReceiver != null) {
            getActivity().unregisterReceiver(mSmsBroadCastReceiver);
        }
        if (mLoginPresenter != null) {
            mLoginPresenter.destroy();
        }
        if (mPhoneVerifyPresenter != null) {
            mPhoneVerifyPresenter.destroy();
        }
        //mUpdateProfilePresenter.destroy();
    }

    private void verify() {
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Verifying");
        progressDialog.setCancelable(false);
        mPhoneVerifyPresenter.vertifyNumber(mUser.getAuthKey(), mOtpEditText.getText().toString().trim(), new Subscriber<MsgFromServer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(final Throwable e) {
                progressDialog.dismiss();
                if (ExceptionTypes.NOINTERNET == getExceptionType(e)) {
                    DialogsHelper.showErrorDialog(getActivity(), getString(R.string.no_internet));
                } else {
                    DialogsHelper.showErrorDialog(getActivity(), e);
                }
            }

            @Override
            public void onNext(final MsgFromServer msgFromServer) {
                if (msgFromServer.getMessage().equals("not found")) {
                    DialogsHelper.showErrorDialog(getActivity(), new Throwable("Invalid OTP entered!"));
                    progressDialog.dismiss();
                    return;
                }
                mUser.setIsLoggedIn(true);
                mUser.setIsPhoneVerified(true);

                //IMplement SaveUserLocally
            }
        });
    }

    private void registerReceiver() {
        mSmsBroadCastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final Bundle bundle = intent.getExtras();
                try {
                    if (bundle != null) {
                        final Object[] pdusObj = (Object[]) bundle.get("pdus");
                        for (int i = 0; i < pdusObj.length; i++) {
                            final SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                            final String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                            final String senderNum = phoneNumber;
                            final String message = currentMessage.getDisplayMessageBody();
                            Log.d(TAG, "senderNum: " + senderNum + "; message: " + message);
                            if (message.contains("Welcome to iRyd. Your OTP is")) {
                                long otp = 0;
                                final Pattern p = Pattern.compile("-?\\d+");
                                final Matcher m = p.matcher(message);
                                if (m.find()) {
                                    otp = Long.parseLong(m.group());
                                }
                                mOtpEditText.setText(String.valueOf(otp));
                                onVerifyClicked();
                            }
                        }
                    }

                } catch (Exception e) {
                    Log.e("SmsReceiver", "Exception smsReceiver" + e);
                }
            }
        };

        getActivity().registerReceiver(mSmsBroadCastReceiver,
                new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
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
     * @param requestCode The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions The requested permissions. Never null
     * @see #requestPermissions(String[], int)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onPermissionGranted(final String permission) {
        alreadyGrantedPermission(permission);
    }

    @Override
    public void onPermissionDenied(final String permission) {
        Toast.makeText(getActivity(), "Can not read SMS! You will have to enter OTP manually!!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void alreadyGrantedPermission(final String permission) {
        if (++mPermissionCount == 2) {
            registerReceiver();
        } else {
            checkForPermission(Manifest.permission.READ_SMS);
        }
    }

    private void resendForChangePassword() {
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", getString(R.string.sending_otp));
        progressDialog.setCancelable(false);


        //Upload profile presenter######
    }

    private void resendForForgotPassword() {
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", getString(R.string.updating_password));
        progressDialog.setCancelable(false);

        //Upload profile presenter######
    }

    private void verifyForForgotPassword() {
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", getString(R.string.verifying));
        progressDialog.setCancelable(false);

        //Upload profile presenter######
    }
}

