package com.joyshah.schedule.myapplication.ui;

import android.support.v7.app.AppCompatActivity;

import com.joyshah.schedule.myapplication.model.Person;

import io.realm.Realm;

/**
 * Created by Joy Shah on 16-10-2017.
 */

public abstract class RealmHelper extends AppCompatActivity {

    protected Realm realm;

    abstract Realm getRealm();

    public void addPerson(final String name) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    Person person = realm.createObject(Person.class, Person.getNextKey(realm));
                    person.setPersonName(name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void removePerson(final int personId) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    Person person = realm.where(Person.class).equalTo(Person.PERSON_ID, personId).findFirst();
                    person.deleteFromRealm();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void editPerson(final int personId, final String name) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    Person person = realm.where(Person.class).equalTo(Person.PERSON_ID, personId).findFirst();
                    person.setPersonName(name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
