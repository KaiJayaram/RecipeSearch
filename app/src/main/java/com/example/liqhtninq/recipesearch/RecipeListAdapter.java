package com.example.liqhtninq.recipesearch;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by liqhtninq on 10/2/2017.
 */

public class RecipeListAdapter extends ArrayAdapter<String> {

    // Variable declarations
    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> data = new ArrayList<>();
    Context ctx;

    // initializes classs vars in constructor
    public RecipeListAdapter(Context context, int textViewResourceId, ArrayList<String> objects, ArrayList<String> data) {
        super(context, textViewResourceId, objects);
        list = objects;
        this.data = data;
        ctx = context;
    }
    // returns the number of objects in list
    @Override
    public int getCount() {
        return super.getCount();
    }

    /**
     * sets up the view for each list item
     * @param position: position in list
     * @param convertView: this items view
     * @param parent: parent view
     * @return: this items view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.recipe_list_child_view, null);

        String name = list.get(position);
        String url = "";
        for(int i=0; i<name.length(); i++){
            if(name.charAt(i)=='@'){
                url = name.substring(i+1,name.length());
                name = name.substring(0,i);
            }
        }
        Button select = (Button)convertView.findViewById(R.id.recipeSelect);
        TextView recipeName = (TextView)convertView.findViewById(R.id.recipeName);
        final String inName = name;
        final String inURL = url;
        recipeName.setText(name);
        final int pos = position;
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(), ViewRecipe.class);
                String info = data.get(pos);
                int end = 0;
                ArrayList<String> infoList = new ArrayList<String>();
                for(int i = 0; i < info.length(); i++){
                    if(info.charAt(i) == '@'){
                        infoList.add(info.substring(end,i));
                        end = i+1;
                    }
                }
                in.putExtra("steps",infoList.get(0));
                in.putExtra("nutrition",infoList.get(1));
                in.putExtra("ingredients",infoList.get(2));
                in.putExtra("imgURL",inURL);
                in.putExtra("name",inName);
                getContext().startActivity(in);

            }
        });

        try {
            ImageView i = (ImageView)convertView.findViewById(R.id.imageSwitch);
            System.out.println(i);
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable drawable = Drawable.createFromStream(is, "src name");
            i.setImageDrawable(drawable);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return convertView;

    }

}
