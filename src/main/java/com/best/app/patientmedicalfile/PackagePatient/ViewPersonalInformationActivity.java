package com.best.app.patientmedicalfile.PackagePatient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.best.app.patientmedicalfile.PackageClass.DB_Connection;
import com.best.app.patientmedicalfile.PackageClass.UserClass;
import com.best.app.patientmedicalfile.R;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class ViewPersonalInformationActivity extends AppCompatActivity {


    public ListView listView;
    private ArrayList<UserClass> DataList = new ArrayList<UserClass>();
    CenterAdapter adapter;
    SearchView searchView;
    SharedPreferences loginPrefs;
int apid=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_personal_information);

        loginPrefs= PreferenceManager.getDefaultSharedPreferences(this);
         apid= loginPrefs.getInt("userid",0);
        setTitle("List of Patient");
        listView = (ListView) findViewById(R.id.list1);
        searchView=(SearchView) findViewById(R.id.searchView);
        ////////////////
        get_date();
        adapter = new CenterAdapter(getApplicationContext(), R.layout.rowpatientprofile, DataList);
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
                    adapter = new CenterAdapter(getApplicationContext(), R.layout.rowpatientprofile, DataList);
                    listView.setAdapter(adapter);
                } else {
                    searchItem(newText.toString());
                }
                return false;
            }
        });

    }

    public void searchItem(String textToSearch){
        Iterator<UserClass> iter = DataList.iterator();
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
                    PreparedStatement st=dbconn.connection.prepareStatement("  SELECT  `userid`,`fullname`,`username`,`password`,`gender`,`age`,`phone`,`userType` FROM `user` ,patient WHERE user.userid=patient.patientid " +
                            " and patient.patientid =? ");
                    st.setInt(1,apid);
                    ResultSet rs = st.executeQuery();
                    while (rs.next()) {
                        DataList.add(new UserClass(rs.getInt(1),rs.getString(2),rs.getString(3),
                                rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8)));
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

        public CenterAdapter(Context context, int layout, ArrayList<UserClass> allSubSerList) {
            this.context = context;
            this.layout = layout;
            AllSubSerList = allSubSerList;
        }
        ArrayList<UserClass> AllSubSerList = new ArrayList<UserClass>();
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
                row = inflater.inflate(R.layout.rowpatientprofile, null);
            }

            final EditText fullname = (EditText) row.findViewById(R.id.fullname);
            final EditText phone = (EditText) row.findViewById(R.id.phone);
            final EditText password = (EditText) row.findViewById(R.id.password);
            final EditText age = (EditText) row.findViewById(R.id.age);
             Button btnupdate = (Button) row.findViewById(R.id.btnupdate);



            final UserClass item = AllSubSerList.get(position);
            fullname.setText(item.getFullname());
            password.setText(item.getPassword());
            age.setText(item.getAge());
            phone.setText(item.getPhone());


            btnupdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UpdateFunction update = new UpdateFunction(item.getUserid(),
                            fullname.getText().toString(),
                            password.getText().toString(),
                            age.getText().toString(),
                            phone.getText().toString());
                    update.execute();
                }
            });





            return row;
        }
    }

    private class UpdateFunction extends AsyncTask<Void,Void,Void> {
        int itemid;
        String fullname;
        String password;
        String age;
        String phone;



        public UpdateFunction(int itemid,String fullname ,String password ,String age ,String phone  ) {
            this.itemid = itemid;
            this.fullname = fullname;
            this.password = password;
            this.age = age;
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
                st=dbconn.connection.prepareStatement(" UPDATE user SET `fullname`=?,`password`=?,`age`=?,`phone`=?  where `userid`=?");
                st.setString(1,fullname);
                st.setString(2,password);
                st.setString(3,age);
                st.setString(4,phone);
                st.setInt(5,itemid);

                int rs = st.executeUpdate();
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
                    adapter = new CenterAdapter(getApplicationContext(), R.layout.rowpatientprofile, DataList);
                    listView.setAdapter(adapter);
                }
            });
            return null;
        }
    }
}