package processHelpers;

import models.BinaryImage;
import models.BlurredImage;

public class SingleThresholdFinder {
	
	/**
	 * Method converts blurred image to binary image using bi-guassian
	 * thresholding. Converts a single blurred image at a time.
	 * @param blurredImage
	 * @return
	 */
	public BinaryImage generateBinaryImage(BlurredImage blurredImage){
		ThresholdFinder tf = new ThresholdFinder();
		BinaryImage bImage = new BinaryImage(blurredImage);
		tf.createImageHistogram(bImage);
		bImage.setThreshold(tf.determineThreshold());
		bImage.convertToBinaryImage();
		return bImage;
	}
}
