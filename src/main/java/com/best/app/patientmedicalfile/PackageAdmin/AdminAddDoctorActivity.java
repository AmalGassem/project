package com.best.app.patientmedicalfile.PackageAdmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.best.app.patientmedicalfile.LoginActivity;
import com.best.app.patientmedicalfile.PackageClass.DB_Connection;
import com.best.app.patientmedicalfile.R;
import com.best.app.patientmedicalfile.RegisterActivity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminAddDoctorActivity extends AppCompatActivity {

    EditText fullname, username, password, age, phone, certificate, specialization;
    Spinner sgender;
    String userGender;
    String usertype;


    View focusView = null;
    Boolean continues = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_doctor);

        //
        fullname = findViewById(R.id.fullname);
        certificate = findViewById(R.id.certificate);
        specialization = findViewById(R.id.specialization);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        age = findViewById(R.id.age);
        phone = findViewById(R.id.phone);
        age = findViewById(R.id.age);
        sgender = findViewById(R.id.spinnergender);

        Button btnback = findViewById(R.id.btnback);
        Button btnreg = findViewById(R.id.btnreg);
        //
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        usertype = "doctor";
        sgender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userGender = sgender.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continues = false;

                isEmpty(fullname);
                isEmpty(specialization);
                isEmpty(certificate);
                isEmpty(username);
                isEmpty(password);
                isEmpty(age);
                isEmpty(phone);
                if (age.getText().toString().length() > 0)
                    if (Integer.parseInt((age.getText().toString().trim())) < 18 || Integer.parseInt(age.getText().toString().trim()) > 100) {
                        Toast.makeText(getApplicationContext(), "Invalid age.", Toast.LENGTH_SHORT).show();
                        continues = true;
                        focusView = age;
                    }

                if (phone.getText().toString().length() > 0)
                    if (!isValidPhone(phone.getText().toString().trim())) {

                        Toast.makeText(getApplicationContext(), "Invalid phone number.", Toast.LENGTH_SHORT).show();
                        continues = true;
                        focusView = phone;
                    }

                if (continues) {
                    focusView.requestFocus();
                } else {
                    do_register();
                }
            }
        });


    }

    public void do_register() {
        set_error_null();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    DB_Connection dbconn = new DB_Connection();
                    dbconn.DB_Connection_open();
                    PreparedStatement sql_statment;
                    sql_statment = dbconn.connection.prepareStatement("  INSERT INTO user(`fullname`,`username`,`password`,`gender`,`age`,`phone`,`userType`)    " +
                            " VALUES (?, ?,?,?,?,?,?)");
                    sql_statment.setString(1, fullname.getText().toString());
                    sql_statment.setString(2, username.getText().toString());
                    sql_statment.setString(3, password.getText().toString());
                    sql_statment.setString(4, userGender);
                    sql_statment.setString(5, age.getText().toString());
                    sql_statment.setString(6, phone.getText().toString());
                    sql_statment.setString(7, usertype);


                    int sql_result = sql_statment.executeUpdate();
                    ResultSet rs = sql_statment.getGeneratedKeys();
                    if (sql_result > 0) {
                        if (rs.next()) {

                            int t = rs.getInt(1);
                            sql_statment = dbconn.connection.prepareStatement("   INSERT INTO doctor(`doctorid`,`certificate`,`specialization`,`adminId`)  VALUES  (?,?,?,?)");
                            sql_statment.setInt(1, t);
                            sql_statment.setString(2, certificate.getText().toString());
                            sql_statment.setString(3, specialization.getText().toString());
                            sql_statment.setInt(4, 1);
                            sql_statment.executeUpdate();
                        }
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Successfully Registration!", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    sql_statment.close();
                    dbconn.DB_Close();
                } catch (Exception e) {
                    if (e.getMessage().toString().contains("Duplicate entry")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                username.setError("The user is Unavailable ..");
                                username.requestFocus();
                            }
                        });
                    }
                    System.out.println(e.getMessage());
                }
            }

        }).start();
    }

    private boolean isEmpty(EditText view) {
        if (TextUtils.isEmpty(view.getText().toString())) {
            view.setError("Check the value!");
            focusView = view;
            continues = true;
        }

        return TextUtils.isEmpty(view.getText().toString());


    }

    public void set_error_null() {
        age.setError(null);
        username.setError(null);
        password.setError(null);
        age.setError(null);
        fullname.setError(null);
        phone.setError(null);

    }

    public static boolean isValidEmailAddress(String emailAddress) {
        String emailRegEx;
        Pattern pattern;
        // Regex for a valid email address
        emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
        // Compare the regex with the email address
        pattern = Pattern.compile(emailRegEx);
        Matcher matcher = pattern.matcher(emailAddress);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }

    public static boolean isValidPhone(String phone) {
        String phoneRegEx;
        Pattern pattern;
        // Regex for a valid email address
        phoneRegEx = "^((?!([1-9]))[0-9]{10})$";

        // Compare the regex with the email address
        pattern = Pattern.compile(phoneRegEx);
        Matcher matcher = pattern.matcher(phone);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }
}