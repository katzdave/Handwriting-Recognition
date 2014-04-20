/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spellchecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

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
  
  public List<String> SpellcheckWord(Word w, int k){
    HashMap<String,Integer> wos = Dictionary.GetWordsOfSize(w.GetSize());
    PriorityQueue<WordWeight> pq = new PriorityQueue<>();
    for (Map.Entry<String, Integer> entry : wos.entrySet()) {
      String key = entry.getKey();
      Integer value = entry.getValue();
      double logLikelihood = w.GetLogLikelihood(key) + Math.log((double) value);
      pq.add(new WordWeight(key, logLikelihood));
      if(pq.size() > k){
        pq.poll();
      }
    }
    List<String> res = new ArrayList<>(k);
    for(int i=k-1; i>=0; i--){
      res.add(i, pq.poll().Word);
    }
    return res;
  }
}
