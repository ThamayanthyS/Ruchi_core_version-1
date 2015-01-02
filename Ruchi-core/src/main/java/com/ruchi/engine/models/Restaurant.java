package com.ruchi.engine.models;

import com.ruchi.engine.preprocessing.Stemmer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by brusoth on 12/12/2014.
 */
public class Restaurant {
    private String id;
    private String name;

    private ArrayList<Review> review_list=new ArrayList<Review>();
    private HashMap<String,Integer> food_map=new HashMap<String,Integer>();

    public Restaurant(String name){
    	this.setName(name);
    }
    
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    public void addReview(Review object){
        review_list.add(object);
    }

    public ArrayList<Review> getReview(){
    	return review_list;
    }

    public void addFood(String food){
        String stemmed_food= Stemmer.pluralToSingular(food).toLowerCase();

        if(food_map.containsKey(stemmed_food)){
            food_map.put(stemmed_food,food_map.get(stemmed_food)+1);
        }
        else{
            food_map.put(stemmed_food,1);
        }
    }
	
}
