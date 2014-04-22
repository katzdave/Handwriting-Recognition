/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spellchecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 *
 * @author David
 */
public class TestData {
  String[] Alphabet = {"a","b","c","d","e","f","g","h","i","j","k","l",
    "m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
  HashMap<String, List<TestExample>> AllLetters;
  
  Random Randomizer = new Random();
  
  public TestData(List<TestExample> lclc){
    AllLetters = new HashMap<>();
    for(String letter : Alphabet){
      AllLetters.put(letter, new ArrayList<TestExample>());
    }
    
    for(TestExample clc : lclc){
      List<TestExample> llh = AllLetters.get(clc.KnownResult);
      llh.add(clc);
    }
  }
  
  public Word GetSampleWord(String word){
    if(word.length() > 15){
      System.err.println("Word too large, maximum length 15");
      System.exit(1);
    }
    List<TestExample> llh = new ArrayList<>();
    for(int i=0; i<word.length(); i++){
      String c = word.substring(i,i+1);
      List<TestExample> samples = AllLetters.get(c);
      llh.add(samples.get(Randomizer.nextInt(samples.size())));
    }
    
    return new Word(llh);
  }
}
