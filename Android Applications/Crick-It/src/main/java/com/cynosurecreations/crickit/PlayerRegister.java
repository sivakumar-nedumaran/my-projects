package com.cynosurecreations.feras;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.test.mock.MockPackageManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.IOException;
import java.util.UUID;

public class PlayerRegister extends AppCompatActivity implements View.OnClickListener{

    //Defining views
    private EditText editTextName;
    private EditText editTextContact;
    private EditText editTextID;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    private Button buttonAdd;
    private EditText editTextEmail;
    private EditText editTextPasswd;
    private EditText editTextPasswd2;
    private EditText editTextPincode;
    private EditText editTextSkypeID;
    private Spinner dropdown,block,spinnersport;
    private String JSON_STRING;
    private String state;

    private Button buttonChoose;
    private Button buttonUpload;
    private ImageView imageView;
    private TextView editTextImage;

    //Image request code
    private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath;

    TextView _latitude, _longitude;

    Button btnShowLocation;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    // GPSTracker class
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_register);

        //Requesting storage permission
        requestStoragePermission();

        //Initializing views
        editTextID = (EditText) findViewById(R.id.editTextID);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextContact = (EditText) findViewById(R.id.editTextContact);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPasswd = (EditText) findViewById(R.id.editTextPasswd);
        editTextPasswd2 = (EditText) findViewById(R.id.editTextPasswd2);
        editTextPincode = (EditText) findViewById(R.id.editTextPincode);
        editTextSkypeID = (EditText) findViewById(R.id.editTextSkypeID);
        radioSexGroup=(RadioGroup)findViewById(R.id.radioGroup);
        block = (Spinner)findViewById(R.id.spinnerBlock);
        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);
        imageView = (ImageView) findViewById(R.id.imageView);
        editTextImage = (TextView) findViewById(R.id.editTextImage);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);

        dropdown = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"Select your state","Andhra_Pradesh", "Arunachal_Pradesh","Assam","Bihar","Chhattisgarh","Dadra_&_Nagar_Haveli", "Daman_&_Diu", "Delhi","Goa","Gujarat","Haryana", "Himachal_Pradesh", "Jammu_and_Kashmir","Jharkhand","Karnataka","Kerala", "Lakshadweep", "Madhya_Pradesh","Maharashtra","Manipur","Meghalaya", "Mizoram", "Nagaland","Orissa","Pondicherry", "Punjab", "Rajasthan","Sikkim","Tamil_Nadu","Tripura", "Uttar_Pradesh", "Uttarakhand"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        spinnersport = (Spinner) findViewById(R.id.sport);
        String[] items2 = new String[]{"Select your sport","Cricket","Volley_Ball","Basket_Ball","Yoga","Swimming","Badminton"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        spinnersport.setAdapter(adapter2);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                state = dropdown.getSelectedItem().toString().trim();
                //Toast.makeText(PlayerRegister.this, state , Toast.LENGTH_SHORT).show();
                getJSON2();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
                //execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnShowLocation = (Button) findViewById(R.id.button);

        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // create class object
                gps = new GPSTracker(PlayerRegister.this);

                // check if GPS enabled
                if(gps.canGetLocation()){

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    // \n is for new line
                    //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

                    _latitude.setText("Latitude: " + latitude);
                    _longitude.setText("Longitude: " + longitude);
                    _latitude.setVisibility(View.VISIBLE);
                    _longitude.setVisibility(View.VISIBLE);
                    btnShowLocation.setVisibility(View.GONE);

                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }

            }
        });

        _latitude = (TextView) findViewById(R.id.latitude);
        _longitude = (TextView) findViewById(R.id.longitude);

        //Setting listeners to button
        buttonAdd.setOnClickListener(this);
        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
    }

    private void getJSON2(){
        class GetJSON2 extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(PlayerRegister.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_CITIES,state);
                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                //Toast.makeText(getActivity(), JSON_STRING , Toast.LENGTH_SHORT).show();
                populate();
            }
        }
        GetJSON2 gj = new GetJSON2();
        gj.execute();
    }

    private void populate(){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            ArrayList<String> classids = new ArrayList<String>();

            classids.add("Select your city");

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);

                String cityname = jo.getString(Config.TAG_CITYNAME);

                if (!classids.contains(cityname)) {
                    classids.add(cityname);
                }
                //Toast.makeText(getActivity(), cityname , Toast.LENGTH_SHORT).show();


            }
            //String[] items = new String[]{"Professor", "Associate Professor", "Assistant Professor (Sr.Gr)","Assistant Professor","Teaching Fellow"};
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(PlayerRegister.this, android.R.layout.simple_spinner_dropdown_item, classids);
            block.setAdapter(adapter1);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //Adding an employee
    private void addPlayer(){

        boolean cancel = false;
        View focusView = null;

        final String state = dropdown.getSelectedItem().toString();

        final String contact = editTextContact.getText().toString().trim();
        final String pincode = editTextPincode.getText().toString().trim();
        final String skypeid = editTextSkypeID.getText().toString().trim();
        final String sport = spinnersport.getSelectedItem().toString();
        final String email = editTextEmail.getText().toString().trim();
        final String imgname = editTextImage.getText().toString().trim();
        final String city = block.getSelectedItem().toString().trim();


        String lati = _latitude.getText().toString().trim();
        String longi = _longitude.getText().toString().trim();
        final String latitude = lati.substring(lati.indexOf(" ")+1);
        final String longitude = longi.substring(longi.indexOf(" ")+1);


        //Toast.makeText(PlayerRegister.this, latitude + " " + longitude , Toast.LENGTH_SHORT).show();

        //final String blockno = blocks;

        final String passwd = editTextPasswd.getText().toString().trim();
        final String passwd2 = editTextPasswd2.getText().toString().trim();

        final String name;
        String temp_name = editTextName.getText().toString().trim();

        final String id = editTextID.getText().toString().trim();
        final String gender;

        if (TextUtils.isEmpty(temp_name)) {
            editTextName.setError(getString(R.string.error_field_required));
            focusView = editTextName;
            cancel = true;
        } else if (!isNameValid(temp_name)) {
            editTextName.setError(getString(R.string.error_invalid_name));
            focusView = editTextName;
            cancel = true;
        }

        if (TextUtils.isEmpty(latitude) || TextUtils.isEmpty(longitude)) {
            btnShowLocation.setError(getString(R.string.error_field_required));
            focusView = btnShowLocation;
            cancel = true;
        }

        name = temp_name;

        if (sport.equals("Select your sport")) {
                Toast.makeText(this, "Please select your sport" , Toast.LENGTH_SHORT).show();
                focusView = spinnersport;
                cancel = true;
        }
        if (sport.equals("Select your state")) {
            Toast.makeText(this, "Please select your state" , Toast.LENGTH_SHORT).show();
            focusView = dropdown;
            cancel = true;
        }

        if (city.equals("Select your city")) {
            Toast.makeText(this, "Please select your city" , Toast.LENGTH_SHORT).show();
            focusView = block;
            cancel = true;
        }

        if (!imgname.equals("Image selected")) {
            Toast.makeText(this, "Please upload profile picture" , Toast.LENGTH_SHORT).show();
            focusView = buttonChoose;
            cancel = true;
        }

        if (TextUtils.isEmpty(pincode)) {
            editTextPincode.setError(getString(R.string.error_field_required));
            focusView = editTextPincode;
            cancel = true;
        }

        if (TextUtils.isEmpty(skypeid)) {
            editTextSkypeID.setError(getString(R.string.error_field_required));
            focusView = editTextSkypeID;
            cancel = true;
        }

        int selectedId=radioSexGroup.getCheckedRadioButtonId();
        if(selectedId!=-1) {
            radioSexButton = (RadioButton) findViewById(selectedId);
            gender = radioSexButton.getText().toString();
        }
        else
        {
            focusView = radioSexGroup;
            cancel = true;
            Toast.makeText(PlayerRegister.this,"Please select your gender",Toast.LENGTH_LONG).show();
            gender = "gender";
        }

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError(getString(R.string.error_field_required));
            focusView = editTextEmail;
            cancel = true;

        } else if (!isEmailValid(email)) {
            editTextEmail.setError(getString(R.string.error_invalid_email));
            focusView = editTextEmail;
            cancel = true;
        }

        if (TextUtils.isEmpty(contact)) {
            editTextContact.setError(getString(R.string.error_field_required));
            focusView = editTextContact;
            cancel = true;
        } else if (!isContactValid(contact)) {
            editTextContact.setError(getString(R.string.error_invalid_contact));
            focusView = editTextContact;
            cancel = true;
        }

        if (!isPasswordValid(passwd)) {
            editTextPasswd.setError(getString(R.string.error_invalid_password));
            focusView = editTextPasswd;
            editTextPasswd.setText("");
            editTextPasswd2.setText("");
            cancel = true;
        }

        if(!passwd.equals(passwd2)) {
            //popup msg
            Toast.makeText(PlayerRegister.this,"Passwords don't match! ", Toast.LENGTH_SHORT).show();
            editTextPasswd.setText("");
            editTextPasswd2.setText("");
            focusView = editTextPasswd;
            cancel = true;
        }

        if (cancel)
        {
            focusView.requestFocus();
            return;
        }

        class AddPlayer extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(PlayerRegister.this,"Adding...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                uploadMultipart();
                Toast.makeText(PlayerRegister.this,s,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(PlayerRegister.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_EMP_NAME,name);
                params.put(Config.KEY_EMP_STATE,state);
                params.put(Config.KEY_EMP_CONTACT,contact);
                params.put(Config.KEY_EMP_CITY,city);
                params.put(Config.KEY_EMP_SPORT,sport);
                params.put(Config.KEY_EMP_EMAIL,email);
                params.put(Config.KEY_EMP_PINCODE,pincode);
                params.put(Config.KEY_EMP_PASSWD,passwd);
                params.put(Config.KEY_EMP_GENDER,gender);
                params.put(Config.KEY_EMP_ID,id);
                params.put(Config.KEY_EMP_SKYPEID,skypeid);
                params.put(Config.KEY_EMP_LATITUDE,latitude);
                params.put(Config.KEY_EMP_LONGITUDE,longitude);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD_PLAYER, params);
                return res;
            }
        }

        AddPlayer ae = new AddPlayer();
        ae.execute();
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@") && email.contains(".");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 5;
    }
    private boolean isNameValid(String name) {
        //TODO: Replace this with your own logic
        return name.length() > 5;
    }

    private boolean isContactValid(String contact) {
        //TODO: Replace this with your own logic
        return contact.length() == 10;
    }

    @Override
    public void onClick(View v) {
        if(v == buttonAdd){
            addPlayer();
        }
        if (v == buttonChoose) {
            showFileChooser();
        }
        if (v == buttonUpload) {
            //uploadMultipart();
        }
    }

    /*
    * This is the method responsible for image upload
    * We need the full image path and the name for the image in this method
    * */
    public void uploadMultipart() {
        //getting name for the image
        String name = editTextID.getText().toString().trim();

        //Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        //getting the actual path of the image
        String path = getPath(filePath);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, Constants.UPLOAD_URL)
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("name", name) //Adding text parameter to the request
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        editTextImage.setText("Image selected");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

}
