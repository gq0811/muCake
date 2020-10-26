package com.cainiao.arrow.arrowservice.redis;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Hashtable;

public class RedisUtil {

    //本地默认的ip和端口
    private static String addr="127.0.0.1";
    private static int port=6379;

    public static void main(String[] args) {
        Jedis jedis=new Jedis(addr,port);
        String val = jedis.get("yourKey");
        System.out.println(val);
        HashMap hashMap = new HashMap<>();
       
    }


}
