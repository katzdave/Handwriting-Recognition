/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spellchecker;

import java.util.HashMap;

/**
 *
 * @author David
 */
public class LetterLikelihoods {
  static double SmoothWeight = .005;
  
  HashMap<String,Double> Likelihoods;
  String KnownCategory = null;

  
  public LetterLikelihoods(CategoryLikelihoodContainer clc){
    Likelihoods = new HashMap<>();
    for(CategoryLikelihood cl : clc.CategoryLikelihoods){
      Likelihoods.put(cl.Category, cl.Likelihood);
    }
  }
  
//  public LetterLikelihoods(CategoryLikelihoodContainer clc, String cat){
//    Likelihoods = new HashMap<>();
//    for(CategoryLikelihood cl : clc.CategoryLikelihoods){
//      Likelihoods.put(cl.Category, cl.Likelihood);
//    }
//    KnownCategory = cat;
//  }
  
  public double GetLikelihood(String letter){
    return Likelihoods.containsKey(letter) ?
            Likelihoods.get(letter) : SmoothWeight;
  }
}
