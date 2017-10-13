package com.example.lloc.tutoajout;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lLoïc on 29/09/2017.
 */

public class Login extends AppCompatActivity {

    public final String LOGIN_URL="http://ecologic-lyon1.fr/test/loginloic.php";
    public final String SHARED_PREF_NAME="";
    private EditText mail,mdp;
    private Button valider;
    private boolean loggedin =false;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mail=(EditText) findViewById(R.id.email);
        mdp=(EditText) findViewById(R.id.motdepasse);
        valider=(Button) findViewById(R.id.button2);
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        final String email= mail.getText().toString();
        final String motdepasse=mdp.getText().toString();
        StringRequest stringRequest= new StringRequest(Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equalsIgnoreCase("success")) {
                    SharedPreferences sharedPreferences = Login.this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("loggedin", true);
                    editor.putBoolean("email", true);
                    editor.commit();
                    Toast.makeText(Login.this, "Connecté !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Login.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
             protected Map<String,String>getParams() throws AuthFailureError{
                 Map<String,String> prams =new HashMap<>();
                 prams.put("email",email);
                 prams.put("password",motdepasse);
                 return prams;
             }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences sharedPreferences= getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        loggedin=sharedPreferences.getBoolean("loggedin",false);
        if(loggedin){

        }
    }

}
