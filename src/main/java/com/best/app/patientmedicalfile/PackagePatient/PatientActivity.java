package com.best.app.patientmedicalfile.PackagePatient;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import com.best.app.patientmedicalfile.PackageDoctor.DoctorActivity;
import com.best.app.patientmedicalfile.R;

public class PatientActivity extends AppCompatActivity {

    SharedPreferences loginPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        loginPrefs= PreferenceManager.getDefaultSharedPreferences(this);
        Button btnreviewmedicalfile=findViewById(R.id.btnreviewmedicalfile);
        Button btnmangepatient=findViewById(R.id.btnmangepatient);
        Button btnlogout=findViewById(R.id.btnlogout);
        //

        btnreviewmedicalfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientActivity.this, ReviewMedicalFileActivity.class));
            }
        });
        btnmangepatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(PatientActivity.this, ViewPersonalInformationActivity.class));
            }
        });
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(PatientActivity.this)
                        .setIcon(R.drawable.exite)
                        .setTitle("Logout")
                        .setMessage("Do you want to exit?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor editor = loginPrefs.edit();
                                editor.remove("logged");
                                editor.commit();
                                finish();
                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .show();

            }
        });


    }
}