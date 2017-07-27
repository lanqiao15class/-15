package com.lanqiao.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.lanqiao.common.SessionUser;
import com.lanqiao.common.WebUtil;
import com.lanqiao.constant.GlobalConstant;

/*import com.gif4j.GifDecoder;
import com.gif4j.GifEncoder;
import com.gif4j.GifImage;
import com.gif4j.GifTransformer;*/
/*import com.jcraft.jsch.Logger;
import com.qiniu.api.io.PutRet;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;*/

/**
 * 文件操作工具
 * @author PanJianPing
 */
public class FileUtil{
	
	private final static Log log = LogFactory.getLog(FileUtil.class);
	private static final String TAG = "uploadFile";
	private static final int TIME_OUT = 10*1000;   //超时时间
	private static final String CHARSET = "utf-8"; //设置编码
	
	/** 
	 * 上传、下载等全路径
	 * 调用这个方法的是上传操作，要获得的是上传文件在服务器本地的保存路径，这个地方我使用了File.separator,目前运行正常，不确定移植到Linux下是否正常.
	 * 估计这里要将path中的斜杠替换成 File.separator
	 */
	public static String getFilePath(HttpServletRequest request, String path, Long shopid, Object filename) {
		String filePath = request.getSession().getServletContext().getRealPath(path);
		if (CommonUtil.isNotNull(shopid)) {
			filePath += File.separator + shopid;
		}

		if (CommonUtil.isNotNull(filename)) {
			filePath += File.separator + filename;
		}

		return filePath;
	}
	
	/** 
	 * 保存相对路径
	 * 本来这个地方用的是File.separator,比如在window平台得到的是\,但用到这个函数的地方都是得到一个相对路径保存到表里用的，
	 * 比如生成页面后得到页面的路径：/cardpage\1\28.html
	 * 在list.jsp中预览链接写的是：${root}${item.pageurl}
	 * 在IE中，会自动将\转换为/，然后浏览器中新打开页面中显示正常，但在FF中显示的却是：http://localhost:8888/ecard/cardpage\1\28.html,导致404
	 */
	public static String getRelativeFilePath(String path, Long shopid, Object filename) {
		String filePath = path;
		if (CommonUtil.isNotNull(shopid)) {
			filePath += "/" + shopid;
		}
		if (CommonUtil.isNotNull(filename)) {
			filePath += "/" + filename;
		}
		return filePath;
	}
	
