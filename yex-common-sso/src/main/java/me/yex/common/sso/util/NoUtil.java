package me.yex.common.sso.util;


import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yex
 * @description cn.zhenhealth.health.user.util
 */
public class NoUtil {

    private static final AtomicInteger cycleCode4 = new AtomicInteger(1);

    private static final long codeMask = 1;

    private static final long dateMask = codeMask * 1000000;

    public static synchronized long generateNo(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        long month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        long nowL = year % 100 * 10000 + month * 100 + day;

        int cycleCode = getCycleCode4();
        return nowL * dateMask + cycleCode;
    }


    private static int getCycleCode4(){
        int i = cycleCode4.incrementAndGet();
        if (i > 999999){
            cycleCode4.compareAndSet(i,i - 1000000);
            return i - 1000000;
        }
        return i;
    }
}
