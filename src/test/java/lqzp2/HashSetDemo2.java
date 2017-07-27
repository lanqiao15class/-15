package lqzp2;

import java.util.HashSet;

import com.lanqiao.common.SysMenuExt;
import com.lanqiao.model.Student;

public  class HashSetDemo2 
{
    public static void main(String[] args) {
        // 创建集合对象
        HashSet<Integer> hs = new HashSet<Integer>();
        // 添加元素
        hs.add(1);
        hs.add(2);
        hs.add(2);
        
        // 遍历集合
        for (Integer s : hs) 
        {
            System.out.println(s);
        }
        
        
        
    }
}
