package com.best.app.patientmedicalfile.PackagePatient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.best.app.patientmedicalfile.PackageClass.ClassDisease;
import com.best.app.patientmedicalfile.PackageClass.ClassMedicalinfo;
import com.best.app.patientmedicalfile.PackageClass.ClassReport;
import com.best.app.patientmedicalfile.PackageClass.DB_Connection;
import com.best.app.patientmedicalfile.R;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ReviewMedicalFileActivity extends AppCompatActivity {

    // medical info
    public ListView listView;
    private ArrayList<ClassMedicalinfo> DataList = new ArrayList<ClassMedicalinfo>();
    CenterAdapterMINFO adapter;

    public ListView listView2;
    private ArrayList<ClassDisease> DataList2 = new ArrayList<ClassDisease>();
    CenterAdapterDISE adapter2;

    public ListView listView3;
    private ArrayList<ClassReport> DataList3 = new ArrayList<ClassReport>();
    CenterAdapter adapter3;

    SharedPreferences loginPrefs;
    int mfid = 0;
    ////



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_medical_file);

        // medical info
        loginPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mfid = loginPrefs.getInt("userid", 0);
        listView = (ListView) findViewById(R.id.list1);
        listView2 = (ListView) findViewById(R.id.list2);
        listView3 = (ListView) findViewById(R.id.list3);



        get_dateMINF();
        adapter = new CenterAdapterMINFO(getApplicationContext(), R.layout.rowinfofordoctor, DataList);
        listView.setAdapter(adapter);



        ////
        get_dateDISE();
        adapter2 = new CenterAdapterDISE(getApplicationContext(), R.layout.rowviewdise, DataList2);
        listView2.setAdapter(adapter2);
        ///

        get_date();
        adapter3 = new CenterAdapter(getApplicationContext(), R.layout.doctorviewreport, DataList3);
        listView3.setAdapter(adapter3);

    }

    private void get_dateMINF() {
        DataList.clear();
        Thread t1;
        t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    DB_Connection dbconn = new DB_Connection();
                    dbconn.DB_Connection_open();
                    PreparedStatement sql_statment;
                    PreparedStatement st = dbconn.connection.prepareStatement("SELECT `infoid`,medicalfile.medicalfileid ,user.fullname,`title`,`description`,`infovalue`FROM `medicalinfo`, medicalfile,patient,user WHERE medicalinfo.medicalfileid=medicalfile.medicalfileid AND medicalfile.patientid=patient.patientid and patient.patientid=user.userid     and   patient.patientid =?");
                    st.setInt(1, mfid);
                    ResultSet rs = st.executeQuery();
                    while (rs.next()) {
                        DataList.add(new ClassMedicalinfo(
                                rs.getInt(1),
                                rs.getInt(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6)
                        ));
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
    private class CenterAdapterMINFO extends BaseAdapter {
        private Context context;
        private int layout;

        public CenterAdapterMINFO(Context context, int layout, ArrayList<ClassMedicalinfo> allSubSerList) {
            this.context = context;
            this.layout = layout;
            AllSubSerList = allSubSerList;
        }

        ArrayList<ClassMedicalinfo> AllSubSerList = new ArrayList<ClassMedicalinfo>();

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
                row = inflater.inflate(R.layout.rowinfofordoctor, null);
            }

            final TextView medicalfileid = (TextView) row.findViewById(R.id.medicalfileid);
            final TextView fullname = (TextView) row.findViewById(R.id.fullname);
            final TextView title = (TextView) row.findViewById(R.id.title);
            final TextView description = (TextView) row.findViewById(R.id.description);
            final TextView infovalue = (TextView) row.findViewById(R.id.infovalue);


            Button btnDelete = (Button) row.findViewById(R.id.btnDelete);

            btnDelete.setVisibility(View.GONE);
            medicalfileid.setVisibility(View.GONE);
            fullname.setVisibility(View.GONE);



            final ClassMedicalinfo item = AllSubSerList.get(position);
            medicalfileid.setText(String.valueOf(item.getMedicalfileid()));
            fullname.setText(item.getFullname());
            title.setText(item.getTitle());
            description.setText(item.getDescription());
            infovalue.setText(item.getInfovalue());


            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            return row;
        }
    }

    private void get_dateDISE() {
        DataList2.clear();
        Thread t1;
        t1=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    DB_Connection dbconn = new DB_Connection();
                    dbconn.DB_Connection_open();
                    PreparedStatement sql_statment;
                    PreparedStatement st=dbconn.connection.prepareStatement(" SELECT `diseaseid`,medicalfile.medicalfileid ,user.fullname, `diseasename`,`diseasedate`,`healthserviceprovider`,`medicine` FROM `disease`, medicalfile,patient,user WHERE disease.medicalfileid=medicalfile.medicalfileid AND medicalfile.patientid=patient.patientid " +
                            "and patient.patientid=user.userid  and   patient.patientid =?");
                    st.setInt(1,mfid);
                    ResultSet rs = st.executeQuery();
                    while (rs.next()) {
                        DataList2.add(new ClassDisease(
                                rs.getInt(1),
                                rs.getInt(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6),
                                rs.getString(7)

                        )  );
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
    private class CenterAdapterDISE extends BaseAdapter {
        private Context context;
        private int layout;

        public CenterAdapterDISE(Context context, int layout, ArrayList<ClassDisease> allSubSerList) {
            this.context = context;
            this.layout = layout;
            AllSubSerList = allSubSerList;
        }
        ArrayList<ClassDisease> AllSubSerList = new ArrayList<ClassDisease>();
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
                row = inflater.inflate(R.layout.rowviewdise, null);
            }

            final TextView diseasename = (TextView) row.findViewById(R.id.diseasename);
            final TextView diseasedate = (TextView) row.findViewById(R.id.diseasedate);
            final TextView healthserviceprovider = (TextView) row.findViewById(R.id.healthserviceprovider);
            final TextView medicine = (TextView) row.findViewById(R.id.medicine);

            Button btnDelete = (Button) row.findViewById(R.id.btnDelete);

            btnDelete.setVisibility(View.GONE);


            final ClassDisease item = AllSubSerList.get(position);
            diseasename.setText(item.getDiseasename());
            diseasedate.setText(item.getDiseasedate());
            healthserviceprovider.setText(item.getHealthserviceprovider());
            medicine.setText(item.getMedicine());


            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });





            return row;
        }
    }

    private void get_date() {
        DataList3.clear();
        Thread t1;
        t1=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    DB_Connection dbconn = new DB_Connection();
                    dbconn.DB_Connection_open();
                    PreparedStatement sql_statment;
                    PreparedStatement st=dbconn.connection.prepareStatement("  SELECT `reportid`,medicalfile.medicalfileid ,user.fullname,`title`,`healthserviceprovider`,`notices`,`reportdate` FROM `report`, medicalfile,patient,user WHERE report.medicalfileid=medicalfile.medicalfileid AND medicalfile.patientid=patient.patientid and patient.patientid=user.userid and   patient.patientid =?");
                    st.setInt(1,mfid);
                    ResultSet rs = st.executeQuery();
                    while (rs.next()) {
                        DataList3.add(new ClassReport(
                                rs.getInt(1),
                                rs.getInt(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6),
                                rs.getString(7)

                        )  );
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

        public CenterAdapter(Context context, int layout, ArrayList<ClassReport> allSubSerList) {
            this.context = context;
            this.layout = layout;
            AllSubSerList = allSubSerList;
        }
        ArrayList<ClassReport> AllSubSerList = new ArrayList<ClassReport>();
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
                row = inflater.inflate(R.layout.doctorviewreport, null);
            }

            final TextView medicalfileid = (TextView) row.findViewById(R.id.medicalfileid);
            final TextView fullname = (TextView) row.findViewById(R.id.fullname);
            final TextView title = (TextView) row.findViewById(R.id.title);
            final TextView healthserviceprovider = (TextView) row.findViewById(R.id.healthserviceprovider);
            final TextView notices = (TextView) row.findViewById(R.id.notices);
            final TextView reportdate = (TextView) row.findViewById(R.id.reportdate);

            Button btnDelete = (Button) row.findViewById(R.id.btnDelete);

            btnDelete.setVisibility(View.GONE);


            final ClassReport item = AllSubSerList.get(position);
            medicalfileid.setText(String.valueOf( item.getMedicalfileid()));
            fullname.setText(item.getFullname());
            title.setText(item.getTitle());
            healthserviceprovider.setText(item.getHealthserviceprovider());
            notices.setText(item.getNotices());
            reportdate.setText(item.getReportdate());

            fullname.setVisibility(View.GONE);
            medicalfileid.setVisibility(View.GONE);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return row;
        }
    }

}