package blurring;

import featureExtraction.Morphology;
import noiseRemover.verticalBleed;
import models.BinaryImage;
import models.ConsolidatedImage;
import models.Image;
import consolidator.*;
import utility.*;

/**
 * This class is responsible for running consolidated blurring on an image.
 * This method is called by ImageBlurrer.java
 * @author mdatla
 *
 */
public class CustomBlurrerEngine {
	
	private double percentageThreshold;
	private final double whiteThreshold = 0.9;
	
	public CustomBlurrerEngine(){
		percentageThreshold = 0.05;
		
	}
	
	
	public ConsolidatedImage consolidateImage(Image Image){
		ConsolidatedImage retImg = new ConsolidatedImage(Image);
		
		/************************
		 * new solution
		 */
//		erodedPixels = verticalBleed.remove(erodedPixels);
		
		ConsolidationEngine engine = new ConsolidationEngine();
		
		//no recursion
//		retImg.setByteImage(engine.noRecursion(Image));

		//recursion
//		retImg.setByteImage(engine.recursion(Image));

		//combine
		retImg.setByteImage(engine.mixedConsolidation(Image));
	
		/************************
		 * old solution
		 */
		
//		retImg.setByteImage(oldSolution(Image));
		
		return retImg;
	}
	
	private int[][] oldSolution(Image Image) {
		int start = -100;
		int end = Image.getHorizontal() +10;
		boolean startSet = false;
		boolean endSet = false;
		
		double totalCount = 0;
		double percentage = 0;
		
		int[][] newPixels = new int[Image.getVertical()][Image.getHorizontal()];
		for (int i = 0; i < Image.getVertical(); i++) {
			for (int j = 0; j < Image.getHorizontal(); j++) {
				newPixels[i][j] = 0;
			}
		}
		
		for (int j = 0; j < Image.getHorizontal(); j++) {
			
			//start = 0;
			//end = binaryImage.getHorizontal()-1;
			//startSet = false;
			//endSet = false;
			totalCount = 0;
			percentage = 0;
			
			for (int i = 0; i < Image.getVertical(); i++) {
				if(Image.getByteImage()[i][j] == 255){
					totalCount++;
					
				}
			}
			
			
			
			
			percentage = totalCount/(double)Image.getVertical();
			if(percentage > this.whiteThreshold){
				for(int x = 0; x <= Image.getVertical()-1; x++){
					newPixels[x][j] = 255;
					
				}
				
			}
		}

/****************************************************************************************************/
		for (int i = 0; i < Image.getVertical(); i++) {
			
			start = 0;
			end = Image.getHorizontal()-1;
			startSet = false;
			endSet = false;
			totalCount = 0;
			percentage = 0;
			
			for (int j = 0; j < Image.getHorizontal(); j++) {
				if(Image.getByteImage()[i][j] == 0){
					totalCount++;
					if(!startSet){
						if(checkSurroundingPixels(j, i, true,Image)){
							start = j;
							startSet = true;
						}
					}
					
				}
			}
			
			for(int k = Image.getHorizontal()-1; k >= start; k--){
				if(Image.getByteImage()[i][k] == 0){
					totalCount++;
					if(!endSet){
						if(checkSurroundingPixels(k,i,false,Image)){
							end = k;
							endSet = true;
						}
					}
					
				}
				
			}
			
			
			percentage = totalCount/(double)Image.getHorizontal();
			if(percentage > percentageThreshold){
				for(int x = 0; x <= Image.getHorizontal()-1; x++){
					if(x >= start && x <= end){
						newPixels[i][x] = 0;
					} else {
						newPixels[i][x] = 255;
					}
					
				}
				
			} else {
				for(int x = 0; x <= Image.getHorizontal()-1; x++){
					newPixels[i][x] = 255;
					
				}
				
			}
		}
		
		/*************************** Part 2 *********************************************/
		/*
		 * Checking the vertical white pixels is not an effective method for cleaning up. 
		 * White lines will often appear in the middle of blocks of text. This method should
		 * not be used after the black side to side blurring process.
		 * 
		for (int j = 0; j < binaryImage.getHorizontal(); j++) {
			
			//start = 0;
			//end = binaryImage.getHorizontal()-1;
			//startSet = false;
			//endSet = false;
			totalCount = 0;
			percentage = 0;
			
			for (int i = 0; i < binaryImage.getVertical(); i++) {
				if(binaryImage.getBinaryImagePixels()[i][j] == 255){
					totalCount++;
					
				}
			}
			
			
			
			
			percentage = totalCount/(double)binaryImage.getVertical();
			if(percentage > this.whiteThreshold){
				for(int x = 0; x <= binaryImage.getVertical()-1; x++){
					newPixels[x][j] = 255;
					
				}
				
			}
		}
		*/
		
		
		return newPixels;
	}
	
	private boolean checkSurroundingPixels(int column, int row, boolean isStart, Image image){
		return true;
		/*
		double count = 0;
		double num = 0;
		double percentage = 0;
		int i = column;
		if(isStart){
			while(num < 10 && i < image.getHorizontal() - 1){
				if(image.getByteImage()[row][i] == 255){
					count++;
				}
				num++;
				i++;
				
			}
			
		} else {
			while(num < 10 && i >=0){
				if(image.getByteImage()[row][i] == 255){
					count++;
				}
				num++;
				i--;
				
			}
		}
		
		percentage = count/num;
		if(percentage > .5){
			return true;
		} else {
			return false;
		}
		*/
	}
	
}
