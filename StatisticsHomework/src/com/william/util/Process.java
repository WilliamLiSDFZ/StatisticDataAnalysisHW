package com.william.util;

import java.io.IOException;
import java.util.List;

/**
 * @author WilliamLi
 * @version: 1.0
 * @date 2022/5/25 22:17
 */
public class Process {

    private FileHelper fileHelper;

    private List<String> dataList = null;

    /**
     * 无参数构造方法，构建同时读取并缓存文件数据。
     */
    public Process() {
        fileHelper = new FileHelper(".\\src\\com\\william\\file\\data.txt");
        List<String> strings = null;
        try {
            strings = fileHelper.bufferFileList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dataList = strings;
    }

    /**
     * 数据解析方法，用于将缓存后的数据拆分成二维数组。
     *
     * @return 数据字符串数组
     */
    public String[][] analyse_Data() {

        String[][] result = new String[dataList.size()][17];

        for (int i = 0; i < result.length; i++) {
            String[] tempList = dataList.get(i).split("\t");
            result[i] = tempList;
        }

        return result;
    }
}
