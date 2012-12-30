package com.github.yftx.wzd.engine;

import com.github.yftx.wzd.domain.Bid;
import com.github.yftx.wzd.utils.StringUtils;
import com.google.common.base.CharMatcher;

import java.util.Comparator;

/**
 * User: yftx
 * Mail: yftx.net@gmail.com
 * Date: 12-9-2
 */
public class Sort {
    public static class ComparatorByApr implements Comparator<Bid> {
        @Override
        public int compare(Bid lhs, Bid rhs) {
            float result = Float.parseFloat(rhs.getApr()) - Float.parseFloat(lhs.getApr());
            if (result == 0L)
                return 0;
            return result > 0L ? 1 : -1;
        }
    }

    public static class ComparatorByAmount implements Comparator<Bid> {
        private final String sep = ",";

        @Override
        public int compare(Bid lhs, Bid rhs) {
            return StringUtils.splitStrToInt(rhs.getAccount_format(), sep) - StringUtils.splitStrToInt(lhs.getAccount_format(), sep);
        }
    }

    public static class ComparatorByTime implements Comparator<Bid> {
        @Override
        public int compare(Bid lhs, Bid rhs) {
            Long result = readStrToLong(rhs.getAddtime()) - readStrToLong(lhs.getAddtime());
            return result == 0L ? 0 : result > 0L ? 1 : -1;
        }

        long readStrToLong(String str) {
            return Long.parseLong(CharMatcher.DIGIT.retainFrom(str));
        }
    }
}
