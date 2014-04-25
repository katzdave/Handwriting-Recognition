/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spellchecker;

import java.util.List;

/**
 *
 * @author David
 */
public class Statistics {
  Spellchecker SpellChecker;
  TestData Testdata;
  
  Statistics(Spellchecker sc, TestData td){
    SpellChecker = sc;
    Testdata = td;
  }
  
  public Double[] GetStatsBySize(int numWords, int K){
    Double[] results = new Double[16];
    for(int i = 1; i<=15; i++){
      System.out.println(i);
      int correct = 0;
      int total = 0;
      List<String> samples =
              SpellChecker.Dictionary.GetSampleWordsOfSize(i, numWords);
      for(String s : samples){
        String[] guesses =
                SpellChecker.SpellcheckWord(Testdata.GetSampleWord(s), K);
        for(String g : guesses){
          if (g.equals(s)){
            correct++;
            break;
          }
        }
        total++;
      }
      results[i] = ((double)correct)/total;
    }
    return results;
  }
}
