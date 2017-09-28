package cn.nsyr.hello.thread;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author ZhouSs
 * @Mail: zhoushengshuai@ufenqi.com
 * @date:2017/9/28 上午9:50
 * @version: 1.0
 **/
public class BankWaterService implements Runnable {

    private CyclicBarrier c = new CyclicBarrier(4,this);
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    public void run() {

        System.out.println();
        int result = 0;
        // 汇总计算
        for (Map.Entry<String, Integer> sheet : sheetBankWaterCount.entrySet()) {
            System.out.println(sheet.getKey() +" :"+sheet.getValue());
            result += sheet.getValue();
        }
        sheetBankWaterCount.put("result",result);

        System.out.println("计算结果:"+result);

        System.out.println(c.getNumberWaiting());

    }


   private Executor executor = Executors.newFixedThreadPool(4);

    private ConcurrentHashMap<String,Integer> sheetBankWaterCount = new ConcurrentHashMap<String, Integer>();

    private void count() {
        for (int i = 0; i < 4; i++) {
            executor.execute(new Runnable() {
                public void run() {
                    // 计算
                    sheetBankWaterCount.put(Thread.currentThread().getName(),1);
                    try {
                        c.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void main(String[] args) {
        BankWaterService bankWaterService = new BankWaterService();
        bankWaterService.count();
    }
}
