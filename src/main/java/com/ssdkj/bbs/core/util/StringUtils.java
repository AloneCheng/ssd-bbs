package com.ssdkj.bbs.core.util;

import java.math.BigDecimal;

public class StringUtils {

    /**
     * 将int数值类型转换为前面补0，共digit位的字符
     * @param c sku代码
     * @return 商品内码
     */
    public static String fillZero(int c, int digit) {
        return String.format("%0" + digit + "d", c);
    }

    public static String toSku(int c) {
        return fillZero(c, 18);
    }
    
    public static String toSku(String c) {
        return String.format("%0" + 18 + "s", c);
    }
    public static String removeZeroSku(String c) {
    	return c.replaceAll("^(0+)", "");
    }

    /**
     * 将*100金额转换为正常金额
     */
    public static String divideAmountString(Integer amount) {
        Float a = Float.valueOf(amount) / 100;
        BigDecimal b = new BigDecimal(a.toString());
        //保留4位小数
        return b.setScale(4, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 将*100金额转换为正常金额
     */
    public static BigDecimal divideAmountDecimal(Integer amount) {
        Float a = Float.valueOf(amount) / 100;
        BigDecimal b = new BigDecimal(a.toString());
        //保留4位小数
        return b.setScale(4, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 将*10000积分转换为正常金额
     */
    public static BigDecimal dividePointDecimal(Long amount) {
        Double a = Double.valueOf(amount) / 10000;
        BigDecimal b = new BigDecimal(a.toString());
        //保留24位小数
        return b.setScale(4, BigDecimal.ROUND_HALF_UP);
    }

    public static String ifNullString(String s) {
        if (s == null) {
            return "";
        } else {
            return s;
        }
    }

    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static void main(String[] args) {
        System.out.println(removeZeroSku("0000000100189698"));
//        System.out.println(divideAmountString(234));
//        System.out.println(divideAmountDecimal(1000));
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    public static boolean containsIgnoreCase(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        int len = searchStr.length();
        int max = str.length() - len;
        for (int i = 0; i <= max; i++) {
            if (str.regionMatches(true, i, searchStr, 0, len)) {
                return true;
            }
        }
        return false;
    }

    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
    }

    public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static String uncapitalize(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        return new StringBuilder(strLen).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString();
    }

    public static String join(final Object[] array, final String separator) {
        if (array == null) {
            return null;
        }
        return join(array, separator, 0, array.length);
    }

    public static String join(final Object[] array, String separator, final int startIndex, final int endIndex) {
        if (array == null) {
            return null;
        }
        if (separator == null) {
            separator = EMPTY;
        }

        // endIndex - startIndex > 0:   Len = NofStrings *(len(firstString) + len(separator))
        //           (Assuming that all Strings are roughly equally long)
        final int noOfItems = endIndex - startIndex;
        if (noOfItems <= 0) {
            return EMPTY;
        }

        final StringBuilder buf = new StringBuilder(noOfItems * 16);

        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    public static final String EMPTY = "";
    
    public static String subString(String str, int length) {
    	if(isBlank(str)){
    	    return null;
    	}
    	if(str.length()>length){
    	    return str.substring(0, length);    	    
    	}
    	return str;
    }

    /**
     * Test if the given String starts with the specified prefix,
     * ignoring upper/lower case.
     * @param str the String to check
     * @param prefix the prefix to look for
     * @see String#startsWith
     */
    public static boolean startsWithIgnoreCase(String str, String prefix) {
        if (str == null || prefix == null) {
            return false;
        }
        if (str.startsWith(prefix)) {
            return true;
        }
        if (str.length() < prefix.length()) {
            return false;
        }
        String lcStr = str.substring(0, prefix.length()).toLowerCase();
        String lcPrefix = prefix.toLowerCase();
        return lcStr.equals(lcPrefix);
    }
    
    
    public static boolean endWithIgnoreCase(String str, String postFix) {
        if (str == null || postFix == null) {
            return false;
        }
        if (str.endsWith(postFix)) {
            return true;
        }
        if (str.length() < postFix.length()) {
            return false;
        }
        String lcStr = str.substring(0, postFix.length()).toLowerCase();
        String lcPrefix = postFix.toLowerCase();
        return lcStr.equals(lcPrefix);
    }
    
    public static boolean isChar(char	c){   
        //char   c   =   fstrData.charAt(0);   
        if(((c>='a'&&c<='z')   ||   (c>='A'&&c<='Z'))) {   
              return   true;   
        }else{   
              return   false;   
        }   
    }
    
    public static String getFailMsg(String faliMsg){
		return faliMsg.length() > 250 ? faliMsg.substring(0, 250) : faliMsg;
    }
    
    public static boolean exist(int src, Integer ... target){
    	if(target == null) return false;
    	for(Integer a: target){
    		if(a==src)	return true;
    	}
    	return false;
    }

}
