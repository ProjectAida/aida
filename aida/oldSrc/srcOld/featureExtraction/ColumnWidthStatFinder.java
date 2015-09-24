package featureExtraction;

import models.BinaryImage;

/**
 * @deprecated
 * @author Joe
 * Copyright 2014
 */
public class ColumnWidthStatFinder {

	public ColumnWidthStatFinder() {

	}
	
	/**
	 * Method inputs a binary image and sends image off to have it's column margins computed.
	 * @param binaryimg
	 * @return successOfMethod
	 */
	public boolean findColumnSizes(BinaryImage binaryimg){
		findLeftColumn(binaryimg);
		findRightColumn(binaryimg);
		return true;
	}
	
	/**
	 * Method finds the size of the left margin by counting the number of columns without a dark
	 * pixel.
	 * @param binaryImg
	 * @return
	 */
	private boolean findLeftColumn(BinaryImage binaryImg){
		boolean marginContinue = true;
		int marginSize = 0;
		while(marginContinue){
			if(analyzeColumn(binaryImg,marginSize)){
				marginSize++;
			} else {
				marginContinue = false;
			}
			
			if(marginSize >= binaryImg.getHorizontal()){
				marginContinue = false;
				
			}
		}
		
		binaryImg.setLeftMarginSize(marginSize);
		return true;
	}
	
	/**
	 * Method finds the size of the right margin by counting the number of columns without a dark
	 * pixel.
	 * @param binaryImg
	 * @return
	 */
	private boolean findRightColumn(BinaryImage binaryImg){
		boolean marginContinue = true;
		int marginSize = 0;
		int columnToUse = binaryImg.getHorizontal() -1;
		while(marginContinue){
			if(analyzeColumn(binaryImg,columnToUse)){
				marginSize++;
				columnToUse--;
			} else {
				marginContinue = false;
			}
			
			if(columnToUse <= 0){
				marginContinue = false;
				
			}
		}
		
		binaryImg.setRightMarginSize(marginSize);
		return true;
	}
	
	private boolean analyzeColumn(BinaryImage binaryImg, int column){
		for(int i = 30; i < binaryImg.getVertical() - 30; i++){
			if(binaryImg.getBinaryImagePixels()[i][column] == 0){
				return false;
			}
		}
		return true;
	}

	
}
