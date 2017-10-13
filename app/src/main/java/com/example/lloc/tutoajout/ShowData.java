package com.example.lloc.tutoajout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;


/**
 * Created by lLoïc on 02/10/2017.
 */

public class ShowData extends AppCompatActivity {

    String JSON_String;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showdata);
    }

    public void getJSON(View view){
new BackgroundTask().execute();
    }
    class BackgroundTask extends AsyncTask<Void,Void,String>{

        String json_url;
        @Override
        protected void onPreExecute(){
            json_url="http://ecologic-lyon1.fr/test/getUsersData.php";

        }
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url =new URL(json_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                InputStream inputStream =httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder=new StringBuilder();

                while((JSON_String=bufferedReader.readLine())!=null){

                stringBuilder.append(JSON_String);


                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String result){
            TextView textView = (TextView) findViewById(R.id.textView);
            textView.setText(result);
            String[] lignes = result.split("NEWLINE");

            for (String ligne:lignes){
                String s[]=ligne.split("_");
                for (String s1:s) Toast.makeText(ShowData.this,s1,Toast.LENGTH_SHORT).show();

                Toast.makeText(ShowData.this,"Nouvelle Ligne",Toast.LENGTH_SHORT).show();

            }
        }
    }
}
