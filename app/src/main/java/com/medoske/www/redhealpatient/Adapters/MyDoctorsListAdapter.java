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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.medoske.www.redhealpatient.DoctorsProfileActivity;
import com.medoske.www.redhealpatient.Items.MyDoctorsItem;
import com.medoske.www.redhealpatient.R;
import com.medoske.www.redhealpatient.SelectClinicACtivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by USER on 7.7.17.
 */

public class MyDoctorsListAdapter extends ArrayAdapter<MyDoctorsItem> {

    private Context activity;
    public List<MyDoctorsItem> parkingList;
    ArrayList<MyDoctorsItem> arraylist;
    MyDoctorsItem productItem;

    // constructor
    public MyDoctorsListAdapter(Context activity, int resource, List<MyDoctorsItem> books) {
        super(activity, resource, books);
        this.activity = activity;
        this.parkingList = books;
        arraylist = new ArrayList<MyDoctorsItem>();
        arraylist.addAll(parkingList);
    }

    @Override
    public int getCount() {
        return parkingList.size();
    }

    @Override
    public MyDoctorsItem getItem(int position) {
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
            convertView = inflater.inflate(R.layout.doctors_fav_list_item, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);


        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtDoctor.setText(productItem.getFullName());
        holder.txtSpecialization.setText(productItem.getSpecialization());

        holder.txtExperience.setText("exp "+productItem.getExperience()+" Years");
        //Loading Image from URL
        Picasso.with(getContext()).load(productItem.getDoctorImage()).placeholder(R.drawable.ic_action_placeholder)   // optional
                .into(holder.image);

       /* if (productItem.getDoctorImage().isEmpty()) {
            holder.image.setImageResource(R.drawable.ic_action_placeholder);
        } else{
            Picasso.with(getContext()).load(productItem.getDoctorImage()).into(holder.image);
        }*/

        return convertView;
    }


    // View Holder
    private class ViewHolder {
        public TextView txtDoctor;
        public TextView txtSpecialization;
        public TextView txtExperience;
        public CircleImageView image;
        public Button book;
        public RelativeLayout relativeItem;

        public ViewHolder(View v) {
            txtDoctor = (TextView) v.findViewById(R.id.txtDoctorName);
            txtDoctor.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            txtSpecialization = (TextView) v.findViewById(R.id.txtSpeclization);
            txtSpecialization.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            txtExperience = (TextView) v.findViewById(R.id.txtExperience);
            txtExperience.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf"));

            image = (CircleImageView) v.findViewById(R.id.profile_image);

            book=(Button)v.findViewById(R.id.button_book);
            book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //getContext().startActivity(new Intent(getContext(), DoctorsProfileActivity.class));

                    Intent intent =new Intent(getContext(),SelectClinicACtivity.class);
                    intent.putExtra("doctorImage",productItem.getDoctorImage());
                    intent.putExtra("doctorName",productItem.getFullName());
                    intent.putExtra("speclization",productItem.getSpecialization());
                    intent.putExtra("doctorId",productItem.getDoctor_redhealId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getContext().startActivity(intent);
                }
            });

            relativeItem=(RelativeLayout)v.findViewById(R.id.relative_item);
            relativeItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // getContext().startActivity(new Intent(getContext(), DoctorsProfileActivity.class));

                    Intent intent =new Intent(getContext(),DoctorsProfileActivity.class);
                    intent.putExtra("doctorImage",productItem.getDoctorImage());
                    intent.putExtra("doctorName",productItem.getFullName());
                    intent.putExtra("specilization",productItem.getSpecialization());
                    /*intent.putExtra("clinicId",productItem.getClinicId());
                    intent.putExtra("areaName",productItem.getAreaName());*/
                    intent.putExtra("experience",productItem.getExperience());
                   /* intent.putExtra("fee",productItem.getNormalPrice());
                    */
                    intent.putExtra("doctorId",productItem.getDoctor_redhealId());
                    intent.putExtra("description",productItem.getDescription());
                    intent.putExtra("like_status",productItem.getLike_status());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getContext().startActivity(intent);
                }
            });




        }

    }
}
