package com.example.meihaoshiguang;

import android.os.Environment;

public interface Constants {
    //http://123.56.145.151:8080/CookBook-server/home
    //http://120.24.100.227:8080/CookBook-server
    String SERVER_HOST = "http://123.56.145.151:8080/CookBook-server";
    //					  http://123.56.145.151:8080/CookBook-server/home/
    String IMG_CACHE_PATH = Environment.getExternalStorageDirectory().getPath() +
            "/meihaoshiguang/img";
    /**
     * ��ȡ���׷���Ľӿڵ�ַ
     **/
    String URL_CATEGORY = SERVER_HOST + "/category";
    /**
     * ��ȡ��ҳ���ݵĽӿڵ�ַ
     **/
    String URL_HOME = SERVER_HOST + "/home";
    /**
     * ��ȡ��������б�Ľӿڵ�ַ
     **/
    String URL_RECIPE_LIST = SERVER_HOST + "/cooklist?id=_id&page=_page&count=10";
    /**
     * ��ȡ��������Ľӿ�
     **/
    String URL_RECIPE_DETAIL = SERVER_HOST + "/caipu?id=_id";
    /**
     * ���ݲ������ֲ�ѯ���׵Ľӿ�
     **/
    String URL_SEARCH_BY_NAME = SERVER_HOST + "/search_name?key=_key&page=_page&count=10";
    /**
     * ����ʳ�Ĳ�ѯ���׵Ľӿ�
     **/
    String URL_SEARCH_BY_MATERIAL = SERVER_HOST + "/search_material?key=_key&page=_page&count=10";
    /**
     * ��ȡ���׼�����۵Ľӿ�
     **/
    String URL_GET_BRIEF_COOK_COMMENTS = SERVER_HOST + "/cookbook/brief_comments?cook_id=_cook_id&time_stamp=_time_stamp";
    /**
     * ��ȡ����all���۵Ľӿ�
     **/
    String URL_GET_RECIPE_ALL_COMMENTS = SERVER_HOST + "/cookbook/all_comments?cook_id=_cook_id&page=_page&count=10";

    /**
     * ����ӿ�
     **/
    String URL_LOGIN = SERVER_HOST + "/login";
    int TAG_CATEGORY = 1;
    int TAG_SEARCH_BY_RECIPE_NAME = 2;
    int TAG_SEARCH_BY_INGREDIENT = 3;
    /**
     * ���۲��׵Ľӿ�
     **/
    String URL_ADD_COOK_COMMENT = SERVER_HOST + "/cookbook/add_comment";

    /**
     * mobƽ̨app-key
     **/
    String MOB_APP_KEY = "95c0d5588af8";
    /**
     * mobƽ̨app-��Կ
     **/
    String MOB_APP_SECRET = "14c32b38e015d4c8d51ff43e081b109f";
    /**
     * ����ֻ����Ƿ���ע��Ľӿ�
     **/
    String URL_CHECK_PHONE = SERVER_HOST + "/regist/check-phone?phone=_phone";

    String URL_REGISTER_BY_PHONE = SERVER_HOST + "/regist/phone";
}
