package models;

/**
 * Binary image class that extends Image and adds blurredImagePixels as a copy so the originals image can still be preserves in imagePixels.
 * @author mdatla
 *
 */
public class BlurredImage extends Image{

	public BlurredImage() {
		super();
	}
	
	public BlurredImage(int h, int w) {
		super(h, w);
	}
	
	public BlurredImage(Image inImage){
		super(inImage);
	}
	
	public BlurredImage(Image inImage, int blurLevel){
		super(inImage);
		StringBuilder sb = new StringBuilder();
		sb.append(inImage.getName()).append(blurLevel);
		this.name = sb.toString();
	}

}
