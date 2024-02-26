package com.madhu.util;


import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import com.madhu.entity.ExcelData;

public class Utility {
	
	
    public static void applyUpdates(ExcelData existingExcelData, Map<String, Object> updates) {
        BeanUtils.copyProperties(updates, existingExcelData, getNullPropertyNames(updates));
    }

    public static String[] getNullPropertyNames(Map<String, Object> source) {
        Set<String> nullPropertyNames = new HashSet<>();
        for (Map.Entry<String, Object> entry : source.entrySet()) {
            if (entry.getValue() == null) {
                nullPropertyNames.add(entry.getKey());
            }
        }
        return nullPropertyNames.toArray(new String[0]);
    }

}
