/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spellcheckerv2;

/**
 * Feature.  For now just a double.  Can expand at some point if necessary
 * @author David
 */
public class Feature {
  double Value;
  
  public Feature(double value){
    Value = value;
  }
  
  @Override
  public boolean equals(Object oth){
    Feature other = (Feature) oth;
    return other.Value == this.Value;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 71 * hash + (int) (Double.doubleToLongBits(this.Value) ^ (Double.doubleToLongBits(this.Value) >>> 32));
    return hash;
  }
  
  @Override
  public String toString(){
    return Double.toString(Value);
  }
}
