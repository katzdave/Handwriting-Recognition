/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spellcheckerv2;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import javax.imageio.ImageIO;

/**
 * Lets you load feature vectors from text file or images
 * @author david
 */
public class FeatureVectorLoader {
  static String DELIM = " ";
  static String filename = "resources/KNNtrain.txt";
  
  public List<FeatureVector> FeatureVectorsFromTextFile(){
    List<FeatureVector> vectors = new ArrayList<>();
    try {
      String sCurrentLine;
      BufferedReader br = new BufferedReader(new FileReader(filename));
      while ((sCurrentLine = br.readLine()) != null) {
        vectors.add(new FeatureVector(sCurrentLine,32));
      }
    } catch (IOException e) {
        System.err.println("Invalid feature vector input file");
        System.exit(1);
    }
    return vectors;
  }

  public void ExportCurrentResultsToFile(String outFile,
                        HashMap<FeatureVector,Integer> testData,
                        HashMap<Integer,CategoryLikelihoodContainer> res){
    try(PrintWriter out = new PrintWriter(new FileWriter(outFile), true)){
      for(Map.Entry<FeatureVector,Integer> entry : testData.entrySet()){
        Integer key = entry.getValue();
        String knownValue = entry.getKey().Category;
        CategoryLikelihoodContainer clc = res.get(key);
        out.println(entry.getKey().toString() + " " + clc.toString());
      }
    } catch(IOException e){
      System.out.println("invalid file");
    }
  }
}
