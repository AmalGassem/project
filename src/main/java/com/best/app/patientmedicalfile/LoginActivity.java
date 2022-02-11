package com.best.app.patientmedicalfile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.best.app.patientmedicalfile.PackageAdmin.AdminActivity;
import com.best.app.patientmedicalfile.PackageClass.DB_Connection;
import com.best.app.patientmedicalfile.PackageDoctor.DoctorActivity;
import com.best.app.patientmedicalfile.PackagePatient.PatientActivity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {


    EditText username,password;
    String userType;
    SharedPreferences loginPrefs;
    View focusView = null;
    boolean continues = false;
    DB_Connection dbconn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //// ini
        loginPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

         Button btnback=findViewById(R.id.btnback);
         Button btnregister=findViewById(R.id.btnregister);


        ////
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }

    private boolean isEmpty(EditText view) {
        if (TextUtils.isEmpty(view.getText().toString())) {
            view.setError("Check the value!");
            focusView = view;
            continues = true;
        }

        return TextUtils.isEmpty(view.getText().toString());


    }
    public void login(View view) {
        final String vuser = username.getText().toString();
        final String vpass = password.getText().toString();
        continues = false;
        isEmpty(username);
        isEmpty(password);
        if (continues) {
            focusView.requestFocus();
        } else {
            username.setError(null);
            password.setError(null);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        dbconn = new DB_Connection();
                        dbconn.DB_Connection_open();
                        String sql = "";
                        String Usersarray[] = getResources().getStringArray(R.array.type_user);
                        sql = "   SELECT  `userid`,`fullname`,`username`,`password`,`gender`,`age`,`phone`,`userType` FROM `user` " +
                                " WHERE   username =? AND password=? ";
                        PreparedStatement st = dbconn.connection.prepareStatement(sql);
                        st.setString(1, vuser);
                        st.setString(2, vpass);
                        ResultSet rs = st.executeQuery();
                        if (rs.next()) {
                            SharedPreferences.Editor editor = loginPrefs.edit();
                            editor.putInt("userid", rs.getInt(1));

                            editor.putString("username", rs.getString(3));
                            editor.putString("password", rs.getString(4));

                            editor.putString("logged", "logged");
                            userType = rs.getString(8);

                            editor.apply();
                            if (userType.equals(Usersarray[0])) //Admin
                            {
                                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            } else if (userType.equals(Usersarray[1])) // doctor
                            {
                                Intent intent = new Intent(LoginActivity.this, DoctorActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            } else if (userType.equals(Usersarray[2])) // patient
                            {
                                Intent intent = new Intent(LoginActivity.this, PatientActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    password.setError("this username or password  error");
                                    password.requestFocus();
                                }
                            });
                        }
                        st.close();
                        ;
                        dbconn.DB_Close();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}