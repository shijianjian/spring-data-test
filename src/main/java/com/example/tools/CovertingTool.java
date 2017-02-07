package com.example.tools;

/**
 * Created by shijian on 07/02/2017.
 */
public class CovertingTool {

    public static String[] ObjectArraryToStringArray(Object[] objects){
        int size = objects.length;
        String [] strings = new String[size];

        for (int i = 0; i < size; i++)
            strings[i] = objects[i].toString();
        return strings;
    }

    public static String StringArrayToString(String[] strings) {
        StringBuffer sb = new StringBuffer();
        for(String string : strings) {
            sb.append("," + string);
        }
        sb.deleteCharAt(0);
        return sb.toString();
    }

}
