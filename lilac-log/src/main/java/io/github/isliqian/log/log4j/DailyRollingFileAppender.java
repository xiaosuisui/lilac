package io.github.isliqian.log.log4j;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.CountingQuietWriter;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.LoggingEvent;

public class DailyRollingFileAppender extends FileAppender {
    static final int TOP_OF_TROUBLE = -1;
    static final int TOP_OF_MINUTE = 0;
    static final int TOP_OF_HOUR = 1;
    static final int HALF_DAY = 2;
    static final int TOP_OF_DAY = 3;
    static final int TOP_OF_WEEK = 4;
    static final int TOP_OF_MONTH = 5;
    protected long maxFileSize = 10485760L;
    protected int maxBackupIndex = 1;
    protected boolean dirRolling = true;
    private String datePattern = "'.'yyyy-MM-dd";
    private String scheduledFilename;
    private long nextCheck = System.currentTimeMillis() - 1L;
    Date now = new Date();
    SimpleDateFormat sdf;
    SimpleDateFormat dirSdf;
    RollingCalendar rc = new RollingCalendar();
    int checkPeriod = -1;
    static final TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");

    public DailyRollingFileAppender() {
    }

    public DailyRollingFileAppender(Layout layout, String filename, String datePattern) throws IOException {
        super(layout, filename, true);
        this.datePattern = datePattern;
        this.activateOptions();
    }

    public long getMaximumFileSize() {
        return this.maxFileSize;
    }

    public void setMaximumFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public void setMaxFileSize(String value) {
        this.maxFileSize = OptionConverter.toFileSize(value, this.maxFileSize + 1L);
    }

    public int getMaxBackupIndex() {
        return this.maxBackupIndex;
    }

    public void setMaxBackupIndex(int maxBackups) {
        this.maxBackupIndex = maxBackups;
    }

    public void setDirRolling(boolean dirRolling) {
        this.dirRolling = dirRolling;
    }

    public boolean getDirRolling() {
        return this.dirRolling;
    }

    public void setDatePattern(String pattern) {
        this.datePattern = pattern;
    }

    public String getDatePattern() {
        return this.datePattern;
    }

    public void activateOptions() {
        super.activateOptions();
        this.dirSdf = new SimpleDateFormat("yyyy-MM-dd");
        if (this.datePattern != null && this.fileName != null) {
            this.now.setTime(System.currentTimeMillis());
            this.sdf = new SimpleDateFormat(this.datePattern);
            int type = this.computeCheckPeriod();
            this.printPeriodicity(type);
            this.rc.setType(type);
            File file = new File(this.fileName);
            this.scheduledFilename = this.fileName + this.sdf.format(new Date(file.lastModified()));
        } else {
            LogLog.error("Either File or DatePattern options are not set for appender [" + this.name + "].");
        }

    }

    void printPeriodicity(int type) {
        switch(type) {
            case 0:
                LogLog.debug("Appender [" + this.name + "] to be rolled every minute.");
                break;
            case 1:
                LogLog.debug("Appender [" + this.name + "] to be rolled on top of every hour.");
                break;
            case 2:
                LogLog.debug("Appender [" + this.name + "] to be rolled at midday and midnight.");
                break;
            case 3:
                LogLog.debug("Appender [" + this.name + "] to be rolled at midnight.");
                break;
            case 4:
                LogLog.debug("Appender [" + this.name + "] to be rolled at start of week.");
                break;
            case 5:
                LogLog.debug("Appender [" + this.name + "] to be rolled at start of every month.");
                break;
            default:
                LogLog.warn("Unknown periodicity for appender [" + this.name + "].");
        }

    }

