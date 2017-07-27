package com.lanqiao.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

/**
 * 2维码生成工具
 * @author chenbaoji
 *
 */
public class Code2Util {

	
	/**
	 * 解析2维码， 得到字符串。
	 * @param in
	 * @return
	 */
	 public static String  DecodeImage(InputStream in ) {  
	        BufferedImage image;  
	        String sret ="";
	        try {  
	            image = ImageIO.read(in);
	            LuminanceSource source = new BufferedImageLuminanceSource(image);  
	            Binarizer binarizer = new HybridBinarizer(source);  
	            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);  
	            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();  
	            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");  
	            Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码  
	            sret = result.getText();
	            } catch (IOException e) {  
	            e.printStackTrace();  
	        } catch (NotFoundException e) {  
	            e.printStackTrace();  
	        }  
	        return sret;
	    }  
	 
	
	 
/**
 *   转换字符串为2维码图。
 * @return
 */
public static void Encode(OutputStream out , String sdata, int w, int h) throws Exception 
{
    int width = w;  
    int height = h;
    String format = "png"; 
    Hashtable hints= new Hashtable();   
    hints.put(EncodeHintType.CHARACTER_SET, "utf-8");   
     BitMatrix bitMatrix = new MultiFormatWriter().encode(sdata, BarcodeFormat.QR_CODE, width, height,hints);   
     MatrixToImageWriter.writeToStream(bitMatrix, format, out);
     
}



}
