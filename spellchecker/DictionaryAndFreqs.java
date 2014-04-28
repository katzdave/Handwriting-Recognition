/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spellchecker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author David
 */
public class DictionaryAndFreqs {
  static String DICTIONARY = "resources/dictionary.txt";
  static String FREQUENCIES = "resources/brown_freq.txt";
  
  List<HashMap<String,Integer>> WordsBySize;
  Integer[] SizeCounts;
  Integer[] SizeFrequencies;
  Random r = new Random();
  
  public DictionaryAndFreqs(){
    initializeWordsBySize();
    loadDictionary();
    loadFrequencies();
    populateSizeCounts();
  }
  
  public HashMap<String,Integer> GetWordsOfSize(int size){
    return WordsBySize.get(size);
  }
  
  public List<String> GetSampleWordsOfSize(int size, int numSamples){
    List<String> words = new ArrayList<>();
    HashMap<String,Integer> wordsOfSize = WordsBySize.get(size);
    long sum = 0;
    for(Integer i : wordsOfSize.values()){
      sum += (long)i;
    }
    for(int i=0; i<numSamples; i++){
      words.add(randomPick(wordsOfSize, sum));
    }
    return words;
  }
  
  public String randomPick(HashMap<String,Integer> w, long sum) {
    long randomValue = (long) (r.nextDouble()*sum);
    long currentSum = 0;
    String lastElement = null;

    for (Map.Entry<String, Integer> entry : w.entrySet()){
      String t = entry.getKey();      
      if (randomValue < currentSum + w.get(t)){
        return t;
      }
      currentSum+= entry.getValue();
      lastElement = t;
    }
    return lastElement;
  }
  
  private void initializeWordsBySize(){
    WordsBySize = new ArrayList<>();
    WordsBySize.add(new HashMap<String, Integer>()); //fill zero index
    for(int i=1; i<=15; i++){
      HashMap<String,Integer> hm = new HashMap<>();
      if(i == 1){ // Hardcode 1 letter words, since dictionary is 2-15 letters
        hm.put("i", 1);
        hm.put("a", 1);
        hm.put("o", 1);
      }
      WordsBySize.add(hm);
    }
  }
  
  private void loadDictionary(){
    try(BufferedReader br = new BufferedReader(new FileReader(DICTIONARY))){
      String line = br.readLine();
      while(line != null){
        String[] s = line.split(" ");
        String word = s[0].toLowerCase();
        boolean invalidChar = false;
        for(int i=0; i<word.length(); i++){
          if(word.charAt(i) > 'z' || word.charAt(i) < 'a'){
            invalidChar = true;
            break;
          }
        }
        if(invalidChar){
          continue;
        }
        WordsBySize.get(word.length()).put(word, 1);
        line = br.readLine();
      }
    }catch(IOException e){
      System.err.println("Invalid file");
      System.exit(1);
    }
  }
  
  private void loadFrequencies(){
    try(BufferedReader br = new BufferedReader(new FileReader(FREQUENCIES))){
      String line = br.readLine();
      while(line != null){
        String[] s = line.split("\t");
        String word = s[0].toLowerCase();
        if(word.length() > 15 ||
                !WordsBySize.get(word.length()).containsKey(word)){
          line = br.readLine();
          continue;
        }
        WordsBySize.get(word.length()).put(word, Integer.parseInt(s[1] + 1));
        line = br.readLine();
      }
    }catch(IOException e){
      System.err.println("Invalid file");
      System.exit(1);
    }
  }
  
  private void populateSizeCounts(){
    SizeCounts = new Integer[16];
    SizeFrequencies = new Integer[16];
    for(int i=1; i<=15; i++){
      HashMap<String,Integer> hm = WordsBySize.get(i);
      int sum = 0;
      int amt = 0;
      for(Integer j : hm.values()){
        sum += j;
        amt++;
      }
      SizeCounts[i] = amt;
      SizeFrequencies[i] = sum;
    }
  }
}
