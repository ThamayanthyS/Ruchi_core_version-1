package com.ruchi.engine.foodextraction;

import com.ruchi.engine.database.DatabaseConnector;
import com.ruchi.engine.preprocessing.Stemming;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by Brus panda on 12/2/2014.
 */
public class Extraction {
    DatabaseConnector db=new DatabaseConnector();
    OpenNLP sent;

    NameFinderME nfm;

    ArrayList<String> food_list=new ArrayList<String>();
    ArrayList<String> rest_list=new ArrayList<String>();

    public void load(){
        db.connect();
        db.getFoodNames(food_list);
        rest_list=db.getRestaurants();
        train();
        sent=new OpenNLP();
        sent.loadModel();
    }

    public void readReviews()
    {
        System.out.print(rest_list.size());
        for(String rest:rest_list){


            ArrayList<String> review_set=db.getRestaurantReviews(rest.trim());
            ArrayList<word> word_list=new ArrayList<word>();

            for(String review:review_set)
            {
                ArrayList<String> sentences=sent.getSentence(review);
                int sent_num=1;
                for(String sentence:sentences){
                    String[] tokens=predict(sentence.trim());
                    String[] toks=sent.getWordTokens(sentence);
                    String[] tags=sent.getWordTags(toks);
                    ArrayList<String> features=sent.findFeatures(tags,toks);

                    for(String fea:features)
                    {
                        for(String pre:tokens)
                        {
                            if(fea.contains(pre)){
                                System.out.println(fea);
                                break;
                            }
                        }
                    }

                    sent_num++;
                }
                analyse(word_list );
            }

        }


    }

    public void train(){
        try {
            FileReader fileReader = new FileReader("res/review_train");
            ObjectStream fileStream = new PlainTextByLineStream(fileReader);
            ObjectStream sampleStream = new NameSampleDataStream(fileStream);
            TokenNameFinderModel model = NameFinderME.train("pt-br", "train", sampleStream, Collections.<String, Object>emptyMap());
            nfm = new NameFinderME(model);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public String[] predict(String line){
        String[] tokens=sent.getTokens(line);
        Span nameSpans[] = nfm.find(tokens);
        String[] array=Span.spansToStrings(nameSpans,tokens);
        return array;
    }

    public void analyse(ArrayList<word> word_list)
    {
        for(word w:word_list){
            //System.out.println(w.getWord());
            if(food_list.contains(Stemming.removeStopWordsAndStem(w.getWord())))
            {

            }
        }
    }


    public static void main(String args[]){
        Extraction exe=new Extraction();
        exe.load();
        exe.readReviews();
    }
}

class word{
    private String word;
    private int sent_number;

    word(String word,int number){
        this.word=word;
        sent_number=number;
    }

    public String getWord(){
        return word;
    }

    public int getNumber(){
        return sent_number;
    }
}
