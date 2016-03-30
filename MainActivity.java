package com.zorbando.harit.zorbandocontests;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText login, password;
    Button loginButton, loginfbutton, signup;
    TextView or, forget;
    ImageView logo;
    private ProgressDialog mProgressDialog;
    private ActionBar actionBar;
    private static final String TAG = "SignInActivity";
    private TextView mStatusTextView;
    private static final int RC_SIGN_IN = 9001;
    private CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;
    String[] cred = new String[4];
    String firstpart;
    String secondpart;
    double longitude, latitude;
    private android.support.v4.app.FragmentTransaction fragmentTransaction;
    private android.support.v4.app.FragmentManager fragmentManager;
    private Toolbar toolBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
       // toolBar = (Toolbar) findViewById(R.id.app_bar);
       // setSupportActionBar(toolBar);
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        new SocialLogin().execute();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

        mStatusTextView = (TextView) findViewById(R.id.username);
        login = (EditText) findViewById(R.id.login);
        loginfbutton = (Button) findViewById(R.id.smlogin);
        password = (EditText) findViewById(R.id.password);
        or = (TextView) findViewById(R.id.or);
        signup = (Button) findViewById(R.id.signup);


        fragmentManager = getSupportFragmentManager();
        loginfbutton.setOnClickListener(this);

        logo = (ImageView) findViewById(R.id.logo);
        forget = (TextView) findViewById(R.id.forget);
        forget.setOnClickListener(this);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
        signup.setOnClickListener(this);
        loginfbutton.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setScopes(gso.getScopeArray());
        signInButton.setColorScheme(SignInButton.COLOR_AUTO);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
       // actionBar = getSupportActionBar();
       // actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#dc4000")));
        //mGoogleApiClient = new GoogleApiClient.Builder(this)
        //   .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
        // .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
        //.build();

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        boolean network_enabled = false;


            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;

       /* Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location == null)
        {
           if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
            {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }
        if (location!=null)
        {
            longitude = location.getLongitude();
            secondpart = String.valueOf(longitude);
            latitude = location.getLatitude();
            firstpart = String.valueOf(latitude);
        }
*/


    }



    public boolean isNetworkAvailable(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private boolean checkLocation(){
        LocationManager locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        boolean network_enabled = false;

        //Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!network_enabled){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Location");
            dialog.setMessage(this.getResources().getString(R.string.location_network_not_enabled));
            dialog.setPositiveButton(this.getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    MainActivity.this.startActivity(myIntent);

                    //get gps
                }
            });
            dialog.setCancelable(false);
            dialog.setNegativeButton(this.getString(R.string.cancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    //Intent i = new Intent(MainActivity.this,PleaseEnabled.class);
                    //  MainActivity.this.startActivity(i);

                }
            });
            dialog.show();

        }
        return network_enabled;
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



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup:
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                this.startActivity(intent);
                break;
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.smlogin:
                if (isNetworkAvailable()) {
                if (checkLocation()) {


                    cred[0] = login.getText().toString();
                    cred[1] = password.getText().toString();
                    cred[2]= secondpart;
                    cred[3]=firstpart;
                   // Toast.makeText(this,secondpart+firstpart,Toast.LENGTH_SHORT).show();
                    new mytask(cred).execute();

                    Toast.makeText(this, "loading...", Toast.LENGTH_LONG).show();
                    }
                }
               // Toast.makeText(this,secondpart,Toast.LENGTH_SHORT).show();

                break;
            case  R.id.forget:
               Intent intent1 = new Intent(this,ForgetPass.class);
                this.startActivity(intent1);
                break;

        }
    }

  /*  @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }
*/

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            //findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
            mStatusTextView.setText(R.string.signed_out);

            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            // findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }


    }


  /*  @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }
*/


    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }



    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }


    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }


    /*
    simple Login Asysntask Starts Here

    */


    private class mytask extends AsyncTask<String, Void, String> {
        String usernm, passwd,longgi,lati;
        HttpURLConnection hUrlConnection = null;
        StringBuilder sb = new StringBuilder();
        DataOutputStream printout;
        String result = null;


        public mytask(String[] user) {
            this.usernm = user[0];
            this.passwd = user[1];
            this.longgi=user[2];
            this.lati = user[3];
            //Toast.makeText(MainActivity.this,usernm+passwd,Toast.LENGTH_LONG).show();
        }


        @Override
        protected String doInBackground(String... params) {
            try {
                // CookieManager cookieManager = new CookieManager();
                //CookieHandler.setDefault(cookieManager);
                URL url = new URL("http://zobrando.com/api/loginUser");
                hUrlConnection = (HttpURLConnection) url.openConnection();
                hUrlConnection.setRequestMethod("POST");
                hUrlConnection.setDoOutput(true);
                hUrlConnection.setDoInput(true);
                JSONObject jsonparams = new JSONObject();
                jsonparams.put("username", usernm);
                jsonparams.put("password", passwd);
                jsonparams.put("appPlatform", "andotttid");
                jsonparams.put("appPlatformID", "72383728763");
                jsonparams.put("latitude", "28.44");
                jsonparams.put("longitude","77.90");
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
                Log.d("result" ,result);
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
            //Log.d("RESULT", result);

            try {
                JSONObject jsonObject = new JSONObject(response);
                String papi = jsonObject.getString("result");
                JSONObject user= jsonObject.getJSONObject("session");
                int usertype = user.getInt("USERTYPE");
                if (papi.equals("Success")&&usertype==0) {


                //Map<String, List<String>> headerFields = hUrlConnection.getHeaderFields();
                //List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);

               /* if (cookiesHeader != null) {
                    for (String cookie : cookiesHeader) {
                        msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
                    }
                }*/
                    JSONObject name = jsonObject.getJSONObject("session");
                    String username = name.getString("USERNAME");
                    int userid = name.getInt("USERID");

                SharedPreferences sharedPreferences = getSharedPreferences("credentials", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("login", usernm);
                editor.putString("password", passwd);
                editor.commit();
               // Toast.makeText(MainActivity.this, "ho gya save ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Entry.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("username",username);
                    bundle.putInt("userid", userid);

                    intent.putExtras(bundle);
                MainActivity.this.startActivity(intent);
                    login.setText("");
                    password.setText("");
                    android.os.Process.killProcess(android.os.Process.myPid());
            } else  if(papi.equalsIgnoreCase("Success")){

                    Log.d("dekh chala :", papi);
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Are you sure you want to exit?");
                    builder.setCancelable(true);
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alert= builder.create();
                    alert.show();

            }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

/*
simple  Login Asysntask Ends Here

*/



/*
Social USer Login Asysntask Starts Here

*/

    private class SocialLogin extends AsyncTask<String,Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return null;
        }
    }


/*
Social USer Login Asysntask Ends Here

*/




    @Override
    public void onBackPressed() {

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?");
        builder.setCancelable(true);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                android.os.Process.killProcess(android.os.Process.myPid());

            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }

}


