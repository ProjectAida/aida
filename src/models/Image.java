package models;

import global.Constants;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;

/**
 * Image class to serve as a model of the desired attributes and stores bytepixels of image in byteImage
 * @author mdatla
 *
 */
public class Image {
    
    private int COLUMN_COUNT_MAX = 50;
    private int WHITE_COLUMN_SEPARATION_MIN = 150;
    private int BLACK_COLUMN_SEPARATION_MIN = 125;
    private int EDGE_COLUMN_DISTANCE_MAX = 100;
    private int COLUMN_SEPARATION_MIN = 75;
    
    public int[][] byteImage;
    protected int[][] byteImage2;
    
    protected String name;
    protected String parentName;
    protected boolean containsPoem;
    protected boolean checkValue;
    protected int vertical;
    protected int horizontal;
    
    protected double stanzaMean;
    protected double stanzaStandardDeviation;
    protected double stanzaMin;
    protected double stanzaMax;
    protected double stanzaRange;
    
    protected double jaggedLineMean;
    protected double jaggedLineStandardDeviation;
    protected double jaggedMin;
    protected double jaggedMax;
    protected double jaggedRange;
    
    protected double marginMean;
    protected double marginStdDev;
    protected double marginMin;
    protected double marginMax;
    protected double marginRange;
    
    protected double lengthMean;
    protected double lengthStdDev;
    protected double lengthMin;
    protected double lengthMax;
    protected double lengthRange;
    
    protected ArrayList<Integer> columnBreaks;
    
    
    
    public double getLengthMean() {
        return lengthMean;
    }
    
    public void setLengthMean(double lengthMean) {
        this.lengthMean = lengthMean;
    }
    
    public double getLengthStdDev() {
        return lengthStdDev;
    }
    
    public void setLengthStdDev(double lengthStdDev) {
        this.lengthStdDev = lengthStdDev;
    }
    
    public double getLengthMin() {
        return lengthMin;
    }
    
    public void setLengthMin(double lengthMin) {
        this.lengthMin = lengthMin;
    }
    
    public double getLengthMax() {
        return lengthMax;
    }
    
    public void setLengthMax(double lengthMax) {
        this.lengthMax = lengthMax;
    }
    
    public double getLengthRange() {
        return lengthRange;
    }
    
    public void setLengthRange(double lengthRange) {
        this.lengthRange = lengthRange;
    }
    protected int blurLevel;
    
    public double getStanzaMin() {
        return stanzaMin;
    }
    
    public void setStanzaMin(double stanzaMin) {
        this.stanzaMin = stanzaMin;
    }
    
    public double getStanzaMax() {
        return stanzaMax;
    }
    
    public void setStanzaMax(double stanzaMax) {
        this.stanzaMax = stanzaMax;
    }
    
    public double getStanzaRange() {
        return stanzaRange;
    }
    
    public void setStanzaRange(double stanzaRange) {
        this.stanzaRange = stanzaRange;
    }
    
    public double getJaggedMin() {
        return jaggedMin;
    }
    
    public void setJaggedMin(double jaggedMin) {
        this.jaggedMin = jaggedMin;
    }
    
    public double getJaggedMax() {
        return jaggedMax;
    }
    
    public void setJaggedMax(double jaggedMax) {
        this.jaggedMax = jaggedMax;
    }
    
    public double getJaggedRange() {
        return jaggedRange;
    }
    
    public void setJaggedRange(double jaggedRange) {
        this.jaggedRange = jaggedRange;
    }
    
    public double getMarginMin() {
        return marginMin;
    }
    
    public void setMarginMin(double marginMin) {
        this.marginMin = marginMin;
    }
    
    public double getMarginMax() {
        return marginMax;
    }
    
    public void setMarginMax(double marginMax) {
        this.marginMax = marginMax;
    }
    
    public double getMarginRange() {
        return marginRange;
    }
    
    public void setMarginRange(double marginRange) {
        this.marginRange = marginRange;
    }
    
    public double getMarginMean() {
        return marginMean;
    }
    
