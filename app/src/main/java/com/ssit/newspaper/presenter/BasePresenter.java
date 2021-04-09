package com.ssit.newspaper.presenter;

public interface BasePresenter {
    interface view{
        void onSuccess(String message);
        void onFailure(String message);
    }

}
