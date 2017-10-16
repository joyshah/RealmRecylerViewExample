package com.joyshah.schedule.myapplication.model;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Joy Shah on 15-10-2017.
 */

public class Person extends RealmObject {
    public static final String PERSON_ID = "personId";

    @PrimaryKey
    private int personId;
    private String personName;

    public static int getNextKey(Realm realm) {
        try {
            Number number = realm.where(Person.class).max(PERSON_ID);
            if (number != null) {
                return number.intValue() + 1;
            } else {
                return 0;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
