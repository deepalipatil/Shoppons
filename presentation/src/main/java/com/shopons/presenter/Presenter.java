package com.shopons.presenter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.shopons.domain.executors.PostExecutionThread;
import com.shopons.domain.executors.ThreadExecutor;
import com.shopons.domain.interactors.UseCase;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Base class for all the presenters
 *
 * @author : Kaustubh Deshmukh
 * @date : 05/10/15 : 5:36 PM
 * @email : akshay@betacraft.co
 */
public abstract class Presenter {
    protected List<UseCase> mSubscriptions = new ArrayList<>();
    /**
     * Executor service for executing non-ui routines
     */
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(2);

    protected ThreadExecutor mThreadExecutor = new ThreadExecutor() {
        @Override
        public void execute(@NotNull final Runnable command) {
            mExecutorService.execute(command);
        }
    };

    protected PostExecutionThread mPostExecutionThread = new PostExecutionThread() {
        @Override
        public Scheduler getScheduler() {
            return AndroidSchedulers.mainThread();
        }
    };

    protected void cancelRunningUseCases(){
        for(UseCase useCase:mSubscriptions){
            useCase.unsubscribe();
        }
    }

    abstract void resume();
    abstract void pause();
    abstract void destroy();
}
