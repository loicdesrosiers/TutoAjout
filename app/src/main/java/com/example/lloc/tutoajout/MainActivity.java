package com.example.lloc.tutoajout;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText Eusername,Epassword,Eemail,Epseudo;
    Button signup;
    private static final String REGISTER_URL="http://ecologic-lyon1.fr/test/Ajout_bd.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Eusername =(EditText) findViewById(R.id.username);
        Epassword =(EditText) findViewById(R.id.password);
        Eemail =(EditText) findViewById(R.id.email);
        Epseudo =(EditText) findViewById(R.id.pseudo);
        signup =(Button) findViewById(R.id.button);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String name=Eusername.getText().toString().trim();
        String email=Eemail.getText().toString().trim();
        String password=Epassword.getText().toString().trim();
        String pseudo=Epseudo.getText().toString().trim();
        register(name,email,password,pseudo);
    }

    private void register(String name, String email, String password, String pseudo) {
        String urlsuffix="?username="+name+"&password="+password+"&email="+email+"&pseudo="+pseudo;
        class RegisterUser extends AsyncTask<String,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading=ProgressDialog.show(MainActivity.this,"Patientez...",null,true,true);
            }


            @Override
            protected String doInBackground(String... params) {
                String s =params[0];

                BufferedReader bufferReader=null;
                try{

                    URL url = new URL(REGISTER_URL+s);
                    HttpURLConnection con =(HttpURLConnection) url.openConnection();
                    bufferReader= new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String result;
                    result=bufferReader.readLine();

                    return result;

                }catch (Exception e ){
                    Toast.makeText(getApplicationContext(),"Erreur ",Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        }
        RegisterUser ur =new RegisterUser();
        ur.execute(urlsuffix);
    }
}
