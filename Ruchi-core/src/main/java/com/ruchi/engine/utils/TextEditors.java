package com.ruchi.engine.utils;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by brusoth on 11/11/2014.
 */
public class TextEditors {

    public static void writeTextFile(ArrayList<String> list)
    {
        File file = new File("F:/filename.txt");
        // if file doesnt exists, then create it
        if (!file.exists()) {
            try {
                file.createNewFile();
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                for(String s:list) {
                    bw.write(s);
                }
                bw.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
    public static void writeFoodNamesToTextFile(ArrayList<String> list)
    {
        for(String s:list) {
            try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("F:\\filename.txt", true)))) {
                out.println(s);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    public static void writeTextFile(String sentence)
    {
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("res/review_train", true)))) {
            out.println(sentence);
        }catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void writeTestSentence(String sentence)
    {
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("res/test_sentences.txt", true)))) {
            out.println(sentence);
        }catch (IOException e) {
            System.out.println(e);
        }
    }

}
