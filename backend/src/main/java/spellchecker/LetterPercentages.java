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
public class LetterPercentages {
//  double[] percentages =
//  {
//    8.167,
//    1.492,
//    2.782,
//    4.253,
//    12.702,
//    2.228,	
//    2.015,	
//    6.094,
//    6.966,
//    0.153,
//    0.772,
//    4.025,
//    2.406,
//    6.749,
//    7.507,
//    1.929,
//    0.095,
//    5.987,
//    6.327,
//    9.056,
//    2.758,
//    0.978,
//    2.360,
//    0.150,
//    1.974,
//    0.074
//  };
  
  double[] percentages =
  {
    4034,
    1284,
    2114,
    1442,
    4955,
    921,	
    2472,	
    861,
    4913,
    189,
    909,
    3140,
    1602,
    5024,
    3897,
    1377,
    341,
    2673,
    1394,
    2136,
    2562,
    664,
    520,
    413,
    1221,
    1094
  };
  
  LetterPercentages(){
    for(double d : percentages){
      d = d/52152;
    }
  }
  
  double GetPercentage(String l){
    char c = l.charAt(0);
    return percentages[c-'a'];
  }
}
