package com.eddy.wechat;

/**
 * 
 * @author 黄路飞
 *
 * @data  2016年6月18日21:28:05
 */
public class VideoMessage extends BaseMessage {
    //语音
    private Video Video;

    public Video getVideo() {
        return Video;
    }

    public void setVideo(Video video) {
        Video = video;
    }

}

