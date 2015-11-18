package com.example.undine.project_ooad;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewFeed extends Fragment {


    public NewFeed() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_new_feed, container, false);
        mListView = (ListView) rootView.findViewById(R.id.listView);
        ArrayList<Event> listContact ;
      //  ListView lv = (ListView)rootView.findViewById(R.id.listView);
        //lv.setAdapter(new CustomAdapter(getActivity(), listContact));
       new SimpleTask().execute(URL + "getTopicAll");


        return rootView;

    }

    public static final String URL = "http://203.151.92.175:8080/";
    private ListView mListView;
    private CustomAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





       // new SimpleTask().execute(URL + "getTopicAll");
    }

    private class SimpleTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // Create Show ProgressBar
        }

        protected String doInBackground(String... urls) {
            String result = "";
            IOException ee;
            Exception ex;
            try {

                HttpGet httpGet = new HttpGet(urls[0]);
                HttpClient client = new DefaultHttpClient();

                HttpResponse response = client.execute(httpGet);

                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    InputStream inputStream = response.getEntity().getContent();
                    BufferedReader reader = new BufferedReader
                            (new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result += line;
                    }
                }

            } catch (ClientProtocolException e) {

            } catch (IOException e) {
                ee=e;
            }catch (Exception e){
                ex=e;
            }
            return result;
        }

        protected void onPostExecute(String jsonString) {
            // Dismiss ProgressBar
            showData(jsonString);
        }
    }

    private void showData(String jsonString) {
        Gson gson = new Gson();
        //Blog blog = gson.fromJson(jsonString, Blog.class);
        //Map map = gson.fromJson(jsonString, Map.class);
        JsonParser parser = new JsonParser();
        JsonArray jArray = parser.parse(jsonString).getAsJsonArray();
        ArrayList<Event> events = new ArrayList<Event>();

        for (JsonElement obj : jArray){
            Event event = gson.fromJson(obj,Event.class);
            events.add(event);
        }

        //StringBuilder builder = new StringBuilder();
        //builder.setLength(0);
        //List<Post> posts = blog.getPosts();
        //List<Event> events = map.getEvents();

        /*for (Event event : events) {
            builder.append(event.getDescription());
            builder.append("\n");
            builder.append(event.getLocation());
            builder.append("\n\n");
        }*/

        //mAdapter = new CustomAdapter(this, posts);
        //mListView.setAdapter(mAdapter);
        //Toast.makeText(this, jsonString, Toast.LENGTH_LONG).show();
        //Toast.makeText(this, builder.toString(), Toast.LENGTH_LONG).show();
        /*
        try {
            JSONArray array = new JSONArray(jsonString);
            JSONObject title = array.getJSONObject(0);

            String ss= title.getString("nameTitle");
            Log.v("AAAAAAAAAA", ss);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        mAdapter = new CustomAdapter(getActivity(), events);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object listItem = mListView.getItemAtPosition(position);
                System.out.println(listItem);
                Intent intent = new Intent(getContext(), DetailActivity.class);
                Event en = (Event) listItem;
                intent.putExtra("EVENT_TITLE", en.getNameTitle());
                intent.putExtra("EVENT_DATE", en.getStartDate());
                intent.putExtra("EVENT_LOCATE", en.getLocation());
                intent.putExtra("EVENT_DES", en.getDescription());
                intent.putExtra("EVENT_RATE", en.getRate());
                System.out.println(en.getDescription());
                startActivity(intent);
            }
        });
    }

}
