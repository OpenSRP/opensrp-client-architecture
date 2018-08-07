package org.smartregister.easy.rules;

import android.content.Context;
import android.util.Log;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.smartregister.easy.rules.domain.Contact;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ContactRule {

    String baseEntityId;
    Context context;
    Rules contactRules;

    List<Contact> contactList = new ArrayList<>();

    public ContactRule(Context context, int startWks) {
        this.context = context;
        this.baseEntityId = UUID.randomUUID().toString();
        this.contactRules(startWks);
    }

    public void contactRules(int startWks) {
        if (startWks > 40) {
            return;
        }

        Contact contact = new Contact(startWks);
        contactList.add(contact);

        Facts facts = new Facts();
        facts.put("contact", contact);

        if (contactRules == null) {
            contactRules = getRulesFromAsset("rules/contact-rules.yml");
            if (contactRules == null) {
                return;
            }
        }

        //create a default rules engine and fire rules on known facts
        RulesEngine rulesEngine = new DefaultRulesEngine();

        rulesEngine.fire(contactRules, facts);

        int afterWks = contact.afterWks;
        Log.d(MainActivity.class.getName(), "Contact: after weeks" + afterWks);

        contactRules(startWks + afterWks);

    }

    private Rules getRulesFromAsset(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));
            return MVELRuleFactory.createRulesFrom(br);
        } catch (IOException e) {
            Log.e(ContactRule.class.getName(), e.getMessage(), e);
            return null;
        }
    }

}
