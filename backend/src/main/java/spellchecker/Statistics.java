/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spellchecker;

import java.util.List;
import java.util.Random;

/**
 *
 * @author David
 */
public class Statistics {
  Spellchecker SpellChecker;
  TestData Testdata;
  Random Randomizer = new Random();
  
  Statistics(Spellchecker sc, TestData td){
    SpellChecker = sc;
    Testdata = td;
  }
  
  public Double[] GetStatsBySize(int numWords, int K,
          boolean subs, boolean error){
    Double[] results = new Double[16];
    for(int i=1; i<=15; i++){
      System.out.println(i);
      int correct = 0;
      int total = 0;
      List<String> samples =
              SpellChecker.Dictionary.GetSampleWordsOfSize(i, numWords);
      for(String s : samples){
        Word w;
        if(error){
          w = Testdata.GetSampleWord(GetErrorString(s));
        }else{
          w = Testdata.GetSampleWord(s);
        }
        String[] guesses = SpellChecker.SpellcheckWord(w, K, subs);
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
  
  public Double GetOverallStats(int numWords, int K, boolean subs, boolean error){
    Integer[] cdf = SpellChecker.Dictionary.SizeFrequencies;
    int cnt = 0;
    int correct = 0;
    int total = 0;
    for(int i=1; i<=15; i++){
      cnt += cdf[i];
      cdf[i] = cnt;
    }
    for(int j=0; j<numWords; j++){
      int n = Randomizer.nextInt(cdf[15]);
      for(int i=1; i<=15; i++){
        if(n <= cdf[i]){
          n = i;
          break;
        }
      }
      List<String> sample = SpellChecker.Dictionary.GetSampleWordsOfSize(n, 1);
      String s = sample.get(0);
      Word w;
      if(error){
        w = Testdata.GetSampleWord(GetErrorString(s));
      }else{
        w = Testdata.GetSampleWord(s);
      }
      String[] guesses = SpellChecker.SpellcheckWord(w, K, subs);
        for(String g : guesses){
          if (g.equals(s)){
            correct++;
            break;
          }
        }
      total++;
    }
    return ((double)correct)/total;
  }
  
  public Integer[] GetCorpusCounts(){
    return SpellChecker.Dictionary.SizeCounts;
  }
  
  public Integer[] GetCorpusFrequencies(){
    return SpellChecker.Dictionary.SizeFrequencies;
  }
  
  private String GetErrorString(String s){
    int i = Randomizer.nextInt(s.length());
    char c = (char) (Randomizer.nextInt(26) + 'a');
    char[] s2 = s.toCharArray();
    s2[i] = c;
    return new String(s2);
  }
}
