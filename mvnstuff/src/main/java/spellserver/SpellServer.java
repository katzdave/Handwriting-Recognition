/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spellserver;

import connectionManager.*;

/**
 *
 * @author H
 */
public class SpellServer {
  
  public static void main(String [] args) {
    int serverPort = 20000;
    SpellServerProtocol protocol = new SpellServerProtocol();
    ConnectionManager server = new ConnectionManager(serverPort, protocol);
    server.runManager();
  }
  
}
