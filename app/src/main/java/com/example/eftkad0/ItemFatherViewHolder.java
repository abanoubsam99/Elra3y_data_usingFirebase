package com.example.eftkad0;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ItemFatherViewHolder extends RecyclerView.ViewHolder
{
    public TextView namaFather;
    public TextView locationFather;
    public TextView streetFather;
    public TextView numadressFather;
    public TextView NumFather;
    public TextView phoneFather;
    public View view;

    public ItemFatherViewHolder(View view){
        super(view);

        namaFather = (TextView)view.findViewById(R.id.nama_father);
        locationFather = (TextView)view.findViewById(R.id.location_father);
        streetFather = (TextView)view.findViewById(R.id.strrt_father);
        numadressFather = (TextView)view.findViewById(R.id.numadress_father);
        NumFather = (TextView)view.findViewById(R.id.Num_father);
        phoneFather = (TextView)view.findViewById(R.id.phone_father);
        this.view = view;
    }
}
