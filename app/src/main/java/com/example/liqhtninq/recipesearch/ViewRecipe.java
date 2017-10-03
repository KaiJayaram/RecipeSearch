package com.example.liqhtninq.recipesearch;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ViewRecipe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);
        String name = getIntent().getStringExtra("name");
        String steps = getIntent().getStringExtra("steps");
        String nutrition = getIntent().getStringExtra("nutrition");
        String ingredients = getIntent().getStringExtra("ingredients");
        String imgURL = getIntent().getStringExtra("imgURL");

        int end = steps.indexOf("Step");
        ArrayList<String> stepList = new ArrayList<>();
        for(int i = 1; i < steps.length()-4; i ++){
            if(steps.substring(i,i+4).equals("Step")){
                String sub = steps.substring(end,i);
                sub = sub.substring(0,6) + "\n" + sub.substring(6,sub.length());
                sub = sub.trim().replaceAll(" +", " ");
                sub = sub.trim().replaceAll(" ", " ");
                stepList.add(sub);
                end = i;
            }
        }
        steps = "";
        for(int i = 0; i < stepList.size(); i++){
            steps += stepList.get(i) + "\n";
        }

        TextView title = (TextView)findViewById(R.id.nameTV);
        TextView nut = (TextView)findViewById(R.id.nutTV);
        TextView ingr = (TextView)findViewById(R.id.ingrTV);
        TextView step = (TextView)findViewById(R.id.stepTV);

        title.setText(name);
        nut.setText(nutrition);
        ingr.setText(ingredients);
        step.setText(steps);

        nut.setMovementMethod(new ScrollingMovementMethod());
        ingr.setMovementMethod(new ScrollingMovementMethod());
        step.setMovementMethod(new ScrollingMovementMethod());

        try {
            ImageView i = (ImageView)findViewById(R.id.img);
            System.out.println(i);
            InputStream is = (InputStream) new URL(imgURL).getContent();
            Drawable drawable = Drawable.createFromStream(is, "src name");
            i.setImageDrawable(drawable);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
