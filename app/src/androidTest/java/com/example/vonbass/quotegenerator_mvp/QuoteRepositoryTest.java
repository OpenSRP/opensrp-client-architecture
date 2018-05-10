package com.example.vonbass.quotegenerator_mvp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.test.runner.AndroidJUnit4;

import com.example.vonbass.quotegenerator_mvp.model.QuoteRepository;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
/**
 * Created by vkaruri on 03/05/2018.
 */

@RunWith(AndroidJUnit4.class)
public class QuoteRepositoryTest {

    private static List<String> quotes;
    private QuoteRepository quoteRepository;

    @BeforeClass
    public static void init() {

        quotes = Arrays.asList(
                "Be yourself. everyone else is already taken.",
                "A room without books is like a body without a soul.",
                "You only live once, but if you do it right, once is enough.",
                "Be the change that you wish to see in the world.",
                "If you tell the truth, you don't have to remember anything.",
                "Android MVP. The choice design pattern!"
        );
    }

    @Before
    public void setUp() {

        quoteRepository = new QuoteRepository(getTargetContext(), quotes);
    }

    @Test
    public void testGetQuoteFunctionality() {

        String quote = quoteRepository.getQuote();
        assertNotNull(quote);
    }

    @Test
    public void testAddQuoteFunctionality() {
        // clear repository
        quoteRepository.clearRepository(quoteRepository.getWritableDatabase());

        // add quote
        ArrayList<String> quote = new ArrayList<>();
        quote.add("This is a quote");
        quoteRepository.addQuotes(quote, quoteRepository.getWritableDatabase());

        assertEquals(7, quoteRepository.getRepositorySize());
    }

    @Test
    public void testGetRepositorySize() {
        // clear repository
        quoteRepository.clearRepository(quoteRepository.getWritableDatabase());

        // recreate table
        quoteRepository.addQuotes(new ArrayList<String>(), quoteRepository.getWritableDatabase());
        assertEquals(6, quoteRepository.getRepositorySize());
    }

    @Test
    public void testClearRepository() {
        List<String> quotes = Arrays.asList(
                "Be yourself. everyone else is already taken.",
                "A room without books is like a body without a soul.",
                "You only live once, but if you do it right, once is enough.",
                "Be the change that you wish to see in the world.",
                "If you tell the truth, you don't have to remember anything.",
                "Android MVP. The choice design pattern!"
        );

        quoteRepository.addQuotes(quotes, quoteRepository.getWritableDatabase());
        assertEquals(13, quoteRepository.getRepositorySize());

        quoteRepository.clearRepository(quoteRepository.getWritableDatabase());
        assertEquals(6, quoteRepository.getRepositorySize());
    }

    @Test
    public void testOnUpgrade() {

        List<String> quotes = Arrays.asList(
                "Be yourself. everyone else is already taken.",
                "A room without books is like a body without a soul.",
                "You only live once, but if you do it right, once is enough.",
                "Be the change that you wish to see in the world.",
                "If you tell the truth, you don't have to remember anything.",
                "Android MVP. The choice design pattern!"
        );

        quoteRepository.addQuotes(quotes, quoteRepository.getWritableDatabase());
        assertEquals(12, quoteRepository.getRepositorySize());

        quoteRepository.onUpgrade(quoteRepository.getWritableDatabase(), -1, -1);
        assertEquals(6, quoteRepository.getRepositorySize());
    }
}
