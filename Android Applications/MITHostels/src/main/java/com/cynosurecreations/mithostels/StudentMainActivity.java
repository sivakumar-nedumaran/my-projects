package com.cynosurecreations.mithostels;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;
import android.view.Menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StudentMainActivity extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    NavigationView navigationView;

    String profileid,type;
    String classid;
    String slno,name,email;
    String facultyid,outpassid;
    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        profileid = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");
        type = sharedPreferences.getString(Config.TYPE_SHARED_PREF,"Not Available");

        getStudent();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;

        //MenuItem mItem = (MenuItem) findViewById(R.id.nav_item_view_faculties) ;
        //mItem.setVisible(false);

        navigationView = (NavigationView) findViewById(R.id.shitstuff);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_item_approve_outpass).setVisible(false);
        nav_Menu.findItem(R.id.nav_item_denied_outpass).setVisible(false);
        nav_Menu.findItem(R.id.nav_item_view_profile).setVisible(false);
        nav_Menu.findItem(R.id.nav_item_view_op_std).setVisible(false);

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the ViewAllStudentsFragment as the first Fragment
         */

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView,new RequestOutpassFragment()).commit();
        /**
         * Setup click events on the Navigation View Items.
         */

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                if (menuItem.getItemId() == R.id.nav_item_view_faculties) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView,new ViewAllFacultiesFragment()).commit();

                }

                if (menuItem.getItemId() == R.id.nav_item_view_outpasses) {

                    Fragment fr=new ViewAllOutpassessFragment();
                    Bundle args = new Bundle();
                    args.putString("profileid", profileid);
                    args.putString("caller", "student");
                    fr.setArguments(args);

                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView,fr).commit();

                }

                if (menuItem.getItemId() == R.id.nav_item_request_outpass) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView,new RequestOutpassFragment()).commit();

                }

                /*if (menuItem.getItemId() == R.id.nav_item_primary) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new ViewAttendanceFragment()).commit();
                }*/

                if (menuItem.getItemId() == R.id.nav_item_logout) {
                    logout();
                }

                return false;
            }

        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

    }

    private void getStudent(){
        class GetStudent extends AsyncTask<Void,Void,String> {
            //ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(StudentMainActivity.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //loading.dismiss();
                //Toast.makeText(StudentMainActivity.this, s, Toast.LENGTH_LONG).show();
                putDetails(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_STD,profileid);
                return s;
            }
        }
        GetStudent gs = new GetStudent();
        gs.execute();
    }

    private void putDetails(String json){
        //Toast.makeText(StudentMainActivity.this, json, Toast.LENGTH_LONG).show();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            for(int i = 0; i<result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);

                name = jo.getString(Config.TAG_STD_NAME);
                email = jo.getString(Config.TAG_STD_EMAIL);
                classid = jo.getString(Config.TAG_CLASSID);
                slno = jo.getString(Config.TAG_STD_SLNO);
            }
            Toast.makeText(StudentMainActivity.this, name + " " + slno + " " + classid, Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void viewFaculty()
    {
        ViewFacultyFragment vpf = new ViewFacultyFragment();
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(R.id.containerView, vpf);
        ft.commit();
    }

    void viewOutpass()
    {
        Fragment fr = new ViewOutpassFragment();

        Bundle args = new Bundle();
        args.putString("caller", "student");
        fr.setArguments(args);

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(R.id.containerView, fr);
        ft.commit();
    }

    void viewAllOutpasses()
    {
        Fragment fr=new ViewAllOutpassessFragment();
        Bundle args = new Bundle();
        args.putString("profileid", profileid);
        args.putString("caller", "student");
        fr.setArguments(args);

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerView,fr).commit();
    }

    //Logout function
    private void logout(){
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //Getting out sharedpreferences
                        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Puting the value false for loggedin
                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                        //Putting blank value to email
                        editor.putString(Config.EMAIL_SHARED_PREF, "");
                        editor.putString(Config.TYPE_SHARED_PREF, "");

                        //Saving the sharedpreferences
                        editor.commit();

                        //Starting login activity
                        Intent intent = new Intent(StudentMainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK twice to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 3000);
    }

}
