package com.technology.computer.mit.homeautomation;

import java.io.IOException;

import java.io.OutputStream;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ArduinoMain extends Activity {

    //Declare buttons & editText
    Button L1N, L1F, L2N, L2F, L3N, L3F, L4N, L4F, L5N, L5F, L6N, L6F, L7N, L7F, L8N, L8F;

    private EditText editText;

    //Memeber Fields
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private OutputStream outStream = null;

    // UUID service - This is the type of Bluetooth device that the BT module is
    // It is very likely yours will be the same, if not google UUID for your manufacturer
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // MAC-address of Bluetooth module
    public String newAddress = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arduino_main);

        //addKeyListener();

        //Initialising buttons in the view
        //mDetect = (Button) findViewById(R.id.mDetect);
        L1N = (Button) findViewById(R.id.L1N);
        L1F = (Button) findViewById(R.id.L1F);
        L2N = (Button) findViewById(R.id.L2N);
        L2F = (Button) findViewById(R.id.L2F);
        L3N = (Button) findViewById(R.id.L3N);
        L3F = (Button) findViewById(R.id.L3F);
        L4N = (Button) findViewById(R.id.L4N);
        L4F = (Button) findViewById(R.id.L4F);
        L5N = (Button) findViewById(R.id.L5N);
        L5F = (Button) findViewById(R.id.L5F);
        L6N = (Button) findViewById(R.id.L6N);
        L6F = (Button) findViewById(R.id.L6F);
        L7N = (Button) findViewById(R.id.L7N);
        L7F = (Button) findViewById(R.id.L7F);
        L8N = (Button) findViewById(R.id.L8N);
        L8F = (Button) findViewById(R.id.L8F);

        //getting the bluetooth adapter value and calling checkBTstate function
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        checkBTState();

        /**************************************************************************************************************************8
         *  Buttons are set up with onclick listeners so when pressed a method is called
         *  In this case send data is called with a value and a toast is made
         *  to give visual feedback of the selection made
         */

        L1N.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                sendData("*1N");
                Toast.makeText(getBaseContext(), "Switch 1 on", Toast.LENGTH_SHORT).show();
            }
        });

        L1F.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                sendData("*1F");
                Toast.makeText(getBaseContext(), "Switch 1 off", Toast.LENGTH_SHORT).show();
            }
        });

        L2N.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                sendData("*2N");
                Toast.makeText(getBaseContext(), "Switch 2 on", Toast.LENGTH_SHORT).show();
            }
        });

        L2F.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                sendData("*2F");
                Toast.makeText(getBaseContext(), "Switch 2 off", Toast.LENGTH_SHORT).show();
            }
        });

        L3N.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                sendData("*3N");
                Toast.makeText(getBaseContext(), "Switch 3 on", Toast.LENGTH_SHORT).show();
            }
        });

        L3F.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                sendData("*3F");
                Toast.makeText(getBaseContext(), "Switch 3 off", Toast.LENGTH_SHORT).show();
            }
        });

        L4N.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                sendData("*4N");
                Toast.makeText(getBaseContext(), "Switch 4 on", Toast.LENGTH_SHORT).show();
            }
        });

        L4F.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                sendData("*4F");
                Toast.makeText(getBaseContext(), "Switch 4 off", Toast.LENGTH_SHORT).show();
            }
        });

        L5N.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                sendData("*5N");
                Toast.makeText(getBaseContext(), "Switch 5 on", Toast.LENGTH_SHORT).show();
            }
        });

        L5F.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                sendData("*5F");
                Toast.makeText(getBaseContext(), "Switch 5 off", Toast.LENGTH_SHORT).show();
            }
        });

        L6N.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                sendData("*6N");
                Toast.makeText(getBaseContext(), "Switch 6 on", Toast.LENGTH_SHORT).show();
            }
        });

        L6F.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                sendData("*6F");
                Toast.makeText(getBaseContext(), "Switch 6 off", Toast.LENGTH_SHORT).show();
            }
        });

        L7N.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                sendData("*7N");
                Toast.makeText(getBaseContext(), "Switch 7 on", Toast.LENGTH_SHORT).show();
            }
        });

        L7F.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                sendData("*7F");
                Toast.makeText(getBaseContext(), "Switch 7 off", Toast.LENGTH_SHORT).show();
            }
        });

        L8N.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                sendData("*8N");
                Toast.makeText(getBaseContext(), "Switch 8 on", Toast.LENGTH_SHORT).show();
            }
        });

        L8F.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                sendData("*8F");
                Toast.makeText(getBaseContext(), "Switch 8 off", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // connection methods are best here in case program goes into the background etc

        //Get MAC address from DeviceListActivity
        Intent intent = getIntent();
        newAddress = intent.getStringExtra(DeviceList.EXTRA_DEVICE_ADDRESS);

        // Set up a pointer to the remote device using its address.
        BluetoothDevice device = btAdapter.getRemoteDevice(newAddress);

        //Attempt to create a bluetooth socket for comms
        try {
            btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e1) {
            Toast.makeText(getBaseContext(), "ERROR - Could not create Bluetooth socket", Toast.LENGTH_SHORT).show();
        }

        // Establish the connection.
        try {
            btSocket.connect();
        } catch (IOException e) {
            try {
                btSocket.close();        //If IO exception occurs attempt to close socket
            } catch (IOException e2) {
                Toast.makeText(getBaseContext(), "ERROR - Could not close Bluetooth socket", Toast.LENGTH_SHORT).show();
            }
        }

        // Create a data stream so we can talk to the device
        try {
            outStream = btSocket.getOutputStream();
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "ERROR - Could not create bluetooth outstream", Toast.LENGTH_SHORT).show();
        }
        //When activity is resumed, attempt to send a piece of junk data ('x') so that it will fail if not connected
        // i.e don't wait for a user to press button to recognise connection failure
        sendData("x");
    }

    @Override
    public void onPause() {
        super.onPause();
        //Pausing can be the end of an app if the device kills it or the user doesn't open it again
        //close all connections so resources are not wasted

        //Close BT socket to device
        try     {
            btSocket.close();
        } catch (IOException e2) {
            Toast.makeText(getBaseContext(), "ERROR - Failed to close Bluetooth socket", Toast.LENGTH_SHORT).show();
        }
    }
    //takes the UUID and creates a comms socket
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return  device.createRfcommSocketToServiceRecord(MY_UUID);
    }

    //same as in device list activity
    private void checkBTState() {
        // Check device has Bluetooth and that it is turned on
        if(btAdapter==null) {
            Toast.makeText(getBaseContext(), "ERROR - Device does not support bluetooth", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            if (btAdapter.isEnabled()) {
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    // Method to send data
    private void sendData(String message) {
        byte[] msgBuffer = message.getBytes();

        try {
            //attempt to place data on the outstream to the BT device
            outStream.write(msgBuffer);
        } catch (IOException e) {
            //if the sending fails this is most likely because device is no longer there
            Toast.makeText(getBaseContext(), "ERROR - Device not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    /*
    public void addKeyListener() {

        // get edittext component
        editText = (EditText) findViewById(R.id.editText1);

        // add a keylistener to keep track user input
        editText.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                // if keydown and send is pressed implement the sendData method
                if ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN)) {
                    //I have put the * in automatically so it is no longer needed when entering text
                    sendData('*' + editText.getText().toString());
                    Toast.makeText(getBaseContext(), "Sending text", Toast.LENGTH_SHORT).show();

                    return true;
                }

                return false;
            }
        });

    }*/

}