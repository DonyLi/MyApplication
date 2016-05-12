package com.example.meihaoshiguang.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jason on 2016/3/28.
 */
public class RecipeDetailInfo implements Serializable {
    public static final long serialVersionUID = 1L;
    public String name;
    public String introduce;
    public String img;
    public String main_materials;
    public String assist_materials;
    public ArrayList<Steps> steps;
    public String tips;
    public int views;
    public int collection_count;
    public int comment_count;
    public Comments comments;

    public static class Steps implements Serializable {
        public String title;
        public String img;
    }

    public static class Comments implements Serializable {
        public int totalCount;
        public ArrayList<List> list;

        public static class List implements Serializable {
            public String userNickName;
            public String content;
            public String commentDate;
            public String userHeadPhoto;
        }

        public String timeStamp;
    }

    public String comment_time_stamp;
}
