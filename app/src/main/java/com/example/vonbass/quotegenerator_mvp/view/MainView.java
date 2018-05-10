package com.example.vonbass.quotegenerator_mvp.view;

/**
 * Created by VonBass on 5/1/2018.
 */
public interface MainView {

    void showProgress();

    void hideProgress();

    void setQuote(String string);
}