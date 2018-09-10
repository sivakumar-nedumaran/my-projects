package com.cynosurecreations.feras;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class ViewTBNFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.view_tbn_layout,null);
        // skype message button click event code here
        ((Button) view.findViewById(R.id.buttonViewSBN)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String playerid = ((EditText) view.findViewById(R.id.editTextID)).getText().toString().trim();
                //Toast pass = Toast.makeText(getActivity().getBaseContext(), playerid2 , Toast.LENGTH_SHORT);
                //pass.show();
                ((PlayerMainActivity)getActivity()).outpassid=playerid;
                ((PlayerMainActivity)getActivity()).viewTeam();


            }
        });
        return view;
    }


}
