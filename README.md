KNN Based Handwriting Recognition
============
David Katz <br>
Harrison Zhao <br>

A handwriting recognition system <br>
Applies KNN learning on 16x8 images to approximate P(letter|image) for each image <br>
Applies the Brown Corpus to compute the MAP estimate for sequences of images representing words <br>
Performs above 93% accuracy for words randomly sampled from the Brown Corpus<br>
Performs above 99% accuracy for words of length greater than 8<br>

To run the server
=================
first get maven and node.js <br>

first, in the directory backend run: make runServer <br>
then, in the directory frontend run: node app.js <br>
make sure port 20000 and port 3000 are open. <br>
Then go to localhost:3000 to see the GUI for spellchecking and word recognition.<br>

To run locally using pre-processed KNN data
=================

Edit the file inputs.txt to contain the words you would like generated <br>
Type make runMainFile to generate an outputs.html file <br>
This file will contain images of words and a prediction for each <br>

Sample Website Outputs
==================
![alt tag](https://raw.github.com/katzdave/SpellChecker/master/sample_hello.png)
![alt tag](https://raw.github.com/katzdave/SpellChecker/master/sample_awesome.png)
![alt tag](https://raw.github.com/katzdave/SpellChecker/master/sample_awesome1.png)

