package com.lanqiao.aop;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.lanqiao.common.SessionUser;
import com.lanqiao.common.SpringContextUtil;
import com.lanqiao.common.WebUtil;
import com.lanqiao.constant.GlobalConstant;
import com.lanqiao.model.SysLog;
import com.lanqiao.service.LogService;

/*
* 拦截sql语句，除了select，统一实现日志保存
* */
@Intercepts({
/*@Signature(method = "query", type = Executor.class, args = { MappedStatement.class, Object.class,
		RowBounds.class, ResultHandler.class }),
@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),*/
@Signature(method = "prepare", type = StatementHandler.class, args = { Connection.class }) })
public class MybatisInterceptor implements Interceptor,Runnable {
	protected static final Log log = org.apache.commons.logging.LogFactory.getLog(MybatisInterceptor.class);
	static long time = 0l;
	static long time4 = 0l;
	static ConcurrentLinkedQueue<SysLog> logList = new ConcurrentLinkedQueue<SysLog>();
	static Thread thread = null;
	LogService logservice = null;
	
	public MybatisInterceptor()
	{
		//启动写日志 线程 。
		thread = new Thread(this);
		thread.start();
		
	}
	public Object intercept(Invocation invocation) throws Throwable {
	//	System.err.println(this.toString());
		//数据库执行sql
		Object result = invocation.proceed();
		try {
			// 获取sql 的参数。 
			if (invocation.getTarget() instanceof RoutingStatementHandler) {
				//是通过Interceptor的plugin方法进行包裹的，所以我们这里拦截到的目标对象肯定是RoutingStatementHandler对象。
				RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
				//通过反射获取到当前RoutingStatementHandler对象的delegate属性
				StatementHandler delegate = (StatementHandler) ReflectUtil.getFieldValue(handler, "delegate");

				MappedStatement stmt = (MappedStatement) ReflectUtil.getFieldValue(delegate, "mappedStatement");
				//mybatis sqlid
				String sqlid = stmt.getId();
				// 避免死循环。 忽略 写入操作日志的sql
				System.out.println("       tianjia          " + sqlid);
				if (sqlid.equals("com.lanqiao.dao.SysLogMapper.insert")) {
					return result;
				}
				//获取到当前StatementHandler的 boundSql，这里不管是调用handler.getBoundSql()还是直接调用delegate.getBoundSql()结果是一样的，因为之前已经说过了
				//RoutingStatementHandler实现的所有StatementHandler接口方法里面都是调用的delegate对应的方法。
				BoundSql boundSql = delegate.getBoundSql();

				String content = "";
				// 计算optype 
				String optype = "3";
				if (stmt.getSqlCommandType() == SqlCommandType.SELECT) {
					return result;
				} else if (stmt.getSqlCommandType() == SqlCommandType.DELETE) {
					optype = "2";
					content = "删除";
					try {
						update(stmt, content, boundSql, optype);
					} catch (Exception e) {
						log.error("删除" + e.getMessage());

						return result;
					}
				} else if (stmt.getSqlCommandType() == SqlCommandType.INSERT) {
					optype = "0";
					content = "添加";
					try {
						add(stmt, content, boundSql, optype);
					} catch (Exception e) {
						log.error("添加出错了" + e.getMessage());
						return result;
					}
				} else if (stmt.getSqlCommandType() == SqlCommandType.UPDATE) {
					optype = "1";
					content = "修改";
					try {
						update(stmt, content, boundSql, optype);
					} catch (Exception e) {
						log.error("修改出错了" + e.getMessage());
						return result;
					}
				}
			}
		} catch (Exception e) {
			return result;
		}
		return result;
	}

