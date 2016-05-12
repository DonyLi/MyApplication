package com.example.meihaoshiguang.entity;

import java.util.ArrayList;

/**
 * Created by Jason on 2016/3/21.
 */


/**
 * {
 * "headObject":
 * {
 * "list":[
 * {"recipeId":7,"name":"最上瘾的绝味川菜——水煮肉片","img":"http://123.56.145.151:8080/img/2/7.jpg"},
 * {"recipeId":38,"name":"自制流油咸鸭蛋","img":"http://123.56.145.151:8080/img/2/38.jpg"},
 * {"recipeId":113,"name":"无需烤箱【圣诞布丁】蒸出来的传统英式圣诞甜品","img":"http://123.56.145.151:8080/img/2/113.jpg"},
 * {"recipeId":184,"name":"菌菇山药排骨汤","img":"http://123.56.145.151:8080/img/2/184.jpg"},
 * {"recipeId":12744,"name":"夏日清爽果蔬，黄瓜苹果汁","img":"http://123.56.145.151:8080/img/2/12744.jpg"},
 * {"recipeId":12726,"name":"酸奶奶酪","img":"http://123.56.145.151:8080/img/2/12726.jpg"}]},
 * <p/>
 * "recipe_object":
 * {"list":[
 * {"id":4,"title":"私房五香肘子","img":"http://123.56.145.151:8080/img/1/4.jpg"},
 * {"id":128,"title":"栗子巧克力蛋糕 ","img":"http://123.56.145.151:8080/img/1/128.jpg"},
 * {"id":865,"title":"小鸡炖土豆","img":"http://123.56.145.151:8080/img/1/865.jpg"},
 * {"id":1139,"title":"奥尔良鸡肉卷","img":"http://123.56.145.151:8080/img/1/1139.jpg"},
 * {"id":1315,"title":"炸鲜奶","img":"http://123.56.145.151:8080/img/1/1315.jpg"},
 * {"id":2324,"title":"炒面段","img":"http://123.56.145.151:8080/img/1/2324.jpg"}
 * ]}
 * }
 */
public class HeadInfo {
    public HeadObject headObject;
    public Recipe_object recipe_object;

    public static class HeadObject {
        public ArrayList<HeadObjectInfo> list;

        public static class HeadObjectInfo {
            public int recipeId;
            public String name;
            public String img;
        }
    }

    public static class Recipe_object {
        public ArrayList<Recipe_objectInfo> list;

        public static class Recipe_objectInfo {
            public int id;
            public String title;
            public String img;
        }
    }
}









