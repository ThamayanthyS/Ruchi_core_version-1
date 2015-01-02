package com.ruchi.engine.foodextraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.ruchi.engine.preprocessing.Stemmer;
import com.ruchi.engine.preprocessing.WordDistance;

/**
 * Created by brusoth on 12/17/2014.
 */
public class FoodClassifier {
    private HashMap<String,Integer> map=new HashMap<String,Integer>();
    private HashMap<String,String> result=new HashMap<String,String>();
    private WordDistance wd;
    
    public void addFood(String food){
        String stemmed_food= Stemmer.pluralToSingular(food).toLowerCase();
        if(map.containsKey(stemmed_food)){
            map.put(stemmed_food,map.get(stemmed_food)+1);
        }
        else{
            map.put(stemmed_food,1);
        }
    }

    public void classify(){

        wd=new WordDistance();
        wd.load();
        ArrayList<String> top=new ArrayList<String>();
        ArrayList<String> down=new ArrayList<String>();

        for(Map.Entry<String,Integer> entry:map.entrySet()){
            if(entry.getValue()>1){
                top.add(entry.getKey());
                result.put(entry.getKey(), entry.getKey());
            }
            else{
                down.add(entry.getKey());
            }
        }

        for(String d:down){
            String matched_value=findBestMatch(top,d);
            
            if(matched_value.trim().length()>0){
            	result.put(d, matched_value);
            }
        }
    }

    public String findBestMatch(ArrayList<String> top,String input){
        String most_matched="";
        float highest=0;
        int high_length=0;
        float value;
        boolean passed=false;
        for(String s:top){
            if(s.length()<6 && input.length()<6){
                value=wd.getLevenshteinSimilarity(s.toLowerCase(),input.toLowerCase());
                if(value>0.8){
                    passed=true;
                }
            }
            else if(s.length()>=6 && input.length()>=6){
                value=wd.getMongeElkanSimilarity(s.toLowerCase(),input.toLowerCase());
                if(value>0.8){
                    passed=true;
                }
            }
            else{
                value=wd.getCosineSimilarity(s.toLowerCase(), input.toLowerCase());
                if(value>0.8){
                    passed=true;
                }
            }
            if(passed && value>highest){
                highest=value;
                high_length=s.length();
                most_matched=s;
            }
            else if(passed && value==highest && s.length()>high_length){
                high_length=s.length();
                most_matched=s;
            }
            passed=false;
        }

        return most_matched;
    }
    
    public String getResult(String food_name){
    	String f=result.get(Stemmer.pluralToSingular(food_name));
    	if(f!=null){
    		return  f;
    	}
    	
    	return "notfound";
    }

    public static void main(String[] args){
        FoodClassifier fc=new FoodClassifier();
        fc.addFood("protein pancakes");
        fc.addFood("protein pancakes");
        fc.addFood("protein pancakes");
        fc.addFood("apple pancakes");
        fc.addFood("apple pancakes");
        fc.addFood("apple pancakes");
        fc.addFood("protien pancakes sue");
        fc.addFood("Apple cake");
        fc.addFood("Apple cake");
        fc.addFood("cake");
        fc.classify();
        ArrayList<String> list=new ArrayList<String>();
        list.add("pancake");
        list.add("Apple cake");
        list.add("protein pancakes");
        list.add("sue");
        System.out.println(fc.findBestMatch(list,"protein pancakes sue"));

    }
}
