package com.zhn.dto;

import com.zhn.annoation.hibernate.Id;
import com.zhn.annoation.hibernate.Persistent;
import com.zhn.annoation.hibernate.Property;

/**
 * @Author zhangnan
 * @Date 18-12-5
 **/
@Persistent(table = "personinf")
public class Person {

    @Id(column = "persion_id", type = "integer", generator = "identity")
    private int id;

    @Property(column = "person_name", type = "string")
    private String name;

    @Property(column = "person_age", type = "integer")
    private int age;


}