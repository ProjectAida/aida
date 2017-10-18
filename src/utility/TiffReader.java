package utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.FileImageInputStream;

public class TiffReader {
	
	//not in use
	
	public static BufferedImage read(String Filename) {
		BufferedImage ret = null;
		ImageReader reader = null;  
        FileImageInputStream fis = null;  
        try {  
        	File inputImageFile = new File(Filename);
            Object[] src = readTiff(inputImageFile);  
            if(src == null) {  
                return null;  
            }  
            reader = (ImageReader) src[0];  
            fis = (FileImageInputStream) src[1];  
            if(reader != null) {  
                int numPages = reader.getNumImages(true);  
              
                if (numPages > 0) {  
                    ret = reader.read(0);  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally {  
            if(reader != null) {  
                reader.dispose();  
            }  
            if (fis != null) {  
                try {  
                    fis.flush();  
                    fis.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return ret;  
    }  
	
	private static BufferedImage loadTiff(File tifFile, long[] dpiData) {  
		  
		ImageReader reader = null;  
		FileImageInputStream fis = null;   
		BufferedImage res = null;  
		try {  
			Object[] src = readTiff(tifFile);  
			if(src == null) {  
				return null;  
			}  
			reader = (ImageReader) src[0];  
			fis = (FileImageInputStream) src[1];  
			if (reader != null) {  
				int numPages = reader.getNumImages(true);  
				if (numPages > 0) {  
					long[] dpiArr = getTiffDPI(reader, 0);  
					dpiData[0] = dpiArr[0];  
					dpiData[1] = dpiArr[1];  
					res = reader.read(0);  
				}  
			}  

		} catch (Exception e) {  
			e.printStackTrace();  
			  
		}finally {  
			  
			if(reader != null) {  
				reader.dispose();  
			}  
			  
			if (fis != null) {  
				try {  
					fis.flush();  
					fis.close();  
				} catch (IOException e) {  
					e.printStackTrace();  
				}  
			}  
			  
			  
		}  
		return res;  
	}  
	
	private static Object[] readTiff(File tifFile) {  
        ImageReader reader = null;  
        FileImageInputStream fis = null;  
        Object[] res = null;  
        try {  
              
            reader = ImageIO.getImageReadersByFormatName("tiff").next();  
            fis = new FileImageInputStream(tifFile);  
            reader.setInput(fis);  
            res = new Object[]{reader, fis};  
        } catch(NoSuchElementException e) {  
            e.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
              
        }finally {  
              
              
        }  
        return res;  
    }  
	
	private static long[] getTiffDPI(ImageReader reader, int index) {  
        long[] dpi = new long[2];  
          
        IIOMetadata meta = null;  
        try {  
              
            meta = reader.getImageMetadata(index);  
            org.w3c.dom.Node n = meta.getAsTree("javax_imageio_1.0");  
            n = n.getFirstChild();  
              
            while (n != null) {  
  
                if (n.getNodeName().equals("Dimension")) {  
  
                    org.w3c.dom.Node n2 = n.getFirstChild();  
  
                    while (n2 != null) {  
  
                        if (n2.getNodeName().equals("HorizontalPixelSize")) {  
  
                            org.w3c.dom.NamedNodeMap nnm = n2.getAttributes();  
  
                            org.w3c.dom.Node n3 = nnm.item(0);  
  
                            float hps = Float.parseFloat(n3.getNodeValue());  
  
                            dpi[0] = Math.round(25.4f / hps);  
  
                        }  
  
                        if (n2.getNodeName().equals("VerticalPixelSize")) {  
  
                            org.w3c.dom.NamedNodeMap nnm = n2.getAttributes();  
  
                            org.w3c.dom.Node n3 = nnm.item(0);  
  
                            float vps = Float.parseFloat(n3.getNodeValue());  
  
                            dpi[1] = Math.round(25.4f / vps);  
                        }  
  
                        n2 = n2.getNextSibling();  
  
                    }  
  
                }  
  
                n = n.getNextSibling();  
            }  
  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return dpi;  
    }  
	
}
