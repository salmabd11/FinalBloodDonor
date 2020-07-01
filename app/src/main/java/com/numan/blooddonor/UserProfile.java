package com.numan.blooddonor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class UserProfile extends AppCompatActivity {
    ImageView imageView;
    TextView Nametxt,Emailtxt;
    Button profileUpdate;
    Button mDonorBtn;
    DatabaseReference ref;
    private FirebaseAuth fAuth;
    String userID;
    TextView Addresst,Aget,Districtt,Divisiont,Phonet,PoliceStationt,BloodGroupt,Gendert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        imageView = findViewById(R.id.singleimageviewVA);
        Nametxt = findViewById(R.id.txtNameVA);
        Emailtxt = findViewById(R.id.txtEmailVA);
        Phonet = findViewById(R.id.EtPhone);
        Aget = findViewById(R.id.Etage);
        Gendert = findViewById(R.id.Etgender);
        Divisiont = findViewById(R.id.EtDivision);
        Districtt = findViewById(R.id.EtDistrict);
        PoliceStationt = findViewById(R.id.EtPoliceSt);
        Addresst = findViewById(R.id.EtAddress);
        BloodGroupt = findViewById(R.id.Bloodgroup);
        profileUpdate = findViewById(R.id.ButtonDelete);
        mDonorBtn = findViewById(R.id.DonorBtn);

        fAuth = FirebaseAuth.getInstance();
        ref=FirebaseDatabase.getInstance().getReference().child("Car").child(fAuth.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String Email = dataSnapshot.child("Email").getValue().toString();
                    String Name = dataSnapshot.child("Name").getValue().toString();
                    String Phone = dataSnapshot.child("Phone").getValue().toString();
                    String Age = dataSnapshot.child("Age").getValue().toString();
                    String Gender = dataSnapshot.child("Gender").getValue().toString();
                    String Division = dataSnapshot.child("Division").getValue().toString();
                    String District = dataSnapshot.child("District").getValue().toString();
                    String PoliceStation = dataSnapshot.child("PoliceStation").getValue().toString();
                    String Address = dataSnapshot.child("Address").getValue().toString();
                    String BloodGroup = dataSnapshot.child("BloodGroup").getValue().toString();
                    String imageUri = dataSnapshot.child("imageUri").getValue().toString();
                    Picasso.get().load(imageUri).into(imageView);
                    Nametxt.setText(Name);
                    Emailtxt.setText(Email);
                    Phonet.setText(Phone);
                    Aget.setText(Age);
                    Gendert.setText(Gender);
                    Divisiont.setText(Division);
                    Districtt.setText(District);
                    PoliceStationt.setText(PoliceStation);
                    Addresst.setText(Address);
                    BloodGroupt.setText(BloodGroup);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        profileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserProfile.this, UpdateProfile.class));
            }
        });
        mDonorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserProfile.this, MainActivity.class));
            }
        });
    }
}
