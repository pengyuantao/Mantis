package com.peng.library.mantis.kit;

import android.util.Log;

/**
 * Created by pyt on 2017/6/29.
 */

public class MLog {

    /**
     * isDebug判断是否开启
     */
    private static boolean isDebug = true;

    public static String customTagPrefix = "";

    private MLog() {

    }


    public static boolean allowD = true;
    public static boolean allowE = true;
    public static boolean allowI = true;
    public static boolean allowV = true;
    public static boolean allowW = true;
    public static boolean allowWtf = true;

    private static String generateTag(StackTraceElement caller) {
        if (caller == null) {
            return MLog.class.getSimpleName();
        }
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, new Object[]{callerClazzName, caller.getMethodName(), Integer.valueOf(caller.getLineNumber())});
        tag = customTagPrefix + ":" + tag;
        return tag;
    }

    public static CustomLogger customLogger;

    public static void d(String content) {
        if (isDebug) {
            if (!allowD) return;
            StackTraceElement caller = OtherUtils.getCallerStackTraceElement();
            String tag = generateTag(caller);

            if (customLogger != null) {
                customLogger.d(tag, content);
            } else {
                Log.d(tag, content);
            }
        }
    }

    public static void d(String content, Throwable tr) {
        if (isDebug) {
            if (!allowD) return;
            StackTraceElement caller = OtherUtils.getCallerStackTraceElement();
            String tag = generateTag(caller);

            if (customLogger != null) {
                customLogger.d(tag, content, tr);
            } else {
                Log.d(tag, content, tr);
            }
        }
    }

    public static void e(String content) {
        if (isDebug) {
            if (!allowE) return;
            StackTraceElement caller = OtherUtils.getCallerStackTraceElement();
            String tag = generateTag(caller);

            if (customLogger != null) {
                customLogger.e(tag, content);
            } else {
                Log.e(tag, content);
            }
        }
    }

    public static void e(String content, Throwable tr) {
        if (isDebug) {
            if (!allowE) return;
            StackTraceElement caller = OtherUtils.getCallerStackTraceElement();
            String tag = generateTag(caller);

            if (customLogger != null) {
                customLogger.e(tag, content, tr);
            } else {
                Log.e(tag, content, tr);
            }
        }
    }

    public static void i(String content) {
        if (isDebug) {
            if (!allowI) return;
            StackTraceElement caller = OtherUtils.getCallerStackTraceElement();
            String tag = generateTag(caller);

            if (customLogger != null) {
                customLogger.i(tag, content);
            } else {
                Log.i(tag, content);
            }
        }
    }

    public static void i(String content, Throwable tr) {
        if (isDebug) {
            if (!allowI) return;
            StackTraceElement caller = OtherUtils.getCallerStackTraceElement();
            String tag = generateTag(caller);

            if (customLogger != null) {
                customLogger.i(tag, content, tr);
            } else {
                Log.i(tag, content, tr);
            }
        }
    }

    public static void v(String content) {
        if (isDebug) {
            if (!allowV) return;
            StackTraceElement caller = OtherUtils.getCallerStackTraceElement();
            String tag = generateTag(caller);

            if (customLogger != null) {
                customLogger.v(tag, content);
            } else {
                Log.v(tag, content);
            }
        }
    }

    public static void v(String content, Throwable tr) {
        if (isDebug) {
            if (!allowV) return;
            StackTraceElement caller = OtherUtils.getCallerStackTraceElement();
            String tag = generateTag(caller);

            if (customLogger != null) {
                customLogger.v(tag, content, tr);
            } else {
                Log.v(tag, content, tr);
            }
        }
    }

    public static void w(String content) {
        if (isDebug) {
            if (!allowW) return;
            StackTraceElement caller = OtherUtils.getCallerStackTraceElement();
            String tag = generateTag(caller);

            if (customLogger != null) {
                customLogger.w(tag, content);
            } else {
                Log.w(tag, content);
            }
        }
    }

    public static void w(String content, Throwable tr) {
        if (isDebug) {
            if (!allowW) return;
            StackTraceElement caller = OtherUtils.getCallerStackTraceElement();
            String tag = generateTag(caller);

            if (customLogger != null) {
                customLogger.w(tag, content, tr);
            } else {
                Log.w(tag, content, tr);
            }
        }
    }

    public static void w(Throwable tr) {
        if (isDebug) {
            if (!allowW) return;
            StackTraceElement caller = OtherUtils.getCallerStackTraceElement();
            String tag = generateTag(caller);

            if (customLogger != null) {
                customLogger.w(tag, tr);
            } else {
                Log.w(tag, tr);
            }
        }
    }

    public static void wtf(String content) {
        if (isDebug) {
            if (!allowWtf) return;
            StackTraceElement caller = OtherUtils.getCallerStackTraceElement();
            String tag = generateTag(caller);

            if (customLogger != null) {
                customLogger.wtf(tag, content);
            } else {
                Log.wtf(tag, content);
            }
        }
    }

    public static void wtf(String content, Throwable tr) {
        if (isDebug) {
            if (!allowWtf) return;
            StackTraceElement caller = OtherUtils.getCallerStackTraceElement();
            String tag = generateTag(caller);

            if (customLogger != null) {
                customLogger.wtf(tag, content, tr);
            } else {
                Log.wtf(tag, content, tr);
            }
        }
    }

    public static void wtf(Throwable tr) {
        if (isDebug) {
            if (!allowWtf) return;
            StackTraceElement caller = OtherUtils.getCallerStackTraceElement();
            String tag = generateTag(caller);

            if (customLogger != null) {
                customLogger.wtf(tag, tr);
            } else {
                Log.wtf(tag, tr);
            }
        }
    }

    public static abstract interface CustomLogger {
        public abstract void d (String paramString1, String paramString2);

        public abstract void d (String paramString1, String paramString2,
								Throwable paramThrowable);

        public abstract void e (String paramString1, String paramString2);

        public abstract void e (String paramString1, String paramString2,
								Throwable paramThrowable);

        public abstract void i (String paramString1, String paramString2);

        public abstract void i (String paramString1, String paramString2,
								Throwable paramThrowable);

        public abstract void v (String paramString1, String paramString2);

        public abstract void v (String paramString1, String paramString2,
								Throwable paramThrowable);

        public abstract void w (String paramString1, String paramString2);

        public abstract void w (String paramString1, String paramString2,
								Throwable paramThrowable);

        public abstract void w (String paramString, Throwable paramThrowable);

        public abstract void wtf (String paramString1, String paramString2);

        public abstract void wtf (String paramString1, String paramString2,
								  Throwable paramThrowable);

        public abstract void wtf (String paramString, Throwable paramThrowable);

    }

    public static class OtherUtils {

        public static StackTraceElement getCallerStackTraceElement() {
            StackTraceElement[] stackTraceArray = Thread.currentThread().getStackTrace();
            for (int i = 0; i < stackTraceArray.length; i++) {
                StackTraceElement stackTraceElement = stackTraceArray[i];
                if (OtherUtils.class.getName().equals(stackTraceElement.getClassName())) {
                    if (i + 2 <= stackTraceArray.length) {
                        return stackTraceArray[i + 2];
                    }
                }
            }
            return null;
        }
    }

}
