package com.cynosurecreations.mithostels;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewOutpassFragment extends Fragment implements View.OnClickListener {

    private Button buttonDelete, buttonApprove, buttonDeny;
    private String id,caller;
    private ImageView approvalimg;
    private String type,profile;

    private EditText block,name,regno,room,visitdt,returndt,timeot,rettime,visitaddress,nodays,contactph,courseet,branchet,rcidet,opid;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_outpass,null);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        type = sharedPreferences.getString(Config.TYPE_SHARED_PREF,"Not Available");
        profile = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");

        caller = getArguments().getString("caller");

        buttonDelete = (Button) view.findViewById(R.id.buttonDelete);
        buttonDeny = (Button) view.findViewById(R.id.buttonDeny);
        buttonApprove = (Button) view.findViewById(R.id.buttonApprove);
        approvalimg = (ImageView) view.findViewById(R.id.approvalimg);

        if(type.equals("Faculty"))
        {
            id = ((FacultyMainActivity)getActivity()).outpassid;
            buttonDelete.setVisibility(view.GONE);

            if(caller.equals("approve"))
            {
                buttonApprove.setVisibility(view.VISIBLE);
                buttonDeny.setVisibility(view.VISIBLE);
                approvalimg.setVisibility(view.GONE);
            }
            else if(caller.equals("reapprove"))
            {
                buttonApprove.setVisibility(view.VISIBLE);
                buttonDeny.setVisibility(View.GONE);
                approvalimg.setVisibility(view.GONE);
            }
            else
            {
                buttonApprove.setVisibility(view.GONE);
                buttonDeny.setVisibility(view.GONE);
                approvalimg.setVisibility(view.VISIBLE);
            }
        }
        else if(type.equals("Student"))
        {
            id = ((StudentMainActivity)getActivity()).outpassid;
            buttonDelete.setVisibility(view.VISIBLE);
            approvalimg.setVisibility(view.VISIBLE);
            buttonApprove.setVisibility(view.GONE);
            buttonDeny.setVisibility(view.GONE);
        }

        courseet = (EditText) view.findViewById(R.id.editTextCourses);
        branchet = (EditText) view.findViewById(R.id.editTextBranch);
        block = (EditText) view.findViewById(R.id.editTextBlock);
        name = (EditText) view.findViewById(R.id.editTextStdName);
        regno = (EditText) view.findViewById(R.id.editTextStdRegno);
        room = (EditText) view.findViewById(R.id.editTextRoom);
        visitdt = (EditText) view.findViewById(R.id.editTextVisitDate);
        returndt = (EditText) view.findViewById(R.id.editTextReturnDate);
        timeot = (EditText) view.findViewById(R.id.editTextTimeOut);
        rettime = (EditText) view.findViewById(R.id.editTextReturnTime);
        visitaddress = (EditText) view.findViewById(R.id.editTextAddress);
        nodays = (EditText) view.findViewById(R.id.editTextNod);
        contactph = (EditText) view.findViewById(R.id.editTextContactPhone);
        rcidet = (EditText) view.findViewById(R.id.editTextRc);
        opid = (EditText) view.findViewById(R.id.editTextOpid);





        buttonDelete.setOnClickListener(this);
        buttonApprove.setOnClickListener(this);
        buttonDeny.setOnClickListener(this);

        opid.setText(id);

            courseet.setFocusable(false);
            branchet.setFocusable(false);
            block.setFocusable(false);
            name.setFocusable(false);
            regno.setFocusable(false);
            room.setFocusable(false);
            visitdt.setFocusable(false);
            returndt.setFocusable(false);
            timeot.setFocusable(false);
            rettime.setFocusable(false);
            visitaddress.setFocusable(false);
            nodays.setFocusable(false);
            contactph.setFocusable(false);
            rcidet.setFocusable(false);
            opid.setFocusable(false);

        getOutpass();

        return view;
    }

    private void getOutpass(){
        class GetEmployee extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showOutpass(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_OUTPASS,id);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }

    private void deleteOutpass(){
        class DeleteEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), "Updating...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                ((StudentMainActivity)getActivity()).viewAllOutpasses();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_DELETE_OUTPASS, id);
                return s;
            }
        }

        DeleteEmployee de = new DeleteEmployee();
        de.execute();
    }

    private void confirmDeleteEmployee(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("Are you sure you want to delete this outpass?");

        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteOutpass();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void showOutpass(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);

            String oname = c.getString(Config.TAG_OUT_NAME);
            String ovisitdate = c.getString(Config.TAG_OUT_VISITDATE);
            String oid = c.getString(Config.TAG_OUT_ID);
            String oregno = c.getString(Config.TAG_OUT_REGNO);
            String oapproval = c.getString(Config.TAG_OUT_APPROVAL);
            String oreturndate = c.getString(Config.TAG_OUT_RETURNDATE);
            String oreturntime = c.getString(Config.TAG_OUT_RETURNTIME);
            String otimeout = c.getString(Config.TAG_OUT_TIMEOUT);
            String ocourse = c.getString(Config.TAG_OUT_COURSE);
            String obranch = c.getString(Config.TAG_OUT_BRANCH);
            String oblock = c.getString(Config.TAG_OUT_BLOCK);
            String oroom = c.getString(Config.TAG_OUT_ROOM);
            String oplaceofvisit = c.getString(Config.TAG_OUT_PLACEOFVISIT);
            String onod = c.getString(Config.TAG_OUT_NOD);
            String ocontact = c.getString(Config.TAG_OUT_CONTACT);
            String orcid = c.getString(Config.TAG_OUT_RCID);

            courseet.setText(ocourse);
            branchet.setText(obranch);
            block.setText(oblock);
            name.setText(oname);
            regno.setText(oregno);
            room.setText(oroom);
            visitdt.setText(ovisitdate);
            returndt.setText(oreturndate);
            timeot.setText(otimeout);
            rettime.setText(oreturntime);
            visitaddress.setText(oplaceofvisit);
            nodays.setText(onod);
            contactph.setText(ocontact);
            rcidet.setText(orcid);

            if(oapproval.equals("approved"))
            {
                approvalimg.setImageResource(R.drawable.approved);
                approvalimg.getLayoutParams().height = 350;
                approvalimg.getLayoutParams().width = 350;
                buttonDelete.setVisibility(View.GONE);
            }
            else if(oapproval.equals("pending"))
            {
                approvalimg.setImageResource(R.drawable.pendings);
                approvalimg.getLayoutParams().height = 270;
                approvalimg.getLayoutParams().width = 550;
            }
            else if(oapproval.equals("denied"))
            {
                approvalimg.setImageResource(R.drawable.denied);
                approvalimg.getLayoutParams().height = 270;
                approvalimg.getLayoutParams().width = 550;
                buttonDelete.setVisibility(View.GONE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void approveOutpass()
    {

        class UpdateStudent extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getActivity(),s.trim(),Toast.LENGTH_LONG).show();
                ((FacultyMainActivity)getActivity()).viewAllOutpasses();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_APPROVE_OUTPASS, id);
                return s;
            }
        }

        UpdateStudent us = new UpdateStudent();
        us.execute();
    }

    private void denyOutpass()
    {

        class UpdateStudent extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
                ((FacultyMainActivity)getActivity()).viewAllOutpasses();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_DENY_OUTPASS, id);
                return s;
            }
        }

        UpdateStudent us = new UpdateStudent();
        us.execute();
    }

    @Override
    public void onClick(View v) {

        if(v == buttonDelete){
            confirmDeleteEmployee();
        }
        if(v == buttonApprove){
            approveOutpass();
        }
        if(v == buttonDeny){
            denyOutpass();
        }
    }
}