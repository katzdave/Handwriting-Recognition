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
}
