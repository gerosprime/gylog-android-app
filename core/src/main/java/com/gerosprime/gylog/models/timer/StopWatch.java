package com.gerosprime.gylog.models.timer;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class StopWatch {

    public Observable<StopWatchEntity> create(final StopWatchEntity entity) {

        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .map(new Function<Long, StopWatchEntity>() {
                    @Override
                    public StopWatchEntity apply(Long aLong) throws Exception {
                        entity.addSeconds(1);
                        return entity;
                    }
                })
                .filter(new Predicate<StopWatchEntity>() {
                    @Override
                    public boolean test(StopWatchEntity countDown) throws Exception {
                        return countDown.getSeconds() >= 0;
                    }
                });

    }

}
