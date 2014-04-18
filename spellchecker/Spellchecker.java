/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spellchecker;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author David
 */
public class Spellchecker {
  DictionaryAndFreqs Dictionary;
  
  public Spellchecker(DictionaryAndFreqs dnf){
    Dictionary = dnf;
  }
  
  public String SpellcheckWord(Word w){
    HashMap<String,Integer> wos = Dictionary.GetWordsOfSize(w.GetSize());
    double maxLikelihood = Double.NEGATIVE_INFINITY;
    String maxLikelihoodWord = null;
    for (Map.Entry<String, Integer> entry : wos.entrySet()) {
      String key = entry.getKey();
      Integer value = entry.getValue();
      double logLikelihood = w.GetLogLikelihood(key) + Math.log((double) value);
      if(logLikelihood > maxLikelihood){
        maxLikelihood = logLikelihood;
        maxLikelihoodWord = key;
      }
    }
    return maxLikelihoodWord;
  }
}
