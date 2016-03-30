package com.zorbando.harit.zorbandocontests;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.util.Progress;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by harit on 2/23/2016.
 */
public class Entry extends AppCompatActivity implements AdapterView.OnClickListener {
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout drawerLayout, drawerLayout1;
    private ListView mDrawerList;
    ActionBarDrawerToggle mDrawerToggle;
    private CharSequence drawerTitles;
    private CharSequence mTitles;
    ExpandableListView expandableListView;
    private List<Contestsinfo> contestslist;
    private NavigationView navigationView, navigationRight;
    private RecyclerView recyclerView;
    private ContestAdapter contestAdapter;
    private RightNavigation rightNavigation;
    Toolbar toolBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);
        Bundle bundle = getIntent().getExtras();
        String user = bundle.getString("username");
        final int userid = bundle.getInt("userid");
        toolBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolBar);
        recyclerView = (RecyclerView) findViewById(R.id.contest_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationRight = (NavigationView) findViewById(R.id.navigation_vright);
        new contests().execute();
        mTitles = drawerTitles = getTitle();
        View header = navigationView.getHeaderView(0);
        TextView text = (TextView) header.findViewById(R.id.username);
        text.setText(user);

        //ActionBar actionBar = getSupportActionBar();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                drawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.changepass:
                        Intent intent = new Intent(Entry.this, ChangePassword.class);
                        intent.putExtra("useridhai", userid);
                        Entry.this.startActivity(intent);
                        break;

                    case R.id.logout:
                        Intent intent1 = new Intent(Entry.this, MainActivity.class);
                        Entry.this.startActivity(intent1);
                       android.os.Process.killProcess(android.os.Process.myPid());
                        break;
                }
                return true;
            }
        });

    /*    actionBar.setDisplayOptions(actionBar.getDisplayOptions()
              | ActionBar.DISPLAY_SHOW_CUSTOM);
        ImageView imageView = new ImageView(actionBar.getThemedContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setImageResource(R.drawable.filterkrle);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.END
                | Gravity.CENTER_VERTICAL);
        layoutParams.rightMargin = 10;
        imageView.setLayoutParams(layoutParams);
        actionBar.setCustomView(imageView);

*/

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerListener(mDrawerToggle);
        //  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#dc4000")));
        try {
            mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolBar, R.string.drawer_open, R.string.drawer_close) {
                /**
                 * Called when a drawer has settled in a completely closed state.
                 */
                public void onDrawerClosed(View view) {
                    super.onDrawerClosed(view);
                    //getActionBar().setTitle(mTitles);
                }

                /**
                 * Called when a drawer has settled in a completely open state.
                 */
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                    // getActionBar().setTitle(drawerTitles);
                }
            };
            mDrawerToggle.syncState();
        } catch (NullPointerException e) {

        }


        expandableListView = (ExpandableListView) findViewById(R.id.expand);
        ArrayList<String> groupname = new ArrayList<>();
        groupname.add("Reward Type");
        groupname.add("Talent Type");
        groupname.add("Brand Type");
        groupname.add("Target Group");
        groupname.add("Age Brackets");
        groupname.add("Participants");
        ArrayList<ArrayList<ChildList>> childs = new ArrayList<ArrayList<ChildList>>();
        ArrayList<ChildList> reward = new ArrayList<ChildList>();
        reward.add(new ChildList("Cash Reward", false));
        reward.add(new ChildList("In-Kind Reward", false));
        reward.add(new ChildList("Products or Services", false));
        reward.add(new ChildList("Travel Vouchers", false));
        reward.add(new ChildList("Participation Certificate", false));
        reward.add(new ChildList("Invitation to Event", false));
        reward.add(new ChildList("Food Vouchers", false));
        reward.add(new ChildList("Gift Vouchers", false));
        reward.add(new ChildList("Movie Tickets", false));
        childs.add(reward);
        ArrayList<ChildList> talent = new ArrayList<ChildList>();
        talent.add(new ChildList("Arts", false));
        talent.add(new ChildList("Music", false));
        talent.add(new ChildList("Dancing", false));
        talent.add(new ChildList("Sports", false));
        talent.add(new ChildList("Creative Writing", false));
        talent.add(new ChildList("Photography", false));
        talent.add(new ChildList("Adventure Skills", false));
        talent.add(new ChildList("Bollywood Knowledge", false));
        talent.add(new ChildList("General Knowledge", false));
        talent.add(new ChildList("Social Media Skills", false));
        talent.add(new ChildList("Online Game", false));
        talent.add(new ChildList("Cooking Skills", false));
        talent.add(new ChildList("Just Participation", false));
        childs.add(talent);
        ArrayList<ChildList> brand = new ArrayList<ChildList>();
        brand.add(new ChildList("Arts and Culture", false));
        brand.add(new ChildList("Commercial and Industrial", false));
        brand.add(new ChildList("Education Learning", false));
        brand.add(new ChildList("Fashion and Beauty", false));
        brand.add(new ChildList("Financial Services", false));
        brand.add(new ChildList("Food Beverages Hospitality", false));
        brand.add(new ChildList("Games and Sports", false));
        brand.add(new ChildList("Government Services", false));
        brand.add(new ChildList("Health Services", false));
        brand.add(new ChildList("Media Entertainment Advertising", false));
        brand.add(new ChildList("News Magazine Blog", false));
        brand.add(new ChildList("Social Non Profit", false));
        brand.add(new ChildList("Software and Technology", false));
        brand.add(new ChildList("Spiritual Religious", false));
        brand.add(new ChildList("Telecommunication", false));
        brand.add(new ChildList("Travel and Adventure", false));
        brand.add(new ChildList("Website Internet Services", false));
        childs.add(brand);
        ArrayList<ChildList> target = new ArrayList<ChildList>();
        target.add(new ChildList("Male", false));
        target.add(new ChildList("Female", false));
        target.add(new ChildList("Transgender", false));
        target.add(new ChildList("Animals", false));
        target.add(new ChildList("Humans", false));
        childs.add(target);
        ArrayList<ChildList> age = new ArrayList<ChildList>();
        age.add(new ChildList("All ages", false));
        age.add(new ChildList("Baby Infants", false));
        age.add(new ChildList("Childern", false));
        age.add(new ChildList("Teenagers", false));
        age.add(new ChildList("Youngsters", false));
        age.add(new ChildList("Adults", false));
        age.add(new ChildList("Senior Citizens", false));
        childs.add(age);
        ArrayList<ChildList> patc = new ArrayList<ChildList>();
        patc.add(new ChildList("Individual", false));
        patc.add(new ChildList("Group", false));
        patc.add(new ChildList("Organisation", false));
        childs.add(patc);
        rightNavigation = new RightNavigation(this, groupname, childs);
        expandableListView.setAdapter(rightNavigation);


    }


    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        getActionBar().setTitle(mTitles);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        // mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }


    private class contests extends AsyncTask<String, Void, String> {
        HttpURLConnection hUrlConnection = null;
        StringBuilder sb = new StringBuilder();
        DataOutputStream printout;
        String result = null;
        ProgressDialog progressDialog = new ProgressDialog(Entry.this);

        @Override
        protected void onPreExecute() {
            this.progressDialog.setMessage("Please wait");
            this.progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                // CookieManager cookieManager = new CookieManager();
                //CookieHandler.setDefault(cookieManager);
                URL url = new URL("http://zobrando.com/api/getContestList");
                hUrlConnection = (HttpURLConnection) url.openConnection();
                hUrlConnection.setRequestMethod("POST");

                hUrlConnection.setDoOutput(true);
                hUrlConnection.setDoInput(true);
                JSONObject jsonparams = new JSONObject();
                jsonparams.put("limitCountMin", "15");
                jsonparams.put("totalResults", "50");
                jsonparams.put("sort", "");
                jsonparams.put("days", "");
                jsonparams.put("posted_search", "");
                jsonparams.put("rewardSearch", "");
                jsonparams.put("categories", "");
                jsonparams.put("expiry", "");
                jsonparams.put("contestWorth", "");
                jsonparams.put("MediaType", "");
                jsonparams.put("ParticipationType", "");
                jsonparams.put("AgeType", "");
                jsonparams.put("TargetType", "");
                jsonparams.put("TalentType", "");
                jsonparams.put("RewardType", "");


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
                    add(result);

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
            recyclerView.setAdapter(contestAdapter);
            if (progressDialog.isShowing()) {

                progressDialog.dismiss();


            }
        }
    }

