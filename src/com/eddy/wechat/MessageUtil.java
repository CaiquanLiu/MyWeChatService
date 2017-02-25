package com.eddy;

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

/**
 * 
 * @author 黄路飞
 *
 * @data  2016年6月18日21:56:47
 */
public class MessageUtil {
    // 请求消息类型：文本
        public static final String REQ_MESSAGE_TYPE_TEXT = "text";
        // 请求消息类型：图片
        public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
        // 请求消息类型：语音
        public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
        // 请求消息类型：视频
        public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
        // 请求消息类型：地理位置
        public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
        // 请求消息类型：链接
        public static final String REQ_MESSAGE_TYPE_LINK = "link";

        // 请求消息类型：事件推送
        public static final String REQ_MESSAGE_TYPE_EVENT = "event";

        // 事件类型：subscribe(订阅)
        public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
        // 事件类型：unsubscribe(取消订阅)
        public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
        // 事件类型：scan(用户已关注时的扫描带参数二维码)
        public static final String EVENT_TYPE_SCAN = "scan";
        // 事件类型：LOCATION(上报地理位置)
        public static final String EVENT_TYPE_LOCATION = "LOCATION";
        // 事件类型：CLICK(自定义菜单)
        public static final String EVENT_TYPE_CLICK = "CLICK";

        // 响应消息类型：文本
        public static final String RESP_MESSAGE_TYPE_TEXT = "text";
        // 响应消息类型：图片
        public static final String RESP_MESSAGE_TYPE_IMAGE = "image";
        // 响应消息类型：语音
        public static final String RESP_MESSAGE_TYPE_VOICE = "voice";
        // 响应消息类型：视频
        public static final String RESP_MESSAGE_TYPE_VIDEO = "video";
        // 响应消息类型：音乐
        public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
        // 响应消息类型：图文
        public static final String RESP_MESSAGE_TYPE_NEWS = "news";

        /**
         * 解析微信发来的请求（XML）
         * 
         * @param request
         * @return Map<String, String>
         * @throws Exception
         */
        //@SuppressWarnings("unchecked")
        public static HashMap<String, String> parseXML(HttpServletRequest request) throws Exception {
            // 将解析结果存储在HashMap中
            HashMap<String, String> map = new HashMap<String, String>();

            // 从request中取得输入流
            InputStream inputStream = request.getInputStream();
            // 读取输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(request.getInputStream());
            // 得到xml根元素
            Element root = document.getRootElement();
            
            recursiveParseXML(root,map);

            inputStream.close();
            inputStream = null;

            return map;
        }
        
        private static void recursiveParseXML(Element root,HashMap<String, String> map){
            // 得到根元素的所有子节点
            List<Element> elementList = root.elements();
            //判断有没有子元素列表
            if(elementList.size() == 0){
                map.put(root.getName(), root.getText());
            }else{
                //遍历
                for (Element e : elementList){
                    recursiveParseXML(e,map);
                }
            }
        }

        /**
         * 扩展xstream使其支持CDATA
         */
        private static XStream xstream = new XStream(new XppDriver() {
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out) {
                    // 对所有xml节点的转换都增加CDATA标记
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

        /**
         * 文本消息对象转换成xml
         * 
         * @param textMessage 文本消息对象
         * @return xml
         */
        public static String messageToXML(TextMessage textMessage) {
            xstream.alias("xml", TextMessage.class);
            return xstream.toXML(textMessage);
        }

        /**
         * 图片消息对象转换成xml
         * 
         * @param imageMessage 图片消息对象
         * @return xml
         */
        public static String messageToXML(ImageMessage imageMessage) {
            xstream.alias("xml", ImageMessage.class);
            return xstream.toXML(imageMessage);
        }

        /**
         * 语音消息对象转换成xml
         * 
         * @param voiceMessage 语音消息对象
         * @return xml
         */
        public static String messageToXML(VoiceMessage voiceMessage) {
            xstream.alias("xml", VoiceMessage.class);
            return xstream.toXML(voiceMessage);
        }

        /**
         * 视频消息对象转换成xml
         * 
         * @param videoMessage 视频消息对象
         * @return xml
         */
        public static String messageToXML(VideoMessage videoMessage) {
            xstream.alias("xml", VideoMessage.class);
            return xstream.toXML(videoMessage);
        }
    }