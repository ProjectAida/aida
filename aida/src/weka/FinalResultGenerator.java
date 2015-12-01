package weka;

import global.Constants;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

/**
 * This class generates the results for the parent images, its accuracies and precision etc by comparing the 
 * names of the snippets identified as positive and an existing list of all true parent images.
 * @author mdatla
 * See written report explaining in detail the process taking place for additional information.
 */
public class FinalResultGenerator {

	public void generateResults(){
		try {
			// Three readers are used to read three separate files. Each file consists intermediate data from WekaAdapter and also the required labelledImages.txt
			BufferedReader truePageReader = new BufferedReader(new FileReader(Constants.data+"TruePages.txt"));    // Reads in files classified as true by WEKA
			BufferedReader falsePageReader = new BufferedReader(new FileReader(Constants.data+"FalsePages.txt"));  // Reads in files classified as false by WEKA
			BufferedReader actualReader = new BufferedReader(new FileReader(Constants.data+"labelledImages.txt")); // Reads in files with their actual accurate classification
			String line2 = null;
			HashSet<String> TrueList = new HashSet<String>();	// HashSets automatically prevent duplication.Acts as a safety net in case our duploication check fails.
			HashSet<String> FalseList = new HashSet<String>();	// These lists are the reference lists to generate the accuracy rates and confusion matrix.
			int actualTrues=0,actualFalses=0;
			while ((line2=actualReader.readLine())!=null){
				if(line2.contains("true.jpg")){
					TrueList.add(line2);
					actualTrues++;
				}else if(line2.contains("false.jpg")){
					FalseList.add(line2);
					actualFalses++;
				}
			}
			String line=null;
			double i=1,truePositives=0,falsePositives=0,trueNegatives=0,falseNegatives=0,total=0;
			HashSet<String> classTrue = new HashSet<String>();
			HashSet<String> classFalse = new HashSet<String>();	// These lists are populated according to the classifer. 
			
			while((line=truePageReader.readLine())!=null){
				classTrue.add(line);
			}
			line=null;
			while((line=falsePageReader.readLine())!=null){
				classFalse.add(line);
			}

			// Remove cross-list duplication. (Removes elements from classFalse that also exist in classTrue
			HashSet<String> falseTemp = new HashSet(classFalse);
			for (String s: classFalse){
				if (classTrue.contains(s)){
					falseTemp.remove(s);
				}
			}
			classFalse = new HashSet(falseTemp);

			// # of truePositives, trueNegative etc are counted here to generate confusion matrix later.
			for(String s: classTrue){
				total++;
				boolean contains = false;
				for (String t : TrueList){
					if(t.contains(s)){
						truePositives++;
						contains = true;					
					}				
				}
					
				if(contains == false){
					falsePositives++;
				}
			}

			for(String s: classFalse){			
				total++;
				boolean contains =false;
				for (String t : FalseList){
					if(t.contains(s)){
						trueNegatives++;
						contains = true;					
					}				
				}
				
				if(contains!=true){
					falseNegatives++;
				}
			}
			
			// Output area. Formulae used are from wikipedia article for "Precision and Recall". More specifically they are taken from 'Terminology and Derivations..' section
			System.out.println("truePositives: "+truePositives);
			System.out.println("falsePositives: "+falsePositives);
			System.out.println("Total Positive Predicted: "+ (truePositives + falsePositives));
			System.out.println("Total Actual Positives"+ actualTrues);
			System.out.println("trueNegatives: "+trueNegatives);
			System.out.println("falseNegatives: "+falseNegatives);

			System.out.println("Total Negative Predicted: "+ (trueNegatives + falseNegatives));
			System.out.println("Total Actual Negatives"+ actualFalses);
			System.out.println("Total Newspapers: "+total);
			System.out.println("Precision: "+(truePositives/(truePositives+falsePositives)));


			System.out.println("True positive rate (Recall): "+(truePositives/(truePositives+falseNegatives))*100+" %");
			System.out.println("False Negative rate:         "+ (falseNegatives/(falseNegatives+truePositives))*100+" %");
			System.out.println("False Positive rate:         "+ (falsePositives/(falsePositives+trueNegatives))*100+" %");
			System.out.println("True Negaive Rate:           "+ (trueNegatives/(trueNegatives+falsePositives))*100+" %");


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
