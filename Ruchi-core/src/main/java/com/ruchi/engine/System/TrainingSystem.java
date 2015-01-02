package com.ruchi.engine.System;

import java.io.IOException;
import java.util.ArrayList;

import com.ruchi.engine.database.DatabaseConnector;
import com.ruchi.engine.foodextraction.OpenNLP;
import com.ruchi.engine.preprocessing.LanguageDetector;

public class TrainingSystem {
	
	private static TrainingSystem instance=null;
	
	private DatabaseConnector db;
    private LanguageDetector ld;
    private OpenNLP sent;
    
    private TrainingSystem(){
    	db=new DatabaseConnector();
    	ld=new LanguageDetector();
    	sent=new OpenNLP();
    	db.connect();
        ld.load_profile();
    }
    
    public static TrainingSystem getInstance(){
    	if(instance==null){
    		instance=new TrainingSystem();
    	}
    	return instance;
    }
	
	public void addToTrainingSet(String line){
		if(line!=null && line.length()>0){
			try {
				sent.tagSentence(line);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void trainFromDatabase(){
		ArrayList<String> res_list=db.getRestaurants();
		
		for(String s:res_list)
        {
            ArrayList<String> reviews=db.getRestaurantReviews(s);
            for(String s1:reviews)
            {
                if(ld.check_Language(s1))
                {

                    ArrayList<String> sentences=sent.getSentence(s1);
                    for(String s2:sentences)
                    {
                        String sen=LanguageDetector.remove_symbols(s2);
                        try {
                            if(sen.length()>1)
                            sent.tagSentence(sen);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        db.disconect();

	}

}
