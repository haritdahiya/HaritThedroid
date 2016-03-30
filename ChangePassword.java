package com.zorbando.harit.zorbandocontests;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
 * Created by harit on 3/9/2016.
 */
public class ChangePassword extends AppCompatActivity implements View.OnClickListener {
    private TextView provideinfo;
    private EditText oldpass, newpass, confirmnpass;
    private Button changepass;
    String [] newset = new String[3];
    int userid;
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword);
        Bundle bundle = getIntent().getExtras();
         userid = bundle.getInt("useridhai");
       // actionBar = getSupportActionBar();
      //  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#dc4000")));


        provideinfo = (TextView) findViewById(R.id.provide);
        oldpass = (EditText) findViewById(R.id.oldpass);
        newpass = (EditText) findViewById(R.id.newpass);
        confirmnpass = (EditText) findViewById(R.id.conewpas);
        changepass = (Button) findViewById(R.id.changep);
        changepass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String old = oldpass.getText().toString();
        String newp = newpass.getText().toString();
        String conp = confirmnpass.getText().toString();
    String useridle = String.valueOf(userid);
        if (conp.equals(newp))
        {
            newset[0]=old;
            newset[1]=newp;
            newset[2]=useridle;

            new changepassrequest(newset).execute();
        }
        else {
            Toast.makeText(this,"Password is not same!",Toast.LENGTH_SHORT).show();
        }

    }

    private class changepassrequest extends AsyncTask<String,Void,String> {
        String old,newp,uid;

        HttpURLConnection hUrlConnection = null;
        StringBuilder sb = new StringBuilder();
        DataOutputStream printout;
        String result = null;
        AlertDialog.Builder dialog = new AlertDialog.Builder(ChangePassword.this);
        public changepassrequest(String[] newset) {
            this.old = newset[0];
            this.newp = newset[1];
            this.uid = newset[2];

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                // CookieManager cookieManager = new CookieManager();
                //CookieHandler.setDefault(cookieManager);
                URL url = new URL("http://zobrando.com/api/changepassword");
                hUrlConnection = (HttpURLConnection) url.openConnection();
                hUrlConnection.setRequestMethod("POST");

                hUrlConnection.setDoOutput(true);
                hUrlConnection.setDoInput(true);
                JSONObject jsonparams = new JSONObject();
                jsonparams.put("oldpass", old);
                jsonparams.put("newpass", newp);
                jsonparams.put("userid", uid);


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
            Log.d("LUNd :", result);
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            String response = result;

            try {
                JSONObject jsonObject = new JSONObject(response);
                String papi = jsonObject.getString("result");
                String mess = jsonObject.getString("message");

                if (papi.equals("success")) {


                    dialog.setTitle("Successfully Changed!");
                    dialog.setMessage(mess);
                    dialog.setNeutralButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            // TODO Auto-generated method stub

                            android.os.Process.killProcess(android.os.Process.myPid());

                            //get gps
                        }
                    });
                    dialog.show();
                }
              else if(papi.equalsIgnoreCase("success")){

                    dialog.setTitle("Error!");
                    dialog.setMessage(getString(R.string.aches_dal));
                    dialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            // TODO Auto-generated method stub
                            oldpass.setText("");
                            newpass.setText("");
                            confirmnpass.setText("");
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
}
