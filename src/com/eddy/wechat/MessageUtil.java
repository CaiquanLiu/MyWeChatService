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
 * @author ��·��
 *
 * @data  2016��6��18��21:56:47
 */
public class MessageUtil {
    // ������Ϣ���ͣ��ı�
        public static final String REQ_MESSAGE_TYPE_TEXT = "text";
        // ������Ϣ���ͣ�ͼƬ
        public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
        // ������Ϣ���ͣ�����
        public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
        // ������Ϣ���ͣ���Ƶ
        public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
        // ������Ϣ���ͣ�����λ��
        public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
        // ������Ϣ���ͣ�����
        public static final String REQ_MESSAGE_TYPE_LINK = "link";

        // ������Ϣ���ͣ��¼�����
        public static final String REQ_MESSAGE_TYPE_EVENT = "event";

        // �¼����ͣ�subscribe(����)
        public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
        // �¼����ͣ�unsubscribe(ȡ������)
        public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
        // �¼����ͣ�scan(�û��ѹ�עʱ��ɨ���������ά��)
        public static final String EVENT_TYPE_SCAN = "scan";
        // �¼����ͣ�LOCATION(�ϱ�����λ��)
        public static final String EVENT_TYPE_LOCATION = "LOCATION";
        // �¼����ͣ�CLICK(�Զ���˵�)
        public static final String EVENT_TYPE_CLICK = "CLICK";

        // ��Ӧ��Ϣ���ͣ��ı�
        public static final String RESP_MESSAGE_TYPE_TEXT = "text";
        // ��Ӧ��Ϣ���ͣ�ͼƬ
        public static final String RESP_MESSAGE_TYPE_IMAGE = "image";
        // ��Ӧ��Ϣ���ͣ�����
        public static final String RESP_MESSAGE_TYPE_VOICE = "voice";
        // ��Ӧ��Ϣ���ͣ���Ƶ
        public static final String RESP_MESSAGE_TYPE_VIDEO = "video";
        // ��Ӧ��Ϣ���ͣ�����
        public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
        // ��Ӧ��Ϣ���ͣ�ͼ��
        public static final String RESP_MESSAGE_TYPE_NEWS = "news";

        /**
         * ����΢�ŷ���������XML��
         * 
         * @param request
         * @return Map<String, String>
         * @throws Exception
         */
        //@SuppressWarnings("unchecked")
        public static HashMap<String, String> parseXML(HttpServletRequest request) throws Exception {
            // ����������洢��HashMap��
            HashMap<String, String> map = new HashMap<String, String>();

            // ��request��ȡ��������
            InputStream inputStream = request.getInputStream();
            // ��ȡ������
            SAXReader reader = new SAXReader();
            Document document = reader.read(request.getInputStream());
            // �õ�xml��Ԫ��
            Element root = document.getRootElement();
            
            recursiveParseXML(root,map);

            inputStream.close();
            inputStream = null;

            return map;
        }
        
        private static void recursiveParseXML(Element root,HashMap<String, String> map){
            // �õ���Ԫ�ص������ӽڵ�
            List<Element> elementList = root.elements();
            //�ж���û����Ԫ���б�
            if(elementList.size() == 0){
                map.put(root.getName(), root.getText());
            }else{
                //����
                for (Element e : elementList){
                    recursiveParseXML(e,map);
                }
            }
        }

        /**
         * ��չxstreamʹ��֧��CDATA
         */
        private static XStream xstream = new XStream(new XppDriver() {
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out) {
                    // ������xml�ڵ��ת��������CDATA���
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
         * �ı���Ϣ����ת����xml
         * 
         * @param textMessage �ı���Ϣ����
         * @return xml
         */
        public static String messageToXML(TextMessage textMessage) {
            xstream.alias("xml", TextMessage.class);
            return xstream.toXML(textMessage);
        }

        /**
         * ͼƬ��Ϣ����ת����xml
         * 
         * @param imageMessage ͼƬ��Ϣ����
         * @return xml
         */
        public static String messageToXML(ImageMessage imageMessage) {
            xstream.alias("xml", ImageMessage.class);
            return xstream.toXML(imageMessage);
        }

        /**
         * ������Ϣ����ת����xml
         * 
         * @param voiceMessage ������Ϣ����
         * @return xml
         */
        public static String messageToXML(VoiceMessage voiceMessage) {
            xstream.alias("xml", VoiceMessage.class);
            return xstream.toXML(voiceMessage);
        }

        /**
         * ��Ƶ��Ϣ����ת����xml
         * 
         * @param videoMessage ��Ƶ��Ϣ����
         * @return xml
         */
        public static String messageToXML(VideoMessage videoMessage) {
            xstream.alias("xml", VideoMessage.class);
            return xstream.toXML(videoMessage);
        }
    }