package io.github.isliqian.utils;


import com.google.common.collect.Maps;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;


import org.springframework.core.io.DefaultResourceLoader;

public class Global {
    private static Global global = new Global();
    private static Map<String, String> map = Maps.newHashMap();
    private static PropertiesLoader loader = new PropertiesLoader(new String[]{"luma.properties"});
    public static final String SHOW = "1";
    public static final String HIDE = "0";
    public static final String YES = "1";
    public static final String NO = "0";
    public static final String TRUE = "true";
    public static final String FALSE = "false";
    public static final String USERFILES_BASE_URL = "/userfiles/";

    public Global() {
    }

    public static Global getInstance() {
        return global;
    }

    public static String getConfig(String key) {
        String value = (String)map.get(key);
        if (value == null) {
            value = loader.getProperty(key);
            map.put(key, value != null ? value : "");
        }

        return value;
    }

    public static String getAdminPath() {
        return getConfig("adminPath");
    }

    public static String getFrontPath() {
        return getConfig("frontPath");
    }

    public static String getUrlSuffix() {
        return getConfig("urlSuffix");
    }

    public static Boolean isDemoMode() {
        String dm = getConfig("demoMode");
        return "true".equals(dm) || "1".equals(dm);
    }

    public static Boolean isSynActivitiIndetity() {
        String dm = getConfig("activiti.isSynActivitiIndetity");
        return "true".equals(dm) || "1".equals(dm);
    }

    public static Object getConst(String field) {
        try {
            return Global.class.getField(field).get((Object)null);
        } catch (Exception var2) {
            return null;
        }
    }

    public static String getUserfilesBaseDir() {
        String dir = getConfig("userfiles.basedir");
        if (StringUtils.isBlank(dir)) {
            try {
                dir = "/dir";
            } catch (Exception var2) {
                return "";
            }
        }

        if (!dir.endsWith("/")) {
            dir = dir + "/";
        }

        return dir;
    }

    public static String getProjectPath() {
        String projectPath = getConfig("projectPath");
        if (StringUtils.isNotBlank(projectPath)) {
            return projectPath;
        } else {
            try {
                File file = (new DefaultResourceLoader()).getResource("").getFile();
                if (file != null) {
                    while(true) {
                        File f = new File(file.getPath() + File.separator + "src_core");
                        if (f == null || f.exists() || file.getParentFile() == null) {
                            projectPath = file.toString().replace(File.separatorChar + "src_code", "");
                            break;
                        }

                        file = file.getParentFile();
                    }
                }
            } catch (IOException var3) {
                var3.printStackTrace();
            }

            return projectPath;
        }
    }

    public static void updateConfig(String key, String value) {
        map.put(key, value);
        loader.getProperties().setProperty(key, value);
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(new File(Global.class.getClassLoader().getResource(File.separator).getPath() + "luma.properties"));
            loader.getProperties().store(fos, "更新" + key);
        } catch (FileNotFoundException var4) {
            var4.printStackTrace();
        } catch (IOException var5) {
            var5.printStackTrace();
        }

    }
}

