package com.ssit.newspaper.presenterImplementation;


import android.content.Context;

import com.ssit.newspaper.presenter.SplashPresenter;


public class SplashImplementation implements SplashPresenter.model {
    private SplashPresenter.view view;

    public SplashImplementation(SplashPresenter.view view) {
        this.view = view;
    }

    @Override
    public void loadSplash(int sleep) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //thread inside thread
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(sleep * 1000);
                            view.onSuccess("Success");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();

            }
        });
        thread.start();

    }

}
