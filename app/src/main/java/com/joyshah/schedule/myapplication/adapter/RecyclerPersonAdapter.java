package com.joyshah.schedule.myapplication.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.joyshah.schedule.myapplication.R;
import com.joyshah.schedule.myapplication.model.Person;

import io.realm.OrderedRealmCollection;

/**
 * Created by Joy Shah on 15-10-2017.
 */

public class RecyclerPersonAdapter extends RealmRecyclerViewAdapter<Person, RecyclerPersonAdapter.RecyclerPersonHolder> {

    public RecyclerPersonAdapter(@Nullable OrderedRealmCollection<Person> data, boolean autoUpdate) {
        super(data, autoUpdate);
    }

    @Override
    public RecyclerPersonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_reclyer_view, parent, false);

        return new RecyclerPersonHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerPersonHolder holder, int position) {

        final Person person = getItem(position);
        holder.textViewName.setText(person.getPersonName());

    }


    public int getCountryId(int index) {
        return getItem(index).getPersonId();
    }

    public class RecyclerPersonHolder extends RecyclerView.ViewHolder {
        public RelativeLayout relativeLayoutForeground, relativeLayoutDelete, relativeLayoutEdit;
        private AppCompatTextView textViewName;

        RecyclerPersonHolder(View itemView) {
            super(itemView);
            textViewName = (AppCompatTextView) itemView.findViewById(R.id.personName);

            relativeLayoutForeground = (RelativeLayout) itemView.findViewById(R.id.view_foreground);
            relativeLayoutDelete = (RelativeLayout) itemView.findViewById(R.id.view_background_delete);
            relativeLayoutEdit = (RelativeLayout) itemView.findViewById(R.id.view_background_edit);
        }
    }
}