    int computeCheckPeriod() {
        RollingCalendar rollingCalendar = new RollingCalendar(gmtTimeZone, Locale.ENGLISH);
        Date epoch = new Date(0L);
        if (this.datePattern != null) {
            for(int i = 0; i <= 5; ++i) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.datePattern);
                simpleDateFormat.setTimeZone(gmtTimeZone);
                String r0 = simpleDateFormat.format(epoch);
                rollingCalendar.setType(i);
                Date next = new Date(rollingCalendar.getNextCheckMillis(epoch));
                String r1 = simpleDateFormat.format(next);
                if (r0 != null && r1 != null && !r0.equals(r1)) {
                    return i;
                }
            }
        }

        return -1;
    }

    public void sizeRollOver() {
        LogLog.debug("rolling over count=" + ((CountingQuietWriter)this.qw).getCount());
        LogLog.debug("maxBackupIndex=" + this.maxBackupIndex);
        String datedFilename = this.fileName + this.sdf.format(this.now);
        int i;
        if (this.dirRolling) {
            i = this.fileName.lastIndexOf("/");
            if (i >= 0) {
                datedFilename = this.fileName.substring(0, i) + "/" + this.dirSdf.format(this.now);
                File dir = new File(datedFilename);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                datedFilename = datedFilename + "/" + this.fileName.substring(i + 1, this.fileName.length());
            }
        }

        File target;
        File file;
        if (this.maxBackupIndex > 0) {
            file = new File(datedFilename + '.' + this.maxBackupIndex);
            if (file.exists()) {
                file.delete();
            }

            for(i = this.maxBackupIndex - 1; i >= 1; --i) {
                file = new File(datedFilename + "." + i);
                if (file.exists()) {
                    target = new File(datedFilename + '.' + (i + 1));
                    LogLog.debug("Renaming file " + file + " to " + target);
                    file.renameTo(target);
                }
            }

            target = new File(datedFilename + "." + 1);
            this.closeFile();
            file = new File(this.fileName);
            LogLog.debug("Renaming file " + file + " to " + target);
            file.renameTo(target);
        } else if (this.maxBackupIndex < 0) {
            for(i = 1; i < 2147483647; ++i) {
                target = new File(datedFilename + "." + i);
                if (!target.exists()) {
                    this.closeFile();
                    file = new File(this.fileName);
                    file.renameTo(target);
                    LogLog.debug("Renaming file " + file + " to " + target);
                    break;
                }
            }
        }

        try {
            this.setFile(this.fileName, false, this.bufferedIO, this.bufferSize);
        } catch (IOException var6) {
            LogLog.error("setFile(" + this.fileName + ", false) call failed.", var6);
        }

        this.scheduledFilename = datedFilename;
    }

    public synchronized void setFile(String fileName, boolean append, boolean bufferedIO, int bufferSize) throws IOException {
        super.setFile(fileName, append, this.bufferedIO, this.bufferSize);
        if (append) {
            File f = new File(fileName);
            ((CountingQuietWriter)this.qw).setCount(f.length());
        }

    }

    protected void setQWForFiles(Writer writer) {
        this.qw = new CountingQuietWriter(writer, this.errorHandler);
    }

    void timeRollOver() throws IOException {
        if (this.datePattern == null) {
            this.errorHandler.error("Missing DatePattern option in rollOver().");
        } else {
            String datedFilename = this.fileName + this.sdf.format(this.now);
            if (!this.scheduledFilename.equals(datedFilename)) {
                this.closeFile();
                File dir;
                if (this.dirRolling) {
                    int ix = this.fileName.lastIndexOf("/");
                    if (ix >= 0) {
                        this.scheduledFilename = this.fileName.substring(0, ix) + "/" + this.dirSdf.format(this.now);
                        dir = new File(this.scheduledFilename);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }

                        this.scheduledFilename = this.scheduledFilename + "/" + this.fileName.substring(ix + 1, this.fileName.length());
                    }
                }

                File target = new File(this.scheduledFilename);
                if (target.exists()) {
                    target.delete();
                }

                dir = new File(this.fileName);
                boolean result = dir.renameTo(target);
                if (result) {
                    LogLog.debug(this.fileName + " -> " + this.scheduledFilename);
                } else {
                    LogLog.error("Failed to rename [" + this.fileName + "] to [" + this.scheduledFilename + "].");
                }

                try {
                    super.setFile(this.fileName, false, this.bufferedIO, this.bufferSize);
                } catch (IOException var6) {
                    this.errorHandler.error("setFile(" + this.fileName + ", false) call failed.");
                }

                this.scheduledFilename = datedFilename;
            }
        }
    }

    protected void subAppend(LoggingEvent event) {
        long n = System.currentTimeMillis();
        if (n >= this.nextCheck) {
            this.now.setTime(n);
            this.nextCheck = this.rc.getNextCheckMillis(this.now);

            try {
                this.timeRollOver();
            } catch (IOException var5) {
                LogLog.error("rollOver() failed.", var5);
            }
        } else if (this.fileName != null && ((CountingQuietWriter)this.qw).getCount() >= this.maxFileSize) {
            this.sizeRollOver();
        }

        super.subAppend(event);
    }
}
