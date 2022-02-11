package com.best.app.patientmedicalfile.PackageDoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.best.app.patientmedicalfile.PackageClass.DB_Connection;
import com.best.app.patientmedicalfile.R;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiseaseActivity extends AppCompatActivity {
    EditText diseasename, healthserviceprovider, medicine ;
    Spinner sgender;
    String userGender;
    String usertype;
    SharedPreferences loginPrefs;

    View focusView = null;
    Boolean continues = false;
    private int mfid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease);


        //
        loginPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mfid=loginPrefs.getInt("mfid",0);
        diseasename = findViewById(R.id.diseasename);
        healthserviceprovider = findViewById(R.id.healthserviceprovider);
        medicine = findViewById(R.id.medicine);

        Button btnback = findViewById(R.id.btnback);
        Button btnreg = findViewById(R.id.btnreg);
        //
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continues = false;

                isEmpty(diseasename);
                isEmpty(healthserviceprovider);
                isEmpty(medicine);

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
                    Date c = Calendar.getInstance().getTime();
                    System.out.println("Current time => " + c);
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    String formattedDate = df.format(c);

                    Class.forName("com.mysql.jdbc.Driver");
                    DB_Connection dbconn = new DB_Connection();
                    dbconn.DB_Connection_open();
                    PreparedStatement sql_statment;
                    sql_statment = dbconn.connection.prepareStatement("  INSERT INTO disease (`diseasename`,`diseasedate`,`healthserviceprovider`,`medicine`,`medicalfileid`) VALUES (?, ?,?,?,?)");
                    sql_statment.setString(1, diseasename.getText().toString());
                    sql_statment.setString(2, formattedDate);
                    sql_statment.setString(3, healthserviceprovider.getText().toString());
                    sql_statment.setString(4, medicine.getText().toString());
                    sql_statment.setInt(5, mfid);

                    int sql_result = sql_statment.executeUpdate();
                    if (sql_result > 0) {

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
                               /* username.setError("The user is Unavailable ..");
                                username.requestFocus();*/
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
        diseasename.setError(null);
        healthserviceprovider.setError(null);
        medicine.setError(null);


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