package com.github.liaochong.converter.example.converter;

import com.github.liaochong.converter.annoation.Converter;
import com.github.liaochong.converter.example.model.UserBO;
import com.github.liaochong.converter.example.model.UserDO;

/**
 * @author liaochong
 * @version V1.0
 */
@Converter
public class UserConverter {

    public static UserBO convertDO2BO(UserDO user) {
        UserBO result = new UserBO();
        result.setName(user.getName());
        result.setAge(user.getAge());
        return result;
    }
}
