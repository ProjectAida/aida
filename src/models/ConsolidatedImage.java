package models;

public class ConsolidatedImage extends Image {
	public ConsolidatedImage() {
		super();
	}
	
	public ConsolidatedImage(int h, int w) {
		super(h, w);
	}
	
	public ConsolidatedImage(Image inImage){
		super(inImage);
	}
}
