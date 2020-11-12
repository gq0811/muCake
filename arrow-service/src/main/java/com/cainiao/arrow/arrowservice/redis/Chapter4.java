package com.cainiao.arrow.arrowservice.redis;

import redis.clients.jedis.*;

import java.lang.reflect.Method;
import java.util.*;
public class Chapter4 {


    public static void main(String[] args) {
        new Chapter4().run();
    }

    private void run() {
        Jedis conn = new Jedis("localhost");
        //刷新缓存
        //conn.flushAll();
        conn.select(15);
        // 卖家 =》 市场 =》 买家
        //testListItem(conn,false);
        //testPurchaseItem(conn);
        testBenchMark(conn);
    }

    private void testBenchMark(Jedis conn) {
        //间隔5秒
        benchmarkUpdateToken(conn, 5);
        benchmarkUpdateToken2(conn, 5);
    }
    private void benchmarkUpdateToken(Jedis conn, int duration) {
        try {
            // 使用java反射功能，减少函数冗余（可以对比发现）
            //@SuppressWarnings，表示警告抑制，告诉编译器不用提示相关的警告信息。
            //"rawtypes"，这个参数是告诉编译器不用提示使用基本类型参数时相关的警告信息。一般是在通过传参调用某个方法的时候进行标识
            @SuppressWarnings("rawtypes")
            Class[] args = new Class[]{
                    Jedis.class, String.class, String.class, String.class
            }; // 定义4个函数参数
            Method[] methods = new Method[]{
                    this.getClass().getDeclaredMethod("updateToken", args),
                    this.getClass().getDeclaredMethod("updateTokenPipeline", args),
            };
            for (Method method : methods) {
                int count = 0;
                long start = System.currentTimeMillis();
                long end = System.currentTimeMillis() + duration * 1000;
                while (start < end) {
                    method.invoke(this, conn, "token", "user", "item");
                    start += 500;
                }

                long delta = System.currentTimeMillis() - start;
                System.out.println(
                        method.getName() + ' ' +
                                count + ' ' +
                                (delta / 1000) + ' ' +
                                (count / (delta / 1000)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 不使用反射
     * @param conn
     * @param duration
     */
    private void benchmarkUpdateToken2(Jedis conn, int duration) {
        int count = 0;
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis() + duration * 1000;
        while (start < end) {
            updateToken(conn, "token", "user", "item");
        }

        long delta = System.currentTimeMillis() - start;
        System.out.println(
                "updateToken" + ' ' +
                        count + ' ' +
                        (delta / 1000) + ' ' +
                        (count / (delta / 1000)));


        count = 0;
        start = System.currentTimeMillis();
        end = System.currentTimeMillis() + duration * 1000;
        while (start < end) {
            updateTokenPipeline(conn, "token", "user", "item");
            start += 500;
        }

        delta = System.currentTimeMillis() - start;
        System.out.println(
                "updateToken" + ' ' +
                        count + ' ' +
                        (delta / 1000) + ' ' +
                        (count / (delta / 1000)));
    }

    /**
     * 使用非事务模式，但是集成提交任务
     * @param conn
     * @param token
     * @param user
     * @param item
     */
    private void updateTokenPipeline(Jedis conn, String token, String user, String item) {
        long timestamp = System.currentTimeMillis() / 1000;
        Pipeline pipe = conn.pipelined();
        pipe.multi();
        pipe.hset("login:", token, user);
        pipe.zadd("recent:", timestamp, token);
        if (item != null) {
            pipe.zadd("viewed:" + token, timestamp, item);
            pipe.zremrangeByRank("viewed:" + token, 0, -26);
            pipe.zincrby("viewed:", -1, item);
        }
        pipe.exec();
        System.out.println("login:"+pipe.hgetAll("login:"));
        System.out.println("recent:"+pipe.zrangeWithScores("recent:",0,-1));
    }

    private void updateToken(Jedis conn, String token, String user, String item) {
        long timestamp = System.currentTimeMillis() / 1000;
        conn.hset("login:", token, user);
        conn.zadd("recent:", timestamp, token);
        if (item != null) {
            conn.zadd("viewed:" + token, timestamp, item);
            conn.zremrangeByRank("viewed:" + token, 0, -26);
            conn.zincrby("viewed:", -1, item);
        }
        System.out.println("recent:"+conn.zrangeWithScores("recent:",0,-1));
    }

    /**
     * 测试2：购买
     * @param conn
     */
    private void testPurchaseItem(Jedis conn) {
        testListItem(conn, true);
        // 初始化买家信息
        conn.hset("users:buyerId", "funds", "100");
        conn.hset("users:buyerId", "name", "李雷");
        conn.hset("users:buyerId", "tel", "123444444");
        Map<String, String> buyersMap = conn.hgetAll("users:buyerId");
        System.out.println("输出买家信息：");
        for (Map.Entry<String, String> entry : buyersMap.entrySet()) {
            System.out.println(" " + entry.getKey() + " " + entry.getValue());
        }
        //买家buyerId购买卖家userID1发布的商品Item1，lprice
        boolean result = purchaseItem(conn, "userID1", "Item1", "buyerId", 10);

        System.out.println("输出卖家信息：");
        Map<String, String> sellerMap = conn.hgetAll( "users:userID1");
        for (Map.Entry<String, String> entry : sellerMap.entrySet()) {
            System.out.println(" " + entry.getKey() + " " + entry.getValue());
        }
    }

    /**
     * 购买
     * @param conn
     * @param sellerId
     * @param itemId
     * @param buyerId
     * @param lprice 预期价格，如果实际价格不等于预期价格，则直接返回。
     * 以下逻辑类似于发布商品
     */
    private boolean purchaseItem(Jedis conn, String sellerId, String itemId, String buyerId, int lprice) {
        String buyer = "users:" + buyerId;
        String seller = "users:" + sellerId;
        Long endTime = System.currentTimeMillis() + 5000;
        String inventory = "inventory:" + buyerId;
        String item = itemId + '.' + sellerId;
        while (System.currentTimeMillis() < endTime) {
            conn.watch("market:", buyer);
            double price = conn.zscore("market:", item);
            double funds = Double.parseDouble(conn.hget(buyer, "funds"));
            //实际价格不等于预期价格 或者 买家的钱不够
            if (price != lprice || price > funds) {
                conn.unwatch();
                return false;
            }
            Transaction trans = conn.multi();
            //如果没有key对应的值，会做新增的动作。
            trans.hincrBy(seller, "funds", (long) price);
            trans.hincrBy(buyer, "funds", (long) -price);
            //在卖家的仓库里，加上对应的商品
            trans.sadd(inventory, itemId);
            //市场上减去该商品
            trans.zrem("market:", item);
            List<Object> results = trans.exec();
            //如果返回null，说明事务执行因为watch的key内容被修改了，所以被拒绝了
            if (results == null) {
                continue;
            }
            return true;
        }
        return false;
    }

    /**
     * 测试1：
     * 使用事务检查watch，将商品放到市场上
     * @param conn
     * @param nested
     */
    private void testListItem(Jedis conn, boolean nested) {
        if (!nested){
            System.out.println("\n----- testListItem -----");
        }

        //给userID1这个卖家分配多个商品
        String sellerId = "userID1";
        String item = "Item1";
        conn.sadd("inventory:" + sellerId, item);

        String item2 = "Item2";
        conn.sadd("inventory:" + sellerId, item2);

        String item3 = "Item3";
        conn.sadd("inventory:" + sellerId, item3);

        System.out.println("当前卖家拥有的商品有：");
        Set<String> itemSet = conn.smembers("inventory:" + sellerId);
        for (String one : itemSet) {
            System.out.println("  " + one);
        }

        //发布两个商品到市场上
        listItem(conn, item, sellerId, 10);
        listItem(conn, item2, sellerId, 100);

        // 输出商场商品：
        System.out.println("输出商场商品：");
        Set<Tuple> sellingItemSets = conn.zrangeWithScores("market:", 0, -1);
        for (Tuple t : sellingItemSets) {
            System.out.println("  " + t.getElement() + "---" + t.getScore());
        }
    }

    /**
     * 发布一个商品到市场上
     *
     * market:[{Item1.userID1,10},{Item2.userID1,100}，{Item1.userID2,11}]
     * 商品内容是商品名称+sellerId，代表属于该卖家的商品；同时把商品价格作为score，进行排名
     * @param conn
     * @param item
     * @param sellerId
     * @param price
     */
    private boolean listItem(Jedis conn, String item, String sellerId, double price) {
        //设置超时时间5秒
        long endTime = System.currentTimeMillis() + 5000;

        String inventory = "inventory:" + sellerId;
        String itemSellerId = item + "." + sellerId;
        while (System.currentTimeMillis() < endTime) {

            // 1.1 监视表inventory，线程可见；乐观锁
            conn.watch(inventory);
            // 1.2 检查卖家是否有这个商品，若没有，返回false
            if (!conn.sismember(inventory, item)) {
                conn.unwatch();
                return false;
            }
            // 1.3 开启事务流水线
            Transaction transaction = conn.multi();
            //发布到市场，同时在自己的库存中去掉该商品（符合正常的买卖逻辑，应该划在一个事务里）
            transaction.zadd("market:", price, itemSellerId);
            transaction.srem(inventory, item);
            // 1.4 执行流水线操作
            List<Object> results = transaction.exec();

            // 1.5 如果事务执行失败，则重试-继续执行，直到超时
            if (results == null) {
                continue;
            }
            return true;
        }
        return false;
    }

}
