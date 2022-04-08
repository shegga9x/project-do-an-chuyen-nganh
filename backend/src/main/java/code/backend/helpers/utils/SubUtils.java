package code.backend.helpers.utils;

import java.util.Arrays;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import code.backend.helpers.advice.CustomException;
import code.backend.persitence.model.UserDetailCustom;

public class SubUtils {

    public static boolean isNullOrBlank(String param) {
        return param == null || param.trim().length() == 0;
    }

    public static Object mapperObject(Object from, Object to) {
        try {
            if (from == null) {
                return null;
            }
            BeanUtils.copyProperties(to, from);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("Error Mapper");
        }
        return to;
    }

    public static String removeBracketsFromArray(String[] array) {
        return Arrays.toString(array).replace(",", "").replace("[", "").replace("]", "").trim();
    }

    public static UserDetailCustom getCurrentUser() {
        return (UserDetailCustom) SecurityContextHolder.getContext()
        .getAuthentication()
        .getPrincipal();
    }


}
