package com.example.eftkad0;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ItemChildrenViewHolder extends RecyclerView.ViewHolder
{
    public TextView fullnamechidren;
    public TextView whochidren;
    public TextView IdNumberchidren;
    public TextView birthdaychidren;
    public TextView statuschidren;
    public TextView qualificationchidren;
    public TextView workchidren;
    public TextView phonechidren;
    public TextView dateofentrychidren;

    public View view;

    public ItemChildrenViewHolder(View view){
        super(view);
        fullnamechidren = (TextView)view.findViewById(R.id.fullname_children);
        whochidren = (TextView)view.findViewById(R.id.who_children);
        IdNumberchidren = (TextView)view.findViewById(R.id.IdNumber_children);
        birthdaychidren = (TextView)view.findViewById(R.id.birthday_children);
        statuschidren = (TextView)view.findViewById(R.id.status_children);
        qualificationchidren = (TextView)view.findViewById(R.id.qualification_children);
        workchidren = (TextView)view.findViewById(R.id.work_children);
        phonechidren = (TextView)view.findViewById(R.id.phone_children);
        dateofentrychidren = (TextView)view.findViewById(R.id.dateofentry_children);

        this.view = view;
    }
}
