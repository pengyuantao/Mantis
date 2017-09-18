package com.peng.library.mantis.kit;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * @author: MartinPeng
 * @time created 2017/2/23 15:35
 */
public class Kits {

    public static class Package {

        /**
         * get app's signature
         *
         * @param context
         * @return
         */
        public static String getSignature(Context context) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
                Signature[] signatures = packageInfo.signatures;
                if (signatures == null) {
                    return "";
                }
                StringBuilder builder = new StringBuilder();
                for (Signature signature : signatures) {
                    builder.append(signature.toCharsString());
                }
                return builder.toString();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            return "";
        }

        /**
         * @param context
         * @return app's version code
         */
        public static int getVersionCode(Context context) {
            PackageManager pManager = context.getPackageManager();
            PackageInfo packageInfo = null;
            try {
                packageInfo = pManager.getPackageInfo(context.getPackageName(), 0);

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return packageInfo.versionCode;
        }

        /**
         * @param context
         * @return app's version name
         */
        public static String getVersionName(Context context) {
            PackageManager pManager = context.getPackageManager();
            PackageInfo packageInfo = null;
            try {
                packageInfo = pManager.getPackageInfo(context.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return packageInfo.versionName;
        }

        /**
         * install app
         *
         * @param context
         * @param filePath
         * @return isSuccess
         */
        public static boolean installNormal(Context context, String filePath) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            java.io.File file = new java.io.File(filePath);
            if (file == null || !file.exists() || !file.isFile() || file.length() <= 0) {
                return false;
            }

            i.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            return true;
        }

        /**
         * uninstall app
         *
         * @param context
         * @param packageName
         * @return
         */
        public static boolean uninstallNormal(Context context, String packageName) {
            if (packageName == null || packageName.length() == 0) {
                return false;
            }

            Intent i = new Intent(Intent.ACTION_DELETE, Uri.parse(new StringBuilder().append("package:")
                    .append(packageName).toString()));
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            return true;
        }

        /**
         * check is system app
         *
         * @param context
         * @param packageName 包名
         * @return
         */
        public static boolean isSystemApplication(Context context, String packageName) {
            if (context == null) {
                return false;
            }
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null || packageName == null || packageName.length() == 0) {
                return false;
            }

            try {
                ApplicationInfo app = packageManager.getApplicationInfo(packageName, 0);
                return (app != null && (app.flags & ApplicationInfo.FLAG_SYSTEM) > 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return false;
        }

        /**
         * check activity is on the top by packageName
         *
         * @param context
         * @param packageName
         * @return
         */
        public static Boolean isTopActivity(Context context, String packageName) {
            if (context == null || TextUtils.isEmpty(packageName)) {
                return null;
            }

            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
            if (tasksInfo == null || tasksInfo.isEmpty()) {
                return null;
            }
            try {
                return packageName.equals(tasksInfo.get(0).topActivity.getPackageName());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * get Meta-Data
         *
         * @param context
         * @param key
         * @return
         */
        public static String getAppMetaData(Context context, String key) {
            if (context == null || TextUtils.isEmpty(key)) {
                return null;
            }
            String resultData = null;
            try {
                PackageManager packageManager = context.getPackageManager();
                if (packageManager != null) {
                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                    if (applicationInfo != null) {
                        if (applicationInfo.metaData != null) {
                            resultData = applicationInfo.metaData.getString(key);
                        }
                    }

                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            return resultData;
        }

        /**
         * check current app is running background
         *
         * @param context
         * @return
         */
        public static boolean isApplicationInBackground(Context context) {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
            if (taskList != null && !taskList.isEmpty()) {
                ComponentName topActivity = taskList.get(0).topActivity;
                if (topActivity != null && !topActivity.getPackageName().equals(context.getPackageName())) {
                    return true;
                }
            }
            return false;
        }
    }


    public static class Dimens {
        public static float dpToPx(Context context, float dp) {
            return dp * context.getResources().getDisplayMetrics().density;
        }

        public static float pxToDp(Context context, float px) {
            return px / context.getResources().getDisplayMetrics().density;
        }

        public static int dpToPxInt(Context context, float dp) {
            return (int) (dpToPx(context, dp) + 0.5f);
        }

        public static int pxToDpCeilInt(Context context, float px) {
            return (int) (pxToDp(context, px) + 0.5f);
        }
    }


    public static class Random {
        public static final String NUMBERS_AND_LETTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        public static final String NUMBERS = "0123456789";
        public static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        public static final String CAPITAL_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        public static final String LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";

        public static String getRandomNumbersAndLetters(int length) {
            return getRandom(NUMBERS_AND_LETTERS, length);
        }

        public static String getRandomNumbers(int length) {
            return getRandom(NUMBERS, length);
        }

        public static String getRandomLetters(int length) {
            return getRandom(LETTERS, length);
        }

        public static String getRandomCapitalLetters(int length) {
            return getRandom(CAPITAL_LETTERS, length);
        }

        public static String getRandomLowerCaseLetters(int length) {
            return getRandom(LOWER_CASE_LETTERS, length);
        }

        public static String getRandom(String source, int length) {
            return TextUtils.isEmpty(source) ? null : getRandom(source.toCharArray(), length);
        }

        public static String getRandom(char[] sourceChar, int length) {
            if (sourceChar == null || sourceChar.length == 0 || length < 0) {
                return null;
            }

            StringBuilder str = new StringBuilder(length);
            java.util.Random random = new java.util.Random();
            for (int i = 0; i < length; i++) {
                str.append(sourceChar[random.nextInt(sourceChar.length)]);
            }
            return str.toString();
        }

        public static int getRandom(int max) {
            return getRandom(0, max);
        }

        public static int getRandom(int min, int max) {
            if (min > max) {
                return 0;
            }
            if (min == max) {
                return min;
            }
            return min + new java.util.Random().nextInt(max - min);
        }
    }

    public static class File {
        public final static String FILE_EXTENSION_SEPARATOR = ".";

        /**
         * read file
         *
         * @param filePath
         * @param charsetName The name of a supported {@link java.nio.charset.Charset </code>charset<code>}
         * @return if file not exist, return null, else return content of file
         * @throws RuntimeException if an error occurs while operator BufferedReader
         */
        public static StringBuilder readFile(String filePath, String charsetName) {
            java.io.File file = new java.io.File(filePath);
            StringBuilder fileContent = new StringBuilder("");
            if (file == null || !file.isFile()) {
                return null;
            }

            BufferedReader reader = null;
            try {
                InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
                reader = new BufferedReader(is);
                String line = null;
                while ((line = reader.readLine()) != null) {
                    if (!fileContent.toString().equals("")) {
                        fileContent.append("\r\n");
                    }
                    fileContent.append(line);
                }
                return fileContent;
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            } finally {
                IO.close(reader);
            }
        }

        /**
         * write file
         *
         * @param filePath
         * @param content
         * @param append   is append, if true, write to the end of file, else clear content of file and write into it
         * @return return false if content is empty, true otherwise
         * @throws RuntimeException if an error occurs while operator FileWriter
         */
        public static boolean writeFile(String filePath, String content, boolean append) {
            if (TextUtils.isEmpty(content)) {
                return false;
            }

            FileWriter fileWriter = null;
            try {
                makeDirs(filePath);
                fileWriter = new FileWriter(filePath, append);
                fileWriter.write(content);
                return true;
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            } finally {
                IO.close(fileWriter);
            }
        }

        /**
         * write file
         *
         * @param filePath
         * @param contentList
         * @param append      is append, if true, write to the end of file, else clear content of file and write into it
         * @return return false if contentList is empty, true otherwise
         * @throws RuntimeException if an error occurs while operator FileWriter
         */
        public static boolean writeFile(String filePath, List<String> contentList, boolean append) {
            if (contentList == null || contentList.isEmpty()) {
                return false;
            }

            FileWriter fileWriter = null;
            try {
                makeDirs(filePath);
                fileWriter = new FileWriter(filePath, append);
                int i = 0;
                for (String line : contentList) {
                    if (i++ > 0) {
                        fileWriter.write("\r\n");
                    }
                    fileWriter.write(line);
                }
                return true;
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            } finally {
                IO.close(fileWriter);
            }
        }

        /**
         * write file, the string will be written to the begin of the file
         *
         * @param filePath
         * @param content
         * @return
         */
        public static boolean writeFile(String filePath, String content) {
            return writeFile(filePath, content, false);
        }

        /**
         * write file, the string list will be written to the begin of the file
         *
         * @param filePath
         * @param contentList
         * @return
         */
        public static boolean writeFile(String filePath, List<String> contentList) {
            return writeFile(filePath, contentList, false);
        }

        /**
         * write file, the bytes will be written to the begin of the file
         *
         * @param filePath
         * @param stream
         * @return
         * @see {@link #writeFile(String, InputStream, boolean)}
         */
        public static boolean writeFile(String filePath, InputStream stream) {
            return writeFile(filePath, stream, false);
        }

        /**
         * write file
         *
         * @param stream the input stream
         * @param append if <code>true</code>, then bytes will be written to the end of the file rather than the beginning
         * @return return true
         * @throws RuntimeException if an error occurs while operator FileOutputStream
         */
        public static boolean writeFile(String filePath, InputStream stream, boolean append) {
            return writeFile(filePath != null ? new java.io.File(filePath) : null, stream, append);
        }

        /**
         * write file, the bytes will be written to the begin of the file
         *
         * @param file
         * @param stream
         * @return
         * @see {@link #writeFile(java.io.File, InputStream, boolean)}
         */
        public static boolean writeFile(java.io.File file, InputStream stream) {
            return writeFile(file, stream, false);
        }

        /**
         * write file
         *
         * @param file   the file to be opened for writing.
         * @param stream the input stream
         * @param append if <code>true</code>, then bytes will be written to the end of the file rather than the beginning
         * @return return true
         * @throws RuntimeException if an error occurs while operator FileOutputStream
         */
        public static boolean writeFile(java.io.File file, InputStream stream, boolean append) {
            OutputStream o = null;
            try {
                makeDirs(file.getAbsolutePath());
                o = new FileOutputStream(file, append);
                byte data[] = new byte[1024];
                int length = -1;
                while ((length = stream.read(data)) != -1) {
                    o.write(data, 0, length);
                }
                o.flush();
                return true;
            } catch (FileNotFoundException e) {
                throw new RuntimeException("FileNotFoundException occurred. ", e);
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            } finally {
                IO.close(o);
                IO.close(stream);
            }
        }

        /**
         * move file
         *
         * @param sourceFilePath
         * @param destFilePath
         */
        public static void moveFile(String sourceFilePath, String destFilePath) {
            if (TextUtils.isEmpty(sourceFilePath) || TextUtils.isEmpty(destFilePath)) {
                throw new RuntimeException("Both sourceFilePath and destFilePath cannot be null.");
            }
            moveFile(new java.io.File(sourceFilePath), new java.io.File(destFilePath));
        }

        /**
         * move file
         *
         * @param srcFile
         * @param destFile
         */
        public static void moveFile(java.io.File srcFile, java.io.File destFile) {
            boolean rename = srcFile.renameTo(destFile);
            if (!rename) {
                copyFile(srcFile.getAbsolutePath(), destFile.getAbsolutePath());
                deleteFile(srcFile.getAbsolutePath());
            }
        }

        /**
         * copy file
         *
         * @param sourceFilePath
         * @param destFilePath
         * @return
         * @throws RuntimeException if an error occurs while operator FileOutputStream
         */
        public static boolean copyFile(String sourceFilePath, String destFilePath) {
            InputStream inputStream = null;
            try {
                inputStream = new FileInputStream(sourceFilePath);
            } catch (FileNotFoundException e) {
                throw new RuntimeException("FileNotFoundException occurred. ", e);
            }
            return writeFile(destFilePath, inputStream);
        }

        /**
         * read file to string list, a element of list is a line
         *
         * @param filePath
         * @param charsetName The name of a supported {@link java.nio.charset.Charset </code>charset<code>}
         * @return if file not exist, return null, else return content of file
         * @throws RuntimeException if an error occurs while operator BufferedReader
         */
        public static List<String> readFileToList(String filePath, String charsetName) {
            java.io.File file = new java.io.File(filePath);
            List<String> fileContent = new ArrayList<String>();
            if (file == null || !file.isFile()) {
                return null;
            }

            BufferedReader reader = null;
            try {
                InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
                reader = new BufferedReader(is);
                String line = null;
                while ((line = reader.readLine()) != null) {
                    fileContent.add(line);
                }
                return fileContent;
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            } finally {
                IO.close(reader);
            }
        }

        /**
         * get file name from path, not include suffix
         * <p/>
         * <pre>
         *      getFileNameWithoutExtension(null)               =   null
         *      getFileNameWithoutExtension("")                 =   ""
         *      getFileNameWithoutExtension("   ")              =   "   "
         *      getFileNameWithoutExtension("abc")              =   "abc"
         *      getFileNameWithoutExtension("a.mp3")            =   "a"
         *      getFileNameWithoutExtension("a.b.rmvb")         =   "a.b"
         *      getFileNameWithoutExtension("c:\\")              =   ""
         *      getFileNameWithoutExtension("c:\\a")             =   "a"
         *      getFileNameWithoutExtension("c:\\a.b")           =   "a"
         *      getFileNameWithoutExtension("c:a.txt\\a")        =   "a"
         *      getFileNameWithoutExtension("/home/admin")      =   "admin"
         *      getFileNameWithoutExtension("/home/admin/a.txt/b.mp3")  =   "b"
         * </pre>
         *
         * @param filePath
         * @return file name from path, not include suffix
         * @see
         */
        public static String getFileNameWithoutExtension(String filePath) {
            if (TextUtils.isEmpty(filePath)) {
                return filePath;
            }

            int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
            int filePosi = filePath.lastIndexOf(java.io.File.separator);
            if (filePosi == -1) {
                return (extenPosi == -1 ? filePath : filePath.substring(0, extenPosi));
            }
            if (extenPosi == -1) {
                return filePath.substring(filePosi + 1);
            }
            return (filePosi < extenPosi ? filePath.substring(filePosi + 1, extenPosi) : filePath.substring(filePosi + 1));
        }

        /**
         * get file name from path, include suffix
         * <p/>
         * <pre>
         *      getFileName(null)               =   null
         *      getFileName("")                 =   ""
         *      getFileName("   ")              =   "   "
         *      getFileName("a.mp3")            =   "a.mp3"
         *      getFileName("a.b.rmvb")         =   "a.b.rmvb"
         *      getFileName("abc")              =   "abc"
         *      getFileName("c:\\")              =   ""
         *      getFileName("c:\\a")             =   "a"
         *      getFileName("c:\\a.b")           =   "a.b"
         *      getFileName("c:a.txt\\a")        =   "a"
         *      getFileName("/home/admin")      =   "admin"
         *      getFileName("/home/admin/a.txt/b.mp3")  =   "b.mp3"
         * </pre>
         *
         * @param filePath
         * @return file name from path, include suffix
         */
        public static String getFileName(String filePath) {
            if (TextUtils.isEmpty(filePath)) {
                return filePath;
            }

            int filePosi = filePath.lastIndexOf(java.io.File.separator);
            return (filePosi == -1) ? filePath : filePath.substring(filePosi + 1);
        }

        /**
         * get folder name from path
         * <p/>
         * <pre>
         *      getFolderName(null)               =   null
         *      getFolderName("")                 =   ""
         *      getFolderName("   ")              =   ""
         *      getFolderName("a.mp3")            =   ""
         *      getFolderName("a.b.rmvb")         =   ""
         *      getFolderName("abc")              =   ""
         *      getFolderName("c:\\")              =   "c:"
         *      getFolderName("c:\\a")             =   "c:"
         *      getFolderName("c:\\a.b")           =   "c:"
         *      getFolderName("c:a.txt\\a")        =   "c:a.txt"
         *      getFolderName("c:a\\b\\c\\d.txt")    =   "c:a\\b\\c"
         *      getFolderName("/home/admin")      =   "/home"
         *      getFolderName("/home/admin/a.txt/b.mp3")  =   "/home/admin/a.txt"
         * </pre>
         *
         * @param filePath
         * @return
         */
        public static String getFolderName(String filePath) {

            if (TextUtils.isEmpty(filePath)) {
                return filePath;
            }

            int filePosi = filePath.lastIndexOf(java.io.File.separator);
            return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
        }

        /**
         * get suffix of file from path
         * <p/>
         * <pre>
         *      getFileExtension(null)               =   ""
         *      getFileExtension("")                 =   ""
         *      getFileExtension("   ")              =   "   "
         *      getFileExtension("a.mp3")            =   "mp3"
         *      getFileExtension("a.b.rmvb")         =   "rmvb"
         *      getFileExtension("abc")              =   ""
         *      getFileExtension("c:\\")              =   ""
         *      getFileExtension("c:\\a")             =   ""
         *      getFileExtension("c:\\a.b")           =   "b"
         *      getFileExtension("c:a.txt\\a")        =   ""
         *      getFileExtension("/home/admin")      =   ""
         *      getFileExtension("/home/admin/a.txt/b")  =   ""
         *      getFileExtension("/home/admin/a.txt/b.mp3")  =   "mp3"
         * </pre>
         *
         * @param filePath
         * @return
         */
        public static String getFileExtension(String filePath) {
            if (TextUtils.isEmpty(filePath)) {
                return filePath;
            }

            int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
            int filePosi = filePath.lastIndexOf(java.io.File.separator);
            if (extenPosi == -1) {
                return "";
            }
            return (filePosi >= extenPosi) ? "" : filePath.substring(extenPosi + 1);
        }

        /**
         * Creates the directory named by the trailing filename of this file, including the complete directory path required
         * to create this directory. <br/>
         * <br/>
         * <ul>
         * <strong>Attentions:</strong>
         * <li>makeDirs("C:\\Users\\Trinea") can only create users folder</li>
         * <li>makeFolder("C:\\Users\\Trinea\\") can create Trinea folder</li>
         * </ul>
         *
         * @param filePath
         * @return true if the necessary directories have been created or the target directory already exists, false one of
         * the directories can not be created.
         * <ul>
         * <li>if {@link File#getFolderName(String)} return null, return false</li>
         * <li>if target directory already exists, return true</li>
         * </ul>
         */
        public static boolean makeDirs(String filePath) {
            String folderName = getFolderName(filePath);
            if (TextUtils.isEmpty(folderName)) {
                return false;
            }

            java.io.File folder = new java.io.File(folderName);
            return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
        }

        /**
         * @param filePath
         * @return
         * @see #makeDirs(String)
         */
        public static boolean makeFolders(String filePath) {
            return makeDirs(filePath);
        }

        /**
         * Indicates if this file represents a file on the underlying file system.
         *
         * @param filePath
         * @return
         */
        public static boolean isFileExist(String filePath) {
            if (TextUtils.isEmpty(filePath)) {
                return false;
            }

            java.io.File file = new java.io.File(filePath);
            return (file.exists() && file.isFile());
        }

        /**
         * Indicates if this file represents a directory on the underlying file system.
         *
         * @param directoryPath
         * @return
         */
        public static boolean isFolderExist(String directoryPath) {
            if (TextUtils.isEmpty(directoryPath)) {
                return false;
            }

            java.io.File dire = new java.io.File(directoryPath);
            return (dire.exists() && dire.isDirectory());
        }

        /**
         * delete file or directory
         * <ul>
         * <li>if path is null or empty, return true</li>
         * <li>if path not exist, return true</li>
         * <li>if path exist, delete recursion. return true</li>
         * <ul>
         *
         * @param path
         * @return
         */
        public static boolean deleteFile(String path) {
            if (TextUtils.isEmpty(path)) {
                return true;
            }

            java.io.File file = new java.io.File(path);
            if (!file.exists()) {
                return true;
            }
            if (file.isFile()) {
                return file.delete();
            }
            if (!file.isDirectory()) {
                return false;
            }
            for (java.io.File f : file.listFiles()) {
                if (f.isFile()) {
                    f.delete();
                } else if (f.isDirectory()) {
                    deleteFile(f.getAbsolutePath());
                }
            }
            return file.delete();
        }

        /**
         * get file size
         * <ul>
         * <li>if path is null or empty, return -1</li>
         * <li>if path exist and it is a file, return file size, else return -1</li>
         * <ul>
         *
         * @param path
         * @return returns the length of this file in bytes. returns -1 if the file does not exist.
         */
        public static long getFileSize(String path) {
            if (TextUtils.isEmpty(path)) {
                return -1;
            }

            java.io.File file = new java.io.File(path);
            return (file.exists() && file.isFile() ? file.length() : -1);
        }

        /**
         * 获得SD卡总大小
         *
         * @return byte为单位
         */
        private long getSDTotalSize() {
            java.io.File file = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(file.getAbsolutePath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return blockSize * totalBlocks;
        }

        /**
         * 获得sd卡剩余容量，即可用大小
         *
         * @return
         */
        private long getSDAvailableSize() {
            java.io.File file = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(file.getAbsolutePath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return blockSize * availableBlocks;
        }

        /**
         * 获得机身内存总大小
         *
         * @return
         */
        private long getRomTotalSize() {
            java.io.File path = Environment.getDataDirectory();
            StatFs stat = new StatFs(path.getAbsolutePath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return blockSize * totalBlocks;
        }

        /**
         * 获得机身可用内存
         *
         * @return
         */
        private long getRomAvailableSize() {
            java.io.File path = Environment.getDataDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return blockSize * availableBlocks;
        }

        static DecimalFormat formater = new DecimalFormat("####.00");

        /**
         * 返回byte的数据大小对应的文本
         *
         * @param size
         * @return
         */
        public static String getDataSizeFormat(long size) {
            if (size < 1024) {
                return size + "bytes";
            } else if (size < 1024 * 1024) {
                float kbsize = size / 1024f;
                return formater.format(kbsize) + "KB";
            } else if (size < 1024 * 1024 * 1024) {
                float mbsize = size / 1024f / 1024f;
                return formater.format(mbsize) + "MB";
            } else if (size < 1024 * 1024 * 1024 * 1024) {
                float gbsize = size / 1024f / 1024f / 1024f;
                return formater.format(gbsize) + "GB";
            } else {
                return "size: error";
            }
        }
    }

    public static class IO {
        /**
         * 关闭流
         *
         * @param closeable
         */
        public static void close(Closeable closeable) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }

        /**
         * Close closable and hide possible { IOException}
         *
         * @param closeable closeable object
         */
        public static void closeQuietly(Closeable closeable) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    // Ignored
                }
            }
        }
    }

    public static class Date {
        public static SimpleDateFormat m = new SimpleDateFormat("MM");
        public static SimpleDateFormat d = new SimpleDateFormat("dd");
        public static SimpleDateFormat md = new SimpleDateFormat("MM-dd");
        public static SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        public static SimpleDateFormat ymdDot = new SimpleDateFormat("yyyy.MM.dd");
        public static SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        public static SimpleDateFormat ymdhmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        public static SimpleDateFormat ymdhm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        public static SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
        public static SimpleDateFormat mdhm = new SimpleDateFormat("MM月dd日 HH:mm");
        public static SimpleDateFormat mdhmLink = new SimpleDateFormat("MM-dd HH:mm");

        /**
         * 返回解析的时间
         * @param date
         * @param format
         * @return
         * @throws ParseException
         */
        public static java.util.Date parse(String date, SimpleDateFormat format) throws ParseException {
            return format.parse(date);
        }

        /**
         * 年月日[2015-07-28]
         *
         * @param timeInMills
         * @return
         */
        public static String getYmd(long timeInMills) {
            return ymd.format(new java.util.Date(timeInMills));
        }

        /**
         * 年月日[2015.07.28]
         *
         * @param timeInMills
         * @return
         */
        public static String getYmdDot(long timeInMills) {
            return ymdDot.format(new java.util.Date(timeInMills));
        }

        public static String getYmdhms(long timeInMills) {
            return ymdhms.format(new java.util.Date(timeInMills));
        }

        public static String getYmdhmsS(long timeInMills) {
            return ymdhmss.format(new java.util.Date(timeInMills));
        }

        public static String getYmdhm(long timeInMills) {
            return ymdhm.format(new java.util.Date(timeInMills));
        }

        public static String getHm(long timeInMills) {
            return hm.format(new java.util.Date(timeInMills));
        }

        public static String getMd(long timeInMills) {
            return md.format(new java.util.Date(timeInMills));
        }

        public static String getMdhm(long timeInMills) {
            return mdhm.format(new java.util.Date(timeInMills));
        }

        public static String getMdhmLink(long timeInMills) {
            return mdhmLink.format(new java.util.Date(timeInMills));
        }

        public static String getM(long timeInMills) {
            return m.format(new java.util.Date(timeInMills));
        }

        public static String getD(long timeInMills) {
            return d.format(new java.util.Date(timeInMills));
        }

        /**
         * 是否是今天
         *
         * @param timeInMills
         * @return
         */
        public static boolean isToday(long timeInMills) {
            String dest = getYmd(timeInMills);
            String now = getYmd(Calendar.getInstance().getTimeInMillis());
            return dest.equals(now);
        }

        /**
         * 是否是同一天
         *
         * @param aMills
         * @param bMills
         * @return
         */
        public static boolean isSameDay(long aMills, long bMills) {
            String aDay = getYmd(aMills);
            String bDay = getYmd(bMills);
            return aDay.equals(bDay);
        }

        /**
         * 获取年份
         *
         * @param mills
         * @return
         */
        public static int getYear(long mills) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);
            return calendar.get(Calendar.YEAR);
        }

        /**
         * 获取月份
         *
         * @param mills
         * @return
         */
        public static int getMonth(long mills) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);
            return calendar.get(Calendar.MONTH) + 1;
        }


        /**
         * 获取月份的天数
         *
         * @param mills
         * @return
         */
        public static int getDaysInMonth(long mills) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);

            switch (month) {
                case Calendar.JANUARY:
                case Calendar.MARCH:
                case Calendar.MAY:
                case Calendar.JULY:
                case Calendar.AUGUST:
                case Calendar.OCTOBER:
                case Calendar.DECEMBER:
                    return 31;
                case Calendar.APRIL:
                case Calendar.JUNE:
                case Calendar.SEPTEMBER:
                case Calendar.NOVEMBER:
                    return 30;
                case Calendar.FEBRUARY:
                    return (year % 4 == 0) ? 29 : 28;
                default:
                    throw new IllegalArgumentException("Invalid Month");
            }
        }


