package com.zorbando.harit.zorbandocontests;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by harit on 3/5/2016.
 */
public class ForgetPass extends AppCompatActivity {
    TextView changepassfg;
    EditText changeemail;
    Button changepsbtn;
    String getemail;
    private ActionBar actionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpass);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#dc4000")));

        changepassfg = (TextView) findViewById(R.id.emailtextfg);
        changeemail = (EditText) findViewById(R.id.enterfgmail);

        changepsbtn = (Button) findViewById(R.id.chngepasssend);
        changepsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getemail = changeemail.getText().toString();
                new changepassrequest(getemail).execute();
            }
        });
    }


    private class changepassrequest extends AsyncTask<String, Void, String> {
        String leliemail;
        HttpURLConnection hUrlConnection = null;
        StringBuilder sb = new StringBuilder();
        DataOutputStream printout;
        String result = null;
        AlertDialog.Builder dialog = new AlertDialog.Builder(ForgetPass.this);

        public changepassrequest(String getemail) {
            this.leliemail = getemail;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                // CookieManager cookieManager = new CookieManager();
                //CookieHandler.setDefault(cookieManager);
                URL url = new URL("http://zobrando.com/api/forgotpassword");
                hUrlConnection = (HttpURLConnection) url.openConnection();
                hUrlConnection.setRequestMethod("POST");

                hUrlConnection.setDoOutput(true);
                hUrlConnection.setDoInput(true);
                JSONObject jsonparams = new JSONObject();
                jsonparams.put("email", leliemail);

                OutputStreamWriter out = new OutputStreamWriter(hUrlConnection.getOutputStream());
                out.write(jsonparams.toString());
                out.close();
                int HttpResult = hUrlConnection.getResponseCode();
                if (HttpResult == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            hUrlConnection.getInputStream(), "utf-8"));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();

                   result = sb.toString();

                }
                hUrlConnection.connect();
                printout = new DataOutputStream(hUrlConnection.getOutputStream());
                printout.writeBytes(jsonparams.toString());
                printout.flush();
                printout.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (hUrlConnection != null)
                    hUrlConnection.disconnect();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            String response = result;

            try {
                JSONObject jsonObject = new JSONObject(response);
                String papi = jsonObject.getString("result");
                if (papi.equals("Success")) {


                    dialog.setTitle("Successfully Sent!");
                    dialog.setMessage(getString(R.string.check_email));
                    dialog.setNeutralButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            // TODO Auto-generated method stub
                            android.os.Process.killProcess(android.os.Process.myPid());
                            Intent myIntent = new Intent(ForgetPass.this, MainActivity.class);
                            ForgetPass.this.startActivity(myIntent);

                            //get gps
                        }
                    });
                    dialog.show();
                }
                else if(papi.equalsIgnoreCase("Success")){

                    dialog.setTitle("Error!");
                    dialog.setMessage(getString(R.string.errorsent));
                    dialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            // TODO Auto-generated method stub
                            changeemail.setText("");
                            dialog.setCancelable(true);
                        }


            });
                    dialog.show();
            }
        }catch (JSONException e) {
                e.printStackTrace();
    }
}
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
