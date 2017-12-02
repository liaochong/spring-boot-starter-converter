package com.github.liaochong.converter.core;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.liaochong.converter.configuration.ConverterProperties;
import com.github.liaochong.converter.context.ConverterContext;
import com.github.liaochong.converter.exception.ConvertException;

/**
 * BeanConverter Tester.
 * 
 * @author <Authors name>
 * @since
 * 
 *        <pre>
 * 十一月 20, 2017
 *        </pre>
 * 
 * @version 1.0
 */
public class BeanConverterTest {

    private static List<UserDO> list = new ArrayList<>();

    static {
        ConverterContext.initialize(new ConverterProperties(), null);

        UserDO use1 = new UserDO();
        use1.setAge(34);
        use1.setName("1111");
        use1.setMan(true);
        use1.setSex("男");

        UserDO use2 = new UserDO();
        use2.setAge(55);
        use2.setName("222");
        use2.setMan(false);
        use2.setSex("女");

        list.add(use1);
        list.add(use2);
    }

    @Before
    public void before() throws Exception {

    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testConvert() throws Exception {
        List<UserBO> users = BeanConverter.convert(list, UserBO.class);
        assert users.size() == 2;
    }

    @Test
    public void testConvertIfNullThrow() throws Exception {
        List<UserBO> users = BeanConverter.convertIfNullThrow(list, UserBO.class, () -> new ConvertException("xx"));
        System.out.println("");
    }

    @Test
    public void testNonNullConvert() throws Exception {
        list.add(null);
        List<UserBO> users = BeanConverter.nonNullConvert(list, UserBO.class);
        assert users.size() == 2;
    }

    @Test
    public void testParallelConvert() throws Exception {
        list.add(null);
        List<UserBO> users = BeanConverter.parallelConvert(list, UserBO.class);
        assert users.size() == 2;
    }

    @Test
    public void testParallelConvertIfNullThrow() throws Exception {
        list.add(null);
        List<UserBO> users = BeanConverter.parallelConvertIfNullThrow(list, UserBO.class,
                () -> new ConvertException("xx"));
        System.out.println("");
    }

    @Test
    public void testNonNullParallelConvert() throws Exception {
        list.add(null);
        List<UserBO> users = BeanConverter.nonNullParallelConvert(list, UserBO.class);
        assert users.size() == 2;
    }

}
