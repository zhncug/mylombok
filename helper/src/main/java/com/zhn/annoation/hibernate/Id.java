package com.zhn.annoation.hibernate;

import java.lang.annotation.*;

/**
 * @Author zhangnan
 * @Date 18-12-5
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface Id {

    String column();

    String type();

    String generator();
}
