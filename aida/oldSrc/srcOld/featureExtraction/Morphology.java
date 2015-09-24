package featureExtraction;

public class Morphology {

	/**
	 * Removes outlying black pixels from a binary image. 
	 * Usually used iteratively in a loop in conjunction with Dilation.
	 * @param original
	 * @return eroded set of matrix pixels
	 */
	public int[][] Erosion(int[][] original){
		
		int[][] eroded = new int[original.length][original[0].length];
		for(int i = 0; i < original.length;i++){
			for(int j = 0; j < original[0].length; j++){
				eroded[i][j] = 255;
			}
		}
		
		//start at 1 and end at one before last so as to avoid needing to check for
		//edges and corners since they will probably just be white space anyway.
		for(int i = 1; i < original.length - 1; i++){
			for(int j = 1; j < original[0].length - 1; j++){
				if(original[i][j] == 0){
					int counter = 0;
					if(original[i-1][j-1] == 255){
						counter++;
					}
					if(original[i-1][j] == 255){
						counter++;
					}
					if(original[i-1][j+1] == 255){
						counter++;
					}
					if(original[i][j-1] == 255){
						counter++;
					}
					if(original[i][j+1] == 255){
						counter++;
					}
					if(original[i+1][j-1] == 255){
						counter++;
					}
					if(original[i+1][j] == 255){
						counter++;
					}
					if(original[i+1][j+1] == 255){
						counter++;
					}
					
					if(counter > 0){
						eroded[i][j] = 255;
					}else if(counter == 0){
						eroded[i][j] = 0;
					}
				}
			}
		}
		return eroded;
	}
	
	/**
	 * Removes outlying black pixels from a binary image. 
	 * Usually used iteratively in a loop in conjunction with Erosion.
	 * @param original
	 * @return dilated set of matrix pixels
	 */
	public int[][] Dilation(int[][] original){
		
		int[][] dilated = new int[original.length][original[0].length];
		for(int i = 0; i < original.length;i++){
			for(int j = 0; j < original[0].length; j++){
				dilated[i][j] = 255;
			}
		}
		
		//start at 1 and end at one before last so as to avoid needing to check for
		//edges and corners since they will probably just be white space anyway.
		for(int i = 1; i < original.length - 1; i++){
			for(int j = 1; j < original[0].length - 1; j++){
				if(original[i][j] == 255){
					int counter = 0;
					if(original[i-1][j-1] == 0){
						counter++;
					}
					if(original[i-1][j] == 0){
						counter++;
					}
					if(original[i-1][j+1] == 0){
						counter++;
					}
					if(original[i][j-1] == 0){
						counter++;
					}
					if(original[i][j+1] == 0){
						counter++;
					}
					if(original[i+1][j-1] == 0){
						counter++;
					}
					if(original[i+1][j] == 0){
						counter++;
					}
					if(original[i+1][j+1] == 0){
						counter++;
					}
					if(counter > 0){
						dilated[i][j] = 0;
					}
				}
				else{
					dilated[i][j] = 0;
				}
			}
		}
		return dilated;
	}
}
