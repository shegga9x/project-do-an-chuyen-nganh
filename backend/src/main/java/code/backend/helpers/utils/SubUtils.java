package code.backend.helpers.utils;

import org.apache.commons.beanutils.BeanUtils;

import code.backend.helpers.advice.CustomException;

public class SubUtils {

    public static boolean isNullOrBlank(String param) {
        return param == null || param.trim().length() == 0;
    }



    public static Object mapperObject(Object from, Object to) {
        try {
            BeanUtils.copyProperties(to, from);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("Error Mapper");
        }
        return to;
    }

}
