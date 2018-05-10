package com.example.vonbass.quotegenerator_mvp.presenter;

import android.content.Context;

import com.example.vonbass.quotegenerator_mvp.presenter.interactor.GetQuoteInteractorImpl;
import com.example.vonbass.quotegenerator_mvp.view.MainView;
import com.example.vonbass.quotegenerator_mvp.presenter.interactor.GetQuoteInteractor;

/**
 * Created by VonBass on 5/1/2018.
 */
public class MainPresenterImpl implements MainPresenter, GetQuoteInteractor.OnFinishedListener {

    private MainView mainView;
    private GetQuoteInteractor getQuoteInteractor;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
        this.getQuoteInteractor = new GetQuoteInteractorImpl((Context) mainView);
    }

    @Override
    public void onButtonClick() {
        if (mainView != null) {
            mainView.showProgress();
        }

        getQuoteInteractor.getNextQuote(this);
    }

    @Override
    public void onDestroy() {
        mainView = null;
    }

    @Override
    public void onFinished(String string) {
        if (mainView != null) {
            mainView.setQuote(string);
            mainView.hideProgress();
        }
    }

    public MainView getMainView() {
        return mainView;
    }
}