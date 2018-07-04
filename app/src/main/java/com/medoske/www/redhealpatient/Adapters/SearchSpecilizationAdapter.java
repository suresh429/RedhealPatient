package com.medoske.www.redhealpatient.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.medoske.www.redhealpatient.Items.SpecailizationSearchItem;
import com.medoske.www.redhealpatient.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 21.6.17.
 */

public class SearchSpecilizationAdapter extends ArrayAdapter<SpecailizationSearchItem> {

    private Context activity;
    public List<SpecailizationSearchItem> parkingList;
    ArrayList<SpecailizationSearchItem> arraylist;
    SpecailizationSearchItem book;


    // constructor
    public SearchSpecilizationAdapter(Context activity, int resource, List<SpecailizationSearchItem> books) {
        super(activity, resource, books);
        this.activity = activity;
        this.parkingList = books;
        arraylist = new ArrayList<SpecailizationSearchItem>();
        arraylist.addAll(parkingList);
    }

    @Override
    public int getCount() {
        return parkingList.size();
    }

    @Override
    public SpecailizationSearchItem getItem(int position) {
        return parkingList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
// result for item get position
        final SpecailizationSearchItem productItem=parkingList.get(position);

        ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {

            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.searchspecailizationlist_item, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);


        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }

        holder.specialization.setText(productItem.getSpecialization());

        return convertView;
    }


    // View Holder
    private class ViewHolder {
        public TextView specialization;

        public ViewHolder(View v) {
            specialization=(TextView)v.findViewById(R.id.txtSearchSpecialization);
            specialization.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));
        }
    }

   /* // Filter for search by doctor name
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        parkingList.clear();
        if (charText.length() == 0) {
            parkingList.addAll(arraylist);

        }

        else
        {
            for (SpecilizationItem sp : arraylist)
            {
                if (sp.getSpecialization().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    parkingList.add(sp);

                }
            }
        }
        notifyDataSetChanged();
    }*/



}