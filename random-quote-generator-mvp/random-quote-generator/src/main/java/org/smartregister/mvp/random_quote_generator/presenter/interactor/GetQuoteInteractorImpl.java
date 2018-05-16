package org.smartregister.mvp.random_quote_generator.presenter.interactor;

/**
 * Created by VonBass on 5/1/2018.
 */
import android.content.Context;
import android.os.Handler;

import org.smartregister.mvp.random_quote_generator.model.QuoteRepository;

import java.util.Arrays;
import java.util.List;

public class GetQuoteInteractorImpl implements GetQuoteInteractor {

    private QuoteRepository quoteRepository;

    private List<String> quotes = Arrays.asList(
            "Be yourself. everyone else is already taken.",
            "A room without books is like a body without a soul.",
            "You only live once, but if you do it right, once is enough.",
            "Be the change that you wish to see in the world.",
            "If you tell the truth, you don't have to remember anything.",
            "Android MVP. The choice design pattern!"
    );

    public GetQuoteInteractorImpl(Context context) {
        quoteRepository = new QuoteRepository(context, quotes);
    }

    @Override
    public void getNextQuote(final OnFinishedListener listener) {

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                listener.onFinished(quoteRepository.getQuote());
            }
        });
    }
}