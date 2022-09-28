package com.hiepnh.nftmarket.accountsvc.domain.helper;

import com.google.common.base.Strings;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtils {

    public static final SimpleDateFormat PART_FORMAT = new SimpleDateFormat("yyyyMMdd");
    static final String regexEmail = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final String USERNAME_PATTERN = "^[A-Za-z0-9_]{4,30}$";
    private static final String FULLNAME_PATTERN = "^[a-zA-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+$";
    private static final String FTTH_PATTERN = "^[a-z0-9_]{9,50}$";
    static String regexURL = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    private static final Random rand = new Random();

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

    public static String MD5(String md5) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuilder sb = new StringBuilder();
            byte[] var4 = array;
            int var5 = array.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                byte b = var4[var6];
                sb.append(Integer.toHexString(b & 255 | 256), 1, 3);
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException var8) {
            return null;
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
        return !Strings.isNullOrEmpty(fullName) && fullName.matches("^[a-zA-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+$");
    }

    public static boolean validatePhone(String phone) {
        String regex = "(09|03|05|08|07)[0-9]{8}";
        return !Strings.isNullOrEmpty(phone) && phone.matches(regex);
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

    public static boolean validateEmail(String email) {
        if (Strings.isNullOrEmpty(email)) {
            return false;
        } else {
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }
    }

}
