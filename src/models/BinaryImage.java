package models;

import utility.ImageToolkit;

/**
 * Binary image class that extends BlurredImage and adds binaryImagePixels as a copy so the originals image can still be preserves in blurredPixels and imagePixels.
 * @author mdatla
 *
 */
public class BinaryImage extends Image{
	
	private int threshold;

	private int threshold2;
	
	public BinaryImage(){
		super();
	}
	
	public BinaryImage(int h, int w){
		super(h, w);
	}

	public BinaryImage(Image inImage){
		super(inImage);
	}
	
	public BinaryImage(BinaryImage inImage){
		super(inImage);
		this.threshold = inImage.getThreshold();
		this.threshold2 = inImage.getThreshold2();
	}

	public void convertToBinaryImage(){
		this.byteImage = ImageToolkit.convertToBinaryImage(this.byteImage, this.threshold);
	}
	
	public void convertToBinaryImage2(){
		this.byteImage2 = ImageToolkit.convertToBinaryImage(this.byteImage2, this.threshold2);
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public int getThreshold2() {
		return threshold2;
	}

	public void setThreshold2(int threshold2) {
		this.threshold2 = threshold2;
	}

	
	
}
