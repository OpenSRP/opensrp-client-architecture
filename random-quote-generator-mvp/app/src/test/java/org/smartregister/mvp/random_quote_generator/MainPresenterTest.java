package org.smartregister.mvp.random_quote_generator;

import android.content.Context;

import org.smartregister.mvp.random_quote_generator.presenter.MainPresenterImpl;
import org.smartregister.mvp.random_quote_generator.presenter.interactor.GetQuoteInteractor;
import org.smartregister.mvp.random_quote_generator.presenter.interactor.GetQuoteInteractorImpl;
import org.smartregister.mvp.random_quote_generator.view.activity.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.doNothing;


/**
 * Created by vkaruri on 03/05/2018.
 */

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock
    MainActivity mainView;

    @Mock
    GetQuoteInteractorImpl getQuoteInteractor;

    @Mock
    GetQuoteInteractor.OnFinishedListener onFinishedListener;

    @Mock
    Context context;

    private MainPresenterImpl mainPresenter;

    @Before
    public void setUp() {

        when(mainView.getApplicationContext()).thenReturn(context);
        mainPresenter = new MainPresenterImpl(mainView);
    }

    @Test
    public void testGetNextQuoteIsCalledOnButtonClick() {

        mainPresenter.setGetQuoteInteractor(getQuoteInteractor);

        doNothing().when(mainView).showProgress();
        doNothing().when(getQuoteInteractor).getNextQuote(onFinishedListener);

        mainPresenter.onButtonClick();

        verify(getQuoteInteractor, times(1)).getNextQuote(any(GetQuoteInteractor.OnFinishedListener.class));
    }

    @Test
    public void testMainViewSetQuoteMethodIsCalledOnFinish() {

        mainPresenter.onFinished("my string");
        verify(mainView, times(1)).setQuote("my string");
    }

    @Test
    public void testMainViewHideProgressMethodIsCalledOnFinish() {

        mainPresenter.onFinished("my string");
        verify(mainView, times(1)).hideProgress();
    }

    @Test
    public void testMainViewIsNullAfterOnDestroyMethodCall() {

        mainPresenter = new MainPresenterImpl(mainView);

        mainPresenter.onDestroy();
        assertNull(mainPresenter.getMainView());
    }
}
