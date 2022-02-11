package com.best.app.patientmedicalfile.PackageDoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import com.best.app.patientmedicalfile.PackageClass.ClassPatient;
import com.best.app.patientmedicalfile.PackageClass.DB_Connection;
import com.best.app.patientmedicalfile.R;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

public class DoctorViewPatientActivity extends AppCompatActivity {


    public ListView listView;
    private ArrayList<ClassPatient> DataList = new ArrayList<ClassPatient>();
    CenterAdapter adapter;
    SearchView searchView;
    int appId=0;
    SharedPreferences loginPrefs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_view_patient);


        loginPrefs= PreferenceManager.getDefaultSharedPreferences(this);
        setTitle("List of Patient");
        listView = (ListView) findViewById(R.id.list1);
        searchView=(SearchView) findViewById(R.id.searchView);
        ////////////////
        get_date();
        adapter = new CenterAdapter(getApplicationContext(), R.layout.rowpatient, DataList);
        listView.setAdapter(adapter);
        searchView.setQueryHint("Search title");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.toString().equals("")) {
                    get_date();
                    adapter = new CenterAdapter(getApplicationContext(), R.layout.rowpatient, DataList);
                    listView.setAdapter(adapter);
                } else {
                    searchItem(newText.toString());
                }
                return false;
            }
        });


    }

    public void searchItem(String textToSearch){
        Iterator<ClassPatient> iter = DataList.iterator();
        while(iter.hasNext()){

            if(!iter.next().getFullname().contains(textToSearch)){
                iter.remove();
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void get_date() {
        DataList.clear();
        Thread t1;
        t1=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    DB_Connection dbconn = new DB_Connection();
                    dbconn.DB_Connection_open();
                    PreparedStatement sql_statment;
                    PreparedStatement st=dbconn.connection.prepareStatement("  SELECT `userid`,medicalfileid ,`fullname`,`username`,`password`,`gender`,`age`,`phone`,`userType` FROM `user` ,patient,medicalfile WHERE user.userid=patient.patientid and patient.patientid=medicalfile.patientid   ");
                    ResultSet rs = st.executeQuery();
                    while (rs.next()) {

                        DataList.add(new ClassPatient(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),
                                rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),
                                rs.getString(9)));
                    }
                } catch (Exception s) {
                    s.printStackTrace();
                }
            }
        });
        try {
            t1.start();
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private class CenterAdapter extends BaseAdapter {
        private Context context;
        private int layout;

        public CenterAdapter(Context context, int layout, ArrayList<ClassPatient> allSubSerList) {
            this.context = context;
            this.layout = layout;
            AllSubSerList = allSubSerList;
        }
        ArrayList<ClassPatient> AllSubSerList = new ArrayList<ClassPatient>();
        @Override
        public int getCount() {
            return AllSubSerList.size();
        }
        @Override
        public Object getItem(int position) {
            return AllSubSerList.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;

            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.rowpatient, null);
            }

            final EditText fullname = (EditText) row.findViewById(R.id.fullname);
            final EditText phone = (EditText) row.findViewById(R.id.phone);
          Button btnViewDisease = (Button) row.findViewById(R.id.btnViewDisease);
            Button btnViewReport = (Button) row.findViewById(R.id.btnViewReport);
            Button btnViewmedicalInfo = (Button) row.findViewById(R.id.btnViewmedicalInfo);

            Button btnAddDisease = (Button) row.findViewById(R.id.btnAddDisease);
            Button btnAddReport = (Button) row.findViewById(R.id.btnAddReport);
            Button btnAddmedicalInfo = (Button) row.findViewById(R.id.btnAddmedicalInfo);


            final ClassPatient item = AllSubSerList.get(position);
            fullname.setText(item.getFullname());
            fullname.setText(item.getFullname());
            phone.setText(item.getPhone());


            btnViewDisease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = loginPrefs.edit();
                    editor.putInt("mfid", item.getMedicalfileid());
                    editor.apply();
                    startActivity(new Intent(DoctorViewPatientActivity.this, ViewDiseaseActivity.class));
                }
            });
            btnViewReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = loginPrefs.edit();
                    editor.putInt("mfid", item.getMedicalfileid());
                    editor.apply();
                    startActivity(new Intent(DoctorViewPatientActivity.this, ViewReportAActivity.class));
                }
            });
            btnViewmedicalInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = loginPrefs.edit();
                    editor.putInt("mfid", item.getMedicalfileid());
                    editor.apply();
                    startActivity(new Intent(DoctorViewPatientActivity.this, ViewMedicalInfoActivity.class));

                }
            });
            btnAddDisease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = loginPrefs.edit();
                    editor.putInt("mfid", item.getMedicalfileid());
                    editor.apply();
                    startActivity(new Intent(DoctorViewPatientActivity.this, DiseaseActivity.class));

                }
            });
            btnAddReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = loginPrefs.edit();
                    editor.putInt("mfid", item.getMedicalfileid());
                    editor.apply();
                    startActivity(new Intent(DoctorViewPatientActivity.this, ReportActivity.class));
                }
            });
            btnAddmedicalInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = loginPrefs.edit();
                    editor.putInt("mfid", item.getMedicalfileid());
                    editor.apply();
                    startActivity(new Intent(DoctorViewPatientActivity.this, MedicalInfoActivity.class));
                 }
            });



       /*     btnupdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UpdateFunction update = new UpdateFunction(item.getUserid(),fullname.getText().toString(),email.getText().toString(),specialty.getText().toString(),phone.getText().toString());
                    update.execute();
                }
            });
            btndelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeleteFunction delete = new DeleteFunction(item.getUserid());
                    delete.execute();
                }
            });*/
            return row;
        }
    }

 /*   private class UpdateFunction extends AsyncTask<Void,Void,Void> {
        int itemid;
        String fullname;
        String email;
        String specialty;
        String phone;


        public UpdateFunction(int itemid,String fullname,String email,String specialty,String phone) {
            this.itemid = itemid;
            this.fullname = fullname;
            this.email = email;
            this.specialty = specialty;
            this.phone = phone;

        }
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                DB_Connection   dbconn = new DB_Connection();
                dbconn.DB_Connection_open();
                PreparedStatement st = null;
                PreparedStatement st2= null;
                st=dbconn.connection.prepareStatement(" UPDATE users SET `fullname`=?,`email`=?,`phone`=? where `userid`=?");
                st.setString(1,fullname);
                st.setString(2,email);
                st.setString(3,phone);
                st.setInt(4,itemid);
                int rs = st.executeUpdate();
                //
                st2=dbconn.connection.prepareStatement(" update `doctor` SET `specialty`=? WHERE `doctorid`=?");
                st2.setString(1,specialty);
                st2.setInt(2,itemid);
                int rs2 = st2.executeUpdate();
                //

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"successfully update",Toast.LENGTH_LONG).show();
                    get_date();
                    adapter = new CenterAdapter(getApplicationContext(), R.layout.rowdoctor, DataList);
                    listView.setAdapter(adapter);
                }
            });
            return null;
        }
    }


    private class DeleteFunction extends AsyncTask<Void,Void,Void> {
        int itemid;
        public DeleteFunction(int itemid) {
            this.itemid = itemid;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                DB_Connection   dbconn = new DB_Connection();
                dbconn.DB_Connection_open();
                PreparedStatement st = null;
                PreparedStatement st2;
                st=dbconn.connection.prepareStatement("DELETE FROM doctor WHERE doctorid=?");
                st.setInt(1,itemid);
                int sql_result = st.executeUpdate();
                if (sql_result>0)
                {
                    st=dbconn.connection.prepareStatement("DELETE FROM `users` WHERE `userid`=?");
                    st.setInt(1,itemid);
                    int sql_result1 = st.executeUpdate();
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"successfully deleted",Toast.LENGTH_LONG).show();
                    get_date();
                    adapter = new CenterAdapter(getApplicationContext(), R.layout.rowdoctor, DataList);
                    listView.setAdapter(adapter);
                }
            });
            return null;
        }
    }*/
}