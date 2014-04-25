/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spellchecker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author David
 */
public class Main {
  public static void main(String[] args) throws IOException {
    String input = "inputs.txt";
    String output = "outputs.html";
    
    TestDataParser tdp = new TestDataParser();
    TestData td = tdp.GetTestData();
    DictionaryAndFreqs df = new DictionaryAndFreqs();
    Spellchecker sc = new Spellchecker(df);
    
    Statistics s = new Statistics(sc, td);
    Double[] d = s.GetStatsBySize(1000, 1);
    for(int i=1; i<=15; i++){
      System.out.println(i + "\t" + d[i]);
    }
    
//    int wordNumber = 0;
//    try(BufferedReader br = new BufferedReader(new FileReader(input));
//            BufferedWriter bw = new BufferedWriter(new FileWriter(output))){
//      String line = br.readLine();
//      while(line != null){
//        line = line.toLowerCase();
//        if(!line.matches("[a-z]+")){
//          line = br.readLine();
//          continue;
//        }
//        Word w = td.GetSampleWord(line);
//        for(int ln=0; ln<line.length(); ln++){
//          String imgFileName =
//                  "resources/images/" + wordNumber + "_" + ln + ".png";
//          ImageIO.write(w.Letters.get(ln).Original.toImage(), "png",
//                  new File(imgFileName));
//          bw.write("<img src=\"" + imgFileName + "\">");
//        }
//        String[] guesses = sc.SpellcheckWord(w,3);
//        bw.write(" " + guesses[0] + " " + guesses[1] + " " + guesses[2]);
//        bw.write("<br>\n");
//        wordNumber++;
//        line = br.readLine();
//      }
//    }catch(IOException e){
//      System.exit(1);
//    }
  }  
}
