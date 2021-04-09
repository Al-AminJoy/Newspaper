package com.ssit.newspaper.presenter;

import android.content.Context;

public interface SplashPresenter extends BasePresenter{
    interface model{
        void loadSplash(int sleep);
    }
}
