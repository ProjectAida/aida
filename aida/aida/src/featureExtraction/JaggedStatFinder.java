package featureExtraction;

import java.util.ArrayList;
import java.util.List;

import models.BinaryImage;

/**
 * @deprecated
 * @author maanas
 *
 */
public class JaggedStatFinder {
	
	List<Double> mBlackLinePercents;

	/**
	 * Constructor method for JaggedStatFinder
	 */
	public JaggedStatFinder(){
		mBlackLinePercents = new ArrayList<Double>();
	}
	
	/**
	 * Controller method that calls the correct sub methods to compute the jaggedness of a line
	 * @param binaryImg
	 */
	public void calcJaggedLinePercent(BinaryImage binaryImg){
		
		generateLineSizes(binaryImg);
		computeJaggednessStatistics(binaryImg);
	}
	
	/**
	 * Method analyzes the percent of a line that is dark pixels to 
	 * compute the jaggedness of the line.
	 * @param binaryImg
	 */
	private void generateLineSizes(BinaryImage binaryImg){
		double blackLineSize = 0;
		
		for(int i = 20; i < binaryImg.getVertical()-20; i++){
			blackLineSize = 0;
			for(int j = 0; j < binaryImg.getHorizontal(); j++){
				if(binaryImg.getBinaryImagePixels()[i][j] == 0){
					
					blackLineSize++;
					
				}
				
			}
			
			blackLineSize = blackLineSize / (binaryImg.getHorizontal() - 40);
			if(blackLineSize > .2){
				mBlackLinePercents.add(blackLineSize);
			}
			
			
		}
	}
	
	private void computeJaggednessStatistics(BinaryImage binaryImg){
		double mean = 0; 
		for(int k = 0; k < mBlackLinePercents.size(); k++){
			mean += mBlackLinePercents.get(k);
		}
		
		mean = mean/mBlackLinePercents.size();
		
		//double mean = getMean();
        double varSum = 0;
        double a = 0;
        for(int j = 0; j < mBlackLinePercents.size(); j++){
        	a = mBlackLinePercents.get(j);
        	varSum += (mean-a)*(mean-a);
        }
        double standardDeviation =  varSum/mBlackLinePercents.size(); // Calculate variance

        standardDeviation = Math.sqrt(standardDeviation); // Convert Variacnce to Standard Deviation
        
        binaryImg.setJaggedLineMean(mean);
        binaryImg.setJaggedLineStandardDeviation(standardDeviation);
		
	}
}
