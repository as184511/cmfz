package com.baizhi.util;

public class PathUtil {

    private static String commonPath;    //公共路径
    private static String serverPath;    //服务器路径


    //功能描述:轮播图图片保存路径

    public static String getBannerPath() {

        return PropertyUtil.getPropertiesPath().getProperty("bannerPath");
    }


    // 功能描述:用户头像保存路径

    public static String getUserPath() {

        return PropertyUtil.getPropertiesPath().getProperty("userPath");
    }

    //专辑图
    public static String getAlbumPath() {

        return PropertyUtil.getPropertiesPath().getProperty("albumPath");
    }
    //专辑章节

    public static String getChapterPath() {

        return PropertyUtil.getPropertiesPath().getProperty("chapterPath");
    }
}
