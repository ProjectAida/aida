package models;

/**
 * Binary image class that extends BlurredImage and adds binaryImagePixels as a copy so the originals image can still be preserves in blurredPixels and imagePixels.
 * @author mdatla
 *
 */
public class BinaryImage extends BlurredImage{

	protected int[][] binaryImagePixels;
	protected int threshold;

	protected int[][] binaryImagePixels2;
	protected int threshold2;
	
	public BinaryImage(){
		super();
	}
	
	public BinaryImage(BlurredImage inImage){
		this.name = inImage.getName();
		this.byteImage = inImage.getByteImage();
		this.checkValue = inImage.getCheckValue();
		this.containsPoem = inImage.isContainsPoem();
		this.vertical = inImage.getVertical();
		this.horizontal = inImage.getHorizontal();
		this.blurredImagePixels = inImage.getByteImage();
		this.binaryImagePixels = inImage.getByteImage();
		this.jaggedLineMean = inImage.getJaggedLineMean();
		this.jaggedLineStandardDeviation = inImage.getJaggedLineStandardDeviation();
		this.jaggedMax = inImage.getJaggedMax();
		this.jaggedMin = inImage.getJaggedMin();
		this.jaggedRange = inImage.getJaggedRange();
		
		this.marginMean = inImage.getMarginMean();
		this.marginStdDev = inImage.getMarginStdDev();
		this.marginMax = inImage.getMarginMax();
		this.marginMin = inImage.getMarginMin();
		this.marginRange = inImage.getMarginRange();
		
		this.stanzaMean = inImage.getStanzaMean();
		this.stanzaStandardDeviation = inImage.getStanzaStdDev();
		this.stanzaMin = inImage.getStanzaMin();
		this.stanzaMax = inImage.getStanzaMax();
		this.stanzaRange = inImage.getStanzaRange();
		
		this.lengthMean = inImage.getLengthMean();
		this.lengthStdDev = inImage.getLengthStdDev();
		this.lengthMin = inImage.getLengthMin();
		this.lengthMax = inImage.getLengthMax();
		this.lengthRange = inImage.getLengthRange();
	}
	
	public BinaryImage(BinaryImage inImage){
		this.name = inImage.getName();
		this.byteImage = inImage.getByteImage();
		this.checkValue = inImage.getCheckValue();
		this.containsPoem = inImage.isContainsPoem();
		this.vertical = inImage.getVertical();
		this.horizontal = inImage.getHorizontal();
		this.blurredImagePixels = inImage.blurredImagePixels;
		binaryImagePixels = new int[this.vertical][this.horizontal];
	}

	public void convertToBinaryImage(){
		for (int i = 0; i < this.vertical; i++){
			for(int j= 0; j < this.horizontal; j++){
				if(this.blurredImagePixels[i][j] < this.threshold){
					binaryImagePixels[i][j] = 0;
				} else {
					binaryImagePixels[i][j] = 255;
				}
			}
		}	
		
	}
	
	public void convertToBinaryImage2(){
		for (int i = 0; i < this.vertical; i++){
			for(int j= 0; j < this.horizontal; j++){
				if(this.blurredImagePixels2[i][j] < this.threshold2){
					binaryImagePixels2[i][j] = 0;
				} else {
					binaryImagePixels2[i][j] = 255;
				}
			}
		}	
		
	}
	
	public int[][] getBinaryImagePixels() {
		return binaryImagePixels;
	}

	public void setBinaryImagePixels(int[][] binaryImagePixels) {
		this.binaryImagePixels = binaryImagePixels;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public int[][] getBinaryImagePixels2() {
		return binaryImagePixels2;
	}

	public void setBinaryImagePixels2(int[][] binaryImagePixels2) {
		this.binaryImagePixels2 = binaryImagePixels2;
	}

	public int getThreshold2() {
		return threshold2;
	}

	public void setThreshold2(int threshold2) {
		this.threshold2 = threshold2;
	}

	
	
}
