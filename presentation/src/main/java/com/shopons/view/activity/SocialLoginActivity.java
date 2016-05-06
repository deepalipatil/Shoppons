package com.shopons.view.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.shopons.R;
import com.shopons.domain.User;
import com.shopons.domain.constants.Constants;
import com.shopons.utils.DialogsHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by deepali on 5/3/16.
 */
public class SocialLoginActivity extends BaseScreen implements GoogleApiClient
        .ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    private static final String TAG = "##SocialLoginActivity##";
    private static final int PERMISSIONS_REQUEST_GET_ACCOUNTS = 1;

    private AtomicBoolean mProcessingFb = new AtomicBoolean(false);
    public static final int GOOGLE_PLUS = 1;
    public static final int FACEBOOK = 2;
    public static final int HAS_PHONE_REGISTERED = 3;
    public static final int LOGIN_SUCCESS = 1;
    public static final int LOGIN_FAILURE = 0;
    //Request code used to invoke sign in user interactions.
    private static final int RC_SIGN_IN = 1;
    // Client used to interact with Google APIs.
    private GoogleApiClient mGoogleApiClient;
    // A flag indicating that a PendingIntent is in progress and prevents us from starting
    // further intents.
    private boolean mIntentInProgress;
    // Store the connection result from onConnectionFailed callbacks so that we can
    // resolve them when the user clicks sign-in.
    private ConnectionResult mConnectionResult;
    private int mSocialNetwork;
    private ProgressDialog mProgressDialog;
    private UiLifecycleHelper mUIHelper;
    private Handler mHandler;
    private String mGooglePlusToken = "";
    private User myuser;
    private Session.StatusCallback mCallback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_login);
        mHandler = new Handler();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        mProgressDialog.setCancelable(false);
        mSocialNetwork = getIntent().getIntExtra("type", 0);

        if (mSocialNetwork == GOOGLE_PLUS) {

            int permissionCheck = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.GET_ACCOUNTS);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.GET_ACCOUNTS)) {
                    DialogsHelper.getDialogBuilder(this, "Allow Shopons to get accounts?",
                            "", "OK", "CANCEL")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                                    materialDialog.dismiss();
                                    ActivityCompat.requestPermissions(SocialLoginActivity.this, new String[]{Manifest.permission.GET_ACCOUNTS},
                                            PERMISSIONS_REQUEST_GET_ACCOUNTS);
                                }
                            })
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                                    materialDialog.dismiss();
                                    Intent resultIntent = new Intent();
                                    resultIntent.putExtra("result", LOGIN_FAILURE);
                                    setResult(RESULT_OK, resultIntent);
                                    finish();
                                }
                            })
                            .show();
                } else {
                    // no need for explanation
                    // request permission

                    ActivityCompat.requestPermissions(SocialLoginActivity.this,
                            new String[]{Manifest.permission.GET_ACCOUNTS},
                            PERMISSIONS_REQUEST_GET_ACCOUNTS);

                }
            } else {
                // permission is granted .. :)
                initGoogleApiClient();
            }

        } else if (mSocialNetwork == FACEBOOK) {
            mUIHelper = new UiLifecycleHelper(this, mCallback);
            mUIHelper.onCreate(savedInstanceState);
            Session session = Session.getActiveSession();
            if (!session.isOpened() && !session.isClosed()) {
                session.openForRead(new Session.OpenRequest(this)
                        .setPermissions(Arrays.asList("email"))
                        .setCallback(mCallback));
            } else {
                Session.openActiveSession(this, true, mCallback);
            }
        }
    }

    private void initGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .addScope(new Scope("https://www.googleapis.com/auth/plus.profile.emails.read"))
                .build();
        mGoogleApiClient.connect();
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_GET_ACCOUNTS
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initGoogleApiClient();
            return;
        }
        Intent resultIntent = new Intent();
        resultIntent.putExtra("result", LOGIN_FAILURE);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSocialNetwork == FACEBOOK) {
            Session session = Session.getActiveSession();
            if (session != null &&
                    (session.isOpened() || session.isClosed())) {
                onSessionStateChange(session, session.getState(), null);
            }
            mUIHelper.onResume();
        }

    }

    private void onSessionStateChange(final Session session, final SessionState state,
                                      final Exception exception) {
        Log.d(TAG, "state changed" + state.isOpened() + state.isClosed());
        if (exception != null) {
            Log.d(TAG, "Exception", exception);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("result", LOGIN_FAILURE);
            setResult(RESULT_OK, resultIntent);
            mProgressDialog.dismiss();
            finish();
            return;
        }
        if (state.isOpened()) {
            Log.i(TAG, "State open");
            if (mProcessingFb.getAndSet(true)) {
                return;
            }
            Log.d(TAG, "Getting my info");
            List<String> permissions = new ArrayList<String>();
            permissions.add("email");
            session.requestNewReadPermissions(new Session.NewPermissionsRequest(this, permissions));
            Request request = Request.newMeRequest(session,
                    new Request.GraphUserCallback() {
                        @Override
                        public void onCompleted(GraphUser user, Response response) {
                            // If the response is successful
                            if (session == Session.getActiveSession()) {

                                if (user != null && user.getProperty("email") != null) {

                                    String email = user.getProperty("email").toString();
                                    String firstName = user.getName().split(" ")[0].trim();
                                    String lastName;
                                    if (user.getName().split(" ").length >= 1) {
                                        lastName = user.getName().split(" ")[1].trim();
                                    }
                                    Log.d(TAG, email);
                                    Log.d(TAG, session.getAccessToken());

                                    Log.d(TAG, "Id " + user.getId());
                                    Intent resultIntent = new Intent();
                                    resultIntent.putExtra("result", LOGIN_SUCCESS);
                                    resultIntent.putExtra("social_network", mSocialNetwork);
                                    resultIntent.putExtra(Constants.EMAIL, email);
                                    String profilePic =
                                            "https://graph.facebook.com/" +
                                                    user.getId() + "/picture?type=large";

                                    resultIntent.putExtra(Constants.TOKEN, session.getAccessToken());
                                    resultIntent.putExtra(Constants.ID, Long.parseLong(user.getId()));
                                    resultIntent.putExtra(Constants.NAME, user.getName());
                                    resultIntent.putExtra(Constants.PHOTO, profilePic);
                                    Log.d(TAG,"Email:"+email);
                                    Log.d(TAG, "Name:" + user.getName());
                                    setResult(RESULT_OK, resultIntent);

                                    mProgressDialog.dismiss();
                                    finish();
                                }
                            }
                            if (response.getError() != null) {
                                // Handle errors, will do so later.
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("result", LOGIN_FAILURE);
                                setResult(RESULT_OK, resultIntent);
                                mProgressDialog.dismiss();
                                finish();
                            }
                        }
                    });
            Bundle params = request.getParameters();
            params.putString("fields", "email,name");
            request.setParameters(params);
            request.executeAsync();
        } else if (state.isClosed()) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("result", LOGIN_FAILURE);
            setResult(RESULT_OK, resultIntent);
            mProgressDialog.dismiss();
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mSocialNetwork == FACEBOOK) {
            mUIHelper.onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSocialNetwork == FACEBOOK) {
            mUIHelper.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSocialNetwork == FACEBOOK) {
            mUIHelper.onDestroy();
        }
    }

    protected void onStart() {
        super.onStart();
        if (mSocialNetwork == GOOGLE_PLUS) {
            if (mGoogleApiClient == null)
                return;
            mGoogleApiClient.connect();
        }
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient == null)
            return;
        if (mSocialNetwork == GOOGLE_PLUS && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    @Override
    public void onConnected(Bundle bundle) {

        Log.d(TAG, "connected");
        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            final Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            final String personName = currentPerson.getDisplayName();
            final Person.Image personPhoto = currentPerson.getImage();
            final String email = Plus.AccountApi.getAccountName(mGoogleApiClient);

            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        mGooglePlusToken = GoogleAuthUtil.getToken(getApplicationContext(), email,
                                "oauth2:" + Scopes.PLUS_LOGIN);
                        Log.d(TAG, "Google Access token " + mGooglePlusToken);

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("result", LOGIN_SUCCESS);
                                resultIntent.putExtra("social_network", mSocialNetwork);
                                resultIntent.putExtra(Constants.EMAIL, email);
                                resultIntent.putExtra(Constants.NAME, personName);
                                //resultIntent.putExtra(Constants.ID, currentPerson.getId());
                                resultIntent.putExtra(Constants.PHOTO, personPhoto.getUrl().replace("sz=50", "sz=500"));
                                resultIntent.putExtra(Constants.TOKEN, mGooglePlusToken);

                                setResult(RESULT_OK, resultIntent);
                                mProgressDialog.dismiss();
                                finish();
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("result", LOGIN_FAILURE);
                                setResult(RESULT_OK, resultIntent);
                                mProgressDialog.dismiss();
                                finish();
                            }
                        });
                    }
                }
            };
            final Thread thread = new Thread(runnable);
            thread.start();

        } else {
            //  Log.d(TAG,"Inside else of onConnected");
            Intent resultIntent = new Intent();
            resultIntent.putExtra("result", LOGIN_FAILURE);
            setResult(RESULT_OK, resultIntent);

            mProgressDialog.dismiss();
            Log.d(TAG,"Inside else of onConnected");
            finish();
        }

    }


    @Override
    public void onConnectionSuspended(int cause) {
        Log.d(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(final ConnectionResult result) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Connection failed => " + result.toString());
                if (!result.hasResolution()) {
                    GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), SocialLoginActivity.this,
                            0).show();
                    return;
                }
                Log.d(TAG, "In google api connect and finding resolution");
                if (!mIntentInProgress && result.hasResolution()) {
                    try {
                        mIntentInProgress = true;
                        result.startResolutionForResult(SocialLoginActivity.this, RC_SIGN_IN);
                    } catch (IntentSender.SendIntentException e) {
                        // The intent was canceled before it was sent.  Return to the default
                        // state and attempt to connect to get an updated ConnectionResult.
                        Log.e(TAG, "Error", e);
                        mIntentInProgress = false;
                        Toast.makeText(SocialLoginActivity.this, "Error connecting to Google", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);
        if (mSocialNetwork == GOOGLE_PLUS && requestCode == RC_SIGN_IN) {
            Log.d(TAG, "Activity result happened " + responseCode);
            mIntentInProgress = false;
            if (!mGoogleApiClient.isConnecting()) {
                Log.d(TAG, "Google API client is connecting again");
                mGoogleApiClient.connect();
            }
            mProgressDialog.dismiss();
        } else if (mSocialNetwork == FACEBOOK) {
            mUIHelper.onActivityResult(requestCode, responseCode, intent);
        }

    }



}