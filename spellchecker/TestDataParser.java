/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spellcheckerv2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author David
 */
public class TestDataParser {
  static String FEATURESORIG = "resources/FeatureVectorOrig.txt";
  static String FEATURESSUMMED = "resources/FeatureVectorSummed.txt";
  static String LIKELIHOODS = "resources/CategoryLikelihood.txt";
  
  HashMap<String, TestExample> ExampleMap = new HashMap<>();
  
  TestDataParser(){
    try(BufferedReader f = new BufferedReader(new FileReader(FEATURESORIG));
      BufferedReader fs = new BufferedReader(new FileReader(FEATURESSUMMED));
      BufferedReader lh = new BufferedReader(new FileReader(LIKELIHOODS)))
    {
      String line1 = f.readLine();
      String line2 = fs.readLine();
      while(line1 != null){
        TestExample te = new TestExample();
        te.Original = new FeatureVector(line1,128);
        te.Summed = new FeatureVector(line2,32);
        ExampleMap.put(te.Summed.toString(), te);
        te.KnownResult = te.Original.Category;
        line1 = f.readLine();
        line2 = fs.readLine();
      }
      
      String line = lh.readLine();
      while(line != null){
        String[] splat = line.split(" ");
        FeatureVector fv = new FeatureVector(splat[0],32);
        TestExample te = ExampleMap.get(fv.toString());
        CategoryLikelihoodContainer clc =
                new CategoryLikelihoodContainer(splat[1]);
        te.Likelihoods = new LetterLikelihoods(clc);
        line = lh.readLine();
      }
    }catch(IOException e){
      System.err.println("File read error");
      System.exit(1);
    }
    
  }
  
  public TestData GetTestData(){
    return new TestData(new ArrayList<>(ExampleMap.values()));
  }
}
