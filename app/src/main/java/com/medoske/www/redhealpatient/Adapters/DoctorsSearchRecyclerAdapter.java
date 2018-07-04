package com.medoske.www.redhealpatient.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.medoske.www.redhealpatient.DoctorsProfileActivity;
import com.medoske.www.redhealpatient.Items.DoctorsSearchItem;
import com.medoske.www.redhealpatient.Items.SpecilizationItem;
import com.medoske.www.redhealpatient.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.medoske.www.redhealpatient.R.id.presentLatitude;
import static java.security.AccessController.getContext;

/**
 * Created by USER on 22.8.17.
 */

public class DoctorsSearchRecyclerAdapter extends RecyclerView.Adapter<DoctorsSearchRecyclerAdapter.MyViewHolder> {
    Context context;
    private List<DoctorsSearchItem> list;

    public DoctorsSearchRecyclerAdapter(Context context, List<DoctorsSearchItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doctorsearch_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        DoctorsSearchItem currentItem =list.get(position);

        holder.doctorName.setText(currentItem.getFullName());
        holder.specialization.setText(currentItem.getSpecialization() +" | "+currentItem.getClinicAddress());


        try {
            Picasso.with(context).load(currentItem.getDoctorImage()).placeholder(R.drawable.ic_action_placeholder).into(holder.imageView);
        }catch (Exception e){
            e.printStackTrace();
        }

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoctorsSearchItem doctorSearchItem = list.get(position);
                Intent intent = new Intent(context, DoctorsProfileActivity.class);
                intent.putExtra("specilization",doctorSearchItem.getSpecialization());
                intent.putExtra("doctorImage",doctorSearchItem.getDoctorImage());
                intent.putExtra("doctorName",doctorSearchItem.getFullName());
                intent.putExtra("areaName",doctorSearchItem.getClinicAddress());
                intent.putExtra("experience",doctorSearchItem.getExperience());
                intent.putExtra("fee",doctorSearchItem.getConsultationFee());
                intent.putExtra("doctorId",doctorSearchItem.getDoctor_redhealId());
                intent.putExtra("description",doctorSearchItem.getDescription());
                intent.putExtra("latitude",doctorSearchItem.getPresentLatitude());
                intent.putExtra("longitude", doctorSearchItem.getPresentLongitude());
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
        public TextView specialization;
        public CircleImageView imageView;
        public RelativeLayout relativeLayout;


        public MyViewHolder(View v) {
            super(v);
            doctorName=(TextView)v.findViewById(R.id.textPatientNae1);
            doctorName.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf"));

            specialization=(TextView)v.findViewById(R.id.textSpecilization);
            specialization.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf"));

            imageView=(CircleImageView)v.findViewById(R.id.doctorImageCard);

            relativeLayout =(RelativeLayout)v.findViewById(R.id.searchDoctorLayout);


        }
    }
}


