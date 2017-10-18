package processHelpers;

import models.Image;
import binarizator.BinarizationEngine;

public class ThresholdFinder {
	
	protected final int THRESHING_WND_OFFSET = 3;
	protected final int THRESHING_SIGMA = 3;
	
	public ThresholdFinder(){

	}

	/**
	 * Method determines the threshold for the blurred image. Threshold value found by
	 * processing the image's histogram using bi-gaussian processing.
	 * @return threshold
	 */
	public int determineThreshold(Image img){
		//D-Lib solution (old solution)
//		return BinarizationEngine.dlibBinMethod(this.mean, this.histogram);
		
		//Otsu's method (new solution)
		return BinarizationEngine.otsuMethod(img.getRdf());
		
		//3-mean method (foreground-bleed_through-background signals)
//		return BinarizationEngine.threeMeanBinMethod(img.getHist());
	}

}
