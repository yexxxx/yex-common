package me.yex.common.core.util;


import cn.hutool.core.util.RandomUtil;
import me.yex.common.core.constant.BizConstant;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yex
 * @description cn.zhenhealth.health.user.util
 */
public class NoUtil {

    private static AtomicInteger cycleCode4 = new AtomicInteger(100000);

    private static final long codeMask = 1;

    private static final long bizIdMask = codeMask * 10000 * 100;

    private static final long dateMask = bizIdMask * 10000;

    private static final long bizMask = dateMask * 100 * 10000;


    /**
     * description: generateOrderNo
     *  生成 18 位 no, 编号规则如下：
     *  2位businessType   yymmdd  [businessId]后四位  6位随机码
     * @param businessType 业务参数 2位
     * @param businessId  业务id 非负
     * @return {@link long}
     */
    public static synchronized long generateNo(int businessType, int businessId) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        long month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        long nowL = year % 100 * 10000 + month * 100 + day;

        int cycleCode = getCycleCode4();
        return businessType * bizMask + nowL * dateMask + (businessId - businessId / 10000 * 10000) * bizIdMask + cycleCode;
    }

    public static synchronized long generateNo(int businessType) {
        return generateNo(businessType,RandomUtil.randomInt(10000));
    }


    private static int getCycleCode4() {
        int i = cycleCode4.incrementAndGet();
        if (i > 999999) {
            cycleCode4.compareAndSet(i, i - 1000000);
            return i - 1000000;
        }
        return i;
    }

    private static String getUserId(int userId) {
        String i = String.valueOf(userId + 10000);
        return i.substring(i.length() - 4);
    }

    public static void main(String[] args) {
//        String yyMMdd = DateUtil.format(new Date(), "yyMMdd");
//        String userId = getUserId(99993);
//        String orderNo = generateOrderNo(TransactionConstant.BusinessType.MemberCard, 345439999);
//        System.out.println(yyMMdd);
        asyncGenerate();
    }

    public static void asyncGenerate() {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    long orderNo = generateNo(BizConstant.BizNo.ORDER_NO, RandomUtil.randomInt(100000));
                    System.out.println(Thread.currentThread() + ":" + orderNo);
                }
            }).start();
        }
    }
}
