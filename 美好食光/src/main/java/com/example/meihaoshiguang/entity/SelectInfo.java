package com.example.meihaoshiguang.entity;

import java.util.List;

/**
 * Created by Jason on 2016/4/1.
 */
public class SelectInfo {


    /**
     * total_count : 99
     * list : [{"cookbook_id":539,"name":"白菜炖豆腐",
     * "introduce":"小时候上幼儿园，吃的都是大锅饭。那个时候父母工资低就几百块钱，物资不像现在这么丰富，加上那时候家里出了很多事情，并不是像现在想吃什么就能吃个够，而那时候在幼儿园吃大师傅做的炸酱面，辣椒白菜炖肉，白菜豆腐粉条炖肉\u2026\u2026都成了我现在最怀念的美味，美味的旧时光\u2026\u2026","img":"http://123.56.145.151:8080/img/1/539.jpg","step_count":6,"views":0,"collection_count":0,"comment_count":0},{"cookbook_id":1501,"name":"韩式辣白菜年糕火锅思密达","introduce":"继烤肉拌饭炸酱面火爆京城后，思密达国的年糕火锅又席卷而来。我是个爱吃又不爱排队的人，所以在尝试了一次之后直接偷师回家了，大冬天的，被涮羊肉腻着吃海底捞吃的无感的人儿快投奔到这红彤彤，芝香满溢的年糕火锅里来。快，到锅里来(*^o^*)","img":"http://123.56.145.151:8080/img/1/1501.jpg","step_count":8,"views":0,"collection_count":0,"comment_count":0},{"cookbook_id":1860,"name":"圆白菜焖饭","introduce":"东欧的一款家常焖饭~因胖子非常喜欢，每隔一段时间做一次~很好吃，放进烤箱等着可以了~有无配菜皆可，于我当然是配汤了，嘿嘿~~","img":"http://123.56.145.151:8080/img/1/1860.jpg","step_count":12,"views":0,"collection_count":0,"comment_count":0},{"cookbook_id":1920,"name":"超级简单-白菜虾仁粥","img":"http://123.56.145.151:8080/img/1/1920.jpg","step_count":3,"views":0,"collection_count":0,"comment_count":0},{"cookbook_id":2058,"name":"圆白菜素炒饼","img":"http://123.56.145.151:8080/img/1/2058.jpg","step_count":6,"views":0,"collection_count":0,"comment_count":0},{"cookbook_id":2067,"name":"圆白菜焖饼","img":"http://123.56.145.151:8080/img/1/2067.jpg","step_count":14,"views":0,"collection_count":0,"comment_count":0},{"cookbook_id":2071,"name":"辣白菜炒饼","introduce":"许多的山珍海味对于我来说，仍及不上一盘很家常的味道。<br />炒饼是其一。<br />饿的肚子呱呱叫时，哪里有时间去捯饬很精细的吃食。<br />打开冰箱发现竟然没什么存货，<br />只有几份常备的饼条子，是平时做的剩余的大饼切好备在冰箱里的。<br />这时取出来，随手加点有什么算什么的小蔬菜，<br />炒这么一锅热腾腾的饼，<br />真是解饿。<br />两人围锅而坐，还增进感情。<br />：）<br />对于炒饼，我没有特别高的要求，可以有肉，也可以没有。<br />只有两点：<br />一是酱油，二是蒜。<br />有了这两样的炒饼，才叫炒饼。","img":"http://123.56.145.151:8080/img/1/2071.jpg","step_count":1,"views":0,"collection_count":0,"comment_count":0},{"cookbook_id":2072,"name":"白菜素炒饼 ","img":"http://123.56.145.151:8080/img/1/2072.jpg","step_count":2,"views":0,"collection_count":0,"comment_count":0},{"cookbook_id":2086,"name":"元白菜炒饼","img":"http://123.56.145.151:8080/img/1/2086.jpg","step_count":1,"views":0,"collection_count":0,"comment_count":0},{"cookbook_id":2460,"name":"猪肉圆白菜包子","img":"http://123.56.145.151:8080/img/1/2460.jpg","step_count":3,"views":0,"collection_count":0,"comment_count":0}]
     */


    /**
     * cookbook_id : 539
     * name : 白菜炖豆腐
     * introduce :
     * 小时候上幼儿园，吃的都是大锅饭。那个时候父母工资低就几百块钱，物资不像现在这么丰富，加上那时候家里出了很多事情，并不是像现在想吃什么就能吃个够，而那时候在幼儿园吃大师傅做的炸酱面，辣椒白菜炖肉，白菜豆腐粉条炖肉……都成了我现在最怀念的美味，美味的旧时光……
     * img : http://123.56.145.151:8080/img/1/539.jpg
     * step_count : 6
     * views : 0
     * collection_count : 0
     * comment_count : 0
     */
    public int total_count;
    public List<ListBean> list;

    public static class ListBean {
        public int cookbook_id;
        public String name;
        public String introduce;
        public String img;
        public int step_count;
        public int views;
        public int collection_count;
        public int comment_count;


    }
}
