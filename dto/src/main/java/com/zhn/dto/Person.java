package com.zhn.dto;

import com.zhn.annoation.hibernate.Persistent;

/**
 * @Author zhangnan
 * @Date 18-12-5
 **/
@Persistent(table = "personinf")
public class Person {

    private int id;

    private String name;

    private int age;


}