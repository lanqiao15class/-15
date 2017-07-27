package com.lanqiao.controller;

import com.alibaba.fastjson.JSON;
import com.lanqiao.common.SessionUser;
import com.lanqiao.common.WebUtil;
import com.lanqiao.model.TeacherCourseArrangement;
import com.lanqiao.service.DictService;
import com.lanqiao.service.TLqclassService;
import com.lanqiao.service.TStudentSignService;
import com.lanqiao.service.TeacherCourseArrangementService;
import com.lanqiao.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wei62_000 on 2017/7/12.
 */
@Controller
@RequestMapping("/arrangement")
public class TeacherCourseArrangementController {

    @Autowired
    private TeacherCourseArrangementService service;

    @Resource
    private DictService dictService;

    @Resource
    private TStudentSignService studentSignService;

    @Resource
    private TLqclassService tLqclassService;

    //查询所有的老师课程安排
    @RequestMapping("/list.do")
    public ModelAndView showCourseArrangementView(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        List<Map<String, Object>> classStatusList = WebUtil.getClassStatusList();
        List<Map<String, Object>> courseTypeList = new ArrayList<Map<String, Object>>();
        try {
            courseTypeList = dictService.getSysCourse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mv.addObject("classStatusList", classStatusList);
        mv.addObject("courseTypeList", courseTypeList);

        mv.setViewName("/WEB-INF/view/course/sysCourseArrangementList.jsp");
        return mv;
    }

    @RequestMapping("/showList.do")
    public void showList(HttpServletRequest request, HttpServletResponse response){
        SessionUser sessionUser = WebUtil.getLoginUser(request);
        ModelAndView mv = new ModelAndView();
        //查询班级状态的下拉列表
        Integer userId = sessionUser.getUserId();

        List<Map<String,Object>> lists = service.findAllByUserId(userId);

        for(Map map:lists){


            switch ((int)map.get("caStatus")){
                case 0:map.put("caStatus","未上课");break;
                case 1:map.put("caStatus","上课中");break;
                case 2:map.put("caStatus","已上课");break;
                default:map.put("caStatus","取消");
            }
            if(((int)map.get("caType"))==0) map.put("caType","正常");
            else                             map.put("caType","小班");

            SimpleDateFormat startsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String startTime = startsdf.format(map.get("startTime"));
            SimpleDateFormat endsdf = new SimpleDateFormat("HH:mm [EEEE]");
            String endTime = endsdf.format(map.get("endTime"));
            String time = new StringBuilder().append(startTime).append("~")
                                            .append(endTime).toString();
            map.put("courseTime",time);

            int currentSignCount = studentSignService.getCurrentCount((Integer) map.get("caId"));
            map.put("currentSignCount",currentSignCount);
            int classCount = tLqclassService.selectClassRealCount((Integer) map.get("classId"));
            map.put("classCount",classCount);

        }
        JsonUtil.write(response, JSON.toJSONString(lists));
    }

    //根据下拉框查询老师的课程安排
    @RequestMapping("/findByInfo")
    public void findArrangeByInfo(HttpServletRequest request,HttpServletResponse response) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (request.getParameter("classStatus") != null
                && !"".equals(request.getParameter("classStatus"))){
            paramMap.put("caStatus",Integer.parseInt(request.getParameter("classStatus")));
        }
        if (request.getParameter("courseType") != null
                && !"".equals(request.getParameter("courseType"))){
            paramMap.put("courseType",Integer.parseInt(request.getParameter("courseType")));
        }
        try {
            if (request.getParameter("beginTime") != null && !"".equals(request.getParameter("beginTime"))) {
                paramMap.put("beginTime", new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("beginTime")));
            }
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        try {
            if (request.getParameter("endTime") != null && !"".equals(request.getParameter("endTime"))) {
                paramMap.put("endTime", new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("endTime")));
            }
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        SessionUser sessionUser = WebUtil.getLoginUser(request);
        Integer userId = sessionUser.getUserId();
        paramMap.put("teaUserId",userId);

        List<Map<String,Object>> list = service.findArrangeByInfo(paramMap);
        for(Map map:list){
            switch ((int)map.get("caStatus")){
                case 0:map.put("caStatus","未上课");break;
                case 1:map.put("caStatus","上课中");break;
                case 2:map.put("caStatus","已上课");break;
                default:map.put("caStatus","取消");
            }
            if(((int)map.get("caType"))==0) map.put("caType","正常");
            else                             map.put("caType","小班");

            int currentSignCount = studentSignService.getCurrentCount((Integer) map.get("caId"));
            map.put("currentSignCount",currentSignCount);
            int classCount = tLqclassService.selectClassRealCount((Integer) map.get("classId"));
            map.put("classCount",classCount);

            SimpleDateFormat startsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String startTime = startsdf.format(map.get("startTime"));
            SimpleDateFormat endsdf = new SimpleDateFormat("HH:mm [EEEE]");
            String endTime = endsdf.format(map.get("endTime"));
            String time = new StringBuilder().append(startTime).append("~")
                    .append(endTime).toString();
            map.put("courseTime",time);

        }
        JsonUtil.write(response, JSON.toJSONString(list));
    }


    //删除课程
    @RequestMapping("/deleteCourse.do")
    @ResponseBody
    public int deleteCourse(Integer caId){
        return service.deleteByPrimaryKey(caId);
    }

    //我要排课
    @RequestMapping("/arrangeCourse.do")
    public ModelAndView arrangeCourse(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/WEB-INF/view/course/arrangementCourse.jsp");
        return mv;
    }

    //我要排课
    @RequestMapping("/paike.do")
    public ModelAndView paike(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/WEB-INF/view/course/createCourseArrange.jsp");
        return mv;
    }


    //插入排课
    @RequestMapping("/createCourseArrange.do")
    @ResponseBody
    public int createCourseArrangement(HttpServletRequest request,HttpServletResponse response
                                        ,String courseName,Integer courseType,String courseContent
                                        ,String startTime,String endTime,Integer courseNumber){
        System.out.println("****************"+courseName+"************");
        System.out.println("****************"+courseType+"************");
        System.out.println("****************"+courseContent+"************");
        System.out.println("****************"+startTime+"************");
        System.out.println("****************"+endTime+"************");
        System.out.println("****************"+courseNumber+"************");
        HashMap<String,Object> paramMap = new HashMap<String, Object>();
        SessionUser sessionUser  =  WebUtil.getLoginUser(request);
        Integer userId = sessionUser.getUserId();
        paramMap.put("userId",userId);
        paramMap.put("courseName",courseName);
        paramMap.put("courseType",courseType);
        paramMap.put("courseContent",courseContent);
        paramMap.put("classTime",courseNumber);
        return 1;
    }

}
