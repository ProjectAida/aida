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
 *
 */
public class FinalResultGenerator {

	public void generateResults(){
		String temp= "";
		try {
			BufferedReader truePageReader = new BufferedReader(new FileReader(Constants.data+"TruePages.txt"));
			BufferedReader falsePageReader = new BufferedReader(new FileReader(Constants.data+"FalsePages.txt"));
			BufferedReader actualReader = new BufferedReader(new FileReader(Constants.data+"labelledImages.txt"));
			String line2 = null;
			HashSet<String> TrueList = new HashSet<String>();
			HashSet<String> FalseList = new HashSet<String>();
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
			HashSet<String> classFalse = new HashSet<String>();
			
			while((line=truePageReader.readLine())!=null){
				classTrue.add(line);
			}
			line=null;
			while((line=falsePageReader.readLine())!=null){
				classFalse.add(line);
			}

			
			HashSet<String> falseTemp = new HashSet(classFalse);
			for (String s: classFalse){
				if (classTrue.contains(s)){
					falseTemp.remove(s);
				}
			}
			classFalse = new HashSet(falseTemp);

			
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
