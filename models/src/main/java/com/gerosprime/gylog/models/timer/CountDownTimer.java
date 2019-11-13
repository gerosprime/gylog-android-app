package com.gerosprime.gylog.models.timer;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class CountDownTimer {

    public Observable<CountDownEntity> create(final CountDownEntity entity) {

        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .map(new Function<Long, CountDownEntity>() {
                    @Override
                    public CountDownEntity apply(Long aLong) throws Exception {
                        entity.addSeconds(-1);
                        return entity;
                    }
                })
                .filter(new Predicate<CountDownEntity>() {
                    @Override
                    public boolean test(CountDownEntity countDown) throws Exception {
                        return countDown.getSeconds() >= 0;
                    }
                });

    }

}
