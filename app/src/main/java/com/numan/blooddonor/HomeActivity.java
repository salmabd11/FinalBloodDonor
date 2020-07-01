package com.numan.blooddonor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity {


    private static final int REQUEST_CAMERA = 1;
    private static final int PICK_IMAGE = 2;
    private ImageView mprofilImage;
    private EditText Name,Email,Password,Phone,Age,Division,District,PoliceStation,Address;
    private TextView proggers;
    private Button Save;
    private ProgressBar mProgressbar;
    Uri imageUri;
    boolean imageAdded = false;
    boolean mspinner = false;
    boolean mGender = false;
    DatabaseReference DataRef;
    DatabaseReference DataRefLogin;
    StorageReference StorageRef;
    private FirebaseAuth fAuth;
    String userID;
    Spinner spinner,Gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mprofilImage = findViewById(R.id.profilephoto);
        Name = findViewById(R.id.EtName);
        Email = findViewById(R.id.EtEmail);
        Password = findViewById(R.id.EtPassword);
        Phone = findViewById(R.id.EtPhone);
        Age = findViewById(R.id.Etage);
        Gender = findViewById(R.id.Etgender);
        Division = findViewById(R.id.EtDivision);
        District = findViewById(R.id.EtDistrict);
        PoliceStation = findViewById(R.id.EtPoliceSt);
        Address = findViewById(R.id.EtAddress);
        spinner = findViewById(R.id.spinner);
        proggers = findViewById(R.id.textviewprogress);
        mProgressbar = findViewById(R.id.Progressbar);
       int BloodGroup,mGender;
        Save = findViewById(R.id.SaveBTN);
        fAuth = FirebaseAuth.getInstance();
        DataRef = FirebaseDatabase.getInstance().getReference().child("Car");
        DataRefLogin = FirebaseDatabase.getInstance().getReference().child("Login");
        StorageRef = FirebaseStorage.getInstance().getReference().child("Photo Car");

        proggers.setVisibility(View.GONE);
        mProgressbar.setVisibility(View.GONE);

        List<String> categories = new ArrayList<>();
        categories.add(0, "Blood Group");
        categories.add("A+");
        categories.add("A-");
        categories.add("B+");
        categories.add("B-");
        categories.add("O+");
        categories.add("O-");
        categories.add("AB+");
        categories.add("AB-");

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).equals("Blood Group")) {
                } else {
                    String itemb = adapterView.getItemAtPosition(i).toString();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        List<String> gendercategories = new ArrayList<>();
        gendercategories.add(0, "Gender");
        gendercategories.add("Male");
        gendercategories.add("Female");
        ArrayAdapter<String> GenderAdapter;
        GenderAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,gendercategories);
        GenderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Gender.setAdapter(GenderAdapter);
        Gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).equals("Gender")) {
                } else {
                    String itemg = adapterView.getItemAtPosition(i).toString();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }
    public void ImageClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation ");
        builder.setMessage("Select Profile Photo");
        builder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CAMERA);
                // Do nothing but close the dialog
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {



            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE);
                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();



        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ImageName = Name.getText().toString();
                final String ProfileEmail = Email.getText().toString();
                final String ProfileAge = Age.getText().toString();
                final String ProfilePhone = Phone.getText().toString();
                final String ProfileDivision = Division.getText().toString();
                final String ProfileDistrict = District.getText().toString();
                final String ProfilePoliceStation = PoliceStation.getText().toString();
                final String ProfileAddress = Address.getText().toString();
                String spin = spinner.getSelectedItem().toString();
                String gender = Gender.getSelectedItem().toString();

                if (mspinner != false)
                if (mGender != false)
                if (imageAdded !=false)
                        //&& ImageName !=null && ProfileEmail !=null)
                    if (ImageName.isEmpty()) {
                        Name.setError(getString(R.string.input_error_name));
                        Name.requestFocus();
                        return;
                    }
                if (ProfileEmail.isEmpty()) {
                    Email.setError(getString(R.string.input_error_email));
                    Email.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(ProfileEmail).matches()) {
                    Email.setError(getString(R.string.input_error_email_invalid));
                    Email.requestFocus();
                    return;
                }
                if (ProfileAge.isEmpty()) {
                    Age.setError(getString(R.string.input_error_Age));
                    Age.requestFocus();
                    return;
                }
                if (ProfileDivision.isEmpty()) {
                    Division.setError(getString(R.string.input_error_Division));
                    Division.requestFocus();
                    return;
                }
                if (ProfileDistrict.isEmpty()) {
                    District.setError(getString(R.string.input_error_District));
                    District.requestFocus();
                    return;
                }
                if (ProfilePoliceStation.isEmpty()) {
                    PoliceStation.setError(getString(R.string.input_error_PoliceStation));
                    PoliceStation.requestFocus();
                    return;
                }
                if (ProfileAddress.isEmpty()) {
                    Address.setError(getString(R.string.input_error_Address));
                    Address.requestFocus();
                    return;
                }
                if (ProfilePhone.isEmpty()) {
                    Phone.setError(getString(R.string.input_error_phone));
                    Phone.requestFocus();
                    return;
                }
                if (ProfilePhone.length() !=11) {
                    Phone.setError(getString(R.string.input_error_phone_invalid));
                    Phone.requestFocus();
                    return;
                }
                {
                    UploadImage(ImageName);
                }

            }
        });
    }



    private void UploadImage(final String imageName) {
        final String ImageEmail = Email.getText().toString();
        final String ProfileAge = Age.getText().toString();
        final String ProfilePhone = Phone.getText().toString();
        final String ProfileDivision = Division.getText().toString();
        final String ProfileDistrict = District.getText().toString();
        final String ProfilePoliceStation = PoliceStation.getText().toString();
        final String ProfileAddress = Address.getText().toString();
        final String BloodGroup = spinner.getSelectedItem().toString();
        final String gender = Gender.getSelectedItem().toString();
        proggers.setVisibility(View.VISIBLE);
        mProgressbar.setVisibility(View.VISIBLE);
        userID = fAuth.getCurrentUser().getUid();

        // Upload Image Firebase Storage
        StorageRef.child(userID+".jpg").putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                StorageRef.child(userID+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //Upload Data Firebase Database
                        HashMap hashMap = new HashMap();
                        hashMap.put("Name", imageName);
                        hashMap.put("Email", ImageEmail);
                        hashMap.put("Age", ProfileAge);
                        hashMap.put("Phone", ProfilePhone);
                        hashMap.put("Division", ProfileDivision);
                        hashMap.put("District", ProfileDistrict);
                        hashMap.put("PoliceStation", ProfilePoliceStation);
                        hashMap.put("Address", ProfileAddress);
                        hashMap.put("imageUri", uri.toString());
                        hashMap.put("BloodGroup",BloodGroup);
                        hashMap.put("Gender", gender);
                        DataRef.child(userID).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startActivity(new Intent(getApplicationContext(),UserProfile.class));
                                Toast.makeText(HomeActivity.this,"Deta Successfully Uploaded",Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (taskSnapshot.getBytesTransferred()*100)/taskSnapshot.getTotalByteCount();
                mProgressbar.setProgress((int) progress);
                proggers.setText(progress+" % ");

            }
        });
    }

   /* private void LoginUser() {
        final String ImageEmail = Email.getText().toString();
        final String ImagePassword = Password.getText().toString();
        final String kye = DataRef.push().getKey();
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        HashMap hashMap = new HashMap();
        hashMap.put("CarKye", kye);
        hashMap.put("Email", ImageEmail);
        hashMap.put("Password", ImagePassword);

        DataRefLogin.child(uid).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Intent intent = new Intent(HomeActivity.this,UserProfile.class);
                intent.putExtra("CarKye",uid);
                Toast.makeText(HomeActivity.this,"Deta Successfully Uploaded",Toast.LENGTH_SHORT).show();
            }
        });

    }


    */
    @Override

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA || resultCode == RESULT_OK ||
                    data != null || data.getData() != null) {
                imageUri = data.getData();
                imageAdded = true;
                mprofilImage.setImageURI(imageUri);

            } else if (requestCode == PICK_IMAGE || resultCode == RESULT_OK ||
                    data != null || data.getData() != null) {
                imageUri = data.getData();
                imageAdded = true;
                mprofilImage.setImageURI(imageUri);
            }
        }
 }

}
