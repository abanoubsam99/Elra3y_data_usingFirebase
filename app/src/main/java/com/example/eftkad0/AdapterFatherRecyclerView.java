package com.example.eftkad0;

import android.annotation.SuppressLint;
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

public class AdapterFatherRecyclerView extends RecyclerView.Adapter<ItemFatherViewHolder>
{
    private Context context;
    private ArrayList<Father> daftarfather;
    private FirebaseDataListener listener;

    public AdapterFatherRecyclerView(Context context, ArrayList<Father> daftarfather){
        this.context = context;
        this.daftarfather = daftarfather;
        this.listener = (FirebaseDataListener)context;
    }

    @Override
    public ItemFatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // TODO: Implement this method
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_father, parent, false);
        ItemFatherViewHolder holder = new ItemFatherViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemFatherViewHolder holder, final int position)
    {
        // TODO: Implement this method
      /*
        holder.namaFather.setText(" اسم رب الاسرة :   "+daftarfather.get(position).getNama());
        holder.locationFather.setText(" المنطقة            :   "+daftarfather.get(position).getLocation());
        holder.streetFather.setText(" اسم الشارع      :   "+daftarfather.get(position).getStreet());
        holder.numadressFather.setText(" رقم العقار        :   "+daftarfather.get(position).getNumadress());
        holder.NumFather.setText(" رقم الدور         :   "+daftarfather.get(position).getNum());
        holder.phoneFather.setText(" رقم التليفون     :   "+daftarfather.get(position).getPhone());
*/

        Spannable ss_namaFather=new SpannableString(" اسم رب الاسرة :   "+daftarfather.get(position).getNama());
        Spannable ss_locationFather=new SpannableString(" المنطقة            :   "+daftarfather.get(position).getLocation());
        Spannable ss_streetFather=new SpannableString(" اسم الشارع      :   "+daftarfather.get(position).getStreet());
        Spannable ss_numadressFather=new SpannableString(" رقم العقار        :   "+daftarfather.get(position).getNumadress());
        Spannable ss_NumFather=new SpannableString(" رقم الدور         :   "+daftarfather.get(position).getNum());
        Spannable ss_phoneFather=new SpannableString(" رقم التليفون     :   "+daftarfather.get(position).getPhone());

        // change colore in the same text
        ForegroundColorSpan Blue=new ForegroundColorSpan(Color.BLUE);

        ss_namaFather.setSpan(Blue,1,15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss_locationFather.setSpan(Blue,1,9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss_streetFather.setSpan(Blue,1,11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss_numadressFather.setSpan(Blue,1,15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss_NumFather.setSpan(Blue,1,15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss_phoneFather.setSpan(Blue,1,15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        // change style in the same text
        StyleSpan Bold=new StyleSpan(Typeface.BOLD);

        ss_namaFather.setSpan(Bold,1,15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss_locationFather.setSpan(Bold,1,9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss_streetFather.setSpan(Bold,1,11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss_numadressFather.setSpan(Bold,1,15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss_NumFather.setSpan(Bold,1,15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss_phoneFather.setSpan(Bold,1,15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);



        holder.namaFather.setText(ss_namaFather);
        holder.locationFather.setText(ss_locationFather);
        holder.streetFather.setText(ss_streetFather);
        holder.numadressFather.setText(ss_numadressFather);
        holder.NumFather.setText(ss_NumFather);
        holder.phoneFather.setText(ss_phoneFather);



        holder.view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                listener.onDataClick(daftarfather.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        // TODO: Implement this method
        return daftarfather.size();
    }


    //interface data listener
    public interface FirebaseDataListener {
        void onDataClick(Father father, int position);
    }
}
