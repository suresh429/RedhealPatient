package com.medoske.www.redhealpatient.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import static java.security.AccessController.getContext;

/**
 * Created by USER on 16.8.17.
 */

public class CurrentRecycleAdapter extends RecyclerView.Adapter<CurrentRecycleAdapter.MyViewHolder> {
    Context context;
    private List<CurrentItem> list;

    public CurrentRecycleAdapter(Context context, List<CurrentItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.current_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final CurrentItem currentItem =list.get(position);

        /*holder.doctorName.setText(currentItem.getDoctorName());
        holder.address.setImageResource(list.get(position).getImageResourceId());
        holder.coverImageView.setTag(list.get(position).getImageResourceId());
        holder.likeImageView.setTag(R.drawable.ic_like);*/


        holder.doctorName.setText("Your Appointment with "+currentItem.getDoctorName());
        holder.doctorName.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf"));

        holder.address.setText(currentItem.getClinicName()+" | "+currentItem.getAddress());
        holder.address.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf"));
        // holder.actualPrice.setPaintFlags(holder.actualPrice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

        holder.dateTime.setText("scheduled on "+currentItem.getDate()+" & "+currentItem.getTime());
        holder.dateTime.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf"));

        holder.status.setText(currentItem.getStatus());
        holder.status.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf"));

        holder.note.setText(currentItem.getNote());
        holder.note.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf"));

        holder.pcci.setText("Pcci : "+currentItem.getPcci());
        holder.pcci.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf"));


        //get first letter of each String item
        String firstLetter = String.valueOf(currentItem.getDoctorName().charAt(4));

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getColor(list.get(position));
        //int color = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color); // radius in px


        // holder.imageView.setImageDrawable(drawable);
        if (currentItem.getStatus().equals("confirm")){
            holder.pcci.setVisibility(View.VISIBLE);
        }
        else {
            holder.pcci.setVisibility(View.GONE);
        }

        if (currentItem.getStatus().equals("reschedule")){
            holder.noteLayout.setVisibility(View.VISIBLE);
        }else {
            holder.noteLayout.setVisibility(View.GONE);
        }

        if (currentItem.getDoctorImage().equals("662c262491890cf2a6f93f6d8d05f399.jpg")){

            holder.imageView.setImageDrawable(drawable);
        }
        else {
            Picasso.with(context).load(currentItem.getDoctorImage()).into(holder.imageView);

        }

        final  String sourceLatitude ="17.7307";
        final  String sourceLongitude ="83.3087";

        holder.getDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "http://maps.google.com/maps?saddr=" + sourceLatitude + "," + sourceLongitude + "&daddr=" + currentItem.getLatitude() + "," + currentItem.getLongitude();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                context.startActivity(intent);
            }
        });

       /* holder.cancelAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CurrentItem currentItem =list.get(position);

                Intent newIntent =new Intent(context,CancelAppointmentActivity.class);
                newIntent.putExtra("appointmentRefId",currentItem.getAppointRefNo());
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(newIntent);
            }
        });*/

        holder.LinearLayoutAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentItem currentItem =list.get(position);
                Intent intent =new Intent(context,CurrentAppointmentDetails.class);
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
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);

            }
        });

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView doctorName;
        public TextView address;
        public TextView dateTime;
        public TextView status;
        public TextView note;
        public TextView pcci;
        public ImageView imageView;
        public LinearLayout noteLayout;
        public Button getDirections;
       // public Button cancelAppointments;
        public LinearLayout LinearLayoutAppointment;

        public MyViewHolder(View v) {
            super(v);
            doctorName=(TextView)v.findViewById(R.id.txtDoctorName);
            address=(TextView)v.findViewById(R.id.txtAddress);
            dateTime=(TextView)v.findViewById(R.id.txtDateTime);
            status=(TextView)v.findViewById(R.id.txtStatus);
            note=(TextView)v.findViewById(R.id.txtNote);
            pcci=(TextView)v.findViewById(R.id.txtPcci);
            imageView=(ImageView) v.findViewById(R.id.patientImage1);
            noteLayout=(LinearLayout)v.findViewById(R.id.noteLayout);
            getDirections=(Button)v.findViewById(R.id.button_getDirections);
           // cancelAppointments=(Button)v.findViewById(R.id.button_cancelAppointments);
            LinearLayoutAppointment =(LinearLayout)v.findViewById(R.id.LinearLayoutAppointment);

        }
    }
}