package com.example.liqhtninq.recipesearch;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchOptions extends AppCompatActivity {

    Button ingr, nutri, go;
    ListView recipes;
    CheckBox usr;
    SearchPreferences sp;
    EditText name;
    ArrayList<String> list;
    ArrayList<String> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_options);
        ingr = (Button)findViewById(R.id.ingrButton);
        nutri = (Button)findViewById(R.id.nutButton);
        go = (Button)findViewById(R.id.searchButton);
        usr = (CheckBox) findViewById(R.id.userCheck);
        recipes = (ListView)findViewById(R.id.RecipeList);
        name = (EditText)findViewById(R.id.searchBar);
        sp = new SearchPreferences();

        list = new ArrayList<>();
        RecipeListAdapter adapter = new RecipeListAdapter(this, R.layout.recipe_list_child_view, list, data);
        recipes.setAdapter(adapter);
    }

    public void selectIngredients(View view){
        final Dialog ingrDialog = new Dialog(this);
        ingrDialog.setContentView(R.layout.add_ingredients_layout);
        ingrDialog.show();
        ListView ingrList = (ListView)ingrDialog.findViewById(R.id.ingr_list);
        ingrList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sp.getIngredients()));
        ingrList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sp.removeIngredient(position);
                ingrDialog.hide();
            }
        });

        Button add = (Button)ingrDialog.findViewById(R.id.addBtn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText search = (EditText)ingrDialog.findViewById(R.id.ingrSearch);
                String ingredientToAdd = search.getText().toString();
                sp.addIngredient(ingredientToAdd);
                ingrDialog.hide();
            }
        });
    }

    public void selectNutrition(View view){
        final Dialog nutDialog = new Dialog(this);
        nutDialog.setContentView(R.layout.set_nutrition_layout);
        nutDialog.show();
        Button ok = (Button)nutDialog.findViewById(R.id.okBtn);
        Button clr = (Button)nutDialog.findViewById(R.id.clrButton);
        EditText pro = (EditText)nutDialog.findViewById(R.id.proteinBox);
        EditText cal = (EditText)nutDialog.findViewById(R.id.calBox);
        EditText carb = (EditText)nutDialog.findViewById(R.id.carbBox);
        EditText fat = (EditText)nutDialog.findViewById(R.id.fatBox);
        ArrayList<Double> nutrition = sp.getNutrients();
        if(nutrition.size()==4) {
            if(nutrition.get(0)!=null)
                pro.setText(String.valueOf(nutrition.get(0)));
            if(nutrition.get(1)!=null)
                cal.setText(String.valueOf(nutrition.get(1)));
            if(nutrition.get(2)!=null)
                carb.setText(String.valueOf(nutrition.get(2)));
            if(nutrition.get(3)!=null)
                fat.setText(String.valueOf(nutrition.get(3)));
        }

        clr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.setNutrients(new ArrayList<Double>());
                nutDialog.hide();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText pro = (EditText)nutDialog.findViewById(R.id.proteinBox);
                EditText cal = (EditText)nutDialog.findViewById(R.id.calBox);
                EditText carb = (EditText)nutDialog.findViewById(R.id.carbBox);
                EditText fat = (EditText)nutDialog.findViewById(R.id.fatBox);
                ArrayList<Double> nutrition = new ArrayList<Double>();
                if(!pro.getText().toString().equals(""))
                    nutrition.add(Double.parseDouble(pro.getText().toString()));
                else{
                    nutrition.add(null);
                }
                if(!cal.getText().toString().equals(""))
                    nutrition.add(Math.floor(Double.parseDouble(cal.getText().toString()) *4.184));
                else{
                    nutrition.add(null);
                }
                if(!carb.getText().toString().equals(""))
                    nutrition.add(Double.parseDouble(carb.getText().toString()));
                else{
                    nutrition.add(null);
                }
                if(!fat.getText().toString().equals(""))
                    nutrition.add(Double.parseDouble(fat.getText().toString()));
                else{
                    nutrition.add(null);
                }
                sp.setNutrients(nutrition);
                nutDialog.hide();
            }
        });
    }

    public void checkBoxSelected(View view){
        sp.toggleUR();
    }

    public void search(View view){
        sp.setName(name.getText().toString());
        String query = "SELECT * FROM recipe_data WHERE Name LIKE '%" + sp.getName() + "%'";
        for(int i = 0; i < sp.getIngredients().size(); i ++){
            query += " AND Ingredients LIKE '%" +sp.getIngredients().get(i)+ "%'";
        }
        ArrayList<Double> n = sp.getNutrients();
        if(n != null && n.size() > 0) {
            if (n.get(0) != null) {
                if(n.get(0) == Math.floor(n.get(0)))
                {
                    query += " AND Nutrition LIKE '%" + (int)(n.get(0).doubleValue()) + "gProtein%'";
                }
                else {
                    query += " AND Nutrition LIKE '%" + n.get(0) + "gProtein%'";
                }
            }
            if (n.get(1) != null) {
                if(n.get(1) == Math.floor(n.get(1)))
                {
                    query += " AND Nutrition LIKE '%" + (int)(n.get(1).doubleValue()) + " kjEnergy%'";
                }
                else {
                    query += " AND Nutrition LIKE '%" + n.get(1) + " kjEnergy%'";
                }
            }
            if (n.get(2) != null) {
                if(n.get(2) == Math.floor(n.get(2)))
                {
                    query += " AND Nutrition LIKE '%" + (int)(n.get(2).doubleValue()) + "gCarbs%'";
                }
                else {
                    query += " AND Nutrition LIKE '%" + n.get(2) + "gCarbs%'";
                }
            }
            if (n.get(3) != null) {
                if(n.get(3) == Math.floor(n.get(3)))
                {
                    query += " AND Nutrition LIKE '%" + (int)(n.get(3).doubleValue()) + "gFat%'";
                }
                else {
                    query += " AND Nutrition LIKE '%" + n.get(3) + "gFat%'";
                }
            }
        }
        query += ";";
        DataBaseConnector cn = new DataBaseConnector(this);
        cn.execute(query);

    }

    public void setRecipeList(ArrayList<String[]> input){
        list = new ArrayList<>();
        data = new ArrayList<>();
        for(int i =0; i <input.size(); i++){
            list.add(input.get(i)[0]+ "@" + input.get(i)[4]);
            data.add(input.get(i)[1] +"@" +input.get(i)[2] + "@" + input.get(i)[3] + "@");
        }
        RecipeListAdapter adapter = new RecipeListAdapter(this, R.layout.recipe_list_child_view, list, data);
        recipes.setAdapter(adapter);
    }
}
