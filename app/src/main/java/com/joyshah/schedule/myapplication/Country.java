package com.joyshah.schedule.myapplication;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Joy Shah on 15-10-2017.
 */

public class Country extends RealmObject {
    @PrimaryKey
    private int countryId;
    private String countryName;

    public static int getNextKey(Realm realm) {
        try {
            Number number = realm.where(Country.class).max("countryId");
            if (number != null) {
                return number.intValue() + 1;
            } else {
                return 0;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}