	public static Long getMaxsize(String tagsize) {
		int length = Integer.parseInt(tagsize.substring(0, tagsize.length() - 1));
		String unit = tagsize.substring(tagsize.length() - 1).toLowerCase();

		Long max = null;
		if (unit.equalsIgnoreCase("k")) {
			max = new Long(length * 1024);
		} else if (unit.equalsIgnoreCase("m")) {
			max = new Long(length * 1024 * 1024);
		}
		return max;
	}
	
	
	/**
	 * 对上传的图片进行缩放处理，只存储处理后得到的文件
	 * 如果是等比例缩放，那么处理后的图片的宽度很有可能与指定的新宽度和新高度不一致！
	 * 因此在名片制作流程中上传的图片，都要使用非等比例缩放。
	 * 
	 * @param sourceFile	Spring上传的源文件
	 * @param targetPath	目标全路径
	 * @param width			新宽度
	 * @param height		新高度
	 * @param proportion	是否等比例缩放 true是 false否
	 * @param smooth		是否平滑处理 gif格式时使用
	 */
	/*public static void compressThenCopy(MultipartFile sourceFile, String targetPath, int width, int height, boolean proportion, boolean smooth) throws Exception{
		String filename = sourceFile.getOriginalFilename();
		String filetype = getLowerCaseFileType(filename);
		if (CommonUtil.isNotNull(filetype)) {
			File targetFile = new File(targetPath);
			targetFile.getParentFile().mkdirs();
			
			if (width > 0 && height > 0) {
				if ("gif".equalsIgnoreCase(filetype)) {
					GifImage gifImage = GifDecoder.decode(sourceFile.getInputStream());
					int imageWideth = gifImage.getScreenWidth();
					int imageHeight = gifImage.getScreenHeight();
					int changeToWideth = width;
					int changeToHeight = height;
					if (proportion) {
						if (imageWideth > 0 && imageHeight > 0) {
							if (imageWideth / imageHeight >= width / height) {
								if (imageWideth > width) {
									changeToWideth = width;
									changeToHeight = (imageHeight * width) / imageWideth;
								} else {
									changeToWideth = imageWideth;
									changeToHeight = imageHeight;
								}
							} else {
								if (imageHeight > height) {
									changeToHeight = height;
									changeToWideth = (imageWideth * height) / imageHeight;
								} else {
									changeToWideth = imageWideth;
									changeToHeight = imageHeight;
								}
							}
						}
					}
					GifImage resizedGifImage2 = GifTransformer.resize(gifImage, changeToWideth, changeToHeight, smooth);
					GifEncoder.encode(resizedGifImage2, targetFile);
				} else {
					Image image = ImageIO.read(sourceFile.getInputStream());
					if (image.getWidth(null) == -1) {
						throw new Exception("图片格式错误");
					} else {
						int newWidth;
						int newHeight;
						if (proportion) {
							// 为等比缩放计算输出的图片宽度及高度
							double rate1 = ((double) image.getWidth(null)) / (double) width + 0.1;
							double rate2 = ((double) image.getHeight(null)) / (double) height + 0.1;
							// 根据缩放比率大的进行缩放控制
							double rate = rate1 > rate2 ? rate1 : rate2;
							newWidth = (int) (((double) image.getWidth(null)) / rate);
							newHeight = (int) (((double) image.getHeight(null)) / rate);
						} else {
							newWidth = width;
							newHeight = height;
						}
						BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);

						tag.getGraphics().drawImage(image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
						FileOutputStream out = new FileOutputStream(targetFile);
						JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
						encoder.encode(tag);
						out.close();
					}
				}
			} else {
				BufferedInputStream in = new BufferedInputStream(sourceFile.getInputStream());
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(targetFile));
				byte[] data = new byte[1024];
				while (in.read(data) != -1) {
					out.write(data);
				}
				out.flush();
				out.close();
				in.close();
			}
		} else {
			throw new Exception("上传图片文件类型错误");
		}
	}*/
	
/*	*//** 拷贝之前先非等比例缩放 *//*
	public static void newCopy(String targetPath, InputStream inputStream, int width, int height) throws Exception {
		File target = new File(targetPath);
		target.getParentFile().mkdirs();

		if (width != 0 && height != 0) {
			Image img = ImageIO.read(inputStream);
			if (img.getWidth(null) == -1) {
				throw new Exception("图片格式错误");
			} else {
				BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

				//Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
				tag.getGraphics().drawImage(img.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
				FileOutputStream out = new FileOutputStream(target);
				// JPEGImageEncoder可适用于其他图片类型的转换
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				encoder.encode(tag);
				out.close();
			}
		} else {
			BufferedInputStream in = new BufferedInputStream(inputStream);
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(target));
			byte[] data = new byte[1024];
			while (in.read(data) != -1) {
				out.write(data);
			}
			out.flush();
			out.close();
			in.close();
		}
	}*/
	
