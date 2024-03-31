package com.android.quizapp.dao;

/**
 * Created by Aashis on 31,March,2024
 */
import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Converters {
    @TypeConverter
    public static String fromList(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String item : list) {
            sb.append(item).append(",");
        }
        return sb.toString();
    }

    @TypeConverter
    public static List<String> toList(String data) {
        return new ArrayList<>(Arrays.asList(data.split(",")));
    }
}
