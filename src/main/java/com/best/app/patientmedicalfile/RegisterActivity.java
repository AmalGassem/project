package com.best.app.patientmedicalfile;

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

import com.best.app.patientmedicalfile.PackageClass.DB_Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {



    EditText fullname,username,password,age,phone;
    Spinner sgender;
    String userGender;
    String usertype;


    View focusView=null;
    Boolean continues =false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //
        fullname=findViewById(R.id.fullname);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        age=findViewById(R.id.age);
        phone=findViewById(R.id.phone);
        age=findViewById(R.id.age);
        sgender=findViewById(R.id.spinnergender);

        Button btnback=findViewById(R.id.btnback);
        Button btnreg=findViewById(R.id.btnreg);
        //
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
        usertype="patient";
        sgender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userGender=sgender.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continues=false;

                isEmpty(fullname ) ;
                isEmpty(username);
                isEmpty(password) ;
                isEmpty(age);
                isEmpty(phone);
                if(age.getText().length()>0)
                {
                    if (Integer.parseInt((age.getText().toString().trim()))<18 || Integer.parseInt(age.getText().toString().trim())>100 )
                    {

                        Toast.makeText(getApplicationContext(), "Invalid age.", Toast.LENGTH_SHORT).show();
                        continues=true;
                        focusView=age;
                    }
                }


                if (!isValidPhone(phone.getText().toString().trim()))
                {

                    Toast.makeText(getApplicationContext(), "Invalid phone number.", Toast.LENGTH_SHORT).show();
                    continues=true;
                    focusView=phone;
                }
                if(!isValidPassword(password.getText().toString())){
                    // Toast.makeText(getApplicationContext(), "Password must contain mix of upper and lower case letters,digits and one special charecter(8-20)", Toast.LENGTH_SHORT).show();
                    password.setError("Password must contain mix of upper and lower case letters,digits and one special charecter(8-20)");
                    focusView = password;
                    continues=true;
                }

                if(continues)
                {
                    focusView.requestFocus();
                }
                else
                {
                    do_register();
                }
            }
        });




    }

    public void do_register()
    {
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
                    ResultSet rs=sql_statment.getGeneratedKeys();
                    if (sql_result>0)
                    {
                        if (rs.next()) {

                            int t = rs.getInt(1);
                            sql_statment = dbconn.connection.prepareStatement("  INSERT INTO patient(`patientid`,`adminid`) VALUES  (?,?)");
                            sql_statment.setInt(1, t);
                            sql_statment.setInt(2, 1);
                            sql_statment.executeUpdate();

                            Date c = Calendar.getInstance().getTime();
                            System.out.println("Current time => " + c);

                            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                            String formattedDate = df.format(c);


                            sql_statment = dbconn.connection.prepareStatement("   INSERT INTO  `medicalfile` (`lastupdatedate`,`patientid`,`doctorid`)  VALUES  (?,?,?)");
                            sql_statment.setString(1,formattedDate );
                            sql_statment.setInt(2, t);
                            sql_statment.setInt(3, 2);
                            sql_statment.executeUpdate();


                        }
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Successfully Registration!", Toast.LENGTH_LONG).show();
                            }});
                    }
                    sql_statment.close();
                    dbconn.DB_Close();
                }
                catch (Exception e)
                {
                    if (e.getMessage().toString().contains("Duplicate entry"))
                    {
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
    private boolean isEmpty(EditText view)
    {
        if(TextUtils.isEmpty(view.getText().toString()))
        {
            view.setError("Check the value!");
            focusView = view;
            continues = true;
        }

        return TextUtils.isEmpty(view.getText().toString());


    }
    public void set_error_null()
    {
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

/*

    private boolean validateFullName() {
        String val = fullName.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            fullName.setError("Field can not be empty");
            return false;
        } else {
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            return true;
        }
    }
*/
/*

    private boolean validateUsername() {
        String val = username.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            username.setError("Field can not be empty");
            return false;
        } else if (val.length() > 20) {
            username.setError("Username is too large!");
            return false;
        } else if (!val.matches(checkspaces)) {
            sername.setError("No White spaces are allowed!");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }
*/

 /*   private boolean validateEmail() {
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError("Invalid Email!");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }
*/

    public static boolean isValidPassword(String password) {
        //Matcher matcher = Pattern.compile("[a-zA-Z0-9\\!\\@\\#\\$\\.\\-]{8,24}").matcher(password);
        Matcher matcher = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\da-zA-Z]).{8,20}$").matcher(password);
        return matcher.matches();
    }




/*
    private boolean validateGender() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
*/

/*    private boolean validateAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = calendar.getYear();
        int isAgeValid = currentYear - userAge;

        if (isAgeValid < 14) {
            Toast.makeText(this, "You are not eligible to apply", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }


    private boolean validatePhoneNumber() {
        String val = phoneNumber.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";
        if (val.isEmpty()) {
            phoneNumber.setError("Enter valid phone number");
            return false;
        } else if (!val.matches(checkspaces)) {
            phoneNumber.setError("No White spaces are allowed!");
            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }

 */

}