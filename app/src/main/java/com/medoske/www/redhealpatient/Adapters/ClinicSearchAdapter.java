package com.medoske.www.redhealpatient.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.medoske.www.redhealpatient.Items.ClinicSearchItem;
import com.medoske.www.redhealpatient.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by USER on 21.6.17.
 */

public class ClinicSearchAdapter extends ArrayAdapter<ClinicSearchItem> {

    private Context activity;
    public List<ClinicSearchItem> parkingList;
    ArrayList<ClinicSearchItem> arraylist;
    ClinicSearchItem book;


    // constructor
    public ClinicSearchAdapter(Context activity, int resource, List<ClinicSearchItem> books) {
        super(activity, resource, books);
        this.activity = activity;
        this.parkingList = books;
        arraylist = new ArrayList<ClinicSearchItem>();
        arraylist.addAll(parkingList);
    }

    @Override
    public int getCount() {
        return parkingList.size();
    }

    @Override
    public ClinicSearchItem getItem(int position) {
        return parkingList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // result for item get position
        final ClinicSearchItem productItem=parkingList.get(position);

        ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {

            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.clinicsearch_item, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);


        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }
        holder.clinicName.setText(productItem.getClinicName());
        Log.e("clinicName123",""+productItem.getClinicName());
        holder.area.setText(productItem.getAddress());
        Log.e("area123",""+productItem.getAddress());
        Picasso.with(getContext()).load(productItem.getClinicImage()).placeholder(R.drawable.ic_action_placeholder).into(holder.imageView);

        return convertView;
    }


    // View Holder
    private class ViewHolder {
        public TextView clinicName;
        public TextView area;
        public CircleImageView imageView;

        public ViewHolder(View v) {
            clinicName=(TextView)v.findViewById(R.id.textClinicName);
            clinicName.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            area=(TextView)v.findViewById(R.id.textArea);
            area.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            imageView=(CircleImageView)v.findViewById(R.id.cliniImage);
        }
    }




}
