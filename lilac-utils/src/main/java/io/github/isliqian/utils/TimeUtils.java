package io.github.isliqian.utils;

import java.util.Arrays;
import java.util.Date;
import org.apache.commons.lang3.time.DateFormatUtils;

public class TimeUtils {
    public static final int SECOND = 0;
    public static final int MINUTE = 1;
    public static final int HOUR = 2;
    public static final int DAY = 3;
    private final int[] maxFields;
    private final int[] minFields;
    private String timeSeparator;
    private int[] fields;

    public static String toTimeString(long time) {
        TimeUtils t = new TimeUtils(time);
        int day = t.get(3);
        int hour = t.get(2);
        int minute = t.get(1);
        int second = t.get(0);
        StringBuilder sb = new StringBuilder();
        if (day > 0) {
            sb.append(day).append("天");
        }

        if (hour > 0) {
            sb.append(hour).append("时");
        }

        if (minute > 0) {
            sb.append(minute).append("分");
        }

        if (second > 0) {
            sb.append(second).append("秒");
        }

        return sb.toString();
    }

    public TimeUtils() {
        this(0, 0, 0, 0);
    }

    public TimeUtils(int hour, int minute) {
        this(0, hour, minute, 0);
    }

    public TimeUtils(int hour, int minute, int second) {
        this(0, hour, minute, second);
    }

    public TimeUtils(String time) {
        this(time, (String)null);
    }

    public TimeUtils(long time) {
        this(new Date(time));
    }

    public TimeUtils(Date date) {
        this(DateFormatUtils.formatUTC(date, "HH:mm:ss"));
    }

    public TimeUtils(int day, int hour, int minute, int second) {
        this.maxFields = new int[]{59, 59, 23, 2147483646};
        this.minFields = new int[]{0, 0, 0, -2147483648};
        this.timeSeparator = ":";
        this.fields = new int[4];
        this.initialize(day, hour, minute, second);
    }

    public TimeUtils(String time, String timeSeparator) {
        this.maxFields = new int[]{59, 59, 23, 2147483646};
        this.minFields = new int[]{0, 0, 0, -2147483648};
        this.timeSeparator = ":";
        this.fields = new int[4];
        if (timeSeparator != null) {
            this.setTimeSeparator(timeSeparator);
        }

        this.parseTime(time);
    }

    public void set(int field, int value) {
        if (value < this.minFields[field]) {
            throw new IllegalArgumentException(value + ", time value must be positive.");
        } else {
            this.fields[field] = value % (this.maxFields[field] + 1);
            int carry = value / (this.maxFields[field] + 1);
            if (carry > 0) {
                int upFieldValue = this.get(field + 1);
                this.set(field + 1, upFieldValue + carry);
            }

        }
    }

    public int get(int field) {
        if (field >= 0 && field <= this.fields.length - 1) {
            return this.fields[field];
        } else {
            throw new IllegalArgumentException(field + ", field value is error.");
        }
    }

    public TimeUtils addTime(TimeUtils time) {
        TimeUtils result = new TimeUtils();
        int up = 0;

        for(int i = 0; i < this.fields.length; ++i) {
            int sum = this.fields[i] + time.fields[i] + up;
            up = sum / (this.maxFields[i] + 1);
            result.fields[i] = sum % (this.maxFields[i] + 1);
        }

        return result;
    }

    public TimeUtils subtractTime(TimeUtils time) {
        TimeUtils result = new TimeUtils();
        int down = 0;
        int i = 0;

        for(int k = this.fields.length - 1; i < k; ++i) {
            int difference = this.fields[i] + down;
            if (difference >= time.fields[i]) {
                difference -= time.fields[i];
                down = 0;
            } else {
                difference += this.maxFields[i] + 1 - time.fields[i];
                down = -1;
            }

            result.fields[i] = difference;
        }

        result.fields[3] = this.fields[3] - time.fields[3] + down;
        return result;
    }

    public String getTimeSeparator() {
        return this.timeSeparator;
    }

    public void setTimeSeparator(String timeSeparator) {
        this.timeSeparator = timeSeparator;
    }

    private void initialize(int day, int hour, int minute, int second) {
        this.set(3, day);
        this.set(2, hour);
        this.set(1, minute);
        this.set(0, second);
    }

    private void parseTime(String time) {
        if (time == null) {
            this.initialize(0, 0, 0, 0);
        } else {
            String t = time;
            int field = 3;
            int var5 = field - 1;
            this.set(field, 0);

            int p;
            for(boolean var4 = true; (p = t.indexOf(this.timeSeparator)) > -1; t = t.substring(p + this.timeSeparator.length())) {
                this.parseTimeField(time, t.substring(0, p), var5--);
            }

            this.parseTimeField(time, t, var5--);
        }
    }

    private void parseTimeField(String time, String t, int field) {
        if (field < 0 || t.length() < 1) {
            this.parseTimeException(time);
        }

        char[] chs = t.toCharArray();
        int n = 0;

        for(int i = 0; i < chs.length; ++i) {
            if (chs[i] > ' ') {
                if (chs[i] >= '0' && chs[i] <= '9') {
                    n = n * 10 + chs[i] - 48;
                } else {
                    this.parseTimeException(time);
                }
            }
        }

        this.set(field, n);
    }

    private void parseTimeException(String time) {
        throw new IllegalArgumentException(time + ", time format error, HH" + this.timeSeparator + "mm" + this.timeSeparator + "ss");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(16);
        sb.append(this.fields[3]).append(',').append(' ');
        this.buildString(sb, 2).append(this.timeSeparator);
        this.buildString(sb, 1).append(this.timeSeparator);
        this.buildString(sb, 0);
        return sb.toString();
    }

    private StringBuilder buildString(StringBuilder sb, int field) {
        if (this.fields[field] < 10) {
            sb.append('0');
        }

        return sb.append(this.fields[field]);
    }

    public int hashCode() {
        int result = 1;
        result = 31 * result + Arrays.hashCode(this.fields);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            TimeUtils other = (TimeUtils)obj;
            return Arrays.equals(this.fields, other.fields);
        }
    }
}

