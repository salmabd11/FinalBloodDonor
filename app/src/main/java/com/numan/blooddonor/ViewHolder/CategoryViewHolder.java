package com.numan.blooddonor.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.numan.blooddonor.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtUserName,txtUserSurname,
    Address,Age,District,Division,Phone,PoliceStation,BloodGroup,Gender;
    public ImageView ProimageView;


    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        txtUserName = (TextView)itemView.findViewById(R.id.userName_textView);
        txtUserSurname = (TextView)itemView.findViewById(R.id.userSurname_textView);
        Phone = (TextView)itemView.findViewById(R.id.Phone);
        Address = (TextView)itemView.findViewById(R.id.Address);
        Age = (TextView)itemView.findViewById(R.id.Age);
        District = (TextView)itemView.findViewById(R.id.District);
        Division = (TextView)itemView.findViewById(R.id.Division);
        PoliceStation = (TextView)itemView.findViewById(R.id.PoliceStation);
        BloodGroup = (TextView)itemView.findViewById(R.id.Bloodgroup);
        Gender = (TextView)itemView.findViewById(R.id.Gender);
        ProimageView = (ImageView)itemView.findViewById(R.id.CImageView);
    }

    @Override
    public void onClick(View v) {

    }
}
