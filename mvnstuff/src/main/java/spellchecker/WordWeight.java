/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spellcheckerv2;

/**
 *
 * @author David
 */
public class WordWeight implements Comparable<WordWeight>{
  String Word;
  double Likelihood;
  
  WordWeight(String word, double likelihood){
    Word = word;
    Likelihood = likelihood;
  }

  @Override
  public int compareTo(WordWeight t) {
    if(t.Likelihood < this.Likelihood)
      return 1;
    if(t.Likelihood == this.Likelihood)
      return 0;
    return -1;
  }
}
