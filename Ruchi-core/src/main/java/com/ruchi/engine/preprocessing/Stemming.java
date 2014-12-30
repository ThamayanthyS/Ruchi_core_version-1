package com.ruchi.engine.preprocessing;

import java.io.IOException;

import com.ruchi.engine.foodextraction.FoodSearch;
import org.tartarus.snowball.ext.PorterStemmer;


public class Stemming {

    public static void main(String[] args) {
            FoodSearch fs=new FoodSearch();
            fs.loadFood();
            System.out.println(fs.search("their deluxe burger and chocolate frozen custard"));


    }

    public static  String removeStopWordsAndStem(String input) {

        PorterStemmer stemmer = new PorterStemmer();
        stemmer.setCurrent(input);
        stemmer.stem();
        return stemmer.getCurrent();
    }


}