public void add(String resp){
    String result=resp ;

    try {
        JSONObject jsonObject = new JSONObject(result);
        contestslist = new ArrayList<>();
        String papi = jsonObject.getString("result");
        if (papi.equals("Success")) {
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i <= jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String title = jsonObject1.getString("title");
                String img_link = jsonObject1.getString("img_link");

                String win_string = jsonObject1.getString("winString");
                String description = jsonObject1.getString("seo_shortDesc");
                String dateadded = jsonObject1.getString("date_added");
                String date_expiry = jsonObject1.getString("date_expiry");
                String name = jsonObject1.getString("name");
                String link = jsonObject1.getString("source");


                Contestsinfo contestsinfo = new Contestsinfo();
                contestsinfo.setTitle(title);
                contestsinfo.setName(name);
                contestsinfo.setDate_added(dateadded);
                contestsinfo.setDate_expiry(date_expiry);
                contestsinfo.setWin_string(win_string);
                contestsinfo.setSeo_description(description);
                contestsinfo.setParticipate_src(link);
                contestsinfo.setSrc(img_link);
                contestslist.add(contestsinfo);
                contestAdapter = new ContestAdapter(Entry.this, contestslist);


            }

        } else {


        }
    } catch (JSONException e) {
        e.printStackTrace();
    }
}

    @Override
        public void onBackPressed() {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

