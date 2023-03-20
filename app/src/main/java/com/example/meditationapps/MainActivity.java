package com.example.meditationapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.meditationapps.adapters.AdapterList;
import com.example.meditationapps.adapters.AdapterFeel;
import com.example.meditationapps.clasess.Chuvstvo;
import com.example.meditationapps.clasess.citata;
import com.example.meditationapps.clasess.User;
import com.example.meditationapps.transforms.CircleTransform;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public RecyclerView recycler;
    ListView listview;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageview = findViewById(R.id.image);
        recycler = findViewById(R.id.recyclerview);
        listview = findViewById(R.id.citatalist);
        listview.setDivider(new ColorDrawable(ContextCompat.getColor(this, R.color.background)));
        listview.setDividerHeight(50);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // Установка ориентации горизонтальной
        recycler.setLayoutManager(layoutManager);
        String imageUrl = User.avatar;
        Picasso.get()
                .load(imageUrl)
                .transform(new CircleTransform())
                .into(imageview);
        TextView w_name = findViewById(R.id.firstname_user);
        w_name.setText("С возвращением, " + User.nickName);

        getcitata taskc = new getcitata(listview, MainActivity.this);
        taskc.execute();
        getFeelings taskh = new getFeelings(recycler, MainActivity.this);
        taskh.execute();
    }
    public void GoProfile(View v){
        Intent profile = new Intent(MainActivity.this,profile.class);
        startActivity(profile);
    }

    class getcitata extends AsyncTask<Void, Void, List<citata>>{
        private ListView listview;
        private Context context;

        public getcitata(ListView listview, Context context) {
            this.listview = listview;
            this.context = context;
        }


        @Override
        protected List<citata> doInBackground(Void... voids) {
            List<citata> listcitata = new ArrayList<citata>();
            try {
                // создаем соединение
                URL url = new URL("http://mskko2021.mad.hakta.pro/api/quotes");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    StringBuilder sb = new StringBuilder();
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    JSONObject response = new JSONObject(sb.toString());
                    JSONArray json_array = new JSONArray(response.getString("data"));
                    for (int i = 0; i < json_array.length(); i++) {
                        JSONObject json_object = json_array.getJSONObject(i);
                        citata citat = new citata();
                        citat.image = json_object.getString("image");
                        citat.title = json_object.getString("title");
                        citat.description = json_object.getString("description");
                        listcitata.add(citat);
                    }
                    AdapterList adapter = new AdapterList(MainActivity.this,listcitata);
                    listview.setAdapter(adapter);

                } else {
                    Log.e("TAG", "Error: " + responseCode);
                }

                conn.disconnect();
            }
            catch (Exception e){
                Log.d("e",e.toString());
            }
            return listcitata;
        }
        @Override
        protected void onPostExecute(List<citata> citataList) {
            super.onPostExecute(citataList);
            //Заполняем список
            AdapterList adapter = new AdapterList(MainActivity.this,citataList);
            listview.setAdapter(adapter);
        }
    }

    class getFeelings extends AsyncTask<Void, Void, List<Chuvstvo>>{
        private RecyclerView recycler;
        private Context cnt;

        public getFeelings(RecyclerView recycler, Context context) {
            this.recycler = recycler;
            this.cnt = context;
        }

        @Override
        protected List<Chuvstvo> doInBackground(Void... voids) {
            List<Chuvstvo> feelingList = new ArrayList<Chuvstvo>();
            try {
                // создаем соединение
                URL url = new URL("http://mskko2021.mad.hakta.pro/api/feelings");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    StringBuilder stringBuilder = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    bufferedReader.close();
                    JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                    JSONArray json_array = new JSONArray(jsonObject.getString("data"));
                    for (int i = 0; i < json_array.length(); i++) {
                        JSONObject json_object = json_array.getJSONObject(i);
                        Chuvstvo chuvstvo = new Chuvstvo();
                        chuvstvo.image = json_object.getString("image");
                        chuvstvo.title = json_object.getString("title");
                        chuvstvo.position = json_object.getInt("position");
                        feelingList.add(chuvstvo);
                    }
                    AdapterFeel adapter = new AdapterFeel(feelingList);
                    recycler.setAdapter(adapter);

                } else {
                    Log.e("TAG", "Error: " + responseCode);
                }
                conn.disconnect();
            }
            catch (Exception e){
                Log.d("e",e.toString());
            }
            return feelingList;
        }
        @Override
        protected void onPostExecute(List<Chuvstvo> chuvstvolist) {
            super.onPostExecute(chuvstvolist);
            //Заполняем список
            AdapterFeel adapterFeel = new AdapterFeel(chuvstvolist);
            recycler.setAdapter(adapterFeel);
        }
    }
}