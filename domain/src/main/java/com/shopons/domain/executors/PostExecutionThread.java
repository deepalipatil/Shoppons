package com.shopons.domain.executors;

import rx.Scheduler;

/**
 * @author : Kaustubh Deshmukh
 * @date : 01/10/15 : 3:51 PM
 * @email : akshay@betacraft.co
 */
public interface PostExecutionThread {
    Scheduler getScheduler();
}
