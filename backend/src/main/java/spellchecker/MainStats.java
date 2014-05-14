/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spellchecker;

/**
 *
 * @author David
 */
public class MainStats {  
  public static void main(String[] args){
    TestDataParser tdp = new TestDataParser();
    TestData td = tdp.GetTestData();
    DictionaryAndFreqs df = new DictionaryAndFreqs();
    Spellchecker sc = new Spellchecker(df);
    
    Statistics s = new Statistics(sc, td);
//    for(Integer i : s.GetCorpusCounts()){
//      System.out.println(i);
//    }
//    System.out.println();
//    for(Integer i : s.GetCorpusFrequencies()){
//      System.out.println(i);
//    }
    
    Double[] d = s.GetStatsBySize(1000, 1, false, false);
    for(int i=1; i<=15; i++){
      System.out.println(i + "\t" + d[i]);
    }
    
    //System.out.println(s.GetOverallStats(1000, 2, true, true));
  }
}
