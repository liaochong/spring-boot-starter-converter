package com.github.liaochong.converter.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.liaochong.converter.core.BeanConverter;
import com.github.liaochong.converter.example.model.UserBO;
import com.github.liaochong.converter.example.model.UserDO;
import com.github.liaochong.ratel.tools.core.builder.CollectionBuilder;

/**
 * @author liaochong
 * @version V1.0
 */
@RestController
public class UserController {

    @GetMapping("/users/one")
    public UserBO getUser() {
        UserDO user = new UserDO();
        user.setName("one");
        user.setAge(18);
        return BeanConverter.convert(user, UserBO.class);
    }

    @GetMapping("/users")
    public List<UserBO> getUsers() {
        UserDO user = new UserDO();
        user.setName("one");
        user.setAge(18);

        UserDO user1 = new UserDO();
        user1.setName("two");
        user1.setAge(23);

        return BeanConverter.convert(CollectionBuilder.arrayList(user, user1), UserBO.class);
    }
}
