package processHelpers;

import models.BinaryImage;
import models.BlurredImage;
import models.Image;

public class SingleThresholdFinder {
	
	/**
	 * Method converts blurred image to binary image using bi-guassian
	 * thresholding. Converts a single blurred image at a time.
	 * @param blurredImage
	 * @return
	 */
	public BinaryImage generateBinaryImage(Image img){
		ThresholdFinder tf = new ThresholdFinder();
		BinaryImage bImage = new BinaryImage(img);
		bImage.setThreshold(tf.determineThreshold(img));
		bImage.convertToBinaryImage();
		return bImage;
	}
}
