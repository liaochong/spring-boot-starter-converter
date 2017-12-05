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

    private static List<UserDO> list;

    static {
        ConverterContext.initialize(new ConverterProperties(), null);
    }

    @Before
    public void before() throws Exception {
        list = new ArrayList<>();

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

        for (int i = 0; i < 1000; i++) {
            list.add(use1);
            list.add(use2);
        }
    }

    @After
    public void after() throws Exception {
    }

    @Test(expected = ConvertException.class)
    public void testParallelConvertIfNullThrow() throws Exception {
        list.add(null);
        List<UserBO> users = BeanConverter.parallelConvertIfNullThrow(list, UserBO.class,
                () -> new ConvertException("xx"));
    }

    @Test(expected = ConvertException.class)
    public void testConvertIfNullThrow() throws Exception {
        list.add(null);
        List<UserBO> users = BeanConverter.convertIfNullThrow(list, UserBO.class, () -> new ConvertException("xx"));
    }

    @Test
    public void testConvert() {
        List<UserBO> users = BeanConverter.convert(list, UserBO.class);
        assert users.size() == 2000;
    }

    @Test
    public void testConvertBean() {
        UserDO use1 = new UserDO();
        use1.setAge(34);
        use1.setName("6666");
        use1.setMan(true);
        use1.setSex("男");
        UserBO userBO = BeanConverter.convert(use1, UserBO.class);
        System.out.println("");
    }

    @Test(expected = ConvertException.class)
    public void testConvertIfNullThrowBean() {
        UserDO use1 = null;
        UserBO userBO = BeanConverter.convertIfNullThrow(use1, UserBO.class, () -> new ConvertException("zzz"));
    }

    @Test
    public void testNonNullConvert() {
        list.add(null);
        List<UserBO> users = BeanConverter.nonNullConvert(list, UserBO.class);
        assert users.size() == 2000;
    }

    @Test
    public void testParallelConvert() {
        list.add(null);
        List<UserBO> users = BeanConverter.parallelConvert(list, UserBO.class);
        assert users.size() == 2001;
    }

    @Test
    public void testNonNullParallelConvert() {
        list.add(null);
        List<UserBO> users = BeanConverter.nonNullParallelConvert(list, UserBO.class);
        assert users.size() == 2000;
    }

}
