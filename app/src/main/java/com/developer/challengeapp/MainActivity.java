package com.developer.challengeapp;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.challengeapp.adapter.challengeadapter;
import com.developer.challengeapp.model.ChallengeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements com.developer.challengeapp.adapter.challengeadapter.OnChallengitemClickListener {


    RecyclerView recyclerView;
    private ArrayList<ChallengeModel> challengelist;
    private challengeadapter challengeadapter;

    public String loadChallengeJson() {
        String json = null;
        try {
            InputStream is = MainActivity.this.getAssets().open("challenge.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
    }

    private void initview() {

        //recyclerview init
        challengelist = new ArrayList<>();
        recyclerView = findViewById(R.id.challenge_recycler);
        if(isTablet(getApplicationContext())) recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));else   recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
        challengeadapter = new challengeadapter(challengelist, MainActivity.this);
        challengeadapter.setOnChallengitemClickListener(this);
        recyclerView.setAdapter(challengeadapter);
        getdata();
    }

    private void getdata() {
        try {
            JSONArray challengearray = new JSONArray(loadChallengeJson());
            challengelist = new ArrayList<>();
            for (int i = 0; i < challengearray.length(); i++) {
                JSONObject jobj = challengearray.getJSONObject(i);
                ChallengeModel data = new ChallengeModel(jobj.getString("avatar"), jobj.getString("name"));
                challengelist.add(data);
                Log.d("Details-->", jobj.getString("name"));
            }
            challengeadapter.updateData(challengelist);
            challengeadapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(View view, ChallengeModel viewModel) {
        Toast.makeText(this, "view model"+viewModel.getName(), Toast.LENGTH_SHORT).show();
    }
}