        /**
         * 获取星期,0-周日,1-周一，2-周二，3-周三，4-周四，5-周五，6-周六
         *
         * @param mills
         * @return
         */
        public static int getWeek(long mills) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);

            return calendar.get(Calendar.DAY_OF_WEEK) - 1;
        }

        /**
         * 获取当月第一天的时间（毫秒值）
         *
         * @param mills
         * @return
         */
        public static long getFirstOfMonth(long mills) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);
            calendar.set(Calendar.DAY_OF_MONTH, 1);

            return calendar.getTimeInMillis();
        }

    }


    public static class NetWork {
        public static final String NETWORK_TYPE_WIFI = "wifi";
        public static final String NETWORK_TYPE_3G = "3g";
        public static final String NETWORK_TYPE_2G = "2g";
        public static final String NETWORK_TYPE_WAP = "wap";
        public static final String NETWORK_TYPE_UNKNOWN = "unknown";
        public static final String NETWORK_TYPE_DISCONNECT = "disconnect";

        public static int getNetworkType(Context context) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager == null ? null : connectivityManager.getActiveNetworkInfo();
            return networkInfo == null ? -1 : networkInfo.getType();
        }

        public static String getNetworkTypeName(Context context) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo;
            String type = NETWORK_TYPE_DISCONNECT;
            if (manager == null || (networkInfo = manager.getActiveNetworkInfo()) == null) {
                return type;
            }

            if (networkInfo.isConnected()) {
                String typeName = networkInfo.getTypeName();
                if ("WIFI".equalsIgnoreCase(typeName)) {
                    type = NETWORK_TYPE_WIFI;
                } else if ("MOBILE".equalsIgnoreCase(typeName)) {
                    String proxyHost = android.net.Proxy.getDefaultHost();
                    type = TextUtils.isEmpty(proxyHost) ? (isFastMobileNetwork(context) ? NETWORK_TYPE_3G : NETWORK_TYPE_2G)
                            : NETWORK_TYPE_WAP;
                } else {
                    type = NETWORK_TYPE_UNKNOWN;
                }
            }
            return type;
        }

        private static boolean isFastMobileNetwork(Context context) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager == null) {
                return false;
            }

            switch (telephonyManager.getNetworkType()) {
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    return false;
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return false;
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return false;
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return true;
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return true;
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return false;
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return true;
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    return true;
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return true;
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return true;
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                    return true;
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    return true;
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    return true;
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return false;
                case TelephonyManager.NETWORK_TYPE_LTE:
                    return true;
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                    return false;
                default:
                    return false;
            }
        }

    }

    public static class Empty {


        public static boolean check(Object obj) {
            return obj == null;
        }

        public static boolean check(List list) {
            return list == null || list.isEmpty();
        }

        public static boolean check(java.lang.Object[] objects) {
            return objects == null || objects.length == 0;
        }

        public static boolean check(String str) {
            return str == null || "".equals(str);
        }

        public static boolean check(Map sourceMap) {
            return (sourceMap == null || sourceMap.size() == 0);
        }

    }


    public static class Object {
        /**
         * compare two object
         *
         * @param actual
         * @param expected
         * @return if both are null, return true
         * return actual.{ Object#equals(Object)}
         */
        public static boolean isEquals(java.lang.Object actual, java.lang.Object expected) {
            return actual == expected || (actual == null ? expected == null : actual.equals(expected));
        }

        /**
         * null Object to empty string
         * <p>
         * <p>
         * nullStrToEmpty(null) = &quot;&quot;;
         * nullStrToEmpty(&quot;&quot;) = &quot;&quot;;
         * nullStrToEmpty(&quot;aa&quot;) = &quot;aa&quot;;
         *
         * @param str
         * @return
         */
        public static String nullStrToEmpty(java.lang.Object str) {
            return (str == null ? "" : (str instanceof String ? (String) str : str.toString()));
        }

        /**
         * convert long array to Long array
         *
         * @param source
         * @return
         */
        public static Long[] transformLongArray(long[] source) {
            Long[] destin = new Long[source.length];
            for (int i = 0; i < source.length; i++) {
                destin[i] = source[i];
            }
            return destin;
        }

        /**
         * convert Long array to long array
         *
         * @param source
         * @return
         */
        public static long[] transformLongArray(Long[] source) {
            long[] destin = new long[source.length];
            for (int i = 0; i < source.length; i++) {
                destin[i] = source[i];
            }
            return destin;
        }

        /**
         * convert int array to Integer array
         *
         * @param source
         * @return
         */
        public static Integer[] transformIntArray(int[] source) {
            Integer[] destin = new Integer[source.length];
            for (int i = 0; i < source.length; i++) {
                destin[i] = source[i];
            }
            return destin;
        }

        /**
         * convert Integer array to int array
         *
         * @param source
         * @return
         */
        public static int[] transformIntArray(Integer[] source) {
            int[] destin = new int[source.length];
            for (int i = 0; i < source.length; i++) {
                destin[i] = source[i];
            }
            return destin;
        }

        /**
         * compare two object
         * <p>
         * About result
         * if v1 ＞＞ v2, return 1
         * if v1　＝ v2, return 0
         * if v1 ＜＜ v2, return -1
         * <p>
         * About rule
         * if v1 is null, v2 is null, then return 0
         * if v1 is null, v2 is not null, then return -1
         * if v1 is not null, v2 is null, then return 1
         * return v1.{ Comparable#compareTo(Object)}
         *
         * @param v1
         * @param v2
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        public static <V> int compare(V v1, V v2) {
            return v1 == null ? (v2 == null ? 0 : -1) : (v2 == null ? 1 : ((Comparable) v1).compareTo(v2));
        }


    }


    public static class Image {

        /**
         * convert Bitmap to byte array
         *
         * @param b
         * @return
         */
        public static byte[] bitmapToByte(Bitmap b) {
            if (b == null) {
                return null;
            }

            ByteArrayOutputStream o = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.PNG, 100, o);
            return o.toByteArray();
        }


        /**
         * convert byte array to Bitmap
         *
         * @param b
         * @return
         */
        public static Bitmap byteToBitmap(byte[] b) {
            return (b == null || b.length == 0) ? null : BitmapFactory.decodeByteArray(b, 0, b.length);
        }

        /**
         * convert Drawable to Bitmap
         *
         * @param d
         * @return
         */
        public static Bitmap drawableToBitmap(Drawable d) {
            return d == null ? null : ((BitmapDrawable) d).getBitmap();
        }

        /**
         * convert Bitmap to Drawable
         *
         * @param b
         * @return
         */
        public static Drawable bitmapToDrawable(Bitmap b) {
            return b == null ? null : new BitmapDrawable(b);
        }

        public static void bitmapToFile(Bitmap b, java.io.File file) {
            FileOutputStream os = null;
            try {
                boolean folderExist = File.isFolderExist(file.getParent());
                if (!folderExist) {
                    file.getParentFile().mkdirs();
                }
                os = new FileOutputStream(file);
                b.compress(Bitmap.CompressFormat.JPEG, 100, os);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        /**
         * convert Drawable to byte array
         *
         * @param d
         * @return
         */
        public static byte[] drawableToByte(Drawable d) {
            return bitmapToByte(drawableToBitmap(d));
        }

        /**
         * convert byte array to Drawable
         *
         * @param b
         * @return
         */
        public static Drawable byteToDrawable(byte[] b) {
            return bitmapToDrawable(byteToBitmap(b));
        }

        /**
         * get input stream from network by imageurl, you need to close inputStream yourself
         *
         * @param imageUrl
         * @param readTimeOutMillis
         * @return
         * @see Image#getInputStreamFromUrl(String, int, Map)
         */
        public static InputStream getInputStreamFromUrl(String imageUrl, int readTimeOutMillis) {
            return getInputStreamFromUrl(imageUrl, readTimeOutMillis, null);
        }

        /**
         * get input stream from network by imageurl, you need to close inputStream yourself
         *
         * @param imageUrl
         * @param readTimeOutMillis read time out, if less than 0, not set, in mills
         * @param requestProperties http request properties
         * @return
         * @throws RuntimeException
         */
        public static InputStream getInputStreamFromUrl(String imageUrl, int readTimeOutMillis,
                                                        Map<String, String> requestProperties) {
            InputStream stream = null;
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                Http.setURLConnection(requestProperties, con);
                if (readTimeOutMillis > 0) {
                    con.setReadTimeout(readTimeOutMillis);
                }
                stream = con.getInputStream();
            } catch (MalformedURLException e) {
                IO.close(stream);
                throw new RuntimeException("MalformedURLException occurred. ", e);
            } catch (IOException e) {
                IO.close(stream);
                throw new RuntimeException("IOException occurred. ", e);
            }
            return stream;
        }

        /**
         * get drawable by imageUrl
         *
         * @param imageUrl
         * @param readTimeOutMillis
         * @see Image#getDrawableFromUrl(String, int, Map)
         */
        public static Drawable getDrawableFromUrl(String imageUrl, int readTimeOutMillis) {
            return getDrawableFromUrl(imageUrl, readTimeOutMillis, null);
        }

        /**
         * get drawable by imageUrl
         *
         * @param imageUrl
         * @param readTimeOutMillis read time out, if less than 0, not set, in mills
         * @param requestProperties http request properties
         */
        public static Drawable getDrawableFromUrl(String imageUrl, int readTimeOutMillis,
                                                  Map<String, String> requestProperties) {
            InputStream stream = getInputStreamFromUrl(imageUrl, readTimeOutMillis, requestProperties);
            Drawable d = Drawable.createFromStream(stream, "src");
            IO.close(stream);
            return d;
        }

        /**
         * get Bitmap by imageUrl
         *
         * @param imageUrl
         * @param readTimeOut
         * @see Image#getBitmapFromUrl(String, int, Map)
         */
        public static Bitmap getBitmapFromUrl(String imageUrl, int readTimeOut) {
            return getBitmapFromUrl(imageUrl, readTimeOut, null);
        }

        /**
         * get Bitmap by imageUrl
         *
         * @param imageUrl
         * @param requestProperties http request properties
         */
        public static Bitmap getBitmapFromUrl(String imageUrl, int readTimeOut, Map<String, String> requestProperties) {
            InputStream stream = getInputStreamFromUrl(imageUrl, readTimeOut, requestProperties);
            Bitmap b = BitmapFactory.decodeStream(stream);
            IO.close(stream);
            return b;
        }

        /**
         * scale image
         *
         * @param org
         * @param newWidth
         * @param newHeight
         */
        public static Bitmap scaleImageTo(Bitmap org, int newWidth, int newHeight) {
            return scaleImage(org, (float) newWidth / org.getWidth(), (float) newHeight / org.getHeight());
        }

        /**
         * scale image
         *
         * @param org
         * @param scaleWidth  sacle of width
         * @param scaleHeight scale of height
         */
        public static Bitmap scaleImage(Bitmap org, float scaleWidth, float scaleHeight) {
            if (org == null) {
                return null;
            }

            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            return Bitmap.createBitmap(org, 0, 0, org.getWidth(), org.getHeight(), matrix, true);
        }


    }


    public static class Http {

        /**
         * url and para separator
         **/
        public static final String URL_AND_PARA_SEPARATOR = "?";
        /**
         * parameters separator
         **/
        public static final String PARAMETERS_SEPARATOR = "&";
        /**
         * paths separator
         **/
        public static final String PATHS_SEPARATOR = "/";
        /**
         * equal sign
         **/
        public static final String EQUAL_SIGN = "=";

        public static final String EXPIRES = "expires";
        public static final String CACHE_CONTROL = "cache-control";


        /**
         * http get synchronous
         * <p>
         * use gzip compression default
         * use bufferedReader to improve the reading speed
         *
         * @param request
         * @return the response of the url, if null represents http error
         */
        public static HttpResponse httpGet(HttpRequest request) {
            if (request == null) {
                return null;
            }

            BufferedReader input = null;
            HttpURLConnection con = null;
            try {
                URL url = new URL(request.getUrl());
                try {
                    HttpResponse response = new HttpResponse(request.getUrl());
                    // default gzip encode
                    con = (HttpURLConnection) url.openConnection();
                    setURLConnection(request, con);
                    input = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String s;
                    while ((s = input.readLine()) != null) {
                        sb.append(s).append("\n");
                    }
                    response.setResponseBody(sb.toString());
                    setHttpResponse(con, response);
                    return response;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } finally {
                // close buffered
                IO.closeQuietly(input);
                // disconnecting releases the resources held by a connection so they may be closed or reused
                if (con != null) {
                    con.disconnect();
                }
            }

            return null;
        }

        /**
         * http get synchronous
         *
         * @param httpUrl
         * @return the response of the url, if null represents http error
         * @see Http#httpGet(HttpRequest)
         */
        public static HttpResponse httpGet(String httpUrl) {
            return httpGet(new HttpRequest(httpUrl));
        }

        /**
         * http get synchronous
         *
         * @param request
         * @return the content of the url, if null represents http error
         * @see Http#httpGet(HttpRequest)
         */
        public static String httpGetString(HttpRequest request) {
            HttpResponse response = httpGet(request);
            return response == null ? null : response.getResponseBody();
        }

        /**
         * http get synchronous
         *
         * @param httpUrl
         * @return the content of the url, if null represents http error
         * @see Http#httpGet(HttpRequest)
         */
        public static String httpGetString(String httpUrl) {
            HttpResponse response = httpGet(new HttpRequest(httpUrl));
            return response == null ? null : response.getResponseBody();
        }

        /**
         * http get asynchronous
         * <p>
         * It gets data from network asynchronous.
         * If you want get data synchronous, use { #httpGet(HttpRequest)} or { #httpGetString(HttpRequest)}
         *
         * @param url
         * @param listener listener which can do something before or after HttpGet. this can be null if you not want to do
         *                 something
         */
        public static void httpGet(String url, HttpListener listener) {
            new HttpStringAsyncTask(listener).execute(url);
        }

        /**
         * http get asynchronous
         * <p>
         * It gets data or network asynchronous.
         * If you want get data synchronous, use { HttpCache#httpGet(HttpRequest)} or
         * { HttpCache#httpGetString(HttpRequest)}
         *
         * @param request
         * @param listener listener which can do something before or after HttpGet. this can be null if you not want to do
         *                 something
         */
        public static void httpGet(HttpRequest request, HttpListener listener) {
            new HttpRequestAsyncTask(listener).execute(request);
        }

        /**
         * http post
         * <p>
         * use gzip compression default
         * use bufferedReader to improve the reading speed
         *
         * @param request
         * @return the response of the url, if null represents http error
         */
        public static HttpResponse httpPost(HttpRequest request) {
            if (request == null) {
                return null;
            }

            BufferedReader input = null;
            HttpURLConnection con = null;
            try {
                URL url = new URL(request.getUrl());
                try {
                    HttpResponse response = new HttpResponse(request.getUrl());
                    // default gzip encode
                    con = (HttpURLConnection) url.openConnection();
                    setURLConnection(request, con);
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    String paras = request.getParas();
                    if (!Empty.check(paras)) {
                        con.getOutputStream().write(paras.getBytes());
                    }
                    input = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String s;
                    while ((s = input.readLine()) != null) {
                        sb.append(s).append("\n");
                    }
                    response.setResponseBody(sb.toString());
                    setHttpResponse(con, response);
                    return response;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } finally {
                // close buffered
                IO.closeQuietly(input);
                // disconnecting releases the resources held by a connection so they may be closed or reused
                if (con != null) {
                    con.disconnect();
                }
            }

            return null;
        }

        /**
         * http post
         *
         * @param httpUrl
         * @return the response of the url, if null represents http error
         * @see Http#httpPost(HttpRequest)
         */
        public static HttpResponse httpPost(String httpUrl) {
            return httpPost(new HttpRequest(httpUrl));
        }

        /**
         * http post
         *
         * @param httpUrl
         * @return the content of the url, if null represents http error
         * @see Http#httpPost(HttpRequest)
         */
        public static String httpPostString(String httpUrl) {
            HttpResponse response = httpPost(new HttpRequest(httpUrl));
            return response == null ? null : response.getResponseBody();
        }

        /**
         * http post
         *
         * @param httpUrl
         * @param parasMap paras map, key is para name, value is para value. will be transfrom to String by
         *                 { HttpUtils#joinParas(Map)}
         * @return the content of the url, if null represents http error
         * @see Http#httpPost(HttpRequest)
         */
        public static String httpPostString(String httpUrl, Map<String, String> parasMap) {
            HttpResponse response = httpPost(new HttpRequest(httpUrl, parasMap));
            return response == null ? null : response.getResponseBody();
        }

        /**
         * join url and paras
         * <p>
         * getUrlWithParas(null, {(a, b)})                        =   ?a=b
         * getUrlWithParas("baidu.com", {})                       =   baidu.com
         * getUrlWithParas("baidu.com", {(a, b), (i, j)})         =   baidu.com?a=b＆i=j
         * getUrlWithParas("baidu.com", {(a, b), (i, j), (c, d)}) =   baidu.com?a=b＆i=j＆c=d
         * </p>
         *
         * @param url      url
         * @param parasMap paras map, key is para name, value is para value
         * @return if url is null, process it as empty string
         */
        public static String getUrlWithParas(String url, Map<String, String> parasMap) {
            StringBuilder urlWithParas = new StringBuilder(Empty.check(url) ? "" : url);
            String paras = joinParas(parasMap);
            if (!Empty.check(paras)) {
                urlWithParas.append(URL_AND_PARA_SEPARATOR).append(paras);
            }
            return urlWithParas.toString();
        }

        /**
         * join url and encoded paras
         *
         * @param url
         * @param parasMap
         * @return
         * @see #getUrlWithParas(String, Map)
         */
        public static String getUrlWithValueEncodeParas(String url, Map<String, String> parasMap) {
            StringBuilder urlWithParas = new StringBuilder(Empty.check(url) ? "" : url);
            String paras = joinParasWithEncodedValue(parasMap);
            if (!Empty.check(paras)) {
                urlWithParas.append(URL_AND_PARA_SEPARATOR).append(paras);
            }
            return urlWithParas.toString();
        }

        /**
         * join paras
         *
         * @param parasMap paras map, key is para name, value is para value
         * @return join key and value with { #EQUAL_SIGN}, join keys with { #PARAMETERS_SEPARATOR}
         */
        public static String joinParas(Map<String, String> parasMap) {
            if (parasMap == null || parasMap.size() == 0) {
                return null;
            }

            StringBuilder paras = new StringBuilder();
            Iterator<Map.Entry<String, String>> ite = parasMap.entrySet().iterator();
            while (ite.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry<String, String>) ite.next();
                paras.append(entry.getKey()).append(EQUAL_SIGN).append(entry.getValue());
                if (ite.hasNext()) {
                    paras.append(PARAMETERS_SEPARATOR);
                }
            }
            return paras.toString();
        }

        /**
         * join paras with encoded value
         *
         * @param parasMap
         * @return
         * @see #joinParas(Map)
         */
        public static String joinParasWithEncodedValue(Map<String, String> parasMap) {
            StringBuilder paras = new StringBuilder("");
            if (parasMap != null && parasMap.size() > 0) {
                Iterator<Map.Entry<String, String>> ite = parasMap.entrySet().iterator();
                try {
                    while (ite.hasNext()) {
                        Map.Entry<String, String> entry = (Map.Entry<String, String>) ite.next();
                        paras.append(entry.getKey()).append(EQUAL_SIGN).append(String$.utf8Encode(entry.getValue()));
                        if (ite.hasNext()) {
                            paras.append(PARAMETERS_SEPARATOR);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return paras.toString();
        }

        /**
         * append a key and value pair to url
         *
         * @param url
         * @param paraKey
         * @param paraValue
         * @return
         */
        public static String appendParaToUrl(String url, String paraKey, String paraValue) {
            if (Empty.check(url)) {
                return url;
            }

            StringBuilder sb = new StringBuilder(url);
            if (!url.contains(URL_AND_PARA_SEPARATOR)) {
                sb.append(URL_AND_PARA_SEPARATOR);
            } else {
                sb.append(PARAMETERS_SEPARATOR);
            }
            return sb.append(paraKey).append(EQUAL_SIGN).append(paraValue).toString();
        }

        private static final SimpleDateFormat GMT_FORMAT = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z",
                Locale.ENGLISH);

        /**
         * parse gmt time to long
         *
         * @param gmtTime likes Thu, 11 Apr 2013 10:20:30 GMT
         * @return -1 represents exception otherwise time in milliseconds
         */
        public static long parseGmtTime(String gmtTime) {
            try {
                return GMT_FORMAT.parse(gmtTime).getTime();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return -1;
        }

        /**
         * set HttpRequest to HttpURLConnection
         *
         * @param request       source request
         * @param urlConnection destin url connection
         */
        private static void setURLConnection(HttpRequest request, HttpURLConnection urlConnection) {
            if (request == null || urlConnection == null) {
                return;
            }

            setURLConnection(request.getRequestProperties(), urlConnection);
            if (request.getConnectTimeout() >= 0) {
                urlConnection.setConnectTimeout(request.getConnectTimeout());
            }
            if (request.getReadTimeout() >= 0) {
                urlConnection.setReadTimeout(request.getReadTimeout());
            }
        }

        /**
         * set HttpURLConnection property
         *
         * @param requestProperties
         * @param urlConnection
         */
        public static void setURLConnection(Map<String, String> requestProperties, HttpURLConnection urlConnection) {
            if (Empty.check(requestProperties) || urlConnection == null) {
                return;
            }

            for (Map.Entry<String, String> entry : requestProperties.entrySet()) {
                if (!Empty.check(entry.getKey())) {
                    urlConnection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
        }

        /**
         * set HttpURLConnection to HttpResponse
         *
         * @param urlConnection source url connection
         * @param response      destin response
         */
        private static void setHttpResponse(HttpURLConnection urlConnection, HttpResponse response) {
            if (response == null || urlConnection == null) {
                return;
            }
            try {
                response.setResponseCode(urlConnection.getResponseCode());
            } catch (IOException e) {
                response.setResponseCode(-1);
            }
            response.setResponseHeader(EXPIRES, urlConnection.getHeaderField("Expires"));
            response.setResponseHeader(CACHE_CONTROL, urlConnection.getHeaderField("Cache-Control"));
        }

        /**
         * AsyncTask to get data by String url
         *
         * @author 2013-11-15
         */
        private static class HttpStringAsyncTask extends AsyncTask<String, Void, HttpResponse> {

            private HttpListener listener;

            public HttpStringAsyncTask(HttpListener listener) {
                this.listener = listener;
            }

            protected HttpResponse doInBackground(String... url) {
                if (url == null || url.length == 0) {
                    return null;
                }
                return httpGet(url[0]);
            }

            protected void onPreExecute() {
                if (listener != null) {
                    listener.onPreGet();
                }
            }

            protected void onPostExecute(HttpResponse httpResponse) {
                if (listener != null) {
                    listener.onPostGet(httpResponse);
                }
            }
        }

        /**
         * AsyncTask to get data by HttpRequest
         *
         * @author 2013-11-15
         */
        private static class HttpRequestAsyncTask extends AsyncTask<HttpRequest, Void, HttpResponse> {

            private HttpListener listener;

            public HttpRequestAsyncTask(HttpListener listener) {
                this.listener = listener;
            }

            protected HttpResponse doInBackground(HttpRequest... httpRequest) {
                if (httpRequest == null || httpRequest.length == 0) {
                    return null;
                }
                return httpGet(httpRequest[0]);
            }

            protected void onPreExecute() {
                if (listener != null) {
                    listener.onPreGet();
                }
            }

            protected void onPostExecute(HttpResponse httpResponse) {
                if (listener != null) {
                    listener.onPostGet(httpResponse);
                }
            }
        }

        /**
         * HttpListener, can do something before or after HttpGet
         *
         * @author 2013-11-15
         */
        public static abstract class HttpListener {

            /**
             * Runs on the UI thread before httpGet.
             * <p>
             * this can be null if you not want to do something
             */
            protected void onPreGet() {
            }

            /**
             * Runs on the UI thread after httpGet. The httpResponse is returned by httpGet.
             * <p>
             * this can be null if you not want to do something
             *
             * @param httpResponse get by the url
             */
            protected void onPostGet(HttpResponse httpResponse) {
            }
        }

        public static class HttpResponse {

            private String url;
            /**
             * http response content
             **/
            private String responseBody;
            private Map<String, java.lang.Object> responseHeaders;
            /**
             * type to mark this response
             **/
            private int type;
            /**
             * expired time in milliseconds
             **/
            private long expiredTime;
            /**
             * this is a client mark, whether this response is in client cache
             **/
            private boolean isInCache;

            private boolean isInitExpiredTime;
            /**
             * An int representing the three digit HTTP Status-Code.
             * <p>
             * 1xx: Informational
             * 2xx: Success
             * 3xx: Redirection
             * 4xx: Client Error
             * 5xx: Server Error
             */
            private int responseCode = -1;

            public HttpResponse(String url) {
                this.url = url;
                type = 0;
                isInCache = false;
                isInitExpiredTime = false;
                responseHeaders = new HashMap<String, java.lang.Object>();
            }

            public HttpResponse() {
                responseHeaders = new HashMap<String, java.lang.Object>();
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getResponseBody() {
                return responseBody;
            }

            public void setResponseBody(String responseBody) {
                this.responseBody = responseBody;
            }

            /**
             * get reponse code
             *
             * @return An  int  representing the three digit HTTP Status-Code.
             * <p>
             * 1xx: Informational
             * 2xx: Success
             * 3xx: Redirection
             * 4xx: Client Error
             * 5xx: Server Error
             * -1: http error
             */
            public int getResponseCode() {
                return responseCode;
            }

            public void setResponseCode(int responseCode) {
                this.responseCode = responseCode;
            }

            /**
             * not avaliable now
             *
             * @return
             */
            private Map<String, java.lang.Object> getResponseHeaders() {
                return responseHeaders;
            }

            public void setResponseHeaders(Map<String, java.lang.Object> responseHeaders) {
                this.responseHeaders = responseHeaders;
            }

            /**
             * get type
             * <p>
             * type to mark this response, default is 0
             * it will be used in { HttpCache#HttpCache(android.content.Context, int)}
             *
             * @return the type
             */
            public int getType() {
                return type;
            }

            /**
             * set type
             * <p>
             * type to mark this response, default is 0, cannot be smaller than 0.
             * it will be used in { HttpCache#HttpCache(android.content.Context, int)}
             *
             * @param type the type to set
             */
            public void setType(int type) {
                if (type < 0) {
                    throw new IllegalArgumentException("The type of HttpResponse cannot be smaller than 0.");
                }
                this.type = type;
            }

            /**
             * set expired time in millis
             *
             * @param expiredTime
             */
            public void setExpiredTime(long expiredTime) {
                isInitExpiredTime = true;
                this.expiredTime = expiredTime;
            }

            /**
             * get expired time in millis
             * <p>
             * if current time is bigger than expired time, it means this response is dirty
             *
             * @return if max-age in cache-control is exists, return current time plus it
             * else return expires
             * if something error, return -1
             */
            public long getExpiredTime() {
                if (isInitExpiredTime) {
                    return expiredTime;
                } else {
                    isInitExpiredTime = true;
                    return expiredTime = getExpiresInMillis();
                }
            }

            /**
             * whether this response has expired
             *
             * @return
             */
            public boolean isExpired() {
                return System.currentTimeMillis() > expiredTime;
            }

            /**
             * get isInCache, this is a client mark, whethero is in client cache
             *
             * @return the isInCache
             */
            public boolean isInCache() {
                return isInCache;
            }

            /**
             * set isInCache, this is a client mark, whethero is in client cache
             *
             * @param isInCache the isInCache to set
             * @return
             */
            public HttpResponse setInCache(boolean isInCache) {
                this.isInCache = isInCache;
                return this;
            }

            /**
             * http expires in reponse header
             *
             * @return null represents http error or no expires in response headers
             */
            public String getExpiresHeader() {
                try {
                    return responseHeaders == null ? null : (String) responseHeaders.get(EXPIRES);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            /**
             * http cache-control in reponse header
             *
             * @return -1 represents http error or no cache-control in response headers, or max-age in seconds
             */
            private long getCacheControlMaxAge() {
                try {
                    String cacheControl = (String) responseHeaders.get(CACHE_CONTROL);
                    if (!Empty.check(cacheControl)) {
                        int start = cacheControl.indexOf("max-age=");
                        if (start != -1) {
                            int end = cacheControl.indexOf(",", start);
                            String maxAge;
                            if (end != -1) {
                                maxAge = cacheControl.substring(start + "max-age=".length(), end);
                            } else {
                                maxAge = cacheControl.substring(start + "max-age=".length());
                            }
                            return Long.parseLong(maxAge);
                        }
                    }
                    return -1;
                } catch (Exception e) {
                    e.printStackTrace();
                    return -1;
                }
            }

            /**
             * get expires
             *
             * @return if max-age in cache-control is exists, return current time plus it
             * else return expires
             * if something error, return -1
             */
            private long getExpiresInMillis() {
                long maxAge = getCacheControlMaxAge();
                if (maxAge != -1) {
                    return System.currentTimeMillis() + maxAge * 1000;
                } else {
                    String expire = getExpiresHeader();
                    if (!Empty.check(expire)) {
                        return parseGmtTime(getExpiresHeader());
                    }
                }
                return -1;
            }

            /**
             * set response header
             *
             * @param field
             * @param newValue
             */
            public void setResponseHeader(String field, String newValue) {
                if (responseHeaders != null) {
                    responseHeaders.put(field, newValue);
                }
            }

            /**
             * get response header, not avaliable now
             *
             * @param field
             */
            private java.lang.Object getResponseHeader(String field) {
                return responseHeaders == null ? null : responseHeaders.get(field);
            }
        }


        /**
         * HttpRequest
         * <p>
         * Constructor
         * { HttpRequest#HttpRequest(String)}
         * { HttpRequest#HttpRequest(String, Map)}
         * <p>
         * <p>
         * Setting
         * { #setConnectTimeout(int)}
         * { #setReadTimeout(int)}
         * { #setParasMap(Map)}
         * { #setUserAgent(String)}
         * { #setRequestProperty(String, String)}
         * { #setRequestProperties(Map)}
         *
         * @author 2013-5-12
         */
        public static class HttpRequest {

            private String url;
            private int connectTimeout;
            private int readTimeout;
            private Map<String, String> parasMap;
            private Map<String, String> requestProperties;

            public HttpRequest(String url) {
                this.url = url;
                this.connectTimeout = -1;
                this.readTimeout = -1;
                requestProperties = new HashMap<String, String>();
            }

            public HttpRequest(String url, Map<String, String> parasMap) {
                this.url = url;
                this.parasMap = parasMap;
                this.connectTimeout = -1;
                this.readTimeout = -1;
                requestProperties = new HashMap<String, String>();
            }

            public String getUrl() {
                return url;
            }

            /**
             * @return
             * @see URLConnection#getConnectTimeout()
             */
            public int getConnectTimeout() {
                return connectTimeout;
            }

            /**
             * @param timeoutMillis
             * @see URLConnection#setConnectTimeout(int)
             */
            public void setConnectTimeout(int timeoutMillis) {
                if (timeoutMillis < 0) {
                    throw new IllegalArgumentException("timeout can not be negative");
                }
                connectTimeout = timeoutMillis;
            }

            /**
             * @return
             * @see URLConnection#getReadTimeout()
             */
            public int getReadTimeout() {
                return readTimeout;
            }

            /**
             * @param timeoutMillis
             * @see URLConnection#setReadTimeout(int)
             */
            public void setReadTimeout(int timeoutMillis) {
                if (timeoutMillis < 0) {
                    throw new IllegalArgumentException("timeout can not be negative");
                }
                readTimeout = timeoutMillis;
            }

            /**
             * get paras map
             *
             * @return
             */
            public Map<String, String> getParasMap() {
                return parasMap;
            }

            /**
             * set paras map
             *
             * @param parasMap
             */
            public void setParasMap(Map<String, String> parasMap) {
                this.parasMap = parasMap;
            }

            /**
             * @return paras as string
             */
            public String getParas() {
                return joinParasWithEncodedValue(parasMap);
            }

            /**
             * @param field
             * @param newValue
             * @see URLConnection#setRequestProperty(String, String)
             */
            public void setRequestProperty(String field, String newValue) {
                requestProperties.put(field, newValue);
            }

            /**
             * @param field
             * @see URLConnection#getRequestProperty(String)
             */
            public String getRequestProperty(String field) {
                return requestProperties.get(field);
            }

            /**
             * same to { #setRequestProperty(String, String)} filed is User-Agent
             *
             * @param value
             * @see URLConnection#setRequestProperty(String, String)
             */
            public void setUserAgent(String value) {
                requestProperties.put("User-Agent", value);
            }

            /**
             * @return
             */
            public Map<String, String> getRequestProperties() {
                return requestProperties;
            }

            /**
             * @param requestProperties
             */
            public void setRequestProperties(Map<String, String> requestProperties) {
                this.requestProperties = requestProperties;
            }
        }

    }

    public static class String$ {

        public static String utf8Encode(String str) {
            if (!Empty.check(str) && str.getBytes().length != str.length()) {
                try {
                    return URLEncoder.encode(str, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException("UnsupportedEncodingException occurred. ", e);
                }
            }
            return str;
        }
    }


    public static class Resource {

        /**
         * get an asset using ACCESS_STREAMING mode. This provides access to files that have been bundled with an
         * application as assets -- that is, files placed in to the "assets" directory.
         *
         * @param context
         * @param fileName The name of the asset to open. This name can be hierarchical.
         * @return
         */
        public static String geFileFromAssets(Context context, String fileName) {
            if (context == null || Empty.check(fileName)) {
                return null;
            }

            StringBuilder s = new StringBuilder("");
            try {
                InputStreamReader in = new InputStreamReader(context.getResources().getAssets().open(fileName));
                BufferedReader br = new BufferedReader(in);
                String line;
                while ((line = br.readLine()) != null) {
                    s.append(line);
                }
                return s.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        /**
         * get content from a raw resource. This can only be used with resources whose value is the name of an asset files
         * -- that is, it can be used to open drawable, sound, and raw resources; it will fail on string and color
         * resources.
         *
         * @param context
         * @param resId   The resource identifier to open, as generated by the appt tool.
         * @return
         */
        public static String geFileFromRaw(Context context, int resId) {
            if (context == null) {
                return null;
            }

            StringBuilder s = new StringBuilder();
            try {
                InputStreamReader in = new InputStreamReader(context.getResources().openRawResource(resId));
                BufferedReader br = new BufferedReader(in);
                String line;
                while ((line = br.readLine()) != null) {
                    s.append(line);
                }
                return s.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        /**
         * same to { ResourceUtils#geFileFromAssets(Context, String)}, but return type is List(string)
         *
         * @param context
         * @param fileName
         * @return
         */
        public static List<String> geFileToListFromAssets(Context context, String fileName) {
            if (context == null || Empty.check(fileName)) {
                return null;
            }

            List<String> fileContent = new ArrayList<String>();
            try {
                InputStreamReader in = new InputStreamReader(context.getResources().getAssets().open(fileName));
                BufferedReader br = new BufferedReader(in);
                String line;
                while ((line = br.readLine()) != null) {
                    fileContent.add(line);
                }
                br.close();
                return fileContent;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        /**
         * same to { ResourceUtils#geFileFromRaw(Context, int)}, but return type is List(String)
         *
         * @param context
         * @param resId
         * @return
         */
        public static List<String> geFileToListFromRaw(Context context, int resId) {
            if (context == null) {
                return null;
            }

            List<String> fileContent = new ArrayList<String>();
            BufferedReader reader = null;
            try {
                InputStreamReader in = new InputStreamReader(context.getResources().openRawResource(resId));
                reader = new BufferedReader(in);
                String line = null;
                while ((line = reader.readLine()) != null) {
                    fileContent.add(line);
                }
                reader.close();
                return fileContent;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

    }

    public static class Shell {

        public static final String COMMAND_SU = "su";
        public static final String COMMAND_SH = "sh";
        public static final String COMMAND_EXIT = "exit\n";
        public static final String COMMAND_LINE_END = "\n";


        /**
         * check whether has root permission
         *
         * @return
         */
        public static boolean checkRootPermission() {
            return execCommand("echo root", true, false).result == 0;
        }

        /**
         * execute shell command, default return result msg
         *
         * @param command command
         * @param isRoot  whether need to run with root
         * @return
         * @see Shell#execCommand(String[], boolean, boolean)
         */
        public static CommandResult execCommand(String command, boolean isRoot) {
            return execCommand(new String[]{command}, isRoot, true);
        }

        /**
         * execute shell commands, default return result msg
         *
         * @param commands command list
         * @param isRoot   whether need to run with root
         * @return
         * @see Shell#execCommand(String[], boolean, boolean)
         */
        public static CommandResult execCommand(List<String> commands, boolean isRoot) {
            return execCommand(commands == null ? null : commands.toArray(new String[]{}), isRoot, true);
        }

        /**
         * execute shell commands, default return result msg
         *
         * @param commands command array
         * @param isRoot   whether need to run with root
         * @return
         * @see Shell#execCommand(String[], boolean, boolean)
         */
        public static CommandResult execCommand(String[] commands, boolean isRoot) {
            return execCommand(commands, isRoot, true);
        }

        /**
         * execute shell command
         *
         * @param command         command
         * @param isRoot          whether need to run with root
         * @param isNeedResultMsg whether need result msg
         * @return
         * @see Shell#execCommand(String[], boolean, boolean)
         */
        public static CommandResult execCommand(String command, boolean isRoot, boolean isNeedResultMsg) {
            return execCommand(new String[]{command}, isRoot, isNeedResultMsg);
        }

        /**
         * execute shell commands
         *
         * @param commands        command list
         * @param isRoot          whether need to run with root
         * @param isNeedResultMsg whether need result msg
         * @return
         * @see Shell#execCommand(String[], boolean, boolean)
         */
        public static CommandResult execCommand(List<String> commands, boolean isRoot, boolean isNeedResultMsg) {
            return execCommand(commands == null ? null : commands.toArray(new String[]{}), isRoot, isNeedResultMsg);
        }

        /**
         * execute shell commands
         *
         * @param commands        command array
         * @param isRoot          whether need to run with root
         * @param isNeedResultMsg whether need result msg
         * @return if isNeedResultMsg is false, { CommandResult#successMsg} is null and
         * { CommandResult#errorMsg} is null.
         * if { CommandResult#result} is -1, there maybe some excepiton.
         */
        public static CommandResult execCommand(String[] commands, boolean isRoot, boolean isNeedResultMsg) {
            int result = -1;
            if (commands == null || commands.length == 0) {
                return new CommandResult(result, null, null);
            }

            java.lang.Process process = null;
            BufferedReader successResult = null;
            BufferedReader errorResult = null;
            StringBuilder successMsg = null;
            StringBuilder errorMsg = null;

            DataOutputStream os = null;
            try {
                process = Runtime.getRuntime().exec(isRoot ? COMMAND_SU : COMMAND_SH);
                os = new DataOutputStream(process.getOutputStream());
                for (String command : commands) {
                    if (command == null) {
                        continue;
                    }

                    // donnot use os.writeBytes(commmand), avoid chinese charset error
                    os.write(command.getBytes());
                    os.writeBytes(COMMAND_LINE_END);
                    os.flush();
                }
                os.writeBytes(COMMAND_EXIT);
                os.flush();

                result = process.waitFor();
                // get command result
                if (isNeedResultMsg) {
                    successMsg = new StringBuilder();
                    errorMsg = new StringBuilder();
                    successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                    String s;
                    while ((s = successResult.readLine()) != null) {
                        successMsg.append(s);
                    }
                    while ((s = errorResult.readLine()) != null) {
                        errorMsg.append(s);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (os != null) {
                        os.close();
                    }
                    if (successResult != null) {
                        successResult.close();
                    }
                    if (errorResult != null) {
                        errorResult.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (process != null) {
                    process.destroy();
                }
            }
            return new CommandResult(result, successMsg == null ? null : successMsg.toString(), errorMsg == null ? null
                    : errorMsg.toString());
        }

        /**
         * result of command
         * <p>
         * { CommandResult#result} means result of command, 0 means normal, else means error, same to excute in
         * linux shell
         * { CommandResult#successMsg} means success message of command result
         * { CommandResult#errorMsg} means error message of command result
         *
         * @author 2013-5-16
         */
        public static class CommandResult {

            /**
             * result of command
             **/
            public int result;
            /**
             * success message of command result
             **/
            public String successMsg;
            /**
             * error message of command result
             **/
            public String errorMsg;

            public CommandResult(int result) {
                this.result = result;
            }

            public CommandResult(int result, String successMsg, String errorMsg) {
                this.result = result;
                this.successMsg = successMsg;
                this.errorMsg = errorMsg;
            }
        }

    }

    public static class Process{

        /**
         * 获取进程名
         *
         * @return
         */
        public static String getProcessName(Context context) {
            String processName = null;
            ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
            List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
            int myPid = android.os.Process.myPid();
            for (ActivityManager.RunningAppProcessInfo info : processInfos) {
                if (info.pid == myPid) {
                    processName = info.processName;
                    break;
                }
            }
            return processName;
        }
    }

}
