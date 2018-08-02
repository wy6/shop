package com.zcib.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by WY on 2018/1/10.
 */

public class Util {
    public static Date checkOption(String option, Date _date) {
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cl = Calendar.getInstance();
        Date date = null;

        date = _date;
        cl.setTime(date);
        if ("pre".equals(option)) {
            // 时间减一天
            cl.add(Calendar.DAY_OF_MONTH, -1);

        } else if ("next".equals(option)) {
            // 时间加一天
            cl.add(Calendar.DAY_OF_YEAR, 1);
        } else {
            // do nothing
        }
        date = cl.getTime();
        return date;
    }
}
