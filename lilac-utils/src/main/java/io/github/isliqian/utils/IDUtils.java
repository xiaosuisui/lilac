package io.github.isliqian.utils;

import java.util.UUID;

public class IDUtils {

    /**	 id生成	 */
    public static String getId() {
        String id= UUID.randomUUID().toString();
        //替换uuid中的"-"
        id=id.replace("-", "");
        return id;
    }
    public static void main(String[] args) {
        for(int i=0;i< 100;i++)
            System.out.println(getId());
    }


}
