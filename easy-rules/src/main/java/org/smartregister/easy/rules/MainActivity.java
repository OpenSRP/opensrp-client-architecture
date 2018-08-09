package org.smartregister.easy.rules;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.InferenceRulesEngine;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.smartregister.easy.rules.adapter.Adapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TextView.OnEditorActionListener {

    private RecyclerView recyclerView;
    private TextView resultLabel;

    private Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupSpinner();

        setupRecyclerView();

        setupGenerator();
    }

    public void contactRules(int startWks, boolean isFirst) {

        ContactRule contactRule = new ContactRule(startWks, isFirst);
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

        if (resultLabel != null && resultLabel.getVisibility() == View.GONE) {
            resultLabel.setVisibility(View.VISIBLE);
        }

        if (recyclerView != null && recyclerView.getVisibility() == View.GONE) {
            recyclerView.setVisibility(View.VISIBLE);
        }

        adapter.setmDataset(list);
        adapter.notifyDataSetChanged();
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

    private void setupSpinner() {
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.yes_no, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void setupRecyclerView() {
        resultLabel = findViewById(R.id.result);

        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new Adapter(new ArrayList<Integer>());
        recyclerView.setAdapter(adapter);
    }

    private void setupGenerator() {
        final EditText contact = findViewById(R.id.contact);
        contact.setOnEditorActionListener(this);

        Button generator = findViewById(R.id.generate);
        generator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = contact.getText().toString();
                validateAndGenerate(text);
            }
        });
    }

    private void validateAndGenerate(String text) {
        hideSoftKeyboard();
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(MainActivity.this, "Please enter the contact in weeks", Toast.LENGTH_LONG).show();
        } else {
            Integer contact = Integer.valueOf(text);
            if (contact < 1 || contact > 41) {
                Toast.makeText(MainActivity.this, "Contact should be between 1 and 41 weeks", Toast.LENGTH_LONG).show();
            } else {
                contactRules(contact, isFirst);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Object o = parent.getItemAtPosition(position);
        if (o.toString().equalsIgnoreCase("yes")) {
            isFirst = true;
        } else {
            isFirst = false;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_NULL || actionId == EditorInfo.IME_ACTION_DONE) {
            String text = v.getText().toString();
            validateAndGenerate(text);
            return true;
        }
        return false;
    }

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                getCurrentFocus().getWindowToken(), 0);
    }
}
