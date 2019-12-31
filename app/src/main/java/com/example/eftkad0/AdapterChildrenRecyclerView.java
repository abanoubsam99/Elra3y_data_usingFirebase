package com.example.eftkad0;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterChildrenRecyclerView extends RecyclerView.Adapter<ItemChildrenViewHolder>
{
    private Context context;
    private ArrayList<Children> daftarchildren;
    private FirebaseDataListener listener;

    public AdapterChildrenRecyclerView(Context context, ArrayList<Children> daftarchildren){
        this.context = context;
        this.daftarchildren = daftarchildren;
        this.listener = (FirebaseDataListener)context;
    }

    @Override
    public ItemChildrenViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // TODO: Implement this method
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_children, parent, false);
        ItemChildrenViewHolder holder = new ItemChildrenViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemChildrenViewHolder holder, final int position)
    {
        // TODO: Implement this method
        /*
             holder.fullnamechidren.setText(" الاسم رباعي        :   "+daftarchildren.get(position).getFullname());
                  holder.whochidren.setText(" صلة القرابة         :   "+daftarchildren.get(position).getWho());
             holder.IdNumberchidren.setText(" الرقم القومي       :   "+daftarchildren.get(position).getIdNumber());
             holder.birthdaychidren.setText(" تاريخ الميلاد       :   "+daftarchildren.get(position).getBirthday());
               holder.statuschidren.setText(" الحالة الاجتماعية :   "+daftarchildren.get(position).getStatus());
      holder.typeofqualificationchidren.setText(" نوع المؤهل         :   "+daftarchildren.get(position).getTypeofqualification());
        holder.qualificationchidren.setText(" المؤهل               :   "+daftarchildren.get(position).getQualification());
                 holder.workchidren.setText(" الوظيفة             :   "+daftarchildren.get(position).getWork());
                holder.phonechidren.setText(" رقم التليفون       :   "+daftarchildren.get(position).getPhone());
          holder.dateofentrychidren.setText(" تاريخ الزيارة      :   "+daftarchildren.get(position).getDateofentry());

*/
        Spannable ss_fullnamechidren=new SpannableString(" الاسم رباعي        :   "+daftarchildren.get(position).getFullname());
        Spannable ss_whochidren=new SpannableString(" صلة القرابة         :   "+daftarchildren.get(position).getWho());
        Spannable ss_IdNumberchidren=new SpannableString(" الرقم القومي       :   "+daftarchildren.get(position).getIdNumber());
        Spannable ss_birthdaychidren=new SpannableString(" تاريخ الميلاد       :   "+daftarchildren.get(position).getBirthday());
        Spannable ss_statuschidren=new SpannableString(" الحالة الاجتماعية :   "+daftarchildren.get(position).getStatus());
        Spannable ss_qualificationchidren=new SpannableString(" المؤهل               :   "+daftarchildren.get(position).getQualification());
        Spannable ss_workchidren=new SpannableString(" الوظيفة             :   "+daftarchildren.get(position).getWork());
        Spannable ss_phonechidren=new SpannableString(" رقم التليفون       :   "+daftarchildren.get(position).getPhone());
        Spannable ss_dateofentrychidren=new SpannableString(" تاريخ الزيارة      :   "+daftarchildren.get(position).getDateofentry());


        ForegroundColorSpan Blue=new ForegroundColorSpan(Color.BLUE);

        ss_fullnamechidren.setSpan(Blue,1,19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss_whochidren.setSpan(Blue,1,19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss_IdNumberchidren.setSpan(Blue,1,19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss_birthdaychidren.setSpan(Blue,1,19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss_statuschidren.setSpan(Blue,1,19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss_qualificationchidren.setSpan(Blue,1,19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss_workchidren.setSpan(Blue,1,19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss_phonechidren.setSpan(Blue,1,19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss_dateofentrychidren.setSpan(Blue,1,19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        StyleSpan Bold=new StyleSpan(Typeface.BOLD);

        ss_fullnamechidren.setSpan(Bold,1,19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss_whochidren.setSpan(Bold,1,19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss_IdNumberchidren.setSpan(Bold,1,19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss_birthdaychidren.setSpan(Bold,1,19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss_statuschidren.setSpan(Bold,1,19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss_qualificationchidren.setSpan(Bold,1,19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss_workchidren.setSpan(Bold,1,19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss_phonechidren.setSpan(Bold,1,19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss_dateofentrychidren.setSpan(Bold,1,19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.fullnamechidren.setText(ss_fullnamechidren);
        holder.whochidren.setText(ss_whochidren);
        holder.IdNumberchidren.setText(ss_IdNumberchidren);
        holder.birthdaychidren.setText(ss_birthdaychidren);
        holder.statuschidren.setText(ss_statuschidren);
        holder.qualificationchidren.setText(ss_qualificationchidren);
        holder.workchidren.setText(ss_workchidren);
        holder.phonechidren.setText(ss_phonechidren);
        holder.dateofentrychidren.setText(ss_dateofentrychidren);



        holder.view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                listener.onDataClick(daftarchildren.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        // TODO: Implement this method
        return daftarchildren.size();
    }


    //interface data listener
    public interface FirebaseDataListener {
        void onDataClick(Children children, int position);
    }
}
