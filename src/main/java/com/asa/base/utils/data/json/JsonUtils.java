package com.asa.base.utils.data.json;


import com.asa.base.utils.data.list.ListUtils;
import com.asa.base.utils.data.string.StringUtils;
import org.json.JSONArray;

import java.util.List;

/**
 * Created by andrew_asa on 2017/10/11.
 */
public class JsonUtils {

    public static final String EMPTYARRAY = "[]";

    public static String makeSureNotNullArrayStr(String str) {

        if (StringUtils.isEmpty(str)) {
            return EMPTYARRAY;
        }
        return str;
    }

    public static JSONArray listToJsonArray(List<Object> list) {

        JSONArray ret = JSONArray.create();
        if (ListUtils.isNotEmptyList(list)) {
            for (Object o : list) {
                ret.put(o);
            }
        }
        return ret;
    }

}
