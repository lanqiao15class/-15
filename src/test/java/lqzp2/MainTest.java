package lqzp2;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
 

public class MainTest {
	
	
	 public static void testDecode() {  
	        String filePath = "D://new.png";  
	        BufferedImage image;  
	        try {  
	            image = ImageIO.read(new File(filePath));  
	            LuminanceSource source = new BufferedImageLuminanceSource(image);  
	            Binarizer binarizer = new HybridBinarizer(source);  
	            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);  
	            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();  
	            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");  
	            Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码  
	            System.out.println("zxing:" + result.getText());  
	            System.out.println("图片中格式：  ");  
	            System.out.println("encode： " + result.getBarcodeFormat());  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } catch (NotFoundException e) {  
	            e.printStackTrace();  
	        }  
	    }  
	 
	 
	 public static void testEncode()throws Exception 
	 {
		 
		    String text = "你好";   
	        int width = 200;   
	        int height = 200;   
	        String format = "png";   
	        Hashtable hints= new Hashtable();   
	        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");   
	         BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height,hints);   
	         File outputFile = new File("d:\\new.png");   
	         MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);   
	          
	         //bitMatrix.
	         
	 }
	public static void main(String[] args)throws Exception {
	 
		testEncode();
		
		testDecode();
		
		    
		     
		}  
			
		
	

}
