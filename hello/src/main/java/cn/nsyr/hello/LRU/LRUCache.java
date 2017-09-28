package cn.nsyr.hello.LRU;

import java.util.*;

/**
 * @author javarice
 * @Mail: zss10086@126.com
 * @date:2017/3/16 下午2:54
 * @version: 1.0
 **/
public class LRUCache<K,V>  extends LinkedHashMap<K,V>{

    private int cacheSize;

    public LRUCache(int cacheSize) {
        super(16,0.75F,true);
        this.cacheSize = cacheSize;
    }

    protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
        return size() >= cacheSize;
    }

    public static void main(String[] args) {
        Random random = new Random();
        Map<String ,Integer> amount = new TreeMap<String, Integer>();
        LRUCache<String,Integer> cache = new LRUCache<String, Integer>(9);
        for (int i = 0; i < 100; i++) {
            int next = random.nextInt(10);
            if (amount.containsKey(String.valueOf(next))) {
                amount.put(String.valueOf(next),amount.get(String.valueOf(next)) +1);
            } else {
                amount.put(String.valueOf(next),1);
            }
            cache.put(String.valueOf(next),1);
        }
        System.out.println("++++++++++++++++++++++++++++++amount.entrySet()=+"+amount.entrySet().size());
        for (Map.Entry<String, Integer> stringIntegerEntry : amount.entrySet()) {
            System.out.println(stringIntegerEntry.getKey() +"-----"+stringIntegerEntry.getValue());
        }
        System.out.println("+++++++++++++++++++++++++++++++cache.entrySet().size()="+ cache.entrySet().size());
        for (Map.Entry<String, Integer> stringIntegerEntry : cache.entrySet()) {
            System.out.println(stringIntegerEntry.getKey());
        }


    }
}
