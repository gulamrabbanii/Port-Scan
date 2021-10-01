package com.example.portscan;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    EditText editIpAddress;
    Button scanButton;
    TextView scanResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editIpAddress = findViewById(R.id.editIpAddress);
        scanResult = findViewById(R.id.scanResult);

        findViewById(R.id.scanButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ipAddress = editIpAddress.getText().toString().trim();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                                doPortScan(ipAddress);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    private void doPortScan(String ipAddress) throws Exception {
        String newMessage;
        if (ipAddress.isEmpty()) {
            newMessage = "Please Enter IP Address / Hostname";
            scanResult.setText(newMessage);
            return;
        }
        String txt = "Starting Port Scanner...\nScanning " + ipAddress + "\n\n";
        scanResult.setText(txt);
        Map<Integer,String> map=new HashMap<Integer,String>();
        map.put(21,"FTP");
        map.put(22,"SSH");
        map.put(23,"Telnet");
        map.put(25,"SMTP");
        map.put(53,"DNS");
        map.put(68,"DHCP");
        map.put(80,"HTTP");
        map.put(162,"SNMP");
        map.put(443,"SSL");
        map.put(444,"");


            for(Integer portNumber : map.keySet()){
            try {
                if (portNumber == 444){
                    String done = "\nFinished!\n";
                    scanResult.append(done);
                }
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(ipAddress, portNumber), 50);
                socket.close();
                String service = map.get(portNumber);
                newMessage = "Discovered open port " + portNumber + "\\" + service + "\n";
                scanResult.append(newMessage);
            } catch (Exception e){
                }
        }

    }

}

