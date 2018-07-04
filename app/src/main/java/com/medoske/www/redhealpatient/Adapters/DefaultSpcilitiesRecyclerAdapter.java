package com.medoske.www.redhealpatient.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.medoske.www.redhealpatient.DoctorsListActivity;
import com.medoske.www.redhealpatient.Items.AlternateTreatmentItem;
import com.medoske.www.redhealpatient.Items.SpecilizationItem;
import com.medoske.www.redhealpatient.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by USER on 22.8.17.
 */

public class DefaultSpcilitiesRecyclerAdapter extends RecyclerView.Adapter<DefaultSpcilitiesRecyclerAdapter.MyViewHolder> {
    Context context;
    private List<SpecilizationItem> list;

    public DefaultSpcilitiesRecyclerAdapter(Context context, List<SpecilizationItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public DefaultSpcilitiesRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.specailizationlist_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        SpecilizationItem currentItem =list.get(position);

        holder.specialization.setText(currentItem.getSpecialization());

        try {
            Picasso.with(context).load(currentItem.getImagepath()).into(holder.imageview);
        }catch (Exception e){
            e.printStackTrace();
        }

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SpecilizationItem specilizationItem = list.get(position);

                Intent intent = new Intent(context, DoctorsListActivity.class);
                intent.putExtra("specilization", specilizationItem.getSpecialization());
                intent.putExtra("specializationId", specilizationItem.getSpecializationId());
                intent.putExtra("latitude",specilizationItem.getPresentLatitude());
                Log.e("LATITUDE :",""+specilizationItem.getPresentLatitude());
                intent.putExtra("longitude", specilizationItem.getPresentLongitude());
                intent.putExtra("placeName", specilizationItem.getPresentPlaceName());

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

        public TextView specialization;
        public ImageView imageview;
        public RelativeLayout relativeLayout;


        public MyViewHolder(View v) {
            super(v);
            specialization=(TextView)v.findViewById(R.id.txtSpecialization);
            //specialization.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"Fonts/Roboto-Regular.ttf"));
            imageview =(ImageView)v.findViewById(R.id.imgSpecilities);

            relativeLayout=(RelativeLayout)v.findViewById(R.id.defaltLayout);


        }
    }
}

