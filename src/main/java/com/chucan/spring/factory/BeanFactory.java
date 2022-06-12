package com.chucan.spring.factory;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


/**
 * @Author: chucan
 * @CreatedDate: 2022-06-10-2:11
 * @Description:
 */
public class BeanFactory {
    /**
     * 任务一：需要读取并解析beans.xml，通过放射去实例化其中的bean对象，并将所有的bean对象存储在一个map中
     * 任务二：对外提供获取实例对象的接口（根据id来获取）
     */

    // 这个map用来存储的是所有的bean，相当于spring中的ioc容器
    // map的key是
    private static Map<String,Object> map = new HashMap<>();

    static {
        // 实现任务一：读取beans.xml，通过反射去实例化对象
        // 加载xml
        InputStream resourceAsStream = BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml");
        // 解析xml
        SAXReader saxReader = new SAXReader();
        try{
            Document document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement();
            List<Element> beanList = rootElement.selectNodes("//beans");
            for(int i=0;i < beanList.size(); i++){
                Element element = beanList.get(i);
                // 处理每个bean元素，获取其id和class
                String id = element.attributeValue("id");
                String clazz = element.attributeValue("class");
                // 通过反射生成一个对象
                Class<?> aClass = Class.forName(clazz);
                Object o = aClass.newInstance();

                map.put(id,o);
            }

            // 实例化完成之后维护对象的依赖关系，检查哪些对象需要传值进入，根据它的配置，我们传入相应的值
            // 有property子元素的bean就有传值需求
            List<Element> properyList = rootElement.selectNodes("//property");
            // 解析property，并获取父元素
            for(int i = 0;i<properyList.size();i++){
                Element element = properyList.get(i);
                String name = element.attributeValue("name");
                String ref = element.attributeValue("ref");

                // 找到当前需要被处理依赖关系的bean
                Element parent = element.getParent();

                // 调用父元素对象的反射功能
                String parentId = parent.attributeValue("id");
                Object parentObject = map.get(parentId);
                // 遍历父对象中的所有方法，找到"set" + name
                Method[] methods = parentObject.getClass().getMethods();
                for(int j =0; j<methods.length; j++){
                    Method method = methods[j];
                    if(method.getName().equalsIgnoreCase("set" + name)){
                        method.invoke(parentObject, map.get(parentId));
                    }

                }

                // 把处理之后的parentObject重新放到map中
                map.put(parentId,parentObject);
            }


        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }

    // 任务二：对外提供获取实例对象的接口（根据id来获取）
    public static  Object getBean(String id) {
        return map.get(id);
    }
}
