/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hello;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author David
 */
public class Hello {

  /**
   * @param args the command line arguments
   */
    public static void main(String[] args) throws IOException{
            FileWriter fw = new FileWriter(new File("testUnsummed.txt"));
            BufferedReader br = new BufferedReader(new FileReader(new File("testCSV.txt")));

            String line;
                while ((line = br.readLine()) != null) {
                    String[] splat = line.split(",");
                    String cat = splat[1];
                    String res = "";
                    
                    
                    for(int i=6; i<splat.length; i++){
                      res += splat[i] + ",";
                    }
                    res += cat + "\n";
                    fw.write(res);
                    
                    
//                    int cnt = 0;
//                    int flag = 0;
//                    int[][] stuff = new int[16][8];
//                    int[][] more = new int[8][4];
//                    for(int i=6; i<splat.length; i++){
//                                stuff[flag][cnt] = Integer.parseInt(splat[i]);
//                                cnt++;
//                                if(cnt == 8){
//                                        flag++;
//                                        cnt = 0;
//                                }
//                        }
//                        for(int i=0; i<8; i++){
//                                for(int j=0; j<4; j++){
//                                        more[i][j] = 0;
//                                }
//                        }
//                        for(int i=0; i<16; i++){
//                                for(int j=0; j<8; j++){
//                                        more[i/2][j/2] = more[i/2][j/2] + stuff[i][j];
//                                }
//                        }
//
//                        for(int i=0; i<8; i++){
//                                for(int j=0; j<4; j++){
//                                        res += more[i][j] + ",";
//                                }
//                        }
//                    
//                       res += cat + "\n";
//                       fw.write(res);
                }
                    

                fw.close();
                br.close();
    }
  
}
