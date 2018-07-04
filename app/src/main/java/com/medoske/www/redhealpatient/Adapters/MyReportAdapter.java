package com.medoske.www.redhealpatient.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.medoske.www.redhealpatient.Items.MyReportItem;
import com.medoske.www.redhealpatient.MyRecordsActivity;
import com.medoske.www.redhealpatient.R;
import com.medoske.www.redhealpatient.ReportDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by USER on 6.7.17.
 */

public class MyReportAdapter extends RecyclerView.Adapter<MyReportAdapter.MyViewHolder> {
    int i = 0;
    private Context mContext;
    private List<MyReportItem> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textDate;
        RelativeLayout top_layout;


        public MyViewHolder(View view) {
            super(view);
            textName = (TextView) view.findViewById(R.id.textName);
            textDate = (TextView) view.findViewById(R.id.txtDate);
            top_layout =(RelativeLayout)view.findViewById(R.id.top_layout);

        }
    }


    public MyReportAdapter(Context context, List<MyReportItem> moviesList) {

        this.mContext = context;
        this.moviesList = moviesList;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_image_item_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final MyReportItem movie = moviesList.get(position);

        holder.textName.setText(movie.getDoctorName());
        holder.textDate.setText(movie.getTimestamp());
        holder.top_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i5 =new Intent(mContext,ReportDetailsActivity.class);
                i5.putExtra("reportLink",movie.getReport_link());
                i5.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(i5);
            }
        });

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