	/**
	 * 复制文件
	 * @param inputStream 	源文件输入流
	 * @param target 		目标文件
	 * @throws IOException 
	 */
	public static void doCopyFromInputStream(InputStream inputStream, File target) throws IOException {
		target.getParentFile().mkdirs();
		BufferedInputStream in = new BufferedInputStream(inputStream);
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(target));
		byte[] data = new byte[1024];
		while (in.read(data) != -1) {
			out.write(data);
		}
		out.flush();
		out.close();
		in.close();
	}

	/**
	 * 复制文件
	 * @param inputStream 源文件输入流
	 * @param targetPath  目标文件路径
	 */
	public static void copy(InputStream inputStream, String targetPath) throws IOException {
		File target = new File(targetPath);
		doCopyFromInputStream(inputStream,target);
	}

	/**
	 * 
	 * @param inputStream	源文件输入流
	 * @param target		目标文件	
	 * @throws IOException
	 */
	public static void copy(InputStream inputStream, File target) throws IOException {
		doCopyFromInputStream(inputStream,target);
	}
	
	/**
	 * 复制文件
	 * 
	 * @param source 源文件
	 * @param target 目标文件
	 */
	public static void doCopyFromFile(File source, File target) throws IOException {
		target.getParentFile().mkdirs();// 保证文件所在目录一定存在
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(source));
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(target));
		byte[] data = new byte[1024];
		while (in.read(data) != -1) {
			out.write(data);
		}
		out.flush();
		out.close();
		in.close();
	}
	
	/**
	 * 复制文件
	 * 
	 * @param source 	 源文件
	 * @param targetPath 目标文件路径
	 */
	public static void copy(File source, String targetPath) throws IOException {
		File target = new File(targetPath);
		doCopyFromFile(source, target);
	}

	/**
	 * 复制文件
	 * 
	 * @param sourcePath 源文件路径
	 * @param targetPath 目标文件路径
	 */
	public static void copy(String sourcePath, String targetPath) throws IOException {
		File source = new File(sourcePath);
		File target = new File(targetPath);
		doCopyFromFile(source, target);
	}
	
	/**
	 * 允许上传的文件类型
	 * 
	 * @param allowFileTypes
	 * @param filename
	 * @return
	 */
	public static boolean isAllowFileType(String[] allowFileTypes, String filename) {
		// 对文件类型的判断
		String filetype = getLowerCaseFileType(filename);
		if (CommonUtil.isNotNull(allowFileTypes)) {
			for (String type : allowFileTypes) {
				if (filetype.equalsIgnoreCase(type.toLowerCase())) {
					return true;
				}
			}
			return false;
		} else {
			// 默认所有都可以上传
			return true;
		}
	}
	
	/**
	 * 上传文件(Spring上传）
	 * @param request
	 * @param targetPath 上传保存的路径
	 * @param uploadFileFormName 文件域名
	 * @param allowFileTypes 所允许上传的文件类型（null时为全部）
	 * @throws Exception
	 */
	public static Map<String,Object> uploadFile(DefaultMultipartHttpServletRequest request, String targetPath, String uploadFileFormName, String[] allowFileTypes) throws Exception{
		List<MultipartFile> uFiles = request.getFiles(uploadFileFormName);
		Map<String,Object> map=new HashMap<String,Object>();
		//判断上传的文件是否有内容
		if (uFiles == null || uFiles.isEmpty()) {
			return null;
		}
		String saveFilePath= request.getSession().getServletContext().getRealPath("/");
		saveFilePath=saveFilePath.substring(0, saveFilePath.length()-1);
		saveFilePath=saveFilePath.substring(0,saveFilePath.lastIndexOf(File.separator));
		saveFilePath+=File.separator+targetPath;
//		System.out.println("上传图片的路径>>>>>>"+saveFilePath);
		File tmpFile = new File(saveFilePath);
		if (!tmpFile.exists()) {
			// 创建目录
			tmpFile.mkdir();
		}
		for(MultipartFile uFile:uFiles){
			//原文件名
			String filename = uFile.getOriginalFilename();
			//是否允许上传
			boolean allowable  = isAllowFileType(allowFileTypes, filename);
			
			if (allowable) {
				StringBuffer sb=new StringBuffer();
				// 为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
				String image=UUID.randomUUID().toString() + "_" + filename;
				sb.append(targetPath).append("/").append(image);
				map.put(sb.toString(),filename);
				sb.setLength(0);
				sb.append(saveFilePath).append(File.separator).append(image);
				//targetPath += filename.substring(filename.lastIndexOf("."));
				//开始上传
				InputStream inputStream = uFile.getInputStream();
				copy(inputStream, sb.toString());
			}else{
				throw new Exception("上传文件类型错误");
			}
			
		}
		return map;
	}
	
	/**
	 * 上传文件(Spring上传）
	 * @param request
	 * @param uFiles
	 * @param targetPath
	 * @param allowFileTypes
	 * @return
	 * @throws Exception
	 */
	public static List<String> uploadFile(HttpServletRequest request,List<MultipartFile> uFiles, String targetPath, String[] allowFileTypes) throws Exception{
		List<String> fileNameList = new ArrayList<String>();//存放上传后的文件路径
		//判断上传的文件是否有内容
		if (uFiles.size()==0 || uFiles == null || uFiles.isEmpty() ) {
			return null;
		}
		String saveFilePath= request.getSession().getServletContext().getRealPath("/" );
		saveFilePath=saveFilePath.substring(0, saveFilePath.length()-1);
		saveFilePath=saveFilePath.substring(0,saveFilePath.lastIndexOf(File.separator));
		saveFilePath+= File.separator + targetPath;
		log.debug("#######################上传图片的路径"+saveFilePath+"##########################");
		File tmpFile = new File(saveFilePath);
		if (!tmpFile.exists()) {
			// 创建目录
			tmpFile.mkdir();
		}
		for(MultipartFile uFile:uFiles){
			//原文件名
			String filename = uFile.getOriginalFilename();
			//是否允许上传
			boolean allowable  = isAllowFileType(allowFileTypes, filename);
			if (allowable) {
				StringBuffer sb=new StringBuffer();
				// 为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
				String image=UUID.randomUUID().toString();
				sb.append(targetPath).append("/").append(image);
				fileNameList.add(sb.toString());
				sb.setLength(0);
				sb.append(saveFilePath).append(File.separator).append(image);
				//开始上传
				InputStream inputStream = uFile.getInputStream();
				copy(inputStream, sb.toString());
			}else{
				throw new Exception("上传文件类型错误");
			}
		}
		return fileNameList;
	}
	
	/**
	 * 上传文件(Spring上传）
	 * @param request
	 * @param uFiles
	 * @param targetPath
	 * @param allowFileTypes
	 * @return
	 * @throws Exception
	 */
	public static void uploadExcelFile(HttpServletRequest request,MultipartFile file, String targetPath,String targetFileName, String[] allowFileTypes) throws Exception{
		//判断上传的文件是否有内容
		if(file == null || file.isEmpty()) {
			throw new RuntimeException("没有检测Excel到文件");
		}
		log.debug("#######################上传图片的路径"+targetPath+"##########################");
		File tmpFile = new File(targetPath);
		if (!tmpFile.exists()) {
			// 创建目录
			tmpFile.mkdirs();
		}
		//原文件名
		String filename = file.getOriginalFilename();
		//是否允许上传
		boolean allowable  = isAllowFileType(allowFileTypes, filename);
		if (allowable) {
			targetFileName = targetPath + File.separator + targetFileName;
			//开始上传
			BufferedInputStream in = new BufferedInputStream(file.getInputStream());
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(targetFileName));
			byte[] data = new byte[1024];
			while (in.read(data) != -1) {
				out.write(data);
			}
			out.flush();
			out.close();
			in.close();
		}else{
			throw new RuntimeException("上传文件类型错误");
		}
	}
	
	/**
	 * 上传图片文件
	 * @param request
	 * @param file
	 * @param targetPath
	 * @throws Exception
	 */
	public static String uploadIdCardImgFile(HttpServletRequest request,MultipartFile file) throws Exception {
		String targetPath = GlobalConstant.uploadPath;
		Date curDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String curMonth = sdf.format(curDate);
		StringBuffer sb = new StringBuffer();
		StringBuffer dbSbf = new StringBuffer();
		dbSbf.append("/image/idcard/").append(curMonth);
		sb.append(targetPath).append(File.separator).append("idcard").append(File.separator).append(curMonth);
		//判断是否存在当年年月目录
		File dirFile = new File(sb.toString());
		if(!dirFile.exists()) {
			dirFile.mkdirs();
		}
		log.debug("#######################上传图片的路径"+dirFile+"##########################");
		StringBuffer fileNameSbf = new StringBuffer();
		String randomStr = UUID.randomUUID().toString();
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length());
		fileNameSbf.append(File.separator).append(randomStr).append(suffix);
		dbSbf.append("/").append(randomStr).append(suffix);
		sb.append(fileNameSbf);
		//开始上传
		BufferedInputStream in = new BufferedInputStream(file.getInputStream());
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(sb.toString()));
		byte[] data = new byte[1024];
		while (in.read(data) != -1) {
			out.write(data);
		}
		out.flush();
		out.close();
		in.close();
		return dbSbf.toString();
	}
	
	/**
	 * 从服务器删除文件
	 * @param filePath
	 * @return
	 */
	public static void deleteFile(HttpServletRequest request,String targetPath) {
		File file = new File(targetPath);
		try {
			if(file.exists()) {
				file.delete();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 得到小写的文件类型,不带.号 */
	public static String getLowerCaseFileType(String filename) {
		if (CommonUtil.isNotNull(filename)) {
			int position = filename.lastIndexOf(".");
			if (position <= 0) {
				return null;
			} else {
				return filename.substring(position + 1).toLowerCase();
			}
		} else {
			return null;
		}
	}
	
	/** 得到文件名后缀，即带.号的文件类型 */
	public static String getLowerCaseSuffix(String filename){
		if (CommonUtil.isNotNull(filename)) {
			int position = filename.lastIndexOf(".");
			if (position <= 0) {
				return null;
			} else {
				return filename.substring(position).toLowerCase();
			}
		}else{
			return null;
		}
	}
	

	/** 
     * 根据byte数组，生成文件 
     */  
    public static void getFile(byte[] bfile, String filePath,String fileName) {  
        BufferedOutputStream bos = null;  
        FileOutputStream fos = null;  
        File file = null;
        try {  
            File dir = new File(filePath);  
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在  
                dir.mkdirs();  
            }  
            file = new File(filePath+fileName);
            log.info("create file path is "+filePath+fileName);
            fos = new FileOutputStream(file);  
            bos = new BufferedOutputStream(fos);  
            bos.write(bfile);
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (bos != null) {  
                try {  
                    bos.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
            if (fos != null) {  
                try {  
                    fos.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
        }
    }
    
    /**
     * android上传文件到服务器
     * @param file  需要上传的文件
     * @param RequestURL  请求的url
     * @return  返回响应的内容
     */
    public static String uploadFile(File file,String RequestURL){
        String result = null;
        String  BOUNDARY =  UUID.randomUUID().toString();  //边界标识   随机生成
        String PREFIX = "--" , LINE_END = "\r\n"; 
        String CONTENT_TYPE = "multipart/form-data";   //内容类型
        
        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true);  //允许输入流
            conn.setDoOutput(true); //允许输出流
            conn.setUseCaches(false);  //不允许使用缓存
            conn.setRequestMethod("POST");  //请求方式
            conn.setRequestProperty("Charset", CHARSET);  //设置编码
            conn.setRequestProperty("connection", "keep-alive");   
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY); 
            
            if(file!=null)
            {
                /**
                 * 当文件不为空，把文件包装并且上传
                 */
                DataOutputStream dos = new DataOutputStream( conn.getOutputStream());
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意：
                 * name里面的值为服务器端需要key   只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的   比如:abc.png  
                 */
                
                sb.append("Content-Disposition: form-data; name=\"img\"; filename=\""+file.getName()+"\""+LINE_END); 
                sb.append("Content-Type: application/octet-stream; charset="+CHARSET+LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while((len=is.read(bytes))!=-1)
                {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();
                /**
                 * 获取响应码  200=成功
                 * 当响应成功，获取响应的流  
                 */
                int res = conn.getResponseCode();  
                log.info( "response code:"+res);

//                if(res==200)
//                {
                log.info( "response code:"+res);
                    InputStream input =  conn.getInputStream();
                    StringBuffer sb1= new StringBuffer();
                    int ss ;
                    while((ss=input.read())!=-1)
                    {
                        sb1.append((char)ss);
                    }
                    result = sb1.toString();
                    log.info( "response code:"+res);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /*public static Map<String,Object> upload(int tag,HttpServletRequest request, HttpServletResponse response)  
            throws IOException {
    	Map<String,Object> M = new HashMap<String, Object>();
    	boolean flag = false;
		//创建一个临时文件存放要上传的文件，第一个参数为上传文件大小，第二个参数为存放的临时目录
        DiskFileItemFactory factory = new DiskFileItemFactory(1024*1024*5,new File(AppPro.getProp("FILE_TEMP")));
        // 设置缓冲区大小为 5M
        factory.setSizeThreshold(1024 * 1024 * 5);
        //创建一个文件上传的句柄
        ServletFileUpload upload = new ServletFileUpload(factory);
        //设置上传文件的整个大小和上传的单个文件大小
        upload.setSizeMax(1024*1024*50);
        upload.setFileSizeMax(1024*1024*5);

        try {
            //把页面表单中的每一个表单元素解析成一个FileItem
            List<FileItem> items = upload.parseRequest(request);
            for (FileItem fileItem : items) {
                //如果是一个普通的表单元素(type不是file的表单元素)
                if(fileItem.isFormField()){
                    //得到对应表单元素的名字,得到表单元素的值
            		if(Util.isMessyCode(fileItem.getString())){
            			M.put(fileItem.getFieldName(), new String(fileItem.getString().getBytes("ISO-8859-1"),"utf-8"));
            		}else{
            			M.put(fileItem.getFieldName(), fileItem.getString());
            		}
                }else{
                    //获取文件的后缀名 
                	String param = fileItem.getFieldName();
                    String fileName = fileItem.getName();//得到文件的名字
                    M.put(param, fileName);
                    String uploadPath = "";
                    log.info("file tag is "+tag);
	                try {
	                	
	                	
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
                }
            }
        } catch (FileUploadBase.SizeLimitExceededException e) {
            System.out.println("整个请求的大小超过了规定的大小...");
        } catch (FileUploadBase.FileSizeLimitExceededException e) {
            System.out.println("请求中一个上传文件的大小超过了规定的大小...");
        }catch (FileUploadException e) {
            e.printStackTrace();
        }
        
        M.put("flag", flag);
        return M ;
	}*/

	/**
	 * 上传文件(Spring上传）
	 * @param request
	 * @param targetPath 上传保存的路径
	 * @param uploadFileFormName 文件域名
	 * @param allowFileTypes 所允许上传的文件类型（null时为全部）
	 * @throws Exception
	 */
	public static List<String> uploadFile(DefaultMultipartHttpServletRequest request, String targetPath, String[] allowFileTypes) throws IOException{
		List<String> nameList = new ArrayList<String>();
		Map<String, MultipartFile> uFiles = request.getFileMap();

		String saveFilePath= request.getSession().getServletContext().getRealPath("/" );
		saveFilePath=saveFilePath.substring(0, saveFilePath.length()-1);
		saveFilePath=saveFilePath.substring(0,saveFilePath.lastIndexOf(File.separator));
		saveFilePath+=File.separator+targetPath;
//		System.out.println("上传图片的路径>>>>>>"+saveFilePath);
		File tmpFile = new File(saveFilePath);
		if (!tmpFile.exists()) {
			// 创建目录
			tmpFile.mkdir();
		}
		for(MultipartFile uFile:uFiles.values()){
			//原文件名
			String filename = uFile.getOriginalFilename();
			//是否允许上传
			boolean allowable  = isAllowFileType(allowFileTypes, filename);

			if (allowable) {
				StringBuffer sb=new StringBuffer();
				// 为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
				String image=UUID.randomUUID().toString() + "_" + filename;
				sb.append(targetPath).append("/").append(image);
				nameList.add(sb.toString());

				sb.setLength(0);
				sb.append(saveFilePath).append(File.separator).append(image);
				//targetPath += filename.substring(filename.lastIndexOf("."));
				//开始上传
				InputStream inputStream = uFile.getInputStream();
				copy(inputStream, sb.toString());
			}else{
				throw new IOException("上传文件类型错误");
			}

		}
		return nameList;
	}

	public static String uploadPhotoImgFile(HttpServletRequest request,MultipartFile file) throws Exception{
		String targetPath = GlobalConstant.uploadPath;
		Date curDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String curMonth = sdf.format(curDate);
		StringBuffer sb = new StringBuffer();
		StringBuffer dbSbf = new StringBuffer();
		dbSbf.append("/image/photo/").append(curMonth);
		sb.append(targetPath).append(File.separator).append("photo").append(File.separator).append(curMonth);
		//判断是否存在当年年月目录
		File dirFile = new File(sb.toString());
		if(!dirFile.exists()) {
			dirFile.mkdirs();
		}
		log.debug("#######################上传图片的路径"+dirFile+"##########################");
		StringBuffer fileNameSbf = new StringBuffer();
		String randomStr = UUID.randomUUID().toString();
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length());
		fileNameSbf.append(File.separator).append(randomStr).append(suffix);
		dbSbf.append("/").append(randomStr).append(suffix);
		sb.append(fileNameSbf);
		//开始上传
		BufferedInputStream in = new BufferedInputStream(file.getInputStream());
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(sb.toString()));
		byte[] data = new byte[1024];
		while (in.read(data) != -1) {
			out.write(data);
		}
		out.flush();
		out.close();
		in.close();
		return dbSbf.toString();
	}
   
}
