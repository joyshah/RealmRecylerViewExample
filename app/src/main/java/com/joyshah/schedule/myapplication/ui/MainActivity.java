package com.joyshah.schedule.myapplication.ui;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.joyshah.schedule.myapplication.R;
import com.joyshah.schedule.myapplication.adapter.RecyclerPersonAdapter;
import com.joyshah.schedule.myapplication.model.Person;

import io.realm.Realm;

public class MainActivity extends RealmHelper {

    private static final String TAG = "MainActivity";
    private FloatingActionButton mFab;
    private RecyclerView mRecyclerView;
    private Realm mRealm;
    private RecyclerPersonAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRealm = Realm.getDefaultInstance();
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        setupRecyclerView();
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });
    }

    private void setupRecyclerView() {
        //create adapter object and passed data of person present im realm
        mAdapter = new RecyclerPersonAdapter(mRealm.where(Person.class).findAll(), true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        //initialize swipe functionality
        initSwipe();

    }

    private void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int countryID = mAdapter.getCountryId(viewHolder.getAdapterPosition());
                if (direction == ItemTouchHelper.RIGHT) {
                    Log.i(TAG, "swiped right");
                    //edit name in person object
                    showInputDialog(countryID);
                } else if (direction == ItemTouchHelper.LEFT) {
                    Log.i(TAG, "swiped left");
                    //delete person object
                    removePerson(countryID);
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                final View foregroundView = ((RecyclerPersonAdapter.RecyclerPersonHolder) viewHolder).relativeLayoutForeground;
                getDefaultUIUtil().clearView(foregroundView);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                final View foregroundView = ((RecyclerPersonAdapter.RecyclerPersonHolder) viewHolder).relativeLayoutForeground;

                //remove the foreground view inorder to view background view
                getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                        actionState, isCurrentlyActive);

                // dX>0 swiped right side and dX<0 swiped left side
                //show edit or delete view accordingly

                if (dX > 0) {
                    ((RecyclerPersonAdapter.RecyclerPersonHolder) viewHolder).relativeLayoutDelete.setVisibility(View.GONE);
                    ((RecyclerPersonAdapter.RecyclerPersonHolder) viewHolder).relativeLayoutEdit.setVisibility(View.VISIBLE);

                } else {
                    ((RecyclerPersonAdapter.RecyclerPersonHolder) viewHolder).relativeLayoutDelete.setVisibility(View.VISIBLE);
                    ((RecyclerPersonAdapter.RecyclerPersonHolder) viewHolder).relativeLayoutEdit.setVisibility(View.GONE);
                }


            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void showInputDialog(final int countryID) {

        // get layout_edittext_dialog.xml view
        LayoutInflater li = LayoutInflater.from(MainActivity.this);
        View promptsView = li.inflate(R.layout.layout_edittext_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MainActivity.this);

        // set layout_edittext_dialog.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
        final TextView textViewDescription = (TextView) promptsView.findViewById(R.id.textViewDescription);
        textViewDescription.setText(getString(R.string.str_edit));

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(getString(R.string.str_add),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                final String inputValue = userInput.getText().toString();
                                if (inputValue.length() >= 2) {
                                    editPerson(countryID, inputValue);
                                }
                            }
                        })
                .setNegativeButton(getString(R.string.str_cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


    private void showInputDialog() {
        // get layout_edittext_dialog.xml view
        LayoutInflater li = LayoutInflater.from(MainActivity.this);
        View promptsView = li.inflate(R.layout.layout_edittext_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MainActivity.this);

        // set layout_edittext_dialog.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(getString(R.string.str_add),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                final String inputValue = userInput.getText().toString();
                                if (inputValue.length() >= 2) {
                                    addPerson(inputValue);
                                }
                            }
                        })
                .setNegativeButton(getString(R.string.str_cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    @Override
    Realm getRealm() {
        return mRealm;
    }
}
