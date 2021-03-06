/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spellchecker;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * Stores the category and information about all features
 * @author David
 */
public class FeatureVector{
  int NumFeatures;
  static String Delim = ",";
  
  Feature[] Features;
  public String Category;
  
  public FeatureVector(){
    Features = new Feature[NumFeatures];
  }
  
  public FeatureVector(String s, int numfeatures){
    NumFeatures = numfeatures;
    Features = new Feature[NumFeatures];
    String[] strings = s.split(Delim);
    for(int i=0; i<NumFeatures; i++){
      Features[i] = new Feature(Double.parseDouble(strings[i]));
    }
    if(strings.length > NumFeatures){
      Category = strings[NumFeatures];
    }else{
      Category = null;
    }
  }
  
  public FeatureVector(BufferedImage img, String s, int numfeatures){
    NumFeatures = numfeatures;
    
    Category = s;
    Features = new Feature[NumFeatures];
    for(int i=0; i<8; i++){
      for(int j=0; j<8; j++){
        int count = 0;
        for(int k=0; k<4; k++){
          for(int h=0; h<4; h++){
            Color c = new Color(img.getRGB(j*4+k, i*4+h));
            if(c.getBlue()+c.getGreen()+c.getRed() < 350){
              count++;
            }
          }
        }
        Features[i*8 + j] = new Feature(count);
      }
    }
  }
  
  public double GetEuclidianDistance(FeatureVector other){
    double dist = 0;
    for(int i=0; i<NumFeatures; i++){
      dist += Math.pow(this.Features[i].Value - other.Features[i].Value,2);
    }
    return Math.pow(dist, .5);
  }
    
  public String toStringShort(){
    String out = "";
    for(int i=0; i<NumFeatures; i++){
      out += Features[i].toString() + Delim;
    }
    return out.substring(0, out.length()-1);
  }
  
  public BufferedImage toImage(){
    if(NumFeatures != 128){
      System.err.println("Invalid feature vector to export");
      System.exit(1);
    }
    Color black = new Color(0,0,0);
    Color white = new Color(255,255,255);
    BufferedImage img = new BufferedImage(8,16,BufferedImage.TYPE_BYTE_BINARY);
    for(int r=0; r<8; r++){
      for(int c=0; c<16; c++){
        if(Features[c*8+r].Value == 1){
          img.setRGB(r, c, black.getRGB());
        }else{
          img.setRGB(r, c, white.getRGB());
        }
      }
    }
    return img;
  }
  
  @Override
  public String toString(){
    String out = "";
    for(int i=0; i<NumFeatures; i++){
      out += Features[i].toString() + Delim;
    }
    return out + Category;
  }

   @Override
   public boolean equals(Object oth){
     FeatureVector other = (FeatureVector) oth;
     if(this.NumFeatures != other.NumFeatures){
       return false;
     }
     
     for(int i=0; i<NumFeatures; i++){
       if(this.Features[i] != other.Features[i]){
         return false;
       }
     }
     return true;
   }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 83 * hash + this.NumFeatures;
    hash = 83 * hash + Arrays.deepHashCode(this.Features);
    return hash;
  }   
}
