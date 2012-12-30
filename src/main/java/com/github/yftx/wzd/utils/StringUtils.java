package com.github.yftx.wzd.utils;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

/**
 * User: yftx
 * Mail: yftx.net@gmail.com
 * Date: 12-9-2
 */
public class StringUtils {
    /**
     * 将指定文本转换为int数字
     * @param str
     * @param sep
     * @return
     */
    public static Integer splitStrToInt(String str,String sep){
        Iterable<String> parts = Splitter.on(sep).split(str);
        Joiner joiner = Joiner.on("");
        return Integer.parseInt(joiner.join(parts));
    }


}
