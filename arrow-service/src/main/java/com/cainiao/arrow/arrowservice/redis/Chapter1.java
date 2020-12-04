package com.cainiao.arrow.arrowservice.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.ZParams;

import java.util.*;

/**
 * 投票系统
 * 1、每天展示得票数前50的文章
 * 2、每个人对一篇文章只能投一次票
 */
public class Chapter1 {

    Logger logger = LoggerFactory.getLogger(Chapter1.class);
    private static final int ONE_WEEK_IN_SECONDS = 7 * 86400;
    private static final int VOTE_SCORE = 432;
    private static final int ARTICLES_PER_PAGE = 25;
    //带冒号的列表说明是每个id都有的列表。文章id，组id
    //article:111 文章111的基本信息
    private static final String ARTICLE_FLAG = "article:";
    //vote:111 文章111的投票者列表
    private static final String VOTE_FLAG = "vote:";
    //分组的标志，即每个分组，存放着属于该组的文章id列表
    private static final String GROUP_FLAG = "group:";
    //按时间排序的文章排行榜zset
    private static final String TIME_FLAG = "time";
    //按分数排序的文章排行榜zset
    private static final String SCORE_FLAG = "score";


    public static final void main(String[] args) {
        new Chapter1().run();
    }

    public void run() {
        Jedis conn = new Jedis("localhost");
        conn.select(15);

        Long articleId = postArticle(
                conn, "username", "A title", "http://www.google.com");
        System.out.println("We posted a new article with id: " + articleId);
        System.out.println("Its HASH looks like:");
        Map<String,String> articleData = conn.hgetAll("article:" + articleId);
        for (Map.Entry<String,String> entry : articleData.entrySet()){
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }

        System.out.println();

        articleVote(conn, "other_user", articleId);
        String votes = conn.hget("article:" + articleId, "votes");
        System.out.println("We voted for the article, it now has votes: " + votes);
        assert Integer.parseInt(votes) > 1;

        System.out.println("The currently highest-scoring articles are:");
        List<Map<String,String>> articles = getArticles(conn, 1);
        printArticles(articles);
        assert articles.size() >= 1;

        addGroups(conn, articleId, new String[]{"new-group"});
        System.out.println("We added the article to a new group, other articles include:");
        articles = getGroupArticles(conn, "new-group", 1);
        printArticles(articles);
        assert articles.size() >= 1;
    }

