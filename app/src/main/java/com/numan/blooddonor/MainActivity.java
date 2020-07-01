package com.numan.blooddonor;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.numan.blooddonor.Model.Category;
import com.numan.blooddonor.ViewHolder.CategoryViewHolder;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseRecyclerAdapter<Category, CategoryViewHolder> recyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    private FloatingActionButton mFab;
    private EditText inputSerch;
    private Button ProfileBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputSerch= findViewById(R.id.inputSerch);

        //Firebase init
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Car");
        ProfileBTN = findViewById(R.id.ProfileBTN);
        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            }
        });
        ProfileBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UserProfile.class));
            }
        });
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadData("");
        inputSerch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString()!=null){
                    loadData(editable.toString());
                }else{  loadData("");}
            }
        });

    }

    private void loadData(String data){
        Query query = databaseReference.orderByChild("BloodGroup").startAt(data).endAt(data+"\uf8ff");
        FirebaseRecyclerOptions options =new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(query,Category.class).build();
        recyclerAdapter = new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CategoryViewHolder holder, final int position, @NonNull Category model) {

                holder.txtUserName.setText(model.getName());
                holder.txtUserSurname.setText(model.getEmail());
                holder.Phone.setText(model.getPhone());
                holder.Age.setText(model.getAge());
                holder.Address.setText(model.getAddress());
                holder.Division.setText(model.getDivision());
                holder.District.setText(model.getDistrict());
                holder.District.setText(model.getDistrict());
                holder.PoliceStation.setText(model.getPoliceStation());
                holder.BloodGroup.setText(model.getBloodGroup());
                holder.Gender.setText(model.getGender());
                Picasso.get().load(model.getImageUri()).into(holder.ProimageView);
                holder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(MainActivity.this,ViewActivity.class);
                        intent.putExtra("CarKye",getRef(position).getKey());
                        startActivity(intent);

                    }

                });

            }

            @NonNull
            @Override
            public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_view,viewGroup,false);
                return new CategoryViewHolder(view);
            }
        };
       recyclerAdapter.notifyDataSetChanged();
       recyclerAdapter.startListening();
       recyclerView.setAdapter(recyclerAdapter);
    }
}
