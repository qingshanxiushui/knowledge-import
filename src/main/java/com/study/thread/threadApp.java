package com.study.thread;

import java.util.Random;
import java.util.concurrent.*;

public class threadApp {


    /**
     * 买房摇号
     */
    public static class Yaohao implements Callable<Integer> {
        /**
         * 返回摇号结果
         * @return 0：中签   1：没中
         * @throws Exception
         */
        @Override
        public Integer call() throws Exception {
            Random random = new Random();
            //模拟摇号，10天内出结果
            TimeUnit.SECONDS.sleep(random.nextInt(10));
            int result = random.nextInt(2);
            System.out.println("     "+Thread.currentThread().getName()+" is done!");
            return result;
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        threadPoolSubmitFuture();
    }

    private static void threadPoolSubmitFuture() throws InterruptedException, ExecutionException {
        Yaohao gangxu1 = new Yaohao();
        Yaohao gangxu2 = new Yaohao();
        Yaohao gangxu3 = new Yaohao();
        Yaohao gangxu4 = new Yaohao();
        ExecutorService es = Executors.newCachedThreadPool();
        Future<Integer> result1 = es.submit(gangxu1);
        Future<Integer> result2 = es.submit(gangxu2);
        Future<Integer> result3 = es.submit(gangxu3);
        Future<Integer> result4 = es.submit(gangxu4);
        es.shutdown();
        System.out.println("刚需1，摇号结果："+(result1.get()==1?"中签":"没中"));
        System.out.println("刚需2，摇号结果："+(result2.get()==1?"中签":"没中"));
        System.out.println("刚需3，摇号结果："+(result3.get()==1?"中签":"没中"));
        System.out.println("刚需4，摇号结果："+(result4.get()==1?"中签":"没中"));
    }
}
