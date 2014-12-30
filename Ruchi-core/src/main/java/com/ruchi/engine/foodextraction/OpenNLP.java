package com.ruchi.engine.foodextraction; /**
 * Created by brusoth on 11/15/2014.
 */
import com.google.common.base.Joiner;
import com.ruchi.engine.utils.TextEditors;
import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by brusoth on 11/15/2014.
 */
public class OpenNLP {

    InputStream sent_model,token_model,person_model;
    POSModel model;
    PerformanceMonitor perfMon;
    POSTaggerME tagger;
    SentenceModel sentence_model;
    ChunkerModel cModel;
    ChunkerME chunkerME;
    SentenceDetectorME detect;
    FoodSearch fs;
    Tokenizer tokenizer;

    public ArrayList<String> getSentence(String review)
    {
        ArrayList<String> list=new ArrayList<String>();
        try {
            String sentences[] = detect.sentDetect(review);

            for(String s:sentences)
            {
                list.add(s);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public void loadModel()
    {
        try {
            sent_model = new FileInputStream("en-sent.zip");
            token_model = new FileInputStream("en-token.zip");
            person_model= new FileInputStream("en-ner-person.zip");
            model = new POSModelLoader().load(new File("en-pos-maxent.zip"));
            InputStream is = new FileInputStream("en-chunker.zip");

            perfMon = new PerformanceMonitor(System.err, "sent");
            tagger = new POSTaggerME(model);
            perfMon.start();

            sentence_model=new SentenceModel(sent_model);
            detect = new SentenceDetectorME(sentence_model);

            cModel = new ChunkerModel(is);
            chunkerME = new ChunkerME(cModel);

            TokenizerModel model = new TokenizerModel(token_model);
            tokenizer = new TokenizerME(model);

            fs=new FoodSearch();
            fs.loadFood();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public String[] getTokens(String sentence)
    {
            String tokens[] = tokenizer.tokenize(sentence);
            return tokens;
    }


    public void getNames(String[] tokens)
    {
        try {
            TokenNameFinderModel model = new TokenNameFinderModel(person_model);
            NameFinderME nameFinder = new NameFinderME(model);
            person_model.close();
            Span nameSpans[] = nameFinder.find(tokens);
            for(Span s: nameSpans)
                System.out.println(s.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void POSTag(String sentence) throws IOException {

        ObjectStream<String> lineStream = new PlainTextByLineStream(new StringReader(sentence));
        String line;
        String whitespaceTokenizerLine[] = null;

        String[] tags = null;
        while ((line = lineStream.read()) != null) {
            whitespaceTokenizerLine = WhitespaceTokenizer.INSTANCE
                    .tokenize(line);
            tags = tagger.tag(whitespaceTokenizerLine);

            POSSample sample = new POSSample(whitespaceTokenizerLine, tags);
            //System.out.println(sample.toString());
            //perfMon.incrementCounter();
        }
        //perfMon.stopAndPrintFinalResult();


        //chunker implementation
        String result[] = chunkerME.chunk(whitespaceTokenizerLine, tags);

        Span[] span = chunkerME.chunkAsSpans(whitespaceTokenizerLine, tags);
        String[] array=Span.spansToStrings(span, whitespaceTokenizerLine);
        String sent="";

        for(String s:array)
        {
           sent=sent.concat(fs.search(s)+" ");
        }
        TextEditors.writeTextFile(sent);

    }

    public void tag(String sentence) throws IOException {
        ObjectStream<String> lineStream = new PlainTextByLineStream(new StringReader(sentence));
        String line;
        String whitespaceTokenizerLine[] = null;

        String[] tags = null;
        while ((line = lineStream.read()) != null) {
            whitespaceTokenizerLine = WhitespaceTokenizer.INSTANCE
                    .tokenize(line);
            tags = tagger.tag(whitespaceTokenizerLine);

            POSSample sample = new POSSample(whitespaceTokenizerLine, tags);
            System.out.println(Arrays.toString(tags));
            //System.out.println(sample.toString());
            //perfMon.incrementCounter();
        }
    }

    public String[] getWordTokens(String line){
        String whitespaceTokenizerLine[] = null;
        whitespaceTokenizerLine = WhitespaceTokenizer.INSTANCE.tokenize(line);
        return whitespaceTokenizerLine;
    }

    public String[] getWordTags(String[] tokens){
        String[] tags = null;
        tags = tagger.tag(tokens);
        return tags;
    }

    public ArrayList<String>  findFeatures(String[] tags,String[] tokens){
        ArrayList<String> feature_list=new ArrayList<String>();
        ArrayList<String> list=new ArrayList<String>();
        for(int i=0;i<tags.length;i++)
        {
            if(tags[i].trim().equalsIgnoreCase("NN")||tags[i].trim().equalsIgnoreCase("NNS")||tags[i].trim().equalsIgnoreCase("NNP")||tags[i].trim().equalsIgnoreCase("NNPS")){
                list.add(tokens[i]);
            }
            else{
                if(list.size()>1)
                {
                    String[] words=Joiner.on(" ").join(list).split(",");
                    for(String word:words)
                    {
                        feature_list.add(word.replaceAll("[^\\w\\s]","").trim());
                    }

                }
                list=new ArrayList<String>();
            }
        }
        if(list.size()>1)
        {
            String[] words=Joiner.on(" ").join(list).split(",");
            for(String word:words)
            {
                feature_list.add(word.replaceAll("[^\\w\\s]","").trim());
            }
        }

        for(String l:feature_list) {
            System.out.println(l);
        }
        return feature_list;
    }

    public static void main(String args[]) throws IOException {
        OpenNLP sent=new OpenNLP();
        sent.loadModel();
        String[] toks=sent.getWordTokens("kothu rotti is taste");
        String[] tags=sent.getWordTags(toks);
        sent.findFeatures(tags,toks);
        //sent.tag("chicken tato soup");
        //tags[i].trim().equalsIgnoreCase("VBN") ||
    }
}

