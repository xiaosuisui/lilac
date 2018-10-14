package io.github.isliqian.utils;

import com.google.common.collect.Lists;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringEscapeUtils;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
    private static final char SEPARATOR = '_';
    private static final String CHARSET_NAME = "UTF-8";

    public StringUtils() {
    }

    public static byte[] getBytes(String str) {
        if (str != null) {
            try {
                return str.getBytes("UTF-8");
            } catch (UnsupportedEncodingException var2) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static String toString(byte[] bytes) {
        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException var2) {
            return "";
        }
    }

    public static boolean inString(String str, String... strs) {
        if (str != null) {
            String[] arr$ = strs;
            int len$ = strs.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String s = arr$[i$];
                if (str.equals(trim(s))) {
                    return true;
                }
            }
        }

        return false;
    }

    public static String replaceHtml(String html) {
        if (isBlank(html)) {
            return "";
        } else {
            String regEx = "<.+?>";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(html);
            String s = m.replaceAll("");
            return s;
        }
    }

    public static String replaceMobileHtml(String html) {
        return html == null ? "" : html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
    }

    public static String toHtml(String txt) {
        return txt == null ? "" : replace(replace(Encodes.escapeHtml(txt), "\n", "<br/>"), "\t", "&nbsp; &nbsp; ");
    }

    public static String abbr(String str, int length) {
        if (str == null) {
            return "";
        } else {
            try {
                StringBuilder sb = new StringBuilder();
                int currentLength = 0;
                char[] arr$ = replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray();
                int len$ = arr$.length;

                for(int i$ = 0; i$ < len$; ++i$) {
                    char c = arr$[i$];
                    currentLength += String.valueOf(c).getBytes("GBK").length;
                    if (currentLength > length - 3) {
                        sb.append("...");
                        break;
                    }

                    sb.append(c);
                }

                return sb.toString();
            } catch (UnsupportedEncodingException var8) {
                var8.printStackTrace();
                return "";
            }
        }
    }

    public static String abbr2(String param, int length) {
        if (param == null) {
            return "";
        } else {
            StringBuffer result = new StringBuffer();
            int n = 0;
            boolean isCode = false;
            boolean isHTML = false;

            for(int i = 0; i < param.length(); ++i) {
                char temp = param.charAt(i);
                if (temp == '<') {
                    isCode = true;
                } else if (temp == '&') {
                    isHTML = true;
                } else if (temp == '>' && isCode) {
                    --n;
                    isCode = false;
                } else if (temp == ';' && isHTML) {
                    isHTML = false;
                }

                try {
                    if (!isCode && !isHTML) {
                        n += String.valueOf(temp).getBytes("GBK").length;
                    }
                } catch (UnsupportedEncodingException var12) {
                    var12.printStackTrace();
                }

                if (n > length - 3) {
                    result.append("...");
                    break;
                }

                result.append(temp);
            }

            String temp_result = result.toString().replaceAll("(>)[^<>]*(<?)", "$1$2");
            temp_result = temp_result.replaceAll("</?(AREA|BASE|BASEFONT|BODY|BR|COL|COLGROUP|DD|DT|FRAME|HEAD|HR|HTML|IMG|INPUT|ISINDEX|LI|LINK|META|OPTION|P|PARAM|TBODY|TD|TFOOT|TH|THEAD|TR|area|base|basefont|body|br|col|colgroup|dd|dt|frame|head|hr|html|img|input|isindex|li|link|meta|option|p|param|tbody|td|tfoot|th|thead|tr)[^<>]*/?>", "");
            temp_result = temp_result.replaceAll("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>", "$2");
            Pattern p = Pattern.compile("<([a-zA-Z]+)[^<>]*>");
            Matcher m = p.matcher(temp_result);
            ArrayList endHTML = Lists.newArrayList();

            while(m.find()) {
                endHTML.add(m.group(1));
            }

            for(int i = endHTML.size() - 1; i >= 0; --i) {
                result.append("</");
                result.append((String)endHTML.get(i));
                result.append(">");
            }

            return result.toString();
        }
    }

    public static Double toDouble(Object val) {
        if (val == null) {
            return 0.0D;
        } else {
            try {
                return Double.valueOf(trim(val.toString()));
            } catch (Exception var2) {
                return 0.0D;
            }
        }
    }

    public static Float toFloat(Object val) {
        return toDouble(val).floatValue();
    }

    public static Long toLong(Object val) {
        return toDouble(val).longValue();
    }

    public static Integer toInteger(Object val) {
        return toLong(val).intValue();
    }

    public static String getRemoteAddr(HttpServletRequest request) {
        String remoteAddr = request.getHeader("X-Real-IP");
        if (isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("X-Forwarded-For");
        } else if (isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("Proxy-Client-IP");
        } else if (isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }

        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
    }

    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        } else {
            s = s.toLowerCase();
            StringBuilder sb = new StringBuilder(s.length());
            boolean upperCase = false;

            for(int i = 0; i < s.length(); ++i) {
                char c = s.charAt(i);
                if (c == '_') {
                    upperCase = true;
                } else if (upperCase) {
                    sb.append(Character.toUpperCase(c));
                    upperCase = false;
                } else {
                    sb.append(c);
                }
            }

            return sb.toString();
        }
    }

    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        } else {
            s = toCamelCase(s);
            return s.substring(0, 1).toUpperCase() + s.substring(1);
        }
    }

    public static String toUnderScoreCase(String s) {
        if (s == null) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            boolean upperCase = false;

            for(int i = 0; i < s.length(); ++i) {
                char c = s.charAt(i);
                boolean nextUpperCase = true;
                if (i < s.length() - 1) {
                    nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
                }

                if (i > 0 && Character.isUpperCase(c)) {
                    if (!upperCase || !nextUpperCase) {
                        sb.append('_');
                    }

                    upperCase = true;
                } else {
                    upperCase = false;
                }

                sb.append(Character.toLowerCase(c));
            }

            return sb.toString();
        }
    }

    public static void setValueIfNotBlank(String target, String source) {
        if (isNotBlank(source)) {
            ;
        }

    }

    public static String jsGetVal(String objectString) {
        StringBuilder result = new StringBuilder();
        StringBuilder val = new StringBuilder();
        String[] vals = split(objectString, ".");

        for(int i = 0; i < vals.length; ++i) {
            val.append("." + vals[i]);
            result.append("!" + val.substring(1) + "?'':");
        }

        result.append(val.substring(1));
        return result.toString();
    }
}

