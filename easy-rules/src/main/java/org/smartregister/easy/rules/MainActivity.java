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

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            contactRules(10);

        } catch (IOException e) {
            Log.e(MainActivity.class.getName(), e.getMessage(), e);
        }
    }


    public void contactRules(int startWks) throws IOException {

        Contact contact = new Contact(startWks);
        Facts facts = new Facts();
        facts.put("contact", contact);

        BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("rules/contact-rules.yml")));
        Rules rules = MVELRuleFactory.createRulesFrom(br);

        //create a default rules engine and fire rules on known facts
        RulesEngine rulesEngine = new DefaultRulesEngine();

        rulesEngine.fire(rules, facts);

        int afterWks = contact.afterWks;
        Log.d(MainActivity.class.getName(), "Contact: after weeks" + afterWks);

        if ((startWks + afterWks) <= contact.maxWks) {
            contactRules(startWks + afterWks);
        }

    }
}