    /**
     * 发布文章
     * 用"articleId"作为自增的id
     */
    public Long postArticle(Jedis conn, String user, String title, String link) {
        Long articleId = conn.incrBy("articleId",1);
        String articleKey = ARTICLE_FLAG + articleId;
        //按秒来计算
        long now = System.currentTimeMillis() / 1000;
        //组装信息
        Map<String,String> articleMap = new HashMap<>();
        articleMap.put("id",articleId.toString());
        articleMap.put("user",user);
        articleMap.put("title",title);
        articleMap.put("link",link);
        articleMap.put("score","1");
        conn.hmset(articleKey,articleMap);
        //加入到排行榜中
        //时间排行榜只有一开始发布文章的时候有
        conn.zadd(TIME_FLAG,now,articleKey);
        //分数排行榜，给每篇文章一个基础分数
        conn.zadd(SCORE_FLAG,now+VOTE_SCORE,articleKey);
        return articleId;
    }
    /**
     * 用户user给article投票
     */
    public void articleVote(Jedis conn, String user, Long articleId) {
        String articleKey = ARTICLE_FLAG + articleId;
        String voteKey = VOTE_FLAG + articleId;
        //文章已经7天了，不允许再投票了
        long day = System.currentTimeMillis() - ONE_WEEK_IN_SECONDS;
        if(conn.zscore(TIME_FLAG,articleKey)<=day){
            logger.error("current article is out of seven days");
            return;
        }
        //如果文章还存在,且该用户没有投过票，就增加相关记录
        if(conn.exists(articleKey)&&conn.sadd(voteKey,user)==1){
            conn.hincrBy(articleKey,"score",1);
            conn.zincrby(SCORE_FLAG,VOTE_SCORE,articleKey);
        }
    }
    /**
     * 用户user给article取消投票
     */
    public void articleUnVote(Jedis conn, String user, Long articleId) {
        String articleKey = ARTICLE_FLAG + articleId;
        String voteKey = VOTE_FLAG + articleId;
        //文章已经7天了，不允许再取消投票了，没有意义了
        long day = System.currentTimeMillis() - ONE_WEEK_IN_SECONDS;
        if(conn.zscore(TIME_FLAG,articleKey)<=day){
            logger.error("current article is out of seven days");
            return;
        }
        //如果文章还存在,且该用户有投过票，就去除相关记录
        if(conn.exists(articleKey)&&conn.srem(voteKey,user)==1){
            conn.hincrBy(articleKey,"score",-1);
            conn.zincrby(SCORE_FLAG,-VOTE_SCORE,articleKey);
        }
    }
    /**
     * 获取文章列表，前page个
     */
    public List<Map<String,String>> getArticles(Jedis conn, int page) {
        return getArticles(conn,page,SCORE_FLAG);
        //getArticles(conn,page,TIME_FLAG);
    }
    /**
     * 获取文章列表，前page个（用哪种排序）
     * 1、按照评分排序
     * 2、按照自然发布顺序排序
     */
    public List<Map<String,String>> getArticles(Jedis conn, int page, String order) {
        long start = (page-1)*ARTICLES_PER_PAGE;
        long end = start + ARTICLES_PER_PAGE;
        //反向取数据，相当于倒排之后，再去取分页的数据
        Set<String> set = conn.zrevrange(order,start,end);
        List<Map<String,String>> result = new ArrayList<>();
        for (String articleId : set){
            result.add(conn.hgetAll(articleId));
        }
        return result;
    }
    /**
     * 把文章加到指定的组里
     * 例如"地理"，"人文"，"科学"。一篇文章可能符合多个组
     */
    public void addGroups(Jedis conn, Long articleId, String[] toAdd) {
        String articleKey = ARTICLE_FLAG + articleId;
        //每个组，都加上这个文章id，相当于给这个文章打了多个标签
        for (String group : toAdd) {
            conn.sadd(GROUP_FLAG + group, articleKey);
        }
    }
    /**
     * 得到相应的组的文章
     */
    public List<Map<String,String>> getGroupArticles(Jedis conn, String group, int page) {
        return getGroupArticles(conn, group, page, SCORE_FLAG);
    }
    /**
     * 得到相应的组的文章（按照指定的顺序）
     */
    public List<Map<String,String>> getGroupArticles(Jedis conn, String group, int page, String order) {
        long start = (page-1)*ARTICLES_PER_PAGE;
        long end = start + ARTICLES_PER_PAGE;
        String groupKey = GROUP_FLAG + group;
        //inner之后的新表。因为总排序数据都存在了order，group只存了id，所以要做一个联表查询
        String groupOrderKey = order + group;
        //反向取数据，相当于倒排之后，再去取分页的数据
        ZParams zParams = new ZParams().aggregate(ZParams.Aggregate.MAX);
        //把后两个集合做inter之后，结果放到第一个集合。中间是ZParams参数，决定"分数"是取最大值还是最小值，还是求和
        conn.zinterstore(groupOrderKey,zParams,groupKey,order);
        Set<String> set = conn.zrevrange(groupOrderKey,start,end);
        //！！记得加上一个超时时间，因为groupOrderKey相当于是临时表，不做存储。
        conn.expire(groupOrderKey,60);
        return getArticles(conn,page,groupOrderKey);
    }

    /**
     * 打印一个文章列表
     */
    private void printArticles(List<Map<String,String>> articles){
        for (Map<String,String> article : articles){
            System.out.println("  id: " + article.get("id"));
            for (Map.Entry<String,String> entry : article.entrySet()){
                if (entry.getKey().equals("id")){
                    continue;
                }
                System.out.println("    " + entry.getKey() + ": " + entry.getValue());
            }
        }
    }


}
