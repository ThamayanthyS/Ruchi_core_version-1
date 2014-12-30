package com.ruchi.engine.utils;

import com.ruchi.engine.database.DatabaseConnector;
import com.ruchi.engine.foodextraction.OpenNLP;

import java.util.ArrayList;

/**
 * Created by brusoth on 12/9/2014.
 */
public class Tester {
    public static void main(String args[]){
//        DatabaseConnector db=new DatabaseConnector();
//        db.connect();
//        ArrayList<String> food_list=new ArrayList<String>();
//        db.getFoodNames(food_list);
//        System.out.println(food_list.size());
//        TextEditors.writeFoodNamesToTextFile(food_list);
        new Tester().getSentencesFromReviews();
    }

    public void getSentencesFromReviews(){
        DatabaseConnector db=new DatabaseConnector();
        db.getTestData("aaa");
        OpenNLP nlp=new OpenNLP();
        nlp.loadModel();
        ArrayList<String> list=db.getRestaurantReviews("U.S. Egg");
        for(String s:list){
            ArrayList<String> sentences= nlp.getSentence(s);
            for(String line:sentences) {
                TextEditors.writeTestSentence(line);
            }
        }
    }
}
