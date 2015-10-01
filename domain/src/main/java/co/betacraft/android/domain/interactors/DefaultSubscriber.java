package co.betacraft.android.domain.interactors;

/**
 * Default subscriber base class to be used whenever you want default error handling.
 *
 * @author : Akshay Deo
 * @date : 01/10/15 : 8:26 PM
 * @email : akshay@betacraft.co
 */
public class DefaultSubscriber<T> extends rx.Subscriber<T> {
    @Override public void onCompleted() {
        // no-op by default.
    }

    @Override public void onError(Throwable e) {
        // no-op by default.
    }

    @Override public void onNext(T t) {
        // no-op by default.
    }
}