package com.joyshah.schedule.myapplication;

import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.OrderedRealmCollection;

/**
 * Created by Joy Shah on 15-10-2017.
 */

public class RecyclerCountryAdapter extends RealmRecyclerViewAdapter<Country, RecyclerCountryAdapter.RecyclerCountryHolder> {

    public RecyclerCountryAdapter(@Nullable OrderedRealmCollection<Country> data, boolean autoUpdate) {
        super(data, autoUpdate);
    }

    @Override
    public RecyclerCountryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_reclyer_view, parent, false);

        return new RecyclerCountryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerCountryHolder holder, int position) {

        final Country country = getItem(position);
        holder.textView.setText(country.getCountryName());


    }


    public class RecyclerCountryHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView textView;

        public RecyclerCountryHolder(View itemView) {
            super(itemView);
            textView = (AppCompatTextView) itemView.findViewById(R.id.countryName);
        }
    }
}
