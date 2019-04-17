package com.example.twoauth;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {


    MyDBhandler dBhandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final EditText urlbox = (EditText)findViewById(R.id.url);
        final EditText userbox = (EditText)findViewById(R.id.username);
        final EditText passbox = (EditText)findViewById(R.id.password);
        //Toast.makeText(getApplicationContext(),"Hello Javatpoint",Toast.LENGTH_SHORT).show();
        Button fab = findViewById(R.id.button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = urlbox.getText().toString();
                String user = userbox.getText().toString();
                String pass = passbox.getText().toString();
                sendRequestAndCheckResp(url,user,pass);
            }
        });
        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
    private boolean sendRequestAndCheckResp(final String url, final String user, final String pass) {

        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", user);
            jsonBody.put("password", pass);
        } catch (org.json.JSONException e){
            Log.e("error",e.toString());
        }
        dBhandler = new MyDBhandler(this,null,null,1);


        JsonObjectRequest postRequest = new JsonObjectRequest("http://192.168.1.120/register", jsonBody,new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        Log.d("Response", response.toString());
                        String token = "";
                        String loginURL = url;
                        try {
                            token = response.getString("salt");
                        } catch (Exception e){
                            Log.e("error",e.toString());
                        }
                        dBhandler.addCreds(new Creds(0,loginURL,user,token,0));
                        Intent i = new Intent(Main2Activity.this, MainActivity.class);
                        startActivity(i);

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(error));
                        final EditText passbox = (EditText)findViewById(R.id.password);
                        passbox.setText("");
                        Toast.makeText(getApplicationContext(),"That didn't work, try again",Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(postRequest);
        return true;
    }
}
