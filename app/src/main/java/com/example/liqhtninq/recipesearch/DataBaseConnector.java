package com.example.liqhtninq.recipesearch;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Kailash Jayaram on 6/30/2017.
 */


public class DataBaseConnector extends AsyncTask<String, Void, String> {
    Context context;


    DataBaseConnector (Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {

        // connects to local server
        String connect_url = "http://128.54.231.180:8080/recipeData.php";

        // on login
        try {
            String query = params[0];

            // open and set up connection
            URL login = new URL(connect_url);
            HttpURLConnection con = (HttpURLConnection)login.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);

            // send the username and password to the database
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            String post_data = URLEncoder.encode("query","UTF-8")+"="+URLEncoder.encode(query,"UTF-8");
            bw.write(post_data);

            // clean up
            bw.flush();
            bw.close();
            os.close();

            // read in result
            InputStream is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is,"iso-8859-1"));
            String result ="";
            String line ="";
            while((line = br.readLine()) != null) {
                result += line;
            }

            // clean up
            br.close();
            is.close();
            con.disconnect();

            return result;
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }


    @Override
    protected void onPostExecute(String result) {

        if( result.equals("none found")){
            Toast.makeText(context, "No Recipes Found", Toast.LENGTH_LONG).show();
        }
        ArrayList<String[]> list = new ArrayList<>();
        int end = 0;
        int counter = 0;
        String[] temp = new String[5];
        for(int i =0; i<result.length(); i++){
            if(result.charAt(i) =='@'){
                if(counter == 5){
                    list.add(temp);
                    temp = new String[5];
                    counter = 0;
                }
                temp[counter] = result.substring(end,i);
                end = i+1;
                counter++;
            }
        }
        list.add(temp);
        ((SearchOptions)context).setRecipeList(list);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
