package com.eddy;

import java.io.BufferedReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class Util {
	private static Logger logger = Logger.getLogger(MyWeChatMain.class);

	/**
	 * display request parameter from Get/Post
	 * @param request
	 */
	static public void showParams(HttpServletRequest request) {
		Map map = new HashMap();
		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();

			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				if (paramValue.length() != 0) {
					map.put(paramName, paramValue);
				}
			}
		}

		Set<Map.Entry<String, String>> set = map.entrySet();
		// System.out.println("------------------------------");
		logger.info("------------------------------");
		for (Map.Entry entry : set) {
			// System.out.println(entry.getKey() + ":" + entry.getValue());
			logger.info(entry.getKey() + ":" + entry.getValue());
		}
		// System.out.println("------------------------------");
		logger.info("------------------------------");
	}

	/**
	 * get XML data from Post request
	 * @param request
	 * @return
	 */
	static public String getPostData(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				sb.append(line);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sb.toString();
	}
	
//	static public String toXML(){  
//        XStream xStream = new XStream();  
//        Person person = new Person();  
//        person.setName("rojer");  
//        person.setPhoneNuber(999);  
//        Address address1 = new Address();  
//        address1.setHouseNo(888);  
//        address1.setStreet("newyork");  
//        Address address2 = new Address();  
//        address2.setHouseNo(76767);  
//        address2.setStreet("toyo");  
//        person.getAddresses().add(address1);  
//        person.getAddresses().add(address2);  
////        xStream.alias("person", Person.class);  
////        xStream.alias("address",Address.class);  
//        xStream.setMode(XStream.NO_REFERENCES);  
////        xStream.addImplicitCollection(Person.class, "addresses");  
////        xStream.useAttributeFor(Person.class,"name");  
//        //注册使用了注解的VO  
//        xStream.processAnnotations(new Class[]{Person.class,Address.class});  
//        String xml = xStream.toXML(person);  
//  
//        System.out.println(xml);  
//        return xml;  
//    }  
	
	 
}
