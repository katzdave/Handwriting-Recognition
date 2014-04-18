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
  List<LetterLikelihoods> Letters;
  
  public Word(List<LetterLikelihoods> letters){
    Letters = letters;
  }
  
  public int GetSize(){
    return Letters.size();
  }
  
  public double GetLogLikelihood(String other){
    double logLikelihood = 0;
    for(int i=0; i<Letters.size(); i++){
      String character = other.substring(i, i+1);
      logLikelihood += Math.log(Letters.get(i).GetLikelihood(character));
    }
    return logLikelihood;
  }
}
