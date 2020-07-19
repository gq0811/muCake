package com.cainiao.arrow.arrowservice.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TestCallable implements Callable<Integer>{
    //实现Callable并重写call方法作为线程执行体, 并设置返回值1
    @Override
    public Integer call() throws Exception {
        System.out.println("Thread is running..."+Thread.currentThread().getName());
        Thread.sleep(1000);
        return 1;
    }

    /**
     * 线程池提交多个callable任务
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService pool = new ThreadPoolExecutor(1, 3, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
        List<Future> futureList = new ArrayList<>();
        for(int i=0;i<10;i++){
            //创建Callable实现类的对象
            TestCallable tc = new TestCallable();
            //创建FutureTask类的对象
            FutureTask<Integer> task = new FutureTask<Integer>(() -> {
                Thread.sleep(1000);
                return 1;
            });
            pool.submit(task);
            futureList.add(task);
        }
        int sum = 0;
        for(int i=0;i<10;i++){
            Integer count = (Integer) futureList.get(i).get();
            sum+=count;
        }
        System.out.println("The thread running result is :" + sum);
    }
}