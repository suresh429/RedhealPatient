package com.medoske.www.redhealpatient.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.medoske.www.redhealpatient.Items.GridItemAfterNoon;
import com.medoske.www.redhealpatient.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 24.6.17.
 */

public class GridAdapterAfterNoon extends ArrayAdapter<GridItemAfterNoon> {

    private Context activity;
    public List<GridItemAfterNoon> parkingList;
    ArrayList<GridItemAfterNoon> arraylist;
    GridItemAfterNoon book;


    // constructor
    public GridAdapterAfterNoon(Context activity, int resource, List<GridItemAfterNoon> books) {
        super(activity, resource, books);
        this.activity = activity;
        this.parkingList = books;
        arraylist = new ArrayList<GridItemAfterNoon>();
        arraylist.addAll(parkingList);
    }

    @Override
    public int getCount() {
        return parkingList.size();
    }

    @Override
    public GridItemAfterNoon getItem(int position) {
        return parkingList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // result for item get position
        final GridItemAfterNoon productItem=parkingList.get(position);

        ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {

            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.grid_item, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);


        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }

        holder.timeSlot.setText(productItem.getBookingTime());
        if (productItem.getStatus().equals("unavailable")){

            holder.timeSlot.setPaintFlags(holder.timeSlot.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

        }

        return convertView;
    }


    // View Holder
    private class ViewHolder {
        public TextView timeSlot;

        public ViewHolder(View v) {
            timeSlot=(TextView)v.findViewById(R.id.txtTimeSlot);
            timeSlot.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));
        }
    }



}
