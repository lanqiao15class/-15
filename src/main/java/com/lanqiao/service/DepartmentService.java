/*
 * File name:          DepartmentService.java
 * Copyright@Handkoo (China)
 * Editor:           JDK1.6.32
 */
package com.lanqiao.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.lanqiao.dao.SysDeparmentMapper;
import com.lanqiao.model.DepartmentTree;
import com.lanqiao.model.SysDeparment;
import com.lanqiao.util.CommonUtil;

/**
 * TODO: File comments
 * <p>
 * <p>
 * Author:           卢锡文
 * <p>
 * Date:           2017年3月31日
 * <p>
 * Time:           下午1:14:36
 * <p>
 * Director:        卢锡文
 * <p>
 * <p>
 */
@Service(value = "departmentService")
public class DepartmentService extends BaseService {
	/**
	 * 根据登录用户的部门生成部门数
	 * @param sd
	 * @return
	 */
	public List<DepartmentTree> getDepartmentByid(SysDeparment sd) {

		List<SysDeparment> list = new ArrayList<SysDeparment>();
		list.add(sd);
		list = selDepartment(sd, list);
		//list = selDepartment1(sd, list);

		//转化成tree需要的数据结构
		List<DepartmentTree> trees = new ArrayList<DepartmentTree>();

		for (SysDeparment deparment : list) {
			DepartmentTree departmentTree = new DepartmentTree();
			System.out.println("        部门          " + deparment.getDepname());
			try {
				CommonUtil.copyBean(departmentTree, deparment);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			trees.add(departmentTree);
		}

		//排序
		Collections.sort(trees);
		return trees;

	}

	/**
	 * 向下迭代部门树
	 */

	public List<SysDeparment> selDepartment(SysDeparment deparment, List<SysDeparment> list1) {

		System.out.println(deparment.getDepname() + "              11111111");
		SysDeparmentMapper mapper = dao.getMapper(SysDeparmentMapper.class);
		List<SysDeparment> list = mapper.selectByParentId(deparment.getDepid());
		if (list != null) {
			list1.addAll(list);
			for (SysDeparment deparment1 : list) {
				selDepartment(deparment1, list1);
			}
		}
		return list1;

	}

	/**
	 * 根据部门查找用户
	 * @return
	 */
	public List<Map<String, Object>> getUserByDepartment(Map<String, Object> paramMap) {
		SysDeparmentMapper mapper = dao.getMapper(SysDeparmentMapper.class);
		List<Map<String, Object>> maps = mapper.getUserByDepartment(paramMap);
		int i = 1;
		for (Map<String, Object> map2 : maps) {
			map2.put("indexNo", i);
			if (CommonUtil.isNotNull(map2.get("sex")) && StringUtils.equals("1", map2.get("sex").toString())) {
				map2.put("sex1", "女");
			} else if (CommonUtil.isNotNull(map2.get("sex")) && StringUtils.equals("0", map2.get("sex").toString())) {
				map2.put("sex1", "男");
			} else {
				map2.put("sex1", "");
			}
			if (CommonUtil.isNotNull(map2.get("isvalid")) && StringUtils.equals("1", map2.get("isvalid").toString())) {
				map2.put("isvalidName", "有效");
			} else {
				map2.put("isvalidName", "无效");
			}
			i++;
		}
		return maps;
	}

	/**
	 * 根据部门查找用户总记录数
	 * @return
	 */
	public Integer getUserByDepartmentCount(Map<String, Object> paramMap) {
		SysDeparmentMapper mapper = dao.getMapper(SysDeparmentMapper.class);
		Integer i = mapper.getUserByDepartmentCount(paramMap);

		return i;
	}

	/**
	 * 添加部门
	 * @param deparment
	 * @return
	 */
	public int addDepartment(SysDeparment deparment) throws Exception {
		SysDeparmentMapper mapper = dao.getMapper(SysDeparmentMapper.class);
		Integer i = mapper.insertSelective(deparment);
		return i;
	}

	/**
	 * 修改部门
	 * @param deparment
	 * @return
	 */
	public int upDepartment(SysDeparment deparment) throws Exception {
		SysDeparmentMapper mapper = dao.getMapper(SysDeparmentMapper.class);
		Integer i = mapper.updateByPrimaryKeySelective(deparment);
		return i;
	}

	/**
	 * 删除部门
	 * @param deparment
	 * @return
	 */
	public int delDepartment(Integer id) throws Exception {
		SysDeparmentMapper mapper = dao.getMapper(SysDeparmentMapper.class);
		Integer i = mapper.deleteByPrimaryKey(id);
		return i;
	}
}
