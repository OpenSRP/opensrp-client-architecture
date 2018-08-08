package org.smartregister.easy.rules;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ContactRule {

    public String baseEntityId;

    public boolean isFirst;

    public int initialVisit;
    public int currentVisit;

    public int maximumVisits;

    public Set<Integer> set;

    public ContactRule(int wks, boolean isFirst) {
        this.baseEntityId = UUID.randomUUID().toString();

        this.initialVisit = wks;
        this.currentVisit = wks;

        this.isFirst = isFirst;

        this.set = new HashSet<>();
    }

}
