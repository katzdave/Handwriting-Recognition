package spellserver;

import connectionManager.*;
import java.io.BufferedReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import spellchecker.*;

public class SpellServerProtocol extends Protocol{
  
  final int IDINDEX = 1;
  final int STARTINDEX = 2;
  final String DELIM = "~";
  final int NUMFEATURES = 128;
  final int NUMWORDSRETURN = 3;
  
  FeatureVectorContainer TrainingData;
  Spellchecker SpellChecker;
  
  public SpellServerProtocol() {
    super();
    isrunning = true;
    FeatureVectorLoader fvl = new FeatureVectorLoader();
    TrainingData = new FeatureVectorContainer(
            fvl.FeatureVectorsFromTextFile());
    SpellChecker = new Spellchecker(new DictionaryAndFreqs());
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
    String[] outputStrings = convertFeatureVectorsToStrings(featureVectors);
    for (String element : outputStrings)
      sendStr += (DELIM + element);
    sendMessage(connectedID, sendStr);
  }
  
  //converts a list of feature vectors to a list of strings 
  //of most probable words
  String[] convertFeatureVectorsToStrings(List<FeatureVector> listfv) {
    List<TestExample> incoming = new ArrayList<>();
    for(FeatureVector fv : listfv){
      FeatureVector fvs = new FeatureVector(convertFVSize(fv.toString()), 32);
      String s = TrainingData.GetKnnAsString(fvs.toString());
      CategoryFinder acc = new CategoryFinder(1);
      acc.AddListFromString(s);
      CategoryLikelihoodContainer clc = acc.GetCategoryLikelihood();
      TestExample te = new TestExample();
      te.Likelihoods = new LetterLikelihoods(clc);
      te.Original = fv;
      te.Summed = fvs;
      incoming.add(te);
    }
    Word w = new Word(incoming);
    return SpellChecker.SpellcheckWord(w, NUMWORDSRETURN);
  }
  
  String convertFVSize(String s){
    int flag = 0;
    int cnt = 0;
    double[][] stuff = new double[16][8];
    double[][] more = new double[8][4];
    String[] splat = s.split(",");
    String res = "";
    for(int i=0; i<128; i++){
      stuff[flag][cnt] = Double.parseDouble(splat[i]);
      cnt++;
      if(cnt == 8){
        flag++;
        cnt = 0;
      }
    }
    for(int i=0; i<8; i++){
      for(int j=0; j<4; j++){
        more[i][j] = 0;
      }
    }
    for(int i=0; i<16; i++){
      for(int j=0; j<8; j++){
        more[i/2][j/2] = more[i/2][j/2] + stuff[i][j];
      }
    }

    for(int i=0; i<8; i++){
      for(int j=0; j<4; j++){
        res += more[i][j] + ",";
      }
    }
    return res;
  }
  
    public static void main(String [] args){
      String s = "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,0,1,0,0,0,0,1,1,1,0,0,0,0,1,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";
      String s2 = "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,1,1,0,1,1,1,1,0,1,1,0,0,0,0,1,1,1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,1,0,0,0,0,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";
      String s3 = "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,1,0,0,1,0,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,1,1,1,1,0,1,1,0,1,1,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";
      List<FeatureVector> lfv = new ArrayList<>();
      SpellServerProtocol ssp = new SpellServerProtocol();
      lfv.add(new FeatureVector(s,128));
      lfv.add(new FeatureVector(s2,128));
      lfv.add(new FeatureVector(s3,128));
      String[] strings = ssp.convertFeatureVectorsToStrings(lfv);
      System.out.println(strings[0] + " " + strings[1] + " " + strings[2]);
      //Correct word is now!
    }
}