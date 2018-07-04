package com.medoske.www.redhealpatient.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.medoske.www.redhealpatient.ClinicProfileActivity1;
import com.medoske.www.redhealpatient.Items.ClinicSearchItem;
import com.medoske.www.redhealpatient.Items.DoctorsSearchItem;
import com.medoske.www.redhealpatient.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.medoske.www.redhealpatient.R.id.presentLatitude;
import static java.security.AccessController.getContext;

/**
 * Created by USER on 22.8.17.
 */

public class ClinicSearchRecyclerAdapter extends RecyclerView.Adapter<ClinicSearchRecyclerAdapter.MyViewHolder> {
    Context context;
    private List<ClinicSearchItem> list;

    public ClinicSearchRecyclerAdapter(Context context, List<ClinicSearchItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.clinicsearch_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        ClinicSearchItem currentItem =list.get(position);

        holder.clinicName.setText(currentItem.getClinicName());
        Log.e("clinicName123",""+currentItem.getClinicName());
        holder.area.setText(currentItem.getAddress());
        Log.e("area123",""+currentItem.getAddress());


        try {
            Picasso.with(context).load(currentItem.getClinicImage()).placeholder(R.drawable.ic_action_placeholder).into(holder.imageView);
        }catch (Exception e){
            e.printStackTrace();
        }

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClinicSearchItem clinicSearchItem = list.get(position);

                Intent intent = new Intent(context, ClinicProfileActivity1.class);
                intent.putExtra("clinicId",clinicSearchItem.getClinic_redhealId());
                intent.putExtra("clinicName",clinicSearchItem.getClinicName());
                intent.putExtra("latitude",clinicSearchItem.getPresentLatitude());
                intent.putExtra("longitude", clinicSearchItem.getPresentLongitude());
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

        public TextView clinicName;
        public TextView area;
        public CircleImageView imageView;
        public RelativeLayout relativeLayout;


        public MyViewHolder(View v) {
            super(v);
            clinicName=(TextView)v.findViewById(R.id.textClinicName);
            clinicName.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf"));

            area=(TextView)v.findViewById(R.id.textArea);
            area.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf"));

            imageView=(CircleImageView)v.findViewById(R.id.cliniImage);

            relativeLayout=(RelativeLayout)v.findViewById(R.id.searchClinicLayout);


        }
    }
}



