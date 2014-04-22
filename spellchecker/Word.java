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
public class Word {
  static double subWeight = .5;
  
  List<TestExample> Letters;
  
  public Word(List<TestExample> letters){
    Letters = letters;
  }
  
  public int GetSize(){
    return Letters.size();
  }
  
  public double GetLogLikelihood(String other){
    double logLikelihood = 0;
    for(int i=0; i<Letters.size(); i++){
      String character = other.substring(i, i+1);
      logLikelihood += Math.log(Letters.get(i)
              .Likelihoods.GetLikelihood(character));
    }
    return logLikelihood;
  }
  
  public double GetLogLikelihoodSubs(String other){
    double logLikelihood = 0;
    double minLikelihood = Double.POSITIVE_INFINITY;
    
    if(other.length() <= 3){
      return GetLogLikelihood(other);
    }
    
    for(int i=0; i<Letters.size(); i++){
      String character = other.substring(i, i+1);
      double l = Math.log(Letters.get(i).Likelihoods.GetLikelihood(character));
      if(l < minLikelihood){
        minLikelihood = l;
      }
      logLikelihood += l;
    }
    return logLikelihood - minLikelihood + Math.log(subWeight);
  }
  
  public double GetLogLikelihoodDeletion(String other){
    double logLikelihood = 0;
    double minLikelihood = Double.POSITIVE_INFINITY;
    
    if(other.length() <= 3){
      return GetLogLikelihood(other);
    }
    
    for(int i=0; i<Letters.size(); i++){
      String character = other.substring(i, i+1);
      double l = Math.log(Letters.get(i).Likelihoods.GetLikelihood(character));
      if(l < minLikelihood){
        minLikelihood = l;
      }
      logLikelihood += l;
    }
    return logLikelihood - minLikelihood + Math.log(subWeight);
  }
}
