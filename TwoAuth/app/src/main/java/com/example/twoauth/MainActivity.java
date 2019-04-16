package com.example.twoauth;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    MyDBhandler dBhandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dBhandler = new MyDBhandler(this,null,null,1);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), Main2Activity.class);
                startActivity(i);
            }
        });
        Button deldb = findViewById(R.id.deldb);
        deldb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dBhandler.dropAll();
                finish();
                startActivity(getIntent());
            }
        });

        final LinearLayout linear = (LinearLayout)findViewById(R.id.layout);
        List<Creds> creds = dBhandler.dbtoStr();
        for (int i = 0; i < creds.size(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            Button btn = new Button(this);
            Creds cred = creds.get(i);
            btn.setId(creds.get(i).get_id());
            final int id_ = btn.getId();
            final String url = cred.get_url();
            final String token = cred.get_token();
            final String username = cred.get_user();
            final int counter = cred.get_counter();
            btn.setText("URL: " + cred.get_url() + " Username: "+cred.get_user());
            btn.setBackgroundColor(Color.rgb(70, 80, 90));
            linear.addView(btn, params);
            Button btn1 = ((Button) findViewById(id_));

            btn1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    String text = token+Integer.toString(counter);
                    try {
                        MessageDigest md;
                        md = MessageDigest.getInstance( "SHA-256" );
                        md.update( text.getBytes( StandardCharsets.UTF_8 ) );
                        byte[] digest = md.digest();
                        String otp = String.format( "%064x", new BigInteger( 1, digest ) );
                        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                        intent.putExtra("otp", otp);
                        intent.putExtra("user",username);
                        intent.putExtra("url",url);
                        intent.putExtra("id",id_);
                        startActivity(intent);

                    } catch (Exception e){

                    }

                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
