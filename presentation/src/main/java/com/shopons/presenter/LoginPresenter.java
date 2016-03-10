package com.shopons.presenter;

import com.shopons.domain.MsgFromServer;
import com.shopons.domain.User;
import com.shopons.domain.interactors.ChangePassword;
import com.shopons.domain.interactors.FBLogin;
import com.shopons.domain.interactors.GPlusLogin;
import com.shopons.domain.interactors.LoginUser;
import com.shopons.domain.interactors.LogoutUser;
import com.shopons.domain.interactors.OtpForForgotPassword;
import com.shopons.domain.interactors.RegisterUser;
import com.shopons.domain.interactors.UpdateUser;
import com.shopons.domain.interactors.VerifyOtp;
import com.shopons.domain.interactors.VerifyOtpForForgotPassword;
import com.shopons.domain.repositories.UserRepository;

import rx.Subscriber;

/**
 * Login Screen Presenter
 *
 * @author : Kaustubh Deshmukh
 * @date : 05/10/15 : 5:39 PM
 * @email : akshay@betacraft.co
 */
public final class LoginPresenter extends Presenter {

    private UserRepository mUserRepository;
    private User mUser;

    public LoginPresenter() {
        mUserRepository = new com.shopons.data.repository.UserRepository();
    }

    public void fbLogin(final User user, final Subscriber<User> userSubscriber){
        mSubscriptions.add(new FBLogin(mUser.getEmailAddress(),mUser.getFacebookToken(),mUser.getFacebookId(),
                mThreadExecutor, mPostExecutionThread).execute(userSubscriber));
    }

    public void gPlusLogin(final User user, final Subscriber<User> userSubscriber){
        mSubscriptions.add(new GPlusLogin(mUser.getUserName(),mUser.getEmailAddress(),mUser.getGooglePlusToken(),
                mThreadExecutor, mPostExecutionThread).execute(userSubscriber));
    }

    public void registerUserByEmail(final User user, final Subscriber<User> userSubscriber) {
        mSubscriptions.add(new RegisterUser(mUser.getEmailAddress(),mUser.getPhoneNumber(),mUser.getPassword(),
                mThreadExecutor, mPostExecutionThread).execute(userSubscriber));
    }

    public void loginUserByEmail(final User user, final Subscriber<User> userSubscriber) {
        mSubscriptions.add(new LoginUser(mUser.getPhoneNumber(),mUser.getPassword(),
                mThreadExecutor, mPostExecutionThread).execute(userSubscriber));
    }

    public void logoutUser(final Subscriber<Void> subscriber){
        mSubscriptions.add(new LogoutUser(mUserRepository,
                mThreadExecutor, mPostExecutionThread).execute(subscriber));
    }

    public void changePassword(final User user, final Subscriber<MsgFromServer> subscriber) {
        mSubscriptions.add(new ChangePassword(user, mUserRepository, mThreadExecutor,
                mPostExecutionThread).execute(subscriber));
    }

    public void updateUser(final User user, final Subscriber<User> subscriber) {
        mSubscriptions.add(new UpdateUser(user, mUserRepository, mThreadExecutor,
                mPostExecutionThread).execute(subscriber));
    }

    public void resendOtp(final String authKey, final Subscriber<MsgFromServer> subscriber) {
        mSubscriptions.add(new VerifyOtp(mUserRepository, authKey, mThreadExecutor,
                mPostExecutionThread).execute(subscriber));
    }

    public void getForgotPasswordOtp(final String email, final Subscriber<MsgFromServer> successSubscriber) {
        mSubscriptions.add(new OtpForForgotPassword(mUserRepository, email, mThreadExecutor,
                mPostExecutionThread).execute(successSubscriber));
    }

    public void verifyForgotPasswordOtp(final String otp, final String email, final Subscriber<User> successSubscriber) {
        mSubscriptions.add(new VerifyOtpForForgotPassword(mUserRepository, otp, email, mThreadExecutor,
                mPostExecutionThread).execute(successSubscriber));
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {
        cancelRunningUseCases();
    }

    @Override
    public void destroy() {
        cancelRunningUseCases();
    }
}