    public void setMarginMean(double marginMean) {
        this.marginMean = marginMean;
    }
    
    public double getMarginStdDev() {
        return marginStdDev;
    }
    
    public void setMarginStdDev(double marginStdDev) {
        this.marginStdDev = marginStdDev;
    }
    protected double leftMarginSize;
    protected double rightMarginSize;
    
    public void setParentName(String s){
        this.parentName = s;
    }
    
    public String getParentName(){
        return this.parentName;
    }
    
    public Image(){
        this.name = "";
        this.containsPoem = false;
        this.checkValue = false;
        this.vertical = 0;
        this.horizontal = 0;
        this.stanzaMean = 0;
        this.stanzaStandardDeviation = 0;
        this.blurLevel = 3;
        this.jaggedLineMean = 0;
        this.jaggedLineStandardDeviation = 0;
        this.leftMarginSize = 0;
        this.rightMarginSize = 0;
        
    }
    
    public Image(int h, int w){
        this.name = "";
        this.containsPoem = false;
        this.checkValue = false;
        this.vertical = h;
        this.horizontal = w;
        this.stanzaMean = 0;
        this.stanzaStandardDeviation = 0;
        this.blurLevel = 3;
        this.jaggedLineMean = 0;
        this.jaggedLineStandardDeviation = 0;
        this.leftMarginSize = 0;
        this.rightMarginSize = 0;
        this.byteImage = new int[h][w];
        this.byteImage2 = new int[h][w];
    }
    
    public int[][] getByteImage2() {
        return byteImage2;
    }
    
    public void setByteImage2(int[][] byteImage2) {
        this.byteImage2 = byteImage2;
    }
    
    public double getLeftMarginSize() {
        return leftMarginSize;
    }
    
    public void setLeftMarginSize(double leftMarginSize) {
        this.leftMarginSize = leftMarginSize;
    }
    
    public double getRightMarginSize() {
        return rightMarginSize;
    }
    
    public void setRightMarginSize(double rightMarginSize) {
        this.rightMarginSize = rightMarginSize;
    }
    
    public double getJaggedLineMean() {
        return jaggedLineMean;
    }
    
    public void setJaggedLineMean(double jaggedLineMean) {
        this.jaggedLineMean = jaggedLineMean;
    }
    
    public double getJaggedLineStandardDeviation() {
        return jaggedLineStandardDeviation;
    }
    
    public void setJaggedLineStandardDeviation(double jaggedLineStandardDeviation) {
        this.jaggedLineStandardDeviation = jaggedLineStandardDeviation;
    }
    
    public int getVertical() {
        return vertical;
    }
    
    public void setVertical(int vertical) {
        this.vertical = vertical;
    }
    
    public int getHorizontal() {
        return horizontal;
    }
    
    public void setHorizontal(int horizontal) {
        this.horizontal = horizontal;
    }
    
    
    
    public int getBlurLevel() {
        return blurLevel;
    }
    
    public void setBlurLevel(int blurLevel) {
        this.blurLevel = blurLevel;
    }
    
    public double getStanzaMean() {
        return stanzaMean;
    }
    
    public void setStanzaMean(double stanzaMean) {
        this.stanzaMean = stanzaMean;
    }
    
    public double getStanzaStdDev() {
        return stanzaStandardDeviation;
    }
    
    public void setStanzaStandardDeviation(double stanzaStandardDeviation) {
        this.stanzaStandardDeviation = stanzaStandardDeviation;
    }
    
    public Image(String fileName, boolean checkFigure){
        this.name = fileName;
        this.checkValue = checkFigure;
        
    }
    