	//添加
	public void add(MappedStatement stmt, String content, BoundSql boundSql, String optype) throws Throwable {
		long time1 = System.currentTimeMillis();

		//获取boundSql对象
		Configuration configuration = stmt.getConfiguration();
		//获取sql
		String sql1 = getSql(configuration, boundSql);
		//System.err.println(sql1);
		//获取表名
		String tableName = MybatisInterceptor.getTableName(sql1);
		content += tableName;
		// 获取当前登陆者的userid . 
		int userid = 0;
		String requrl = "";
		String reqaddr = "";
		String olddata = "";
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();

		if (request != null) {
			SessionUser sessuser = WebUtil.getLoginUser(request);
			if (sessuser != null) {
				userid = sessuser.getUserId();
				System.out.println("userid                   " + userid);
			}
			reqaddr = request.getRemoteAddr();
			requrl = request.getRequestURI().toString();
		}

		Object obj = boundSql.getParameterObject();
		System.out.println("数据                             " + JSON.toJSONString(obj));
		olddata = JSON.toJSONString(obj);
	
		long time2 = System.currentTimeMillis();
		time += time2 - time1;
		log.error((time2 - time1) + "     " + sql1 + "     " + time);
		log.info("添加");
		log.info("reqaddr:" + reqaddr);
		log.info("requrl:" + requrl);
		log.info("userid:" + userid);
		log.info("optype:" + optype);
		log.info("olddata:" + olddata);
		log.info("content:" + content);
		log.info("-----------------------------------------------------");
		//保存到队列中。 
		SysLog logdata = new SysLog();
		logdata.setContent(content);
		logdata.setCreateDate(new java.util.Date());
		logdata.setOldData(olddata);
		logdata.setOptype(optype);
		logdata.setRemoteAddr(reqaddr);
		logdata.setRequestUri(requrl);
		logdata.setUserId(userid);
		logList.add(logdata);
		
		synchronized (logList) {
			logList.notify();
		}
		
		long time3 = System.currentTimeMillis();
		time4 += time3 - time2;
		log.error(time3 - time2 + "     " + sql1 + "   " + time4);
	}

