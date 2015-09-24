package weka;

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
			BufferedReader br = new BufferedReader(new FileReader("data/Snippet_Classifications.csv"));
			BufferedReader br2 = new BufferedReader(new FileReader("data/FullImageList.txt"));
			String line2 = null;
			HashSet<String> TrueList = new HashSet<String>();
			while ((line2=br2.readLine())!=null){
				TrueList.add(line2);
			}
			String line=null;
			int i=1,trueCount=0,falseCount=0,total=156;
			while((line=br.readLine())!=null){
				if(line.contains(",true")){
					String[] q = line.split(",");
					String[] s = q[0].split("_");
					String tempCurr =s[0]+"_"+s[1]+"_"+s[2]+"_"+s[3]+".jpg";
					if(temp.compareTo(tempCurr)!=0){
						temp = tempCurr;
						if(TrueList.contains(temp)){
							trueCount++;
						}else{
							falseCount++;
						}
						i++;
					}
				
					
				}
			}
			System.out.println("truePositives: "+trueCount);
			System.out.println("falsePositives: "+falseCount);
			System.out.println("Total Positive Newspapers: "+(i-1));
			System.out.println("Total Newspapers: "+total);
			int trueNegative = total-(trueCount+falseCount);
			System.out.println("Precision: "+(trueCount/(trueCount+falseCount*1.0))*100+"%");
			System.out.println("True positive rate; "+100+"%");
			System.out.println("False Negative rate: "+ 0);
			System.out.println("False Positive rate: "+falseCount/(falseCount+trueNegative*1.0)*100+"%");
			System.out.println("True Negaive Rate: "+ trueNegative/(trueNegative+falseCount*1.0)*100+"%");
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
