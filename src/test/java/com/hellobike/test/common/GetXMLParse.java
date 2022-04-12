package com.hellobike.test.common;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class GetXMLParse {

    //解析xml转换成输入流
    public static InputStream getXmlInputStream(String xmlPath) {
        InputStream inputStream = null;
        try {
             inputStream = new FileInputStream(xmlPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    //2-解析XML-IO流 ，获取Document 对象，以及Document对象 的根节点
    public static Element getRootElementFromIs(InputStream inputStream) throws Exception {
        if (inputStream == null) {
            return null;
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder docBuilder = factory.newDocumentBuilder();

        Document doc = docBuilder.parse(inputStream);

        Element root = doc.getDocumentElement();

        if (inputStream != null) {
            inputStream.close();
        }
        return  root;
    }

    //3-从根元素解析得到元素
    private static HashMap<String, String> parseElementFromRoot(Element root) {
        NodeList nl = root.getChildNodes();
//        System.out.println("nodeList =" + nl.getLength());
        HashMap<String, String> maps = new HashMap<>();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Element) {
                Element ele = (Element) node;
                if ("parameter".equals(ele.getTagName())) {
                    //4-从元素解析得到属性值
//                    getDataFromElement(ele);
                    maps.put(ele.getAttribute("name"), ele.getAttribute("value"));
                    //5-从元素解析特定子元素并解析
//                    getCertainElementFromParentElement(ele);
                }
            }
        }

        return maps;
    }

    //根据name值获取value信息
    public static String getParameterSutie(String name) throws Exception {
        InputStream inputStream = new FileInputStream("/Users/hb/Documents/testng/testcase.xml");
        Element element = getRootElementFromIs(inputStream);
        HashMap<String, String> maps =  parseElementFromRoot(element);
//        System.out.println(maps.size());
        String parameterValue = "";
        for (Map.Entry<String, String> entrySet : maps.entrySet()) {
            if (name.equals(entrySet.getKey())) {
                parameterValue = entrySet.getValue();
            }
        }
        return parameterValue;
    }

    //4-从元素解析得到属性值
    private static void getDataFromElement(Element ele) {
        String name = ele.getAttribute("name");//根据属性名称读取属性值
        System.out.println("name == " + name);
        String value = ele.getAttribute("value");
        System.out.println("value == " + value);
    }
    //5-从元素解析特定子元素并解析(以property为例)
    private static void getCertainElementFromParentElement(Element ele) {
        NodeList parameterEleList = ele.getElementsByTagName("parameter");//根据标签名称获取标签元素列表
        for (int i = 0; i < parameterEleList.getLength(); i++) {
            Node node = parameterEleList.item(i);
            System.out.println("node = " + node);
            if (node instanceof Element) {
                Element parameterEle = (Element) node;
                String name = parameterEle.getAttribute("name");
                System.out.println("parameterEle: name == " + name);
                String value = parameterEle.getAttribute("value");
                System.out.println("parameterEle: value == " + value);
            }
        }

    }

}