    public boolean getCheckValue() {
        return checkValue;
    }
    public void setCheckValue(boolean checkValue) {
        this.checkValue = checkValue;
    }
    public int[][] getByteImage() {
        return byteImage;
    }
    public void setByteImage(int[][] byteImage) {
        this.byteImage = byteImage;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isContainsPoem() {
        return containsPoem;
    }
    public void setContainsPoem(boolean containsPoem) {
        this.containsPoem = containsPoem;
    }
    public ArrayList<Integer> getColumnBreaks(){
        return columnBreaks;
    }
    public void setColumnBreaks(ArrayList<Integer> columns){
        this.columnBreaks = columns;
    }
    /**
     * Method used to find the separation columns of a newspaper.
     * These columns can be represented visually by either whitespace separating text columns or
     * by continuous, straight black lines.
     */
    public int findColumnBreaks(){
        
        ArrayList<Integer> whiteColumns = new ArrayList<Integer>();
        int whiteCount;
        int columnCount = 0;
        for(int j = 0; j < this.horizontal; j++){
            whiteCount = 0;
            for(int i = 0; i < this.vertical; i++){
                if(this.byteImage[i][j] == 255){
                    whiteCount++;
                }
            }
            if(whiteCount >= (this.vertical*.9)){
                columnCount++;
                if(columnCount < COLUMN_COUNT_MAX){
                    whiteColumns.add(j);
                }
            }else if(whiteCount < (this.vertical*.9)){
                if(whiteColumns.contains(j-1)){
                    columnCount = 0;
                }else if(!whiteColumns.contains(j-1) && columnCount >= COLUMN_COUNT_MAX){
                    columnCount = 0;
                }
            }
        }
        
        
        ArrayList<Integer> columns = new ArrayList<Integer>();
        int marker = 0;
        /*Search for the part of the array that makes a large jump, this indicates the end of
         *a section of white columns and the beginning of a new section. Once found we find the
         *middle index of the section of white columns and then set the marker to be the first
         *white column of the next section of white columns.
         */
        for(int k = 0; k < whiteColumns.size()-1; k++){
            if(whiteColumns.get(k+1) - whiteColumns.get(k) > WHITE_COLUMN_SEPARATION_MIN){
                int index = k-((k - marker)/2);
                columns.add(whiteColumns.get(index));
                marker = k+1;
            }
            //Special condition used to find middle point of the final batch of white columns
            //(the far right hand side of the newspaper)
            if(k+1 == whiteColumns.size()-1){
                int index = (k+1)-(((k+1) - marker)/2);
                columns.add(whiteColumns.get(index));
            }
        }
        
        //Using white columns often isn't enough, so we check for continuous
        //black lines as well to indictate a column break.
        ArrayList<Integer> blackColumns = new ArrayList<Integer>();
        for(int m = 0; m < this.horizontal; m++){
            int blackContinuous = 0;
            int maxBlack = 0;
            int prev = 255;
            for(int n = 0; n < this.vertical; n++){
                if(this.byteImage[n][m] == 0 && prev == 0){
                    blackContinuous++;
                }else if(this.byteImage[n][m] != 0 && prev == 0){
                    if(maxBlack < blackContinuous){
                        maxBlack = blackContinuous;
                    }
                    blackContinuous = 0;
                    prev = 255;
                }else if(this.byteImage[n][m] == 0 && prev == 255){
                    blackContinuous++;
                    prev = 0;
                }
            }
            if(maxBlack >= (this.vertical * .1) && m > columns.get(0)){
                blackColumns.add(m);
            }
        }
        marker = 0;
        for(int k = 0; k < blackColumns.size()-1; k++){
            if(blackColumns.get(k+1) - blackColumns.get(k) > BLACK_COLUMN_SEPARATION_MIN){
                int index = k-((k - marker)/2);
                columns.add(blackColumns.get(index));
                marker = k+1;
            }
        }
        Collections.sort(columns);
        
        ArrayList<Integer> columnsToAdd = new ArrayList<Integer>();
        ArrayList<Integer> columnsToRemove = new ArrayList<Integer>();
        
        //Determine extraneuous columns to remove
        int index = 0;
        while(columns.get(index) < EDGE_COLUMN_DISTANCE_MAX){
            columnsToRemove.add(columns.get(index));
            index++;
        }
        index = 1;
        while(this.horizontal-columns.get(columns.size()-index) < EDGE_COLUMN_DISTANCE_MAX){
            columnsToRemove.add(columns.get(columns.size()-index));
            index++;
        }
        columns.removeAll(columnsToRemove);
        columnsToRemove.clear();
        
        //If columns are very close together remove the two columns and use
        //the average of the two for the final list of columns
        for(int p = 0; p < columns.size()-1; p++){
            if(columns.get(p+1)-columns.get(p) < COLUMN_SEPARATION_MIN){
                columnsToRemove.add(columns.get(p));
                columnsToRemove.add(columns.get(p+1));
                int middle = columns.get(p+1) - ((columns.get(p+1)-columns.get(p))/2);
                columnsToAdd.add(middle);
            }
        }
        
        columns.removeAll(columnsToRemove);
        columns.addAll(columnsToAdd);
        Collections.sort(columns);
        columnsToRemove.clear();
        
        //Rule 1: check for no columns found
        if(columns.size() == 0){
            //columns.add(0,0);
            //columns.add(this.horizontal);
            this.setColumnBreaks(columns);
            return 1;
        }
        //Rule 2: check for 2 columns
        else if(columns.size()==1){
            //columnWidth.add(columns.get(0));
            //columnWidth.add(this.horizontal - columns.get(0));
            columns.clear();
            columns.add(0,0);
            columns.add(this.horizontal/2);
            columns.add(this.horizontal);
        }
        // columns.size() is 2
        else if (columns.size()==2){
            // columns(0) is around the center
            if((this.horizontal/2.0)*(9.0/10.0) < columns.get(0) && columns.get(0) < (this.horizontal/2.0)*(11.0/10.0)){
                columns.add(0,0);
            }
            // columns(1) is around the center
            else{
                columns.add(this.horizontal);
            }
        }
        
        // Collect column info
        ArrayList<Integer> columnWidth = new ArrayList<Integer>();
        
        for(int p = 0; p < columns.size()-1; p++){
            columnWidth.add(columns.get(p+1)-columns.get(p));
        }
        Collections.sort(columnWidth);
        //Rule 3: check if columns are on more than half of the page
        
        if(columns.get(0)>this.horizontal/2 || columns.get(columns.size()-1) < horizontal/2){
            this.setColumnBreaks(columns);
            return 3;
        }
        
        int numOfColumnWidths = columnWidth.size();
        
        int columnWidthMean = 0;
        for(int width : columnWidth){
            columnWidthMean += width;
        }
        columnWidthMean = columnWidthMean/numOfColumnWidths;
        int columnWidthVarience = 0;
        for(int width : columnWidth){
            int temp = width - columnWidthMean;
            columnWidthVarience += Math.pow(temp,2);
        }
        columnWidthVarience = columnWidthVarience/numOfColumnWidths;
        double columnWidthStdDev = Math.ceil(Math.sqrt(columnWidthVarience));
        System.out.println("Std Dev: "+columnWidthStdDev);
        
        //Rule 4: Check column width Std Dev. Good images were experimentally determined to be below 150 Std Dev.
        if(columnWidthStdDev > 150) {
            this.setColumnBreaks(columns);
            return 4;
        }
        
        //Add in columns based on the average width, columns added from the right hand side
        int averageWidth = columnWidth.get((int) Math.floor(columnWidth.size()/2));
        for(int p = columns.size()-1; p >= 1; p--){
            if(columns.get(p-1) < columns.get(p)-averageWidth-COLUMN_SEPARATION_MIN){
                columns.add(p, columns.get(p)-averageWidth);
                p++;
            }else if(columns.get(p-1) > columns.get(p)-averageWidth+COLUMN_SEPARATION_MIN){
                columns.remove(p-1);
                if(columns.get(p-1)-averageWidth > 0){
                    columns.add(p-1, columns.get(p-1)-averageWidth);
                }
            }
        }
        
        //If no edge column is found, insert the column
        while(columns.get(0) > this.horizontal*.1 && columns.get(0)-averageWidth > 0){
            columns.add(0, columns.get(0)-averageWidth);
        }
        while(columns.get(columns.size()-1) < this.horizontal-(this.horizontal*.1) && columns.get(columns.size()-1)+averageWidth < this.horizontal){
            columns.add(columns.get(columns.size()-1)+averageWidth);
        }
        
        
        System.out.println(columns);
        
        //stores the column breaks list in the class Image
        this.setColumnBreaks(columns);
        return 0;
    }
    /**
     * Method to output an image with red lines indicating the column breaks
     */
    public void showColumnBreaks(){
        BufferedImage OutputImage = new BufferedImage(this.horizontal,this.vertical,BufferedImage.TYPE_INT_RGB);
        int i = 1;
        int marker = this.columnBreaks.get(0);
        int value = 0;
        for (int x = 0; x < this.getHorizontal(); x++) {
            //iterate and acquire column break marker at the correct time
            if(i < this.columnBreaks.size()){
                if(x > marker){
                    marker = this.columnBreaks.get(i);
                    i++;
                }
            }
            for (int y = 0; y < this.getVertical(); y++) {
                if(x == marker){
                    value = 0xFF0000;//hexadecimal code for the color red
                }else{
                    //The following line offsets the pixels' values to fix the 'blue problem'
                    value = this.byteImage2[y][x] << 16 | this.byteImage2[y][x] << 8 | this.byteImage2[y][x];
                }
                OutputImage.setRGB(x, y, value);
            }
        }
        //Output the image to a file of our choosing
        File outputFile = new File(Constants.customOutput,this.name);
        try {
            ImageIO.write(OutputImage, "jpg", outputFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * Helper method that will compare matricies to ensure that they are the same.
     * Used for debugging purposes.
     * @param A matrix
     * @param B matrix
     * @return number of different pixels
     */
    public int compareMatrices(int[][] A, int[][] B){
        int difference = 0;
        for(int i = 0; i < A.length; i++){
            for(int j = 0; j < A[0].length; j++){
                if(A[i][j] != B[i][j]){
                    difference++;
                }
            }
        }
        return difference;
    }
    
    private int snippetHeight(){
        int sum = 0;
        for(int i = 0; i < this.columnBreaks.size()-1; i++){
            sum += this.columnBreaks.get(i+1)-this.columnBreaks.get(i);
        }
        int avgWidth = sum/(this.columnBreaks.size()-1);
        int avgHeight = (int) ((14.0/9.0)*avgWidth);
        return avgHeight;
    }
    
    /**
     * Final step in segmentation algorithm. Once column breaks have been found this method
     * will use those breakpoints to dynamically create snippets of varying width and height (a 14/9 ratio).
     * Snippets are outputted to the directory noted by Constants.Snippets and are grouped together by the full page they came from.
     */
    public void convertPageToSnippets(boolean scaleDown){
        int height = snippetHeight();
        int nextBegin = 0;
        int nextEnd = height;
        int snippetRow = 0;
        int snippetColumn = 0;
        
        assert height <= this.vertical : "Snippet's width is too long. This result snippet's height to be longer than the full page."
        
        //Check if it has a valid snippet size
        if (height > this.vertical){
            return 5;
        }
        
        //Identify the parent image name.
        String snippetSubName = this.getName().substring(0, this.getName().lastIndexOf('.'));
        String issueName = snippetSubName.substring(0, snippetSubName.lastIndexOf('_'));
        String parentName = this.getName().substring(0, this.getName().indexOf('_'));
        
        //populate snippet matrix with pixels from full page.
        for(int i = 0; i < columnBreaks.size()-1; i++){
            int width = columnBreaks.get(i+1) - columnBreaks.get(i);
            int[][] snippet = new int[height][width];
            int c = 0;
            for(int j = columnBreaks.get(i); j < columnBreaks.get(i+1); j++){
                int r = 0;
                for(int k = nextBegin; k < nextEnd; k++){
                    snippet[r][c] = this.byteImage2[k][j];
                    r++;
                }
                c++;
            }
            
            //identify the location of snippet in the full page. Use as name of the snippet
            String snippetName = snippetSubName+"_"+snippetRow+"_"+snippetColumn+".jpg";
            
            //Create BufferedImage for file writing. If scale down is needed perform that
            //first. For the time being scale is hard coded at 4x4.
            BufferedImage OutputImage;
            if(scaleDown){
                int scale = 4;
                int[][] scaledSnippet = scaleDownSnippet(scale, snippet, height, width);
                
                OutputImage = new BufferedImage(width/scale, height/scale, BufferedImage.TYPE_INT_RGB);
                for (int y = 0; y < height/scale; y++) {
                    for (int x = 0; x < width/scale; x++) {
                        //The following line offsets the pixels' values to fix the 'blue problem'
                        int value = scaledSnippet[y][x] << 16 | scaledSnippet[y][x] << 8 | scaledSnippet[y][x];
                        OutputImage.setRGB(x, y, value);
                    }
                }
            }else{
                OutputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        //The following line offsets the pixels' values to fix the 'blue problem'
                        int value = snippet[y][x] << 16 | snippet[y][x] << 8 | snippet[y][x];
                        OutputImage.setRGB(x, y, value);
                    }
                }
            }
            
            //Output the snippet to a file of our choosing
            File outputFile = new File(Constants.Snippets+parentName+"/"+issueName+"/"+snippetSubName+"/",snippetName);
            outputFile.mkdirs();
            try {
                ImageIO.write(OutputImage, "jpg", outputFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            snippetColumn++;
            
            //determine if row is complete, if yes move to next row, and if not move to next column
            if(i == columnBreaks.size() - 2){
                nextBegin = nextEnd - (height/2);
                nextEnd = nextBegin + height;
                if(nextEnd <= this.getVertical()){
                    i = -1;
                    snippetRow++;
                    snippetColumn = 0;
                }
            }
        }
    }
    
    /**
     * When given an integer to represent the scale (3 for 3x3, 4 for 4x4, 5 for 5x5, etc)
     * this function will average the pixels of the snippet based on the given scale.
     * @param scale
     * @param snippet
     * @param height
     * @param width
     * @return
     */
    private int[][] scaleDownSnippet(int scale, int[][] snippet, int height, int width){
        int r = 0,s = 0;
        int[][] scaledImage = new int[height/scale][width/scale];
        for(int i = scale/2; i < height - (scale/2); i = i+scale){
            s = 0;
            for(int j = scale/2; j < width - (scale/2); j = j+scale){
                scaledImage[r][s] = average(scale, snippet, i, j);
                s++;
            }
            r++;
        }
        return scaledImage;
    }
    
    /**
     * returns the average pixel value of a pixels scale x scale area.
     * This function can use both odd and even numbers.
     * @param scale
     * @param snippet
     * @param i
     * @param j
     * @return
     */
    private int average(int scale, int[][] snippet, int i, int j){
        int sum = 0;
        if(scale%2 == 0){
            for(int a = i-(scale/2); a < i+(scale/2); a++){
                for(int b = j-(scale/2); b < j+(scale/2); b++){
                    sum+=snippet[i][j];
                }
            }
        }else{
            for(int a = i-(scale/2); a <= i+(scale/2); a++){
                for(int b = j-(scale/2); b <= j+(scale/2); b++){
                    sum+=snippet[i][j];
                }
            }
        }
        return sum/(scale*scale);
    }
    
    public void printImage(String filePath){
        int w = this.getHorizontal(),h = this.getVertical();
        BufferedImage OutputImage = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
        int[][] pixels3 = this.getByteImage();
        for (int y = 0; y < this.getVertical(); y++) {
            for (int x = 0; x < this.getHorizontal(); x++) {
                //The following line offsets the pixels' values to fix the 'blue problem'
                int value = pixels3[y][x] << 16 | pixels3[y][x] << 8 | pixels3[y][x];
                OutputImage.setRGB(x, y, value);
            }
        }
        
        File outputFile = new File(filePath,this.name);
        try {
            ImageIO.write(OutputImage, "jpg", outputFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
