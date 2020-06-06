package com.ark.roomdb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ark.roomdb.RoomDB.Bio;
import com.ark.roomdb.RoomDB.BioDatabase;

import org.jetbrains.annotations.NotNull;


public class MainActivity extends AppCompatActivity {

    int regNo_ = 0;
    Button btnAdd, btnDelete, btnSearch, btnUpdate, btnClearField;
    BioDatabase bioDatabase;
    EditText et_regNo, et_name, et_fname, et_address, et_mobileNo, et_qualification;
    String gender = "Male";
    RadioButton radioMale, radioFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Buttons
        btnAdd = findViewById(R.id.btn_addbio);
        btnDelete = findViewById(R.id.btn_deleteBio);
        btnSearch = findViewById(R.id.btn_search_Bio);
        btnUpdate = findViewById(R.id.btn_updateBio);
        btnClearField = findViewById(R.id.btn_clearField);

        btnAdd.setVisibility(View.VISIBLE);
        btnClearField.setVisibility(View.INVISIBLE);

        RadioGroup radioGroup = findViewById(R.id.radiogrp);
        radioMale = findViewById(R.id.male);
        radioFemale = findViewById(R.id.female);

        // EditText Fields
        et_regNo = findViewById(R.id.regNo);
        et_name = findViewById(R.id.name);
        et_fname = findViewById(R.id.fname);
        et_address = findViewById(R.id.address);
        et_mobileNo = findViewById(R.id.mobileno);
        et_qualification = findViewById(R.id.qualification);

        // Initializing the instance of room database
        bioDatabase = BioDatabase.getDatabase(this);

        // Gender Selection by RadioGroup
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.male:
                        gender = "Male";
                        break;
                    case R.id.female:
                        gender = "Female";
                        break;
                }
            }
        });

        btnClearField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllField();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bio bio = new Bio();
                if (validateField()) {
                    insertBio_(getInputfromField(bio));
                } else {
                    Toast.makeText(MainActivity.this, "Please Fill the Input Fields", Toast.LENGTH_SHORT).show();
                }
                clearAllField();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateAlertBox();
            }

        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBio_();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBio_();
            }
        });
    }

    private void generateAlertBox() {

        View viewInflated = LayoutInflater.from(MainActivity.this).inflate(R.layout.searchinginput, null);
        final EditText regno = viewInflated.findViewById(R.id.search_regNo);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Search Bio");
        builder.setView(viewInflated);
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String noString = regno.getText().toString().trim();
                if (noString.length() < 1) {
                    dialog.dismiss();
                    clearAllField();
                    Toast.makeText(MainActivity.this, "You haven't Entered", Toast.LENGTH_SHORT).show();
                } else {
                    regNo_ = Integer.parseInt(noString);
                    searchBio_(regNo_);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                clearAllField();
                Toast.makeText(MainActivity.this, "You haven't Entered", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create();
        builder.show();
    }

    private boolean validateField() {
        if (et_regNo.getText().toString().isEmpty()) {
            return false;
        }
        if (et_name.getText().toString().isEmpty()) {
            return false;
        }
        if (et_fname.getText().toString().isEmpty()) {
            return false;
        }
        if (et_address.getText().toString().isEmpty()) {
            return false;
        }
        if (et_mobileNo.getText().toString().isEmpty()) {
            return false;
        }
        return !et_qualification.getText().toString().isEmpty();
    }

    private Bio getInputfromField(@NotNull Bio bio) {
        bio.setRegNo(Integer.parseInt(et_regNo.getText().toString()));
        bio.setName(et_name.getText().toString().trim());
        bio.setFname(et_fname.getText().toString().trim());
        bio.setAddress(et_address.getText().toString().trim());
        bio.setMobileNo(Integer.parseInt(et_mobileNo.getText().toString()));
        bio.setQualification(et_qualification.getText().toString().trim());
        bio.setGender(gender);
        return bio;
    }

    void clearAllField() {
        et_regNo.setText("");
        et_name.setText("");
        et_fname.setText("");
        et_address.setText("");
        et_mobileNo.setText("");
        et_qualification.setText("");
        btnAdd.setVisibility(View.VISIBLE);
        btnClearField.setVisibility(View.INVISIBLE);
        btnDelete.setEnabled(false);
        btnUpdate.setEnabled(false);
    }

    void fillBio(@NotNull Bio bio) {
        clearAllField();
        et_regNo.setText(String.valueOf(bio.getRegNo()));
        et_name.setText(bio.getName());
        et_fname.setText(bio.getFname());
        et_address.setText(bio.getAddress());
        et_mobileNo.setText(String.valueOf(bio.getMobileNo()));
        et_qualification.setText(bio.getQualification());
        if (bio.getGender().equals("Male")) {
            radioMale.setChecked(true);
            radioFemale.setChecked(false);
        } else {
            radioFemale.setChecked(true);
            radioMale.setChecked(false);
        }
    }

    void insertBio_(final Bio bio) {
        new InsertDaoAsync(MainActivity.this, bioDatabase.bioDao()).execute(bio);
    }

    void deleteBio_() {
        Bio bio = new Bio();
        Bio bio_ = getInputfromField(bio);
        new DeleteBioAsync(MainActivity.this,bioDatabase.bioDao(),bio_.getRegNo()).execute();
        clearAllField();
    }

    void updateBio_() {
        Bio bio = new Bio();
        new UpdateBioAsync(MainActivity.this, bioDatabase.bioDao()).execute(getInputfromField(bio));
        clearAllField();
    }

    void searchBio_(int no) {
        new GetBioAsync(MainActivity.this, bioDatabase.bioDao(), no, new AsyncRespone() {
            @Override
            public void onFinishedReturn(Bio bio_) {
                if (bio_ != null) {
                    fillBio(bio_);
                    btnAdd.setVisibility(View.INVISIBLE);
                    btnClearField.setVisibility(View.VISIBLE);
                    btnDelete.setEnabled(true);
                    btnUpdate.setEnabled(true);
                } else {
                    clearAllField();
                    Toast.makeText(MainActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
                }
            }
        }).execute();
    }
}
