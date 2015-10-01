package co.betacraft.android.domain.executors;

import java.util.concurrent.Executor;

/**
 * These are the background task executors. Usually we can use Schedulers.io() or then we can
 * create our own Executors using standard Java APIs.
 *
 * @author : Akshay Deo
 * @date : 01/10/15 : 3:52 PM
 * @email : akshay@betacraft.co
 */
public interface ThreadExecutor extends Executor{
}
