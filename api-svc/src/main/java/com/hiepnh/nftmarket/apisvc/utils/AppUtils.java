package com.hiepnh.nftmarket.apisvc.utils;


import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtils {

    private final static Logger logger = LoggerFactory.getLogger(AppUtils.class);
    public static final SimpleDateFormat PART_FORMAT = new SimpleDateFormat("yyyyMMdd");
    static final String regexEmail = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final String USERNAME_PATTERN = "^[A-Za-z0-9_]{4,30}$";
    private static final String FULLNAME_PATTERN = "^[a-zA-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+$";
    private static final String FTTH_PATTERN = "^[a-z0-9_]{9,50}$";
    static String regexURL = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    private static Random rand = new Random();

    public AppUtils() {
    }

    public static int randInt(int min, int max) {
        return rand.nextInt(max - min + 1) + min;
    }

    public static Date stringToDate(String date, String pattern) {
        if (Strings.isNullOrEmpty(date)) {
            return new Date();
        } else {
            Date date1;
            try {
                date1 = (new SimpleDateFormat(pattern)).parse(date);
            } catch (Exception var4) {
                date1 = new Date();
            }

            return date1;
        }
    }

    public static Date stringToDate2(String date, String pattern) {
        if (Strings.isNullOrEmpty(date)) {
            return null;
        } else {
            Date date1;
            try {
                date1 = (new SimpleDateFormat(pattern)).parse(date);
            } catch (Exception var4) {
                date1 = null;
            }

            return date1;
        }
    }

    public static int formatDate(Date date) {
        return formatDate(date, "yyyyMMdd");
    }

    public static int formatDate(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return parseInt(simpleDateFormat.format(date));
    }

    public static String formatDateToString(Date date) {
        return formatDateToString(date, "dd/MM/yyyy HH:mm:ss");
    }

    public static String formatDateToString(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public static String formatLongToString(Long time, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }

    public static String parseString(Object obj) {
        if (obj == null) {
            return "";
        } else {
            try {
                return String.valueOf(obj);
            } catch (Exception var2) {
                return "";
            }
        }
    }


    public static long parseLong(Object o) {
        if (o == null) {
            return 0L;
        } else if (o instanceof Double) {
            return ((Double) o).longValue();
        } else if (o instanceof Float) {
            return ((Float) o).longValue();
        } else {
            try {
                return Long.parseLong(String.valueOf(o));
            } catch (Exception var2) {
                return 0L;
            }
        }
    }

    public static int parseInt(Object o) {
        if (o == null) {
            return 0;
        } else if (o instanceof Double) {
            return ((Double) o).intValue();
        } else if (o instanceof Float) {
            return ((Float) o).intValue();
        } else {
            try {
                return Integer.parseInt(String.valueOf(o));
            } catch (Exception var2) {
                return 0;
            }
        }
    }

    public static int parseInt2(Object o) {
        if (o == null) {
            return 0;
        } else if (o instanceof Double) {
            return ((Double) o).intValue();
        } else if (o instanceof Float) {
            return ((Float) o).intValue();
        } else {
            try {
                String str = String.valueOf(o);
                String[] splitStr = str.split("\\.");
                return Integer.parseInt(splitStr[0]);
            } catch (Exception var2) {
                return 0;
            }
        }
    }

    public static List<String> parseStringList(Object o) {
        if (o == null) {
            return new ArrayList<>();
        }
        try {
            return (List<String>) o;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public static double parseDouble(Object o) {
        if (o == null) {
            return 0.0D;
        } else {
            try {
                return Double.parseDouble(String.valueOf(o));
            } catch (Exception var2) {
                return 0.0D;
            }
        }
    }

    public static Boolean parseBoolean(Object o){
        if(o == null){
            return false;
        }else {
            try {
                return parseString(o).equals("true");
            }catch (Exception ex){
                return false;
            }
        }
    }

    public static Map<String, Object> parseMap(Map<String, Object> map, String key){
        if(map == null){
            return new HashMap<>();
        }
        try{
            return (Map<String, Object>) map.get(key);
        }catch (Exception ex){
            return new HashMap<>();
        }
    }


    //kiểm tra username
    public static boolean validateUsername(String username) {
        if (Strings.isNullOrEmpty(username)) {
            return false;
        } else {
            Pattern pattern = Pattern.compile("^[A-Za-z0-9_]{4,30}$");
            Matcher matcher = pattern.matcher(username);
            return matcher.matches();
        }
    }

    //kiểm tra password
    public static boolean validateFullName(String fullName) {
        return Strings.isNullOrEmpty(fullName) ? false : fullName.matches("^[a-zA-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+$");
    }

    public static boolean validatePhone(String phone) {
        String regex = "(09|03|05|08|07)[0-9]{8}";
        return Strings.isNullOrEmpty(phone) ? false : phone.matches(regex);
    }

    public static boolean validateUrl(String url) {
        if (Strings.isNullOrEmpty(url)) {
            return false;
        } else {
            Pattern pattern = Pattern.compile(regexURL);
            Matcher matcher = pattern.matcher(url);
            return matcher.matches();
        }
    }

    public static boolean checkNormalIsdn(String ftth) {
        if (Strings.isNullOrEmpty(ftth)) {
            return false;
        } else {
            Pattern pattern = Pattern.compile("^[a-z0-9_]{9,50}$");
            Matcher matcher = pattern.matcher(ftth);
            return matcher.matches();
        }
    }

    public static boolean checkNormalString(String ftth) {
        if (Strings.isNullOrEmpty(ftth)) {
            return false;
        } else {
            Pattern pattern = Pattern.compile("[a-zA-Z0-9_-]{1,50}");
            Matcher matcher = pattern.matcher(ftth);
            return matcher.matches();
        }
    }

    public static boolean checkNormalPath(String ftth) {
        if (Strings.isNullOrEmpty(ftth)) {
            return false;
        } else {
            String str = ftth.replaceAll("/","");
            Pattern pattern = Pattern.compile("[a-zA-Z0-9_*-]{1,50}");
            Matcher matcher = pattern.matcher(str);
            return matcher.matches();
        }
    }

    public static boolean validateEmail(String email) {
        if (Strings.isNullOrEmpty(email)) {
            return false;
        } else {
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }
    }

    public static String buildUrlConnectionMysql(String server, int port, String database) {
        StringBuilder sb = new StringBuilder();
        sb.append("jdbc:mysql://");
        sb.append(server);
        sb.append(":");
        sb.append(port);
        sb.append("/");
        sb.append(database);
        return sb.toString();
    }

    public static String buildUrlConnectionSqlServer(String server, int port, String database) {
        StringBuilder sb = new StringBuilder();
        sb.append("jdbc:sqlserver://");
        sb.append(server);
        sb.append(":");
        sb.append(port);
        sb.append(";databaseName=");
        sb.append(database);
        return sb.toString();
    }

    public static String buildUrlConnectionPostgresSql(String server, int port, String database) {
        StringBuilder sb = new StringBuilder();
        sb.append("jdbc:postgresql://")
                .append(server)
                .append(":")
                .append(port)
                .append("/")
                .append(database);
        return sb.toString();
    }

    public static String buildUrlConnectionOracle(String server, int port, String database, String username, String password) {
        StringBuilder sb = new StringBuilder();
        sb.append("jdbc:oracle://:thin:@")
                .append(server)
                .append(":")
                .append(port)
                .append(":")
                .append(database);
        return sb.toString();
    }

    public static String createApplicationName(String username) {
        return username + "-app";
    }


    public static String createUniqueFileName(String filename) {
        if (Strings.isNullOrEmpty(filename)) {
            return "";
        }
        String[] splitStr = filename.split("\\.");
        if (splitStr.length != 2) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        sb.append(splitStr[0])
                .append("_")
                .append(System.currentTimeMillis())
                .append(".")
                .append(splitStr[1]);
        return sb.toString();
    }

    public static String calculateLastUpdateTime(long updateTime) {
        long currTime = System.currentTimeMillis();
        Calendar currCalendar = Calendar.getInstance();
        currCalendar.setTimeInMillis(currTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(updateTime);
        if (currTime - updateTime < 60_000) {
            return "1 phút trước";
        }
        if (currCalendar.get(Calendar.YEAR) != calendar.get(Calendar.YEAR)) {
            int differentYear = currCalendar.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
            if (differentYear != 1) {
                return differentYear + " năm trước";
            }
            int differentMonth = currCalendar.get(Calendar.MONTH) - calendar.get(Calendar.MONTH);
            if (differentMonth >= 0) {
                return "1 năm trước";
            } else {
                differentMonth = 12 + differentMonth;
                return differentMonth + " tháng trước";
            }
        }
        if (currCalendar.get(Calendar.MONTH) != calendar.get(Calendar.MONTH)) {
            int differentMonth = currCalendar.get(Calendar.MONTH) - calendar.get(Calendar.MONTH);
            if (differentMonth != 1) {
                return differentMonth + " tháng trước";
            }
            int differentDay = currCalendar.get(Calendar.DAY_OF_YEAR) - calendar.get(Calendar.DAY_OF_YEAR);
            if (differentDay >= 0) {
                return "1 tháng trước";
            } else {
                differentDay = getDayOfMonth(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)) + differentDay;
                return differentDay + " ngày trước";
            }
        }
        if (currCalendar.get(Calendar.DAY_OF_YEAR) != calendar.get(Calendar.DAY_OF_YEAR)) {
            int differentDay = currCalendar.get(Calendar.DAY_OF_YEAR) - calendar.get(Calendar.DAY_OF_YEAR);
            if (differentDay != 1) {
                return differentDay + " ngày trước";
            }
            int differentHour = currCalendar.get(Calendar.HOUR) - calendar.get(Calendar.HOUR);
            if (differentHour >= 0) {
                return "1 ngày trước";
            } else {
                differentHour = 24 + differentHour;
                return differentHour + " giờ trước";
            }
        }
        if (currCalendar.get(Calendar.HOUR) != calendar.get(Calendar.HOUR)) {
            int differentHour = currCalendar.get(Calendar.HOUR) - calendar.get(Calendar.HOUR);
            if (differentHour < 0) {
                differentHour += 12;
            }
            if (differentHour != 1) {
                return differentHour + " giờ trước";
            }
            int differentMinute = currCalendar.get(Calendar.MINUTE) - calendar.get(Calendar.MINUTE);
            if (differentMinute > 0) {
                return "1 giờ trước";
            } else {
                differentMinute = 60 + differentMinute;
                return differentMinute + " phút trước";
            }
        }
        int differentMinute = currCalendar.get(Calendar.MINUTE) - calendar.get(Calendar.MINUTE);
        return differentMinute + " phút trước";
    }

    private static int getDayOfMonth(int month, int year) {
        switch (month) {
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                return year % 4 == 0 ? 29 : 28;
            default:
                return 31;
        }
    }

    public static String generateUuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static String convertTimeToStringMessage(long time){
        long currTime = System.currentTimeMillis();
        String rs;
        Calendar currCalendar = Calendar.getInstance();
        currCalendar.setTimeInMillis(currTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        if(currCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
                && currCalendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)
                && currCalendar.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)){
            SimpleDateFormat format = new SimpleDateFormat("hh:mm aa");
            rs = format.format(calendar.getTime());
        }else if(currCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)){
            if(currCalendar.get(Calendar.WEEK_OF_YEAR) == calendar.get(Calendar.WEEK_OF_YEAR)){
                SimpleDateFormat format = new SimpleDateFormat("EEE");
                rs = format.format(calendar.getTime());
            }else {
                SimpleDateFormat format = new SimpleDateFormat("dd MMM");
                rs = format.format(calendar.getTime());
            }
        }else {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            rs = format.format(calendar.getTime());
        }
        return rs;
    }
}
