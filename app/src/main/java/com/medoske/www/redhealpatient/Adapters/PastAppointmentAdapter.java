package com.medoske.www.redhealpatient.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.medoske.www.redhealpatient.Items.PastItem;
import com.medoske.www.redhealpatient.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 5.7.17.
 */

public class PastAppointmentAdapter extends ArrayAdapter<PastItem> {

    private Context activity;
    public List<PastItem> parkingList;
    ArrayList<PastItem> arraylist;
    PastItem book;


    // constructor
    public PastAppointmentAdapter(Context activity, int resource, List<PastItem> books) {
        super(activity, resource, books);
        this.activity = activity;
        this.parkingList = books;
        arraylist = new ArrayList<PastItem>();
        arraylist.addAll(parkingList);
    }

    @Override
    public int getCount() {
        return parkingList.size();
    }

    @Override
    public PastItem getItem(int position) {
        return parkingList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // result for item get position
        final PastItem productItem=parkingList.get(position);

        ViewHolder holder = null;
        final LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {

            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.past_list_item, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);




        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }
        holder.doctorName.setText("Your Appointment with "+productItem.getDoctorName());
        holder.doctorName.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

        holder.address.setText(productItem.getClinicName()+" | "+productItem.getAddress());
        holder.address.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Light.ttf"));
        // holder.actualPrice.setPaintFlags(holder.actualPrice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

        holder.dateTime.setText("scheduled on "+productItem.getDate()+" & "+productItem.getTime());
        holder.dateTime.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Light.ttf"));

        holder.status.setText(productItem.getStatus());
        holder.status.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Light.ttf"));


        Picasso.with(getContext()).load(productItem.getDoctorImage()).placeholder(R.drawable.ic_action_placeholder).into(holder.imageView);

if (productItem.getStatus().equals("rejected")){

    holder.noteLayout.setVisibility(View.GONE);
}else {
    holder.noteLayout.setVisibility(View.VISIBLE);
}

        return convertView;
    }

    // View Holder
    private class ViewHolder {
        public TextView doctorName;
        public TextView address;
        public TextView dateTime;
        public TextView status;
        public TextView addRecord;
        public TextView feedBack;
        public ImageView imageView;
        public RelativeLayout noteLayout;


        public ViewHolder(View v) {

            doctorName=(TextView)v.findViewById(R.id.txtDoctorName);
            address=(TextView)v.findViewById(R.id.txtAddress);
            dateTime=(TextView)v.findViewById(R.id.txtDateTime);
            status=(TextView)v.findViewById(R.id.txtStatus);
            imageView=(ImageView) v.findViewById(R.id.patientImage1);

            addRecord=(TextView)v.findViewById(R.id.textAddRecord);
            feedBack=(TextView)v.findViewById(R.id.textFeedBack);

            noteLayout=(RelativeLayout) v.findViewById(R.id.noteLayout);


        }


    }




}
