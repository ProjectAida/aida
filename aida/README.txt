CONTENTS OF THIS FILE
----------------------

* Introduction
* Requirements
* Installation


INTRODUCTION
------------
AIDA Project procedurally analyzes image archives of newspaper and 
determines if that particular image contains a poem using a Multilayer Perception
Artificial Neural Network

REQUIREMENTS
------------

* Java Runtime 1.5 or higher
* Unix based system architecture

INSTALLATION
------------

1. Move directory 'PoemIdentifier_Full_v1.0' into desired location.
2. edit Constants.java in 'src/global'and change filepaths to PoemIdentifier
   depending on the location of the directory. The program uses these filepaths
   to find the appropriate files.
3. Place Full size newspaper images into 'data/Newspaper Images'
4. Pipe the filenames into 'Image_lists/FullImageList.txt'
5. Open a terminal in 'PoemIdentifier_Full_v1.0/' and run the script 'RunAll.sh'
   by typing './RunAll.sh' into the terminal.

NOTES
-----
* .arff outputs are stored in data/Analysis.
* Standard out is used for final results.
* If using IDE, run the command line script first before running the file in
  the IDE.