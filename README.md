## Contents ##
* Introduction
* Requirements
* Installation
* Directories and Files
* Notes

## Introduction ##
Creates image snippets from page images of historic newspaper pages, processes those snippets to prepare them for feature extraction, extracts feature values, and then determines if an image snippet contains poetic content using a Multilayer Perception Artificial Neural Network trained on feature values.

## Requirements ##
* Java Runtime 1.5 or higher
* Unix based system architecture

## Running ##
Move directory 'aida/' into desired location.

### Training ###
1. If necessary (no image snippets yet exist or more are needed), create snippets from full newspaper pages that are both TRUE (include poetic content) and FALSE (no poetic content). This can be done manually or by running the RunPageSegmentation program described below. If the snippets are created automatically, they will still need to be manually inspected and labeled as true or false.
2. Make sure there are no files in data/Analysis named either TrainList.arff or TestList.arff. If there are files in data/Analysis with these names, remove them, rename them, or move them into a sub-directory or external directory.
3. In the data directory, clear out the following files (leave the files, but they should have no content):
  * FailedList.txt
  * FalsePages.txt
  * FalseSnippets.txt
  * imageFailedNeedHuman.txt
  * imagePassed.txt
  * SnippetNameList.txt
  * snippetPathList.txt
  * successfulSegments.txt
  * TruePages.txt
  * TrueSnippets.txt
4. Make sure that the following directories are empty, and if they are not, then remove all contents (backing up elsewhere, if so desired):
  * Output_Binary
  * Output_Custom
  * Output_Snippets
5. Upload the training snippets to Output_Snippets
6. Copy/paste all of the image filenames of the training snippets, with one filename/line into labelledImages.txt. Append the label \_true to all true image filenames and \_false to all false image filenames before the file extension. You may prefer to append these labels to the files themselves, so that you can simply output a directory listing to the file, with the labels already in place. One way or another, labelledImages.txt needs to have every filename from Output_Snippets with either a \_true or \_false label appended prior to the file extension.
7. Open **src/execute/RunProgram.java**, and in line 50, make sure **int whatSet** is set to **"train."** The line should read: `int whatSet = train;`
8. At the aida root directory, execute the runImageProcess.sh file: `./runImageProcess.sh`
9. Copy the data directory to a new directory with a different name, to preserve all of the input and output data (e.g., cp -r data data-code-run-yyy-mm-dd-hhmm). Create a readme file that explains any necessary variables.

### Testing ###

1. Make sure there is a TrainList.arff file in data/Analysis. If there is already a TestList.arff file, remove it, rename it, or move it to a sub-directory or external directory.  
2. In the data directory, clear out the following files (leave the files, but they should have no content):
  * FailedList.txt
  * FalsePages.txt
  * FalseSnippets.txt
  * imageFailedNeedHuman.txt
  * imagePassed.txt
  * SnippetNameList.txt
  * snippetPathList.txt
  * successfulSegments.txt
  * TruePages.txt
  * TrueSnippets.txt  
3. Make sure that the following directories are empty, and if they are not, then remove all contents (backing up elsewhere, if so desired):
  * FullPages
  * Output_Binary
  * Output_Custom
  * Output_Snippets  
4. Upload the newspaper page images to the FullPages directory. Images should be located in at least two levels of sub-directories below (e.g., 'paper' and 'issue').  Testing works with full newspaper page images, not snippets.
5. Open **src/execute/RunProgram.java**, and in line 50, make sure **int whatSet** is set to **"test."** The line should read: `int whatSet = test;`  
6. Determine which script you want to run. There are three possible scripts to run:
  * RunAll.sh: segments full page images of historic newspapers, processes snippets to prepare them for feature extraction, extracts feature values, and classifies image snippets as True (contains poetic content) or False (does not contain poetic content)
  * RunPageSegementation.sh: segments full page images of historic newspapers and saves snippets for later processing
  * runImageProcess.sh: processes existing snippets to prepare them for feature extraction, extracts feature values, and classifies image snippets as True (contains poetic content) or False (does not contain poetic content)  
7. Specify desired options, if you will be running either **RunAll.sh** or **runImageProcess.sh**:   
In the script you want to run (either **RunAll.sh** or **runImageProcess.sh**), find the line containing `java execute/RunProgram <flag1> <flag2> $i`  
  * update flag1 to one of two values, '357' or '5', to specify which blurring to use
  * update flag2 to one of two values, 'C' or 'R', to specify whether to use regular blurring or the consolidation technique
**Example**: `java execute/RunProgram 5 C $i`
8. Run desired script:
  * To run entire program, run the script with the command `./RunAll.sh` in a terminal while in the parent directory.
  * To run only the subset of the program that segments full page images of historic newspapers and saves snippets for later processing, run the script with the command `./RunPageSegementation.sh` in a terminal while in the parent directory.
  * To run only the subset of the program that processes existing snippets by performing blurring and/or consolidation, feature extraction, and classification processes, run the script with the command `./runImageProcess.sh` in a terminal while in the parent directory.
9. Copy the data directory to a new directory with a different name, to preserve all of the input and output data (e.g., cp -r data data-code-run-yyy-mm-dd-hhmm). Create a readme file that explains any necessary variables.

## Directories and Files ##
aida  
+ data  
    - Analysis: Folder containing .arff files  
    - Output_Snippets: Folder contains snippets generated from segmentation algorithm  
    - Output_Custom: Folder contains custom blurred images  
    - Output_Binary: Folder contains binary images  
    - FullPages: Folder for full newspaper page images  
+ src : Folder containing all source code.  
    - blurring: Package containing classes responsible for different types of blurring  
    - execute: Package containing classes responsible execution of various processes.  
    - featureExtraction: Package containing classes responsible for analyzing features.  
    - global: Package contains Constants class that holds the filepaths for various locations.  
    - models: Package contains Model classes that represents various types of images.  
    - processHelpers : Package contains classes responsible for determining thresholds.  
+ weka: Package containing classes responsible for weka ANN and results.  
    - weka_jar: directory contains the .jar files for weka.  
+ RunAll.sh: Runs all processes  
+ runImageProcess.sh: image process only  
+ runSegmentation.sh: segmentation algorithm only  

### Output Files ###
All output files created by the program are created in the data directory:
* Snippets created from images in FullPages/ are placed in Output_Snippets/ Binarized and consolidated versions of the snippets are placed in Output_Binary/ and Output_Custom/
* File names of pages that pass the segmentation process are written to imagePassed.txt
* File names of pages that fail the segmentation process are written to imageFailedNeedHuman.txt
* A list of snippet names is created and placed in SnippetNameList.txt
* A list of filepaths for these files is created and placed in snippetPathList.txt
* During testing, the file names of snippets classified as true are written to TrueSnippets.txt
* During testing, the file names of snippets classified as false are written to FalseSnippets.txt
* During testing, the snippet classifications recorded in TrueSnippets.txt and FalseSnippets.txt are used to create page-level classifications. The file names of pages that contain poetry, based on the classification of snippets, are written to TruePages.txt. The file names of pages that do not contain poetry, based on the classification of snippets, are written to FalsePages.txt.
* .arff outputs are stored in the Analysis sub-directory of data/.

## Notes ##
* If using IDE, run the command line script first before running the file in
  the IDE.
