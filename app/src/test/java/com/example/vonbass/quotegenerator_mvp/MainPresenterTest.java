package com.example.vonbass.quotegenerator_mvp;

import com.example.vonbass.quotegenerator_mvp.presenter.MainPresenterImpl;
import com.example.vonbass.quotegenerator_mvp.presenter.interactor.GetQuoteInteractor;
import com.example.vonbass.quotegenerator_mvp.presenter.interactor.GetQuoteInteractorImpl;
import com.example.vonbass.quotegenerator_mvp.view.MainView;
import com.example.vonbass.quotegenerator_mvp.view.activity.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doNothing;


/**
 * Created by vkaruri on 03/05/2018.
 */

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock
    MainView mainView;

    @Mock
    GetQuoteInteractorImpl getQuoteInteractor;

    @Mock
    GetQuoteInteractor.OnFinishedListener onFinishedListener;

    MainPresenterImpl mainPresenter;

    @Before
    public void setUp() {
        mainPresenter = new MainPresenterImpl(mainView, getQuoteInteractor);
    }

    @Test
    public void testGetNextQuoteIsCalledOnButtonClick() {

        doNothing().when(mainView).showProgress();
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

        MainActivity mainActivity = new MainActivity();
        mainPresenter = new MainPresenterImpl(mainActivity, getQuoteInteractor);

        mainPresenter.onDestroy();
        assertNull(mainPresenter.getMainView());
    }
}
