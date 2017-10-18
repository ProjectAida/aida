package models;

import utility.ImageToolkit;

public class AdjustedImage extends Image{

	public AdjustedImage() {
		super();
	}
	
	public AdjustedImage(Image inImage) {
		super(inImage);
	}
	
	public int AdjustContrast(){
		this.byteImage = ImageToolkit.histEqualization(this.byteImage, this.cdf);
		return 0;
	}

}
