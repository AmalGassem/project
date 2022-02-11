package com.best.app.patientmedicalfile.PackageAdmin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.best.app.patientmedicalfile.R;

public class AdminActivity extends AppCompatActivity {
    SharedPreferences loginPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        loginPrefs= PreferenceManager.getDefaultSharedPreferences(this);
        Button btnmangedoctor=findViewById(R.id.btnmangedoctor);
        Button btnmangepatient=findViewById(R.id.btnmangepatient);
        Button btnlogout=findViewById(R.id.btnlogout);
        //




        btnmangedoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                builder.setMessage("Select Task");
                builder.setCancelable(true);
                builder.setNegativeButton("Add New Doctor", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(AdminActivity.this, AdminAddDoctorActivity.class));
                        //dialog.cancel();
                    }
                });
                builder.setPositiveButton("View Doctor", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startActivity(new Intent(AdminActivity.this, AdminViewDoctorActivity.class));
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


                // startActivity(new Intent(StaffConrolActivity.this, ManageDoctorActivity.class));
            }
        });
        btnmangepatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, AdminViewPatientActivity.class));
            }
        });
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AdminActivity.this)
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