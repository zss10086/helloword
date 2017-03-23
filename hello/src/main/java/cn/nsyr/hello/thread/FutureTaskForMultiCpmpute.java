package cn.nsyr.hello.thread;

import com.sun.scenario.animation.shared.PulseReceiver;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 *
 * FutureTask可用于异步获取执行结果或取消执行任务的场景。通过传入Runnable或者Callable的任务给FutureTask，直接调用其run方法或者放入线程池执行，
 * 之后可以在外部通过FutureTask的get方法异步获取执行结果，因此，FutureTask非常适合用于耗时的计算，主线程可以在完成自己的任务后，
 * 再去获取结果。另外，FutureTask还可以确保即使调用了多次run方法，它都只会执行一次Runnable或者Callable任务，或者通过cancel取消FutureTask的执行等。
 *  FutureTask执行多任务计算的使用场景
 *  利用FutureTask和ExecutorService，可以用多线程的方式提交计算任务，主线程继续执行其他任务，当主线程需要子线程的计算结果时，在异步获取子线程的执行结果
 * @author ZhouSs
 * @Mail: zhoushengshuai@ufenqi.com
 * @date:2017/3/13 下午1:52
 * @version: 1.0
 **/
public class FutureTaskForMultiCpmpute {


    public static void main(String[] args) {
        FutureTaskForMultiCpmpute inst = new FutureTaskForMultiCpmpute();
        List<Future<Integer>> taskList = new ArrayList<Future<Integer>>();

        ExecutorService exec = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            FutureTask<Integer> ft = new FutureTask<Integer>(inst.new ComputeTask(i,""+i));
            taskList.add(ft);

            exec.submit(ft);
        }

        System.out.println(" 所有计算任务提交完毕, 主线程接着干其他事情");

        Integer totalResult = 0;

        for (Future<Integer> integerFuture : taskList) {
            try {
                totalResult = totalResult + integerFuture.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        exec.shutdown();
        System.out.println(" 多任务计算后的总结果是: " + totalResult);
    }


    private class ComputeTask implements Callable<Integer> {
        private Integer result = 0;
        private String taskName = "";

        public ComputeTask(Integer initResult,String taskName) {
            result = initResult;
            this.taskName = taskName;
            System.out.println(" 生成子线程计算任务 : " + taskName);
        }

        public String getTaskName () {
            return this.taskName;
        }

        public Integer call() throws Exception {

            for (int i = 0; i < 100; i++) {
                result +=+ 1;
            }
            Thread.sleep(5000);
            System.out.println("子线程计算任务 :" + taskName + "执行完成");
            return result;
        }


    }

    /**
     *

    FutureTask在高并发环境下确保任务只执行一次

    在很多高并发的环境下，往往我们只需要某些任务只执行一次。这种使用情景FutureTask的特性恰能胜任。
    举一个例子，假设有一个带key的连接池，当key存在时，即直接返回key对应的对象；当key不存在时，则创建连接。对于这样的应用场景，
    通常采用的方法为使用一个Map对象来存储key和连接池对应的对应关系，典型的代码如下面所示

     */


    private Map<String,Connection> connectionPool = new HashMap<String, Connection>();
    private ReentrantLock lock = new ReentrantLock();

    public Connection getConnection(String key) {

        lock.lock();
        if (connectionPool.containsKey(key)) {
            return connectionPool.get(key);
        } else {
            Connection connection = null;
            return connection;
        }

    }

    /*在上面的例子中，我们通过加锁确保高并发环境下的线程安全，也确保了connection只创建一次，然而确牺牲了性能。改用ConcurrentHash的情况下，
    几乎可以避免加锁的操作，性能大大提高，但是在高并发的情况下有可能出现Connection被创建多次的现象。这时最需要解决的问题就是当key不存在时，
    创建Connection的动作能放在connectionPool之后执行，这正是FutureTask发挥作用的时机，基于ConcurrentHashMap和FutureTask的改造代码如下*/

    private ConcurrentHashMap<String,FutureTask<Connection>> concurrentPool = new ConcurrentHashMap<String, FutureTask<Connection>>();

    public Connection getConnect(String key) throws Exception {
        FutureTask<Connection> connectionFutureTask = concurrentPool.get(key);

        if (connectionFutureTask != null) {
            return connectionFutureTask.get();
        } else {
            Callable<Connection> callable = new Callable<Connection>() {
                public Connection call() throws Exception {
                    return createConnection();
                }
            };

            FutureTask<Connection> newTask = new FutureTask<Connection>(callable);
            connectionFutureTask = concurrentPool.putIfAbsent(key,newTask);
            if (connectionFutureTask == null) {
                connectionFutureTask = newTask;
                connectionFutureTask.run();
            }
            return connectionFutureTask.get();
        }

    }

    private Connection createConnection() {
        return null;
    }
}

