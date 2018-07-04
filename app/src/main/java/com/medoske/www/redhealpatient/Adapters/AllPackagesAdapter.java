package com.medoske.www.redhealpatient.Adapters;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.medoske.www.redhealpatient.Items.AllPackaesItem;
import com.medoske.www.redhealpatient.Items.DianoRecomItem;
import com.medoske.www.redhealpatient.R;

import java.util.List;

/**
 * Created by USER on 1.9.17.
 */

public class AllPackagesAdapter extends RecyclerView.Adapter<AllPackagesAdapter.MyViewHolder> {
    Context context;
    private List<AllPackaesItem> list;

    public AllPackagesAdapter(Context context, List<AllPackaesItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public AllPackagesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.diagnostic_list_item, parent, false);
        AllPackagesAdapter.MyViewHolder holder = new AllPackagesAdapter.MyViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(final AllPackagesAdapter.MyViewHolder holder, final int position) {

        AllPackaesItem currentItem =list.get(position);

        holder.txtPackageName.setText(currentItem.getPackageName());
        holder.txtTestCount.setText(currentItem.getTestCount());
        holder.txtOfferPrice.setText(currentItem.getOfferPrice());
        holder.txtDiscountPrice.setText(currentItem.getDiscountPrice());
        holder.txtLabName.setText(currentItem.getLabName());
        holder.txtAddress.setText(currentItem.getAddress());

       /* try {
            Picasso.with(context).load(currentItem.getImagepath()).into(holder.imageview);
        }catch (Exception e){
            e.printStackTrace();
        }*/

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*SpecilizationItem specilizationItem = list.get(position);

                Intent intent = new Intent(context, DoctorsListActivity.class);
                intent.putExtra("specilization", specilizationItem.getSpecialization());
                intent.putExtra("specializationId", specilizationItem.getSpecializationId());
                intent.putExtra("latitude",specilizationItem.getPresentLatitude());
                Log.e("LATITUDE :",""+specilizationItem.getPresentLatitude());
                intent.putExtra("longitude", specilizationItem.getPresentLongitude());
                intent.putExtra("placeName", specilizationItem.getPresentPlaceName());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);*/
            }
        });


    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtPackageName;
        public TextView txtTestCount;
        public TextView txtOfferPrice;
        public TextView txtDiscountPrice;
        public TextView txtLabName;
        public TextView txtAddress;
        public LinearLayout linearLayout;


        public MyViewHolder(View v) {
            super(v);
            txtPackageName=(TextView)v.findViewById(R.id.txtPackageName);
            txtPackageName.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Regular.ttf"));

            txtTestCount=(TextView)v.findViewById(R.id.txtTestCount);
            txtTestCount.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Light.ttf"));

            txtOfferPrice=(TextView)v.findViewById(R.id.txtOfferPrice);
            txtOfferPrice.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Light.ttf"));

            txtDiscountPrice=(TextView)v.findViewById(R.id.txtDiscountPrice);
            txtDiscountPrice.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Light.ttf"));
            txtDiscountPrice.setPaintFlags(txtDiscountPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            txtLabName=(TextView)v.findViewById(R.id.txtLabName);
            txtLabName.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Regular.ttf"));

            txtAddress=(TextView)v.findViewById(R.id.txtAddress);
            txtAddress.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Light.ttf"));


            linearLayout=(LinearLayout) v.findViewById(R.id.defaltLayout);


        }
    }
}


