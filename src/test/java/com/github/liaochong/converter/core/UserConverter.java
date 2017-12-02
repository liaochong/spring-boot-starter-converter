package com.github.liaochong.converter.core;

import java.util.Objects;

import com.github.liaochong.converter.annoation.Converter;

/**
 * @author liaochong
 * @version V1.0
 */
@Converter
public class UserConverter {

    public static UserBO convertDO2BO(UserDO user) {
        if (Objects.isNull(user)) {
            return null;
        }
        UserBO result = new UserBO();
        result.setAge(user.getAge());
        result.setName(user.getName());
        result.setMan(true);
        result.setSex(user.getSex());
        return result;
    }

    public static UserDO convertBO2DO(UserBO user) {
        if (Objects.isNull(user)) {
            return null;
        }
        UserBO result = new UserBO();
        result.setAge(user.getAge());
        result.setName(user.getName());
        result.setMan(true);
        return result;
    }

}
