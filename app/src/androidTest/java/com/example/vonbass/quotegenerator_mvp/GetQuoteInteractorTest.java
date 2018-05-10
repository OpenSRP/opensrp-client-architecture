package com.example.vonbass.quotegenerator_mvp;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import com.example.vonbass.quotegenerator_mvp.model.QuoteRepository;
import com.example.vonbass.quotegenerator_mvp.presenter.interactor.GetQuoteInteractor;
import com.example.vonbass.quotegenerator_mvp.presenter.interactor.GetQuoteInteractorImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;

/**
 * Created by vkaruri on 03/05/2018.
 */

@RunWith(AndroidJUnit4.class)
public class GetQuoteInteractorTest {

    String testString = null;

    GetQuoteInteractor.OnFinishedListener onFinishedListener = new GetQuoteInteractor.OnFinishedListener() {
        @Override
        public void onFinished(String string) {
            testString = "Function was called";
        }
    };

    private GetQuoteInteractorImpl getQuoteInteractor;

    @Before
    public void setUp() {
        getQuoteInteractor = new GetQuoteInteractorImpl(getTargetContext());
    }

    @Test
    public void testGetQuoteMethodIsCalledOnQuoteRepository() {

        getQuoteInteractor.getNextQuote(onFinishedListener);
        assert(testString.equals("Function was called"));
    }
}
