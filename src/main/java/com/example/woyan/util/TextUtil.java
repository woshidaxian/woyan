package com.example.woyan.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TextUtil {
    private static long FIVE_SECOND = 5000;
    private static long ONE_MINITE = 60000;
    private static long ONE_HOUR = 3600000;
    private static long ONE_DAY = 86400000;
    private static long ONE_MONTH = Long.valueOf("2592000000");

    public String DeleteHTML(String text) {
        if (text == null) return null;
        text = text.replaceAll("<", "&lt")
            .replaceAll(">", "&gt")
            .replaceAll("&", "&amp")
            .replaceAll("\"", "&quot")
            .replaceAll("'", "&apos");
        return text;
    }

    public String formatTime(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        return format.format(date);
    }

    public String changeTime(Date time){
        Date date = new Date();
        long r = date.getTime() - time.getTime();
        if (r < FIVE_SECOND){
            return "刚刚";
        }
        if (r < ONE_MINITE){
            return r/1000 + "秒前";
        }
        if (r < ONE_HOUR){
            return r/1000/60 + "分钟前";
        }
        if (r < ONE_DAY){
            return r/1000/60/60 + "小时前";
        }
        if (r < ONE_MONTH){
            return r/1000/60/60/24 + "天前";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(time);
    }

    public String turnNumber(long num){
        if (num > 100000000){ // 如果大于1亿
            return String.valueOf(String.format("%.1f",(double)num/100000000) + "亿");
        }
        if (num > 10000) { //如果大于1万
            return String.valueOf(String.format("%.1f",(double)num/10000) + "万");
        }
        return String.valueOf(num);
    }
}
