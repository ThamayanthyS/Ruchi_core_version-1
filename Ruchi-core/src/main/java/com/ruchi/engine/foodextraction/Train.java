package com.ruchi.engine.foodextraction;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;

/**
 * Created by brusoth on 11/25/2014.
 */
public class Train {
    public static void main(String args[]) throws IOException {
        try {
            new Train().train();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void train() throws IOException {
        FileReader fileReader = new FileReader("res/review_train");
        ObjectStream fileStream = new PlainTextByLineStream(fileReader);
        ObjectStream sampleStream = new NameSampleDataStream(fileStream);
        TokenNameFinderModel model = NameFinderME.train("pt-br", "train", sampleStream, Collections.<String, Object>emptyMap());
        NameFinderME nfm = new NameFinderME(model);

        String sent="kotthu was super. don't forgot to drink bubble soup";
        OpenNLP s1=new OpenNLP();
        s1.loadModel();
        Span nameSpans[] = nfm.find(s1.getTokens(sent));
        for(Span s: nameSpans)
            System.out.println(s.toString());

    }
}