	//更新
	public void update(MappedStatement stmt, String content, BoundSql boundSql, String optype) throws Throwable {
		long time1 = System.currentTimeMillis();
		//获取boundSql对象
		Configuration configuration = stmt.getConfiguration();
		//获取sql
		String sql1 = getSql(configuration, boundSql);
	//	System.err.println("sql1               " + sql1);
		/*
		 * 查询更新前的数据
		 */
		//获取表名
		String table = MybatisInterceptor.getTable(sql1);
		//获取where条件
		String getwhere = MybatisInterceptor.getwhere(sql1);
		//调用数据库查询数据
	
		if (logservice == null) {
			 logservice = SpringContextUtil.getBean("logservice");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("table", table + "  " + getwhere);
		List<Map<String, Object>> getdata = logservice.getdata(map);
		String olddata = "";
		if (getdata != null) {
			olddata = JSON.toJSONString(getdata);
		}

		//获取表名注释
		String tableName = MybatisInterceptor.getTableName(sql1);
		content += tableName;
		// 获取当前登陆者的userid . 
		int userid = 0;
		String requrl = "";
		String reqaddr = "";

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		if (request != null) {
			SessionUser sessuser = WebUtil.getLoginUser(request);
			if (sessuser != null) {
				userid = sessuser.getUserId();
			}
			reqaddr = request.getRemoteAddr();
			requrl = request.getRequestURI().toString();
		}
		long time2 = System.currentTimeMillis();
		time += time2 - time1;
		log.error((time2 - time1) + "     " + sql1 + "     " + time);
		log.info("更新");
		log.info("reqaddr:" + reqaddr);
		log.info("requrl:" + requrl);
		log.info("userid:" + userid);
		log.info("optype:" + optype);
		log.info("olddata:" + olddata);
		log.info("content:" + content);
		log.info("-----------------------------------------------------");
		//logservice.WriteLog(olddata, content, optype, userid, reqaddr, requrl);
		//保存到队列中。 
		SysLog logdata = new SysLog();
		logdata.setContent(content);
		logdata.setCreateDate(new java.util.Date());
		logdata.setOldData(olddata);
		logdata.setOptype(optype);
		logdata.setRemoteAddr(reqaddr);
		logdata.setRequestUri(requrl);
		logdata.setUserId(userid);
		logList.add(logdata);
		
		synchronized (logList) {
			logList.notify();
		}
		
		long time3 = System.currentTimeMillis();
		time4 += time3 - time2;
		log.error((time3 - time2) + "     " + sql1 + "        " + time4);
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		String prop1 = properties.getProperty("prop1");
		String prop2 = properties.getProperty("prop2");
		System.out.println(prop1 + "------" + prop2);
	}

	/**
	 * 获取数据库表注释
	 */
	public static String getTableName(String st) {
		if (st.toLowerCase().contains("insert")) {
			String str = st.substring(st.toLowerCase().indexOf("into") + 4, st.toLowerCase().indexOf("(")).trim();
			System.out.println(str);
			String name = GlobalConstant.DBTableName.getName(str);
			return name;
		} else if (st.toLowerCase().contains("update")) {
			String str = st.substring(st.toLowerCase().indexOf("update") + 6, st.toLowerCase().indexOf("set")).trim();
			System.out.println(str);
			String name = GlobalConstant.DBTableName.getName(str);
			return name;
		} else if (st.toLowerCase().contains("delete")) {
			String str = st.substring(st.toLowerCase().indexOf("from") + 4, st.toLowerCase().indexOf("from")).trim();
			System.out.println(str);
			String name = GlobalConstant.DBTableName.getName(str);
			return name;
		} else {
			System.out.println("sql        " + st + "           出现错误  ");
		}

		return null;

	}

	/**
	 * 获取数据库表名
	 */
	public static String getTable(String st) {
		if (st.toLowerCase().contains("insert")) {
			String str = st.substring(st.toLowerCase().indexOf("into") + 4, st.toLowerCase().indexOf("(")).trim();
			return str;
		} else if (st.toLowerCase().contains("update")) {
			String str = st.substring(st.toLowerCase().indexOf("update") + 6, st.toLowerCase().indexOf("set")).trim();
			return str;
		} else if (st.toLowerCase().contains("delete")) {
			String str = st.substring(st.toLowerCase().indexOf("from") + 4, st.toLowerCase().indexOf("from")).trim();
			return str;
		} else {
			System.out.println("sql        " + st + "           出现错误  ");
		}

		return "";

	}

	/**
	 * 获取where 条件
	 */
	public static String getwhere(String st) {
		String str = (st.toLowerCase().indexOf("where") > 0) ? st.toLowerCase().substring(st.indexOf("where")) : "";
		return str;
	}

	public static void main(String[] args) throws Exception {
		String st = "update sys_deparment SET depname = '123123123123', isvalid = 1 where depid = 79 ";
		String str = st.substring(st.toLowerCase().indexOf("update") + 6, st.toLowerCase().indexOf("set")).trim();
		System.out.println(str);
	}

	public static String getSql(Configuration configuration, BoundSql boundSql) {
		String sql = showSql(configuration, boundSql);
		StringBuilder str = new StringBuilder(100);
		str.append(sql);
		return str.toString();
	}

	private static String getParameterValue(Object obj) {
		String value = null;
		if (obj instanceof String) {
			value = "'" + obj.toString() + "'";
		} else if (obj instanceof Date) {
			DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
			value = "'" + formatter.format(new Date()) + "'";
		} else {
			if (obj != null) {
				value = obj.toString();
			} else {
				value = "";
			}

		}
		return value;
	}

	public static String showSql(Configuration configuration, BoundSql boundSql) {
		Object parameterObject = boundSql.getParameterObject();
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
		if (parameterMappings.size() > 0 && parameterObject != null) {
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
				sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));

			} else {
				MetaObject metaObject = configuration.newMetaObject(parameterObject);
				for (ParameterMapping parameterMapping : parameterMappings) {
					String propertyName = parameterMapping.getProperty();
					if (metaObject.hasGetter(propertyName)) {
						Object obj = metaObject.getValue(propertyName);
						sql = sql.replaceFirst("\\?", getParameterValue(obj));
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						Object obj = boundSql.getAdditionalParameter(propertyName);
						sql = sql.replaceFirst("\\?", getParameterValue(obj));
					}
				}
			}
		}
		return sql;
	}

	/**
	 * 利用反射进行操作的一个工具类
	 */
	private static class ReflectUtil {
		/**
		 * 利用反射获取指定对象的指定属性
		 */
		public static Object getFieldValue(Object obj, String fieldName) {
			Object result = null;
			final Field field = ReflectUtil.getField(obj, fieldName);
			if (field != null) {
				field.setAccessible(true);
				try {
					result = field.get(obj);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return result;
		}

		/**
		 * 利用反射获取指定对象里面的指定属性
		 */
		private static Field getField(Object obj, String fieldName) {
			Field field = null;
			for (Class clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
				try {
					field = clazz.getDeclaredField(fieldName);
					break;
				} catch (NoSuchFieldException e) {
					// 这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null。
				}
			}
			return field;
		}

		/**
		 * 利用反射设置指定对象的指定属性为指定的值
		 */
		public static void setFieldValue(Object obj, String fieldName, String fieldValue) {
			final Field field = ReflectUtil.getField(obj, fieldName);
			if (field != null) {
				try {
					field.setAccessible(true);
					field.set(obj, fieldValue);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			try
			{
				
				SysLog logdata = logList.poll();
				if(logdata ==null)
				{
					//队列中没数据， 等待。 直到有新的数据进入队列
					synchronized (logList) 
					{
						logList.wait();
					}
				}
				else
				{
					//有数据， 写数据库。
					if (logservice == null) {
						 logservice = SpringContextUtil.getBean("logservice");
					} 
					
					logservice.WriteLog(logdata);
					
				}
				
				
			}catch(Exception se)
			{
				se.printStackTrace();
			}
			
		}
	}
}
