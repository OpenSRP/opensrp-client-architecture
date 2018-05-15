package org.smartregister.mvp.random_quote_generator.presenter;

import android.content.Context;

import org.smartregister.mvp.random_quote_generator.presenter.interactor.GetQuoteInteractor;
import org.smartregister.mvp.random_quote_generator.presenter.interactor.GetQuoteInteractorImpl;
import org.smartregister.mvp.random_quote_generator.view.MainView;
import org.smartregister.mvp.random_quote_generator.view.activity.MainActivity;

/**
 * Created by VonBass on 5/1/2018.
 */
public class MainPresenterImpl implements MainPresenter, GetQuoteInteractor.OnFinishedListener {

    private MainActivity mainView;
    private GetQuoteInteractor getQuoteInteractor;

    public MainPresenterImpl(MainActivity mainActivity) {

        this.mainView = mainActivity;
        this.getQuoteInteractor = new GetQuoteInteractorImpl((Context) mainView.getApplicationContext());
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

    public void setGetQuoteInteractor(GetQuoteInteractor getQuoteInteractor) {

        this.getQuoteInteractor = getQuoteInteractor;
    }

    public MainView getMainView() {
        return mainView;
    }
}