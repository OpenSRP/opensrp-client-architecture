package com.example.vonbass.quotegenerator_mvp.presenter.interactor;

/**
 * Created by VonBass on 5/1/2018.
 */
public interface GetQuoteInteractor {

    interface OnFinishedListener {
        void onFinished(String string);
    }

    void getNextQuote(OnFinishedListener listener);
}
