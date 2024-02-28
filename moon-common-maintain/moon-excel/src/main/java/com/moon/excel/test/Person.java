package com.moon.excel.test;

import com.alibaba.excel.annotation.ExcelProperty;

import java.math.BigDecimal;


public class Person {

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("年龄")
    private Integer age;

    @ExcelProperty(value = "性别", converter = SexConverter.class)
    private Integer sex;
    @ExcelProperty("薪资")
    private BigDecimal money;

    public Person(String name, Integer age, Integer sex, BigDecimal money) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.money = money;
    }

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", money=" + money +
                '}';
    }
}
