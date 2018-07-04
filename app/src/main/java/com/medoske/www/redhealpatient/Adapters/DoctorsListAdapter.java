package com.medoske.www.redhealpatient.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.medoske.www.redhealpatient.DoctorsProfileActivity;
import com.medoske.www.redhealpatient.SelectClinicACtivity;
import com.medoske.www.redhealpatient.Items.DoctorsListItem;
import com.medoske.www.redhealpatient.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by USER on 9.6.17.
 */

public class DoctorsListAdapter extends ArrayAdapter<DoctorsListItem> {

    private Context activity;
    public List<DoctorsListItem> parkingList;
    ArrayList<DoctorsListItem> arraylist;
    DoctorsListItem productItem;

    // constructor
    public DoctorsListAdapter(Context activity, int resource, List<DoctorsListItem> books) {
        super(activity, resource, books);
        this.activity = activity;
        this.parkingList = books;
        arraylist = new ArrayList<DoctorsListItem>();
        arraylist.addAll(parkingList);
    }

    @Override
    public int getCount() {
        return parkingList.size();
    }

    @Override
    public DoctorsListItem getItem(int position) {
        return parkingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // result for item get position
         productItem = parkingList.get(position);

        ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {

            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.doctor_item_list, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);


        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtDoctor.setText(productItem.getDoctorName());
        holder.txtSpecialization.setText(productItem.getSpecialization());
        holder.txtAvilibulity.setText(productItem.getStatus());
        holder.txtClinicName.setText(productItem.getClinicName()+" | "+productItem.getAreaName());

        holder.txtDistance.setText(productItem.getDistance()+"km");

        if (productItem.getPremiumFee().equals("")){
            holder.instantLayout.setVisibility(View.GONE);
        }else {
           // holder.instantLayout.setVisibility(View.VISIBLE);
            holder.instantLayout.setVisibility(View.GONE);
        }

        holder.txtExperience.setText("exp "+productItem.getExperience()+" | "+"price "+productItem.getConsultationFee());
        //Loading Image from URL
        Picasso.with(getContext()).load(productItem.getDoctorImage()).placeholder(R.drawable.ic_action_placeholder)   // optional
                .into(holder.image);

        holder.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getContext().startActivity(new Intent(getContext(), DoctorsProfileActivity.class));

                DoctorsListItem book =parkingList.get(position);

                Intent intent =new Intent(getContext(),SelectClinicACtivity.class);
                intent.putExtra("doctorImage",book.getDoctorImage());
                intent.putExtra("doctorName",book.getDoctorName());
                intent.putExtra("speclization",book.getSpecialization());
                intent.putExtra("doctorId",book.getDoctor_redhealId());
                Log.e("doctoridbook",""+book.getDoctor_redhealId());
                intent.putExtra("latitude",book.getLatitude());
                intent.putExtra("longitude",book.getLongitude());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(intent);
            }
        });

        holder.relativeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getContext().startActivity(new Intent(getContext(), DoctorsProfileActivity.class));

                DoctorsListItem relative =parkingList.get(position);

                Intent intent =new Intent(getContext(),DoctorsProfileActivity.class);
                intent.putExtra("doctorImage",relative.getDoctorImage());
                intent.putExtra("doctorName",relative.getDoctorName());
                intent.putExtra("specilization",relative.getSpecialization());
                intent.putExtra("clinicId",relative.getClinic_redhealId());
                intent.putExtra("areaName",relative.getAreaName());
                intent.putExtra("experience",relative.getExperience());
                intent.putExtra("fee",relative.getConsultationFee());
                intent.putExtra("doctorId",relative.getDoctor_redhealId());
                intent.putExtra("description",relative.getDescription());
                intent.putExtra("like_status",relative.getLike_status());
                intent.putExtra("latitude",relative.getLatitude());
                intent.putExtra("longitude",relative.getLongitude());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(intent);
            }
        });


        return convertView;
    }


    // View Holder
    private class ViewHolder {
        public TextView txtDoctor;
        public TextView txtSpecialization;
        public TextView txtAvilibulity;
        public TextView txtExperience;
        public TextView txtClinicName;
        public TextView txtDistance;
        public CircleImageView image;
        public Button book;
        public RelativeLayout instantLayout;
        public RelativeLayout relativeItem;

        public ViewHolder(View v) {
            txtDoctor = (TextView) v.findViewById(R.id.txtDoctorName);
            txtDoctor.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            txtSpecialization = (TextView) v.findViewById(R.id.txtSpeclization);
            txtSpecialization.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            txtAvilibulity = (TextView) v.findViewById(R.id.txtAvailibulity);
            txtAvilibulity.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            txtExperience = (TextView) v.findViewById(R.id.txtExperience);
            txtExperience.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            txtClinicName = (TextView) v.findViewById(R.id.txtClinicName);
            txtClinicName.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            txtDistance = (TextView) v.findViewById(R.id.textDistance);
            txtDistance.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            image = (CircleImageView) v.findViewById(R.id.profile_image);

            book=(Button)v.findViewById(R.id.button_book);


            instantLayout =(RelativeLayout)v.findViewById(R.id.relativeLayout_Instant);
            relativeItem=(RelativeLayout)v.findViewById(R.id.relative_item);

        }

    }
}