package com.cainiao.arrow.arrowservice.redis;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;

public class RedisUtil {

    //本地默认的ip和端口
    private static String addr="127.0.0.1";
    private static int port=6379;

    public static void main(String[] args) {
//        Jedis jedis=new Jedis(addr,port);
//        String val = jedis.get("yourKey");
//        System.out.println(val);
//        HashMap hashMap = new HashMap<>();
//
        testLindex();
    }

    public static void testLindex(){
        Jedis jedis=new Jedis(addr,port);
        Random random = new Random();

        jedis.del("mylist");
        System.out.println(jedis.llen("mylist"));
        int len = 100000;
        String key = "mylist";
        for(int i=0;i<len;i++){
            jedis.lpush(key,i+"");
        }
        long curr = System.currentTimeMillis();
//        for(int i=0;i<len;i++){
//            String res = jedis.lindex(key,i);
////            if(i<10){
////                System.out.println(res);
////            }
//        }
//        System.out.println(System.currentTimeMillis()-curr);

        long curr2 = System.currentTimeMillis();
        for(int i=0;i<len;i++){
            jedis.lpop(key);
        }
        System.out.println(System.currentTimeMillis()-curr2);
        System.out.println(jedis.llen(key));
    }

}
