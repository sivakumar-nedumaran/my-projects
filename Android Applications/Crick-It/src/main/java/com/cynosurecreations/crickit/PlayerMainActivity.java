package com.cynosurecreations.feras;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.os.Handler;

public class PlayerMainActivity extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    NavigationView navigationView;

    String playerid;
    String profileid;
    String outpassid;
    double latitude;
    double longitude;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        profileid = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");

        Toast.makeText(PlayerMainActivity.this, "You are logged in as "+profileid , Toast.LENGTH_LONG).show();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;

        navigationView = (NavigationView) findViewById(R.id.shitstuff);
        Menu nav_Menu = navigationView.getMenu();
        //nav_Menu.findItem(R.id.nav_item_request_outpass).setVisible(false);
        //nav_Menu.findItem(R.id.nav_item_view_outpasses).setVisible(false);


        Fragment fr1=new CreateTeamFragment();
        Bundle args = new Bundle();
        args.putString("caller", "approve");
        fr1.setArguments(args);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView,fr1).commit();

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();




                if (menuItem.getItemId() == R.id.nav_item_request_outpass) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView,new CreateTeamFragment()).commit();

                }

                if (menuItem.getItemId() == R.id.nav_item_approve_outpass) {

                    Fragment fr=new ViewPlayersTabFragment();
                    Bundle args = new Bundle();
                    args.putString("caller", "approve");
                    fr.setArguments(args);

                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView,fr).commit();

                }

                if (menuItem.getItemId() == R.id.nav_item_denied_outpass) {



                }

                if (menuItem.getItemId() == R.id.nav_item_view_profile) {
                    playerid =profileid;
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView,new ViewPlayerFragment()).commit();

                }

                if (menuItem.getItemId() == R.id.nav_item_view_op_std) {

                    Fragment fr=new ViewTeamsTabFragment();
                    Bundle args = new Bundle();
                    args.putString("caller", "approve");
                    fr.setArguments(args);

                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView,fr).commit();

                }

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

    void viewAreaPlayers()
    {
        ViewAreaPlayersFragment vpf = new ViewAreaPlayersFragment();
        Bundle args = new Bundle();
        args.putDouble("latitude", latitude);
        args.putDouble("longitude", longitude);
        vpf.setArguments(args);
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(R.id.containerView, vpf);
        ft.commit();
    }

        void viewAreaTeams()
    {
        ViewAreaTeamsFragment vpf = new ViewAreaTeamsFragment();
        Bundle args = new Bundle();
        args.putDouble("latitude", latitude);
        args.putDouble("longitude", longitude);
        vpf.setArguments(args);
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(R.id.containerView, vpf);
        ft.commit();
    }

    void viewPlayer()
    {
        ViewPlayerFragment vpf = new ViewPlayerFragment();
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(R.id.containerView, vpf);
        ft.commit();
    }

    void viewTeam()
    {
        ViewOutpassFragment vpf = new ViewOutpassFragment();
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(R.id.containerView, vpf);
        ft.commit();
    }

    void viewAllFaculties()
    {
        ViewAllFacultiesFragment vpf = new ViewAllFacultiesFragment();
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(R.id.containerView, vpf);
        ft.commit();
    }

    void viewOutpass()
    {
        Fragment fr = new ViewOutpassFragment();

        Bundle args = new Bundle();
        args.putString("caller", "faculty");
        fr.setArguments(args);

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(R.id.containerView, fr);
        ft.commit();
    }

    void approveOutpass()
    {
        Fragment fr = new ViewOutpassFragment();

        Bundle args = new Bundle();
        args.putString("caller", "approve");
        fr.setArguments(args);

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(R.id.containerView, fr);
        ft.commit();
    }

    void approveDeniedOutpass()
    {
        Fragment fr = new ViewOutpassFragment();

        Bundle args = new Bundle();
        args.putString("caller", "reapprove");
        fr.setArguments(args);

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(R.id.containerView, fr);
        ft.commit();
    }

    void AddMaterial()
    {
        CreateTeamFragment vpf = new CreateTeamFragment();
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(R.id.containerView, vpf);
        ft.commit();
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
                        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
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
                        Intent intent = new Intent(PlayerMainActivity.this, LoginActivity.class);
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
        //viewAllOutpasses();
        Toast.makeText(this, "Please click BACK twice to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
