package com.medoske.www.redhealpatient.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gigamole.infinitecycleviewpager.VerticalInfiniteCycleViewPager;
import com.medoske.www.redhealpatient.ConfirmBookingActivity;
import com.medoske.www.redhealpatient.ConfirmationTicketActivity;
import com.medoske.www.redhealpatient.Items.FeedsItem;
import com.medoske.www.redhealpatient.Items.NotificationDoctorItem;
import com.medoske.www.redhealpatient.PaymentActivity;
import com.medoske.www.redhealpatient.R;
import com.medoske.www.redhealpatient.TimeSlotActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by USER on 2.6.17.
 */

public class InfiniteCyclerAdapter extends PagerAdapter {

    List<NotificationDoctorItem> img1;
    Context context;
    private LayoutInflater mLayoutInflater;

    public InfiniteCyclerAdapter(List<NotificationDoctorItem> img1, Context context) {
        this.img1 = img1;
        this.context = context;
        mLayoutInflater =LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return img1.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final View view;

        view = mLayoutInflater.inflate(R.layout.infinitecycler_item, container, false);

        final NotificationDoctorItem movie = img1.get(position);

        ImageView imageView =(ImageView)view.findViewById(R.id.imageViewItem);
        Picasso.with(context).load(movie.getImagePath()).into(imageView);

        TextView txtDoctorName =(TextView)view.findViewById(R.id.txtDoctorName);
        txtDoctorName.setText(movie.getDoctorName());

        TextView txtSpecialization =(TextView)view.findViewById(R.id.txtSpecialization);
        txtSpecialization.setText(movie.getSpecialization());

        TextView txtClinicName =(TextView)view.findViewById(R.id.txtClinicName);
        txtClinicName.setText(movie.getClinicName()+"\n"+movie.getClinicAddress());

        TextView txtNormalFee =(TextView)view.findViewById(R.id.txtNormalFee);
        txtNormalFee.setText("Normal Fee : "+movie.getConsultationFee());

        TextView txtInstantFee =(TextView)view.findViewById(R.id.txtInstantFee);
        txtInstantFee.setText("Instant Fee : "+movie.getPremiumConsultationFee());

        TextView txtTimings =(TextView)view.findViewById(R.id.txtTimings);
        txtTimings.setText(movie.getFromtime1()+" - "+movie.getTotime1()+"\n"+movie.getFromtime2()+" - "+movie.getTotime2()+"\n"+movie.getFromtime3()+" - "+movie.getTotime3());

        TextView txtAboutDoctor =(TextView)view.findViewById(R.id.txtAboutDoctor);
        txtAboutDoctor.setText(movie.getAboutDoctor());

        Button button =(Button)view.findViewById(R.id.bookButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final NotificationDoctorItem movie = img1.get(position);

                Intent intent =new Intent(context, TimeSlotActivity.class);
                intent.putExtra("clinicId",movie.getClinic_redhealId());
                intent.putExtra("doctorId",movie.getDoctor_redhealId());
                intent.putExtra("doctorImage",movie.getImagePath());
                intent.putExtra("clinicName",movie.getClinicName());
                intent.putExtra("doctorName",movie.getDoctorName());
                intent.putExtra("speclization",movie.getSpecialization());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);

                Toast.makeText(context,"position="+position,Toast.LENGTH_LONG).show();
            }
        });



        container.addView(view);
        return view;
    }
}
