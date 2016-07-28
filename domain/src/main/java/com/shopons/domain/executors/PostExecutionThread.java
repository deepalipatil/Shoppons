package com.shopons.domain.executors;

import rx.Scheduler;

public interface PostExecutionThread {
    Scheduler getScheduler();
}
