package com.adgvit.qr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class QRActivity extends AppCompatActivity implements View.OnClickListener {

    private Button scanButton;
    private TextView Name, RegNum;

    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        scanButton = (Button) findViewById(R.id.btn);
        Name = (TextView) findViewById(R.id.textViewName);
        RegNum = (TextView) findViewById(R.id.textViewRegNum);

        qrScan = new IntentIntegrator(this);

        scanButton.setOnClickListener(this);
    }

    //function to get scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                //if qrCode is empty
                Toast.makeText(this, "Result not found", Toast.LENGTH_LONG).show();
            } else {
                //if qrCode has data
                try {
                    //converting the data to JSON
                    JSONObject obj = new JSONObject(result.getContents());
                    //set the values that are returned to the text views
                    Name.setText(obj.getString("name"));
                    RegNum.setText(obj.getString("reg_num"));

                } catch (JSONException e) {
                    e.printStackTrace();
                    //If control comes here
                    //It means that encoded format doesn't match
                    //ie, User scanned a QR code which isn't in our DB
                    Toast.makeText(this, "Wrong QR Code", Toast.LENGTH_LONG).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        qrScan.initiateScan();
    }
}
