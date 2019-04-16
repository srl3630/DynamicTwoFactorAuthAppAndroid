package com.example.twoauth;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class enter_details extends AppCompatActivity {



    private StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText urlbox = (EditText) findViewById(R.id.url);
        final EditText userbox = (EditText) findViewById(R.id.username);
        final EditText passbox = (EditText) findViewById(R.id.password);
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

            }
        });

    }

    private boolean sendRequestAndCheckResp(String url, final String user, final String pass) {
        RequestQueue queue = Volley.newRequestQueue(this);
        final TextView textView = (TextView)findViewById(R.id.textView);


        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        textView.setText("yes");

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(error));
                        textView.setText("no");

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("username", user);
                params.put("password", pass);

                return params;
            }
        };
        queue.add(postRequest);
        textView.setText("done");
        return true;


    }

}
