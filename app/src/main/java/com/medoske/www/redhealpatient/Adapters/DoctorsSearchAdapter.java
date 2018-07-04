package com.medoske.www.redhealpatient.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.medoske.www.redhealpatient.Items.DoctorsSearchItem;
import com.medoske.www.redhealpatient.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by USER on 21.6.17.
 */

public class DoctorsSearchAdapter extends ArrayAdapter<DoctorsSearchItem> {

    private Context activity;
    public List<DoctorsSearchItem> parkingList;
    ArrayList<DoctorsSearchItem> arraylist;
    DoctorsSearchItem book;


    // constructor
    public DoctorsSearchAdapter(Context activity, int resource, List<DoctorsSearchItem> books) {
        super(activity, resource, books);
        this.activity = activity;
        this.parkingList = books;
        arraylist = new ArrayList<DoctorsSearchItem>();
        arraylist.addAll(parkingList);
    }

    @Override
    public int getCount() {
        return parkingList.size();
    }

    @Override
    public DoctorsSearchItem getItem(int position) {
        return parkingList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // result for item get position
        final DoctorsSearchItem productItem=parkingList.get(position);

        ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {

            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.doctorsearch_item, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);


        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }
        holder.doctorName.setText(productItem.getFullName());
        holder.specialization.setText(productItem.getSpecialization() +" | "+productItem.getClinicAddress());
        Picasso.with(getContext()).load(productItem.getDoctorImage()).placeholder(R.drawable.ic_action_placeholder).into(holder.imageView);

        return convertView;
    }


    // View Holder
    private class ViewHolder {
        public TextView doctorName;
        public TextView specialization;
        public CircleImageView imageView;

        public ViewHolder(View v) {
            doctorName=(TextView)v.findViewById(R.id.textPatientNae1);
            doctorName.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            specialization=(TextView)v.findViewById(R.id.textSpecilization);
            specialization.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            imageView=(CircleImageView)v.findViewById(R.id.doctorImageCard);
        }
    }




}
