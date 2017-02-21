package featureExtraction;

import java.util.ArrayList;
import java.util.List;

import models.BinaryImage;

/**
 * @deprecated
 * @author Joe
 * Copyright 2014
 */
public class StanzaStatFinder {

	List<Integer> mWhiteSpaceList;
	
	public StanzaStatFinder(){
		mWhiteSpaceList = new ArrayList<Integer>();
	}
	
	/**
	 * Controller method called to find the vertical white space sizes of an image.
	 * @param binaryImg
	 */
	public void findStanzas(BinaryImage binaryImg){
		findStanzasSizes(binaryImg);
		generateStanzaStatistics(binaryImg);
		
	}
	
	
	
	private void findStanzasSizes(BinaryImage binaryImg){
		int whiteSpaceSize = 0;
		for (int i = 20; i < binaryImg.getVertical()-20; i++) {

			for (int j = 20; j < binaryImg.getHorizontal()-20; j++) {

				if(binaryImg.getBinaryImagePixels()[i][j] == 255){
					whiteSpaceSize++;
				} else {
					if(whiteSpaceSize != 0){
						mWhiteSpaceList.add(whiteSpaceSize);
						whiteSpaceSize = 0;
						
					} else {
						
					}
				}
				
			}
		}
		
	}
	
	private void generateStanzaStatistics(BinaryImage binaryImg){
		int size = mWhiteSpaceList.size();
		
		double mean = 0;
		
		for(int i = 0; i < size; i++){
			
			mean += mWhiteSpaceList.get(i);
		}
		
		mean = mean/size;
		
        double varSum = 0;
        double a = 0;
        for(int j = 0; j < size; j++){
        	a = mWhiteSpaceList.get(j);
        	varSum += (mean-a)*(mean-a);
        }
        double standardDeviation =  varSum/size;

        standardDeviation = Math.sqrt(standardDeviation);
        
        binaryImg.setStanzaMean(mean);
        binaryImg.setStanzaStandardDeviation(standardDeviation);
		
		
	}
}
