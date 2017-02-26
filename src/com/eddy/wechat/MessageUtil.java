package com.eddy.wechat;

import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class MessageUtil {
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
    public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
    public static final String REQ_MESSAGE_TYPE_LINK = "link";

    public static final String REQ_MESSAGE_TYPE_EVENT = "event";

    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
    public static final String EVENT_TYPE_SCAN = "scan";
    public static final String EVENT_TYPE_LOCATION = "LOCATION";
    public static final String EVENT_TYPE_CLICK = "CLICK";

    public static final String RESP_MESSAGE_TYPE_TEXT = "text";
    public static final String RESP_MESSAGE_TYPE_IMAGE = "image";
    public static final String RESP_MESSAGE_TYPE_VOICE = "voice";
    public static final String RESP_MESSAGE_TYPE_VIDEO = "video";
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";

    //@SuppressWarnings("unchecked")
        public static HashMap<String, String> parseXML(HttpServletRequest request) throws Exception {
            HashMap<String, String> map = new HashMap<String, String>();

            InputStream inputStream = request.getInputStream();
            SAXReader reader = new SAXReader();
            Document document = reader.read(request.getInputStream());
            Element root = document.getRootElement();
            
            recursiveParseXML(root,map);

            inputStream.close();
            inputStream = null;

            return map;
        }
        
        private static void recursiveParseXML(Element root,HashMap<String, String> map){
            List<Element> elementList = root.elements();
            if(elementList.size() == 0){
                map.put(root.getName(), root.getText());
            }else{
                for (Element e : elementList){
                    recursiveParseXML(e,map);
                }
            }
        }

   
        private static XStream xstream = new XStream(new XppDriver() {
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out) {
                    boolean cdata = true;

                    //@SuppressWarnings("unchecked")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });


    public static String messageToXML(TextMessage textMessage) {
        xstream.alias("xml", TextMessage.class);
        return xstream.toXML(textMessage);
    }


    public static String messageToXML(ImageMessage imageMessage) {
        xstream.alias("xml", ImageMessage.class);
        return xstream.toXML(imageMessage);
    }


    public static String messageToXML(VoiceMessage voiceMessage) {
        xstream.alias("xml", VoiceMessage.class);
        return xstream.toXML(voiceMessage);
    }

    public static String messageToXML(VideoMessage videoMessage) {
        xstream.alias("xml", VideoMessage.class);
        return xstream.toXML(videoMessage);
    }
}