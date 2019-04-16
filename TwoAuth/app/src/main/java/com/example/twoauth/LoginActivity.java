package com.example.twoauth;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */

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

public class LoginActivity extends AppCompatActivity {


    MyDBhandler dBhandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText passbox = (EditText)findViewById(R.id.password);
        //Toast.makeText(getApplicationContext(),"Hello Javatpoint",Toast.LENGTH_SHORT).show();
        Button fab = findViewById(R.id.button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp= getIntent().getStringExtra("otp");
                String user= getIntent().getStringExtra("user");
                String url= getIntent().getStringExtra("url");
                String id= getIntent().getStringExtra("id");
                sendRequestAndCheckResp(url,user, otp, id);
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
    private boolean sendRequestAndCheckResp(final String url, final String user, final String otp, final String ID) {

        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", user);
            jsonBody.put("otp", otp);
        } catch (org.json.JSONException e){
            Log.e("error",e.toString());
        }
        dBhandler = new MyDBhandler(this,null,null,1);


        JsonObjectRequest postRequest = new JsonObjectRequest("http://192.168.1.120/login", jsonBody,new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response) {
                // response
                Log.d("Response", response.toString());
                String oauth = "";


                try {
                    dBhandler.incrementCount();
                    oauth = response.getString("oauth_token");
                    String newurl = url+"?oauth="+oauth;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(newurl));
                    startActivity(i);

                } catch (Exception e){
                    Log.e("error",e.toString());
                }

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
