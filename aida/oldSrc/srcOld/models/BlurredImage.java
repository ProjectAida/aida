package models;

/**
 * Binary image class that extends Image and adds blurredImagePixels as a copy so the originals image can still be preserves in imagePixels.
 * @author mdatla
 *
 */
public class BlurredImage extends Image{
	
	protected int[][] blurredImagePixels;
	protected int[][] blurredImagePixels2;
	public BlurredImage(){
		super();
	}
	
	public BlurredImage(Image inImage){
		this.name = inImage.getName();
		this.byteImage = inImage.getByteImage();
		this.checkValue = inImage.getCheckValue();
		this.containsPoem = inImage.isContainsPoem();
		this.vertical = inImage.getVertical();
		this.horizontal = inImage.getHorizontal();
		this.byteImage = inImage.getByteImage();
		
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
	
	public BlurredImage(Image inImage, int blurLevel){
		StringBuilder sb = new StringBuilder();
		sb.append(inImage.getName()).append(blurLevel);
		this.name = sb.toString();
		this.byteImage = inImage.getByteImage();
		this.checkValue = inImage.getCheckValue();
		this.containsPoem = inImage.isContainsPoem();
		this.vertical = inImage.getVertical();
		this.horizontal = inImage.getHorizontal();
	}

	public int[][] getBlurredImagePixels() {
		return blurredImagePixels;
	}

	public void setBlurredImagePixels(int[][] blurredImagePixels) {
		this.blurredImagePixels = blurredImagePixels;
	}

	public int[][] getBlurredImagePixels2() {
		return blurredImagePixels2;
	}

	public void setBlurredImagePixels2(int[][] blurredImagePixels2) {
		this.blurredImagePixels2 = blurredImagePixels2;
	}
	
	
	
	
}
