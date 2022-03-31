package code.backend.helpers.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import code.backend.helpers.advice.CustomException;

public class SubUtils {

    public static boolean isNullOrBlank(String param) {
        return param == null || param.trim().length() == 0;
    }

    public static List<?> mapperList(List<?> list, Object objectToReturn) {
        List<Object> listRespone = new ArrayList<>();
        for (Object object2 : list) {
            try {
                BeanUtils.copyProperties(objectToReturn, object2);
                listRespone.add(objectToReturn);
            } catch (Exception e) {
                throw new CustomException("Error Mapper");
            }
        }
        return listRespone;
    }

    public static Object mapperObject(Object from, Object to) {
        try {
            BeanUtils.copyProperties(to, from);
        } catch (Exception e) {
            throw new CustomException("Error Mapper");
        }
        return to;
    }

}
