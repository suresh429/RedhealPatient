package com.medoske.www.redhealpatient.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.medoske.www.redhealpatient.TimeSlotActivity;
import com.medoske.www.redhealpatient.Items.InstantItem;
import com.medoske.www.redhealpatient.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by USER on 10.6.17.
 */

public class InstantListAdapter extends ArrayAdapter<InstantItem> {

    private Context activity;
    public List<InstantItem> parkingList;
    ArrayList<InstantItem> arraylist;
    InstantItem book;


    // constructor
    public InstantListAdapter(Context activity, int resource, List<InstantItem> books) {
        super(activity, resource, books);
        this.activity = activity;
        this.parkingList = books;
        arraylist = new ArrayList<InstantItem>();
        arraylist.addAll(parkingList);
    }

    @Override
    public int getCount() {
        return parkingList.size();
    }

    @Override
    public InstantItem getItem(int position) {
        return parkingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // result for item get position
        final InstantItem productItem = parkingList.get(position);

        ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {

            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.instant_item_list, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);


        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtDoctor.setText(productItem.getDoctorName());
        holder.txtSpecialization.setText(productItem.getSpeclization()+" "+productItem.getAddress());
        holder.txtAvilibulity.setText(productItem.getAvalibulity());
        holder.txtPremium.setText("PremiumFee: "+productItem.getPremiumFee());
        holder.txtExperience.setText("exp "+productItem.getExperience()+" | "+"price "+productItem.getPrice());
        //Loading Image from URL
        Picasso.with(getContext()).load(productItem.getDoctorImage()).placeholder(R.drawable.ic_action_placeholder)   // optional
                .into(holder.image);


        return convertView;
    }


    // View Holder
    private class ViewHolder {
        public TextView txtDoctor;
        public TextView txtSpecialization;
        public TextView txtAvilibulity;
        public TextView txtExperience;
        public TextView txtPremium;
        public CircleImageView image;

        public Button book;

        public ViewHolder(View v) {

            txtDoctor = (TextView) v.findViewById(R.id.txtDoctorName);
            txtDoctor.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            txtSpecialization = (TextView) v.findViewById(R.id.txtSpeclization);
            txtSpecialization.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            txtAvilibulity = (TextView) v.findViewById(R.id.txtAvailibulity);
            txtAvilibulity.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            txtExperience = (TextView) v.findViewById(R.id.txtExperience);
            txtExperience.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            txtPremium = (TextView) v.findViewById(R.id.txtPremimFee);
            txtPremium.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            image = (CircleImageView) v.findViewById(R.id.profile_image);

            book=(Button)v.findViewById(R.id.button_book);
            book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getContext().startActivity(new Intent(getContext(), TimeSlotActivity.class));
                }
            });



        }

    }
}
