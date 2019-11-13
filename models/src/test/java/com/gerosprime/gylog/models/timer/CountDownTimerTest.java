package com.gerosprime.gylog.models.timer;

import android.os.SystemClock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import io.reactivex.functions.Consumer;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class CountDownTimerTest {

    CountDownTimer countDownTimer;

    CountDownEntity countDownEntity;

    @Before
    public void init() {
        countDownTimer = new CountDownTimer();
        countDownEntity = new CountDownEntity(3);
    }

    @Test
    public void create_running_timerCountDown() {
        countDownTimer.create(countDownEntity)
                .subscribe(new Consumer<CountDownEntity>() {
                    @Override
                    public void accept(CountDownEntity countDownEntity) throws Exception {
                        System.out.println(countDownEntity.getSeconds());
                    }
                });

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


}