package com.example.se2einzelaufgabe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnMatNr;
    private Button btnCalculate;
    private TextView txtResponse;
    private TextView txtCalculationResult;
    private EditText txtMatNr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.btnMatNr = findViewById(R.id.btnMatNr);
        this.btnCalculate = findViewById(R.id.btnCalculate);
        this.txtCalculationResult = findViewById(R.id.txtCalculationResult);
        this.txtResponse = findViewById(R.id.txtResponse);
        this.txtMatNr = findViewById(R.id.txtMatNr);
        this.btnMatNr.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnMatNr) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String in = txtMatNr.getText().toString();

                        Socket clientSocket = new Socket("se2-isys.aau.at", 53212);
                        DataOutputStream dos = new DataOutputStream((clientSocket.getOutputStream()));

                        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                        dos.writeBytes(in + '\n');

                        final String response = inFromServer.readLine();
                        clientSocket.close();


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtResponse.setText(response);
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else if (v.getId() == R.id.btnCalculate) {
            int sum = 0;
            String number = this.txtMatNr.getText().toString();
        }
    }
}
