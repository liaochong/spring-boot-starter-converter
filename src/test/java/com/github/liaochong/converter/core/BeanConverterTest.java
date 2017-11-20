package com.github.liaochong.converter.core;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.liaochong.converter.configuration.ConverterProperties;
import com.github.liaochong.converter.context.ConverterContext;
import com.github.liaochong.converter.exception.InvalidParameterException;
import com.github.liaochong.ratel.tools.core.builder.CollectionBuilder;

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

    @Before
    public void before() throws Exception {
        ConverterContext.initialize(new ConverterProperties(), null);

        UserDO use1 = new UserDO();
        use1.setAge(34);
        use1.setName("1111");
        use1.setMan(true);

        UserDO use2 = new UserDO();
        use2.setAge(55);
        use2.setName("222");
        use2.setMan(false);

        list = CollectionBuilder.arrayList(use1, use2, null);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * 
     * Method: nonNullConvert(List<T> source, Class<E> targetClass)
     * 
     */
    @Test
    public void testNonNullConvert() throws Exception {
        List<UserBO> users = BeanConverter.nonNullConvert(list, UserBO.class);
        assert users.size() == 2;

    }

    /**
     * 
     * Method: parallelConvert(List<T> source, Class<E> targetClass)
     * 
     */
    @Test
    public void testParallelConvert() throws Exception {
        List<UserBO> users = BeanConverter.parallelConvert(list, UserBO.class);
        assert users.size() == 3;
    }

    /**
     * 
     * Method: parallelConvertIfNullThrow(List<T> source, Class<E> targetClass,
     * Supplier<G> supplier)
     * 
     */
    @Test
    public void testParallelConvertIfNullThrow() throws Exception {
        List<UserBO> users = BeanConverter.parallelConvertIfNullThrow(list, UserBO.class,
                () -> InvalidParameterException.of("xx"));
    }

    /**
     * 
     * Method: parallelConvertList(List<T> source, Class<E> targetClass, Supplier<G>
     * supplier)
     * 
     */
    @Test
    public void testParallelConvertList() throws Exception {
        // TODO: Test goes here...
    }

    /**
     * 
     * Method: nonNullParallelConvert(List<T> source, Class<E> targetClass)
     * 
     */
    @Test
    public void testNonNullParallelConvert() throws Exception {
        List<UserBO> users = BeanConverter.nonNullParallelConvert(list, UserBO.class);
        assert users.size() == 3;
    }

    /**
     * 
     * Method: convertBeans(List<T> source, Class<E> targetClass, Supplier<G>
     * supplier)
     * 
     */
    @Test
    public void testConvertBeans() throws Exception {
        // TODO: Test goes here...
        /*
         * try { Method method = BeanConverter.getClass().getMethod("convertBeans",
         * List<T>.class, Class<E>.class, Supplier<G>.class);
         * method.setAccessible(true); method.invoke(<Object>, <Parameters>); }
         * catch(NoSuchMethodException e) { } catch(IllegalAccessException e) { }
         * catch(InvocationTargetException e) { }
         */
    }

}
