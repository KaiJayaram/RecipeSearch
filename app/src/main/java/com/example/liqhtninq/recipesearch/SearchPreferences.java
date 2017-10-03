package com.example.liqhtninq.recipesearch;

import java.util.ArrayList;

/**
 * Created by liqhtninq on 10/1/2017.
 */

public class SearchPreferences {
    private ArrayList<String> ingredients;
    private boolean userRecipes;
    private ArrayList<Double> nutrients;
    private String name;

    public SearchPreferences(){
        ingredients = new ArrayList<String>();
        nutrients = new ArrayList<Double>();
        name = "";
        userRecipes = false;
    }
    public void setName(String name){
        this.name = name;
    }
    public void addIngredient(String ingr){
        ingredients.add(ingr);
    }
    public void setNutrients(ArrayList<Double> newList){
        nutrients = newList;
    }
    public void addNutrients(double nut){
        nutrients.add(nut);
    }
    public void toggleUR(){
        userRecipes = !userRecipes;
    }
    public void removeNutrient(int index){
        nutrients.remove(index);
    }
    public void removeIngredient(int index){
        ingredients.remove(index);
    }
    public ArrayList<String> getIngredients(){
        return ingredients;
    }
    public ArrayList<Double> getNutrients(){
        return nutrients;
    }
    public boolean getUserRecipes(){
        return userRecipes;
    }
    public String getName(){
        return name;
    }
}
