package com.medoske.www.redhealpatient.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.medoske.www.redhealpatient.CancelAppointmentActivity;
import com.medoske.www.redhealpatient.CurrentAppointmentDetails;
import com.medoske.www.redhealpatient.Items.CurrentItem;
import com.medoske.www.redhealpatient.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 5.7.17.
 */

public class CurrentAppointmentAdapter extends ArrayAdapter<CurrentItem> {

    private Context activity;
    public List<CurrentItem> parkingList;
    ArrayList<CurrentItem> arraylist;
    CurrentItem book;


    // constructor
    public CurrentAppointmentAdapter(Context activity, int resource, List<CurrentItem> books) {
        super(activity, resource, books);
        this.activity = activity;
        this.parkingList = books;
        arraylist = new ArrayList<CurrentItem>();
        arraylist.addAll(parkingList);
    }

    @Override
    public int getCount() {
        return parkingList.size();
    }

    @Override
    public CurrentItem getItem(int position) {
        return parkingList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // result for item get position
        final CurrentItem productItem=parkingList.get(position);

        ViewHolder holder = null;
        final LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {

            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.upcomin_list_item, parent, false);
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

        holder.note.setText(productItem.getNote());
        holder.note.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Light.ttf"));

        holder.pcci.setText("Pcci : "+productItem.getPcci());
        holder.pcci.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Light.ttf"));

        //get first letter of each String item
        String firstLetter = String.valueOf(productItem.getDoctorName().charAt(4));

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getColor(getItem(position));
        //int color = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color); // radius in px


       // holder.imageView.setImageDrawable(drawable);

        if (productItem.getStatus().equals("confirm")){
            holder.pcci.setVisibility(View.VISIBLE);
        }
        else {
            holder.pcci.setVisibility(View.GONE);
        }


        if (productItem.getStatus().equals("reschedule")){
            holder.noteLayout.setVisibility(View.VISIBLE);
        }else {
            holder.noteLayout.setVisibility(View.GONE);
        }

        if (productItem.getDoctorImage().equals("662c262491890cf2a6f93f6d8d05f399.jpg")){

            holder.imageView.setImageDrawable(drawable);
        }
        else {
            Picasso.with(getContext()).load(productItem.getDoctorImage()).into(holder.imageView);

        }

        final  String sourceLatitude ="17.7307";
        final  String sourceLongitude ="83.3087";

        holder.getDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "http://maps.google.com/maps?saddr=" + sourceLatitude + "," + sourceLongitude + "&daddr=" + productItem.getLatitude() + "," + productItem.getLongitude();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                getContext().startActivity(intent);
            }
        });

        holder.cancelAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CurrentItem currentItem =parkingList.get(position);

                Intent newIntent =new Intent(getContext(),CancelAppointmentActivity.class);
                newIntent.putExtra("appointmentRefId",currentItem.getAppointRefNo());
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(newIntent);
            }
        });
        holder.LinearLayoutAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentItem currentItem =parkingList.get(position);
                Intent intent =new Intent(getContext(),CurrentAppointmentDetails.class);
                intent.putExtra("doctorName",currentItem.getDoctorName());
                intent.putExtra("doctorId",currentItem.getDoctorId());
                intent.putExtra("doctorImage",currentItem.getDoctorImage());
                intent.putExtra("clinicName",currentItem.getClinicName());
                intent.putExtra("date",currentItem.getDate());
                intent.putExtra("time",currentItem.getTime());
                intent.putExtra("speclization",currentItem.getSpeclization());
                intent.putExtra("address",currentItem.getAddress());
                intent.putExtra("patientName",currentItem.getPatientName());
                intent.putExtra("patientImage",currentItem.getPatientImage());
                intent.putExtra("patientId",currentItem.getPatientId());
                intent.putExtra("mobileNo",currentItem.getMobileNo());
                intent.putExtra("latitude",currentItem.getLatitude());
                intent.putExtra("longitude",currentItem.getLongitude());
                intent.putExtra("paymentMode",currentItem.getPaymentMode());
                intent.putExtra("appointmentRefId",currentItem.getAppointRefNo());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(intent);

            }
        });

        return convertView;
    }

    // View Holder
    private class ViewHolder {
        public TextView doctorName;
        public TextView address;
        public TextView dateTime;
        public TextView status;
        public TextView note;
        public TextView pcci;
        public ImageView imageView;
        public LinearLayout noteLayout;
        public Button getDirections;
        public Button cancelAppointments;
        public LinearLayout LinearLayoutAppointment;


        public ViewHolder(View v) {

            doctorName=(TextView)v.findViewById(R.id.txtDoctorName);
            address=(TextView)v.findViewById(R.id.txtAddress);
            dateTime=(TextView)v.findViewById(R.id.txtDateTime);
            status=(TextView)v.findViewById(R.id.txtStatus);
            note=(TextView)v.findViewById(R.id.txtNote);
            pcci=(TextView)v.findViewById(R.id.txtPcci);
            imageView=(ImageView) v.findViewById(R.id.patientImage1);
            noteLayout=(LinearLayout)v.findViewById(R.id.noteLayout);

            getDirections=(Button)v.findViewById(R.id.button_getDirections);
            cancelAppointments=(Button)v.findViewById(R.id.button_cancelAppointments);
            LinearLayoutAppointment =(LinearLayout)v.findViewById(R.id.LinearLayoutAppointment);

        }


    }




}
