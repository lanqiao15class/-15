package com.lanqiao.util;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;

/**
 * 
 * TODO 动态加载文件类
 * 
 * @author chenbaoji
 * @date
 *
 */
public class DynamicLoadingFileUtil
{

	/**
	 * 获取webstatic文件夹下的所有js、css文件的最后修改时间
	 */
	public static Map<String, String> FILE_LAST_MODIFIED_MAP = new HashMap<String, String>();

	/**
	 * 时间格式化
	 */
	private static DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");

	/**
	 * Head标签元素定义
	 */
	private static List<String> headTagDefineList = new ArrayList<String>();

	/**
	 * Head标签元素内容
	 */
	private static Map<String, Object> headTagMap = new HashMap<String, Object>();

	private static final String start = "start".toUpperCase();

	private static final String end = "end".toUpperCase();

	private static String staticpath = "D:\\work\\training\\lqzp2\\src\\main\\webapp\\static\\";

	private static String staticurl = "/static/";

	/**
	 * 初始化加载
	 */
	public static void initialize(String stpath, String sturl)
	{
		staticpath = stpath;
		staticurl = sturl;
		
		headTagDefineList.add("css".toUpperCase());
		headTagDefineList.add("js".toUpperCase());
		//
		headTagMap.put(("css." + start).toUpperCase(),
				"<link rel='stylesheet' type='text/css' href='");
		headTagMap.put(("css." + end).toUpperCase(), "' />");
		headTagMap.put(("js." + start).toUpperCase(),
				"<script type='text/javascript' src='");
		headTagMap.put(("js." + end).toUpperCase(), "'></script>");
		//
		getAllFileLastModified(staticpath, FILE_LAST_MODIFIED_MAP);
	}

	/**
	 * 通过定时器, 定时检测是否有新的文件更新. 如果有,更新到配置中
	 * 
	 */
	public synchronized static void CheckAndReloadFiles()
	{

		Map<String, String> newmap = new HashMap<String, String>();
		getAllFileLastModified(staticpath, newmap);
		// 检测是否有更新.
		Iterator<String> sit = newmap.keySet().iterator();
		while (sit.hasNext())
		{
			String key = sit.next();
			String val = (String) newmap.get(key);

			String oldval = (String) FILE_LAST_MODIFIED_MAP.get(key);
			if (oldval == null || !oldval.equals(val))
			{
				FILE_LAST_MODIFIED_MAP.put(key, val);
				System.out.println("Reload new Static File:" + key + "," + val);
			}

		}

	}

	/**
	 * 加载文件及最后路径
	 * 
	 * @param staticurl
	 * @param staticpath
	 * @param filePath
	 * @return
	 */
	public static String getFileLastModified(String filePath)
	{

		String sourceFilePath = filePath;

		String newpath = staticpath
				.replaceAll("\\\\", "/") + filePath;
		String  vtime = FILE_LAST_MODIFIED_MAP.get(newpath);
	//	System.out.println ("newpath::"+newpath);
		// 添加文件版本号
		if (null != FILE_LAST_MODIFIED_MAP
				&& FILE_LAST_MODIFIED_MAP.size() > 0
				&&  vtime != null)
		{
			sourceFilePath = staticurl
					+ filePath
					+ "?v="
					+ vtime;
		}

		// 判断文件类型
		if (headTagDefineList.contains(FilenameUtils.getExtension(filePath)
				.toUpperCase()))
		{

			return headTagMap
					.get((FilenameUtils.getExtension(filePath) + "." + start)
							.toUpperCase())
					+ sourceFilePath
					+ headTagMap.get((FilenameUtils.getExtension(filePath)
							+ "." + end).toUpperCase());
		}

		return null;
	}

	/**
	 * 获取文件的最后修改日期
	 * 
	 * @param sourceFilePath
	 * @return
	 */
	private static void getAllFileLastModified(String staticPath,
			Map<String, String> mpdata)
	{

		File webStaticFile = new File(staticPath);

		if (null != webStaticFile && webStaticFile.isDirectory()
				&& null != webStaticFile.listFiles()
				&& webStaticFile.listFiles().length > 0)
		{
			// 获取目录下所有文件
			File directoryFile[] = webStaticFile.listFiles();
			for (File file : directoryFile)
			{

				if (null != file
						&& file.isFile()
						&& headTagDefineList.contains(FilenameUtils
								.getExtension(file.getName()).toUpperCase()))
				{
					// 文件判断
					String filePath = file.getPath();
					String fpath = filePath.replaceAll("\\\\", "/");
				//System.out.println (fpath);
					mpdata.put(fpath,
							dateFormat.format(file.lastModified()));
				} else if (null != file && file.isDirectory())
				{
					// 文件判断
					getAllFileLastModified(file.getPath(), mpdata);
				}
			}
		}
	}
}