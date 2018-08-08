package org.smartregister.easy.rules;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.InferenceRulesEngine;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.smartregister.easy.rules.domain.Contact;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactRules(10);
        contactRules(20);
        contactRules(23);
        contactRules(24);
        contactRules(26);
        contactRules(30);
        contactRules(34);
    }


    public void contactRules(int startWks) {

        ContactRule contactRule = new ContactRule(startWks, true);
        Facts facts = new Facts();
        facts.put("contactRule", contactRule);

        Rules rules = getRulesFromAsset("rules/contact-rules.yml");
        if (rules == null) {
            return;
        }

        //create a default rules engine and fire rules on known facts
        RulesEngine rulesEngine = new InferenceRulesEngine();

        rulesEngine.fire(rules, facts);


        Log.d(MainActivity.class.getName(), "Contact list for : " + contactRule.baseEntityId);
        Log.d(MainActivity.class.getName(), "Contact Initial visit : " + contactRule.initialVisit + " Weeks");
        Log.d(MainActivity.class.getName(), "Contact Is first visit? : " + (contactRule.isFirst ? "Yes" : "No"));
        Log.d(MainActivity.class.getName(), "Contact currentVisit : " + contactRule.currentVisit);
        Log.d(MainActivity.class.getName(), "Contact maximumVisits : " + contactRule.maximumVisits);
        Log.d(MainActivity.class.getName(), "Contact list size : " + contactRule.set.size());

        Set<Integer> contactList = contactRule.set;
        List<Integer> list = new ArrayList<>(contactList);
        Collections.sort(list);
        for (Integer startWeeks : list) {
            Log.d(MainActivity.class.getName(), "Next Contact: " + startWeeks);
        }

    }

    private Rules getRulesFromAsset(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open(fileName)));
            return MVELRuleFactory.createRulesFrom(br);
        } catch (IOException e) {
            Log.e(ContactRule.class.getName(), e.getMessage(), e);
            return null;
        }
    }

}
