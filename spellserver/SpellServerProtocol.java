package spellserver;

import connectionManager.*;
import spellchecker.FeatureVector;
import java.io.BufferedReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SpellServerProtocol extends Protocol{
  
  final int IDINDEX = 1;
  final int STARTINDEX = 2;
  final String DELIM = "~";
  final int NUMFEATURES = 128;
  
  public SpellServerProtocol() {
    super();
    isrunning = true;
  }

  //returns true to accept a connection
  @Override
  public boolean processAcceptorMessages(int numConnections, 
                                         BufferedReader incomingStream, 
                                         Socket cSocket) {
    return true;
  }
  
  @Override
  public void connect() {
    //intentionally left empty
  }
  

  //need to be able to send message to all
  @Override
  public void sendMessage(int connectedID, String message) {
    try {
      outgoingMessages.put(new Message(connectedID, message));
    } catch (InterruptedException e) {
      System.err.println(
              "Unable to send message: " + message + " to" + connectedID);
    }
  } 

  @Override
  public void handleDisconnection(int connectedID) {
    System.out.println("The webserver disconnected!");
  }

  /**
   * Receives message of form W~ID~[feature vector]~[feature vector]~...
   */
  @Override
  public void processManagerMessages(Message message) {
    System.out.println("received msg: " + message.message);
    String [] messagePieces;
    messagePieces = message.message.split(DELIM);
    switch(messagePieces[0].charAt(0)) {
      case 'W':
        processWord(message.connectedID, messagePieces);
        break;
      default:
        System.out.println("Invalid Message");
    }
    sendMessage(message.connectedID, message.message);
  }
  
  void processWord(Integer connectedID, String [] messagePieces) {
    String sendStr = messagePieces[IDINDEX];
    List<FeatureVector> featureVectors;
    featureVectors = new ArrayList<>();
    for (int i = STARTINDEX; i != messagePieces.length; ++i)
      featureVectors.add(new FeatureVector(messagePieces[i], NUMFEATURES));
    List<String> outputStrings = convertFeatureVectorsToStrings(featureVectors);
    for (String element : outputStrings)
      sendStr += (DELIM + element);
    sendMessage(connectedID, sendStr);
  }
  
  //converts a list of feature vectors to a list of strings 
  //of most probable words
  List<String> convertFeatureVectorsToStrings(List<FeatureVector> listfv) {
    return null;
  }
}
