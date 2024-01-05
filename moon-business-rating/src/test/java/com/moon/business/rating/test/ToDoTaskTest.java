package com.moon.business.rating.test;

import com.alibaba.fastjson.JSONObject;
import com.moon.business.rating.MoonBusinessRating;
import com.moon.business.rating.service.ToDoTaskInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {MoonBusinessRating.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ToDoTaskTest {
    @Autowired
    private ToDoTaskInterface toDoTaskInterface;

    @Test
    public void test() {
        toDoTaskInterface.doTask(new JSONObject());
    }


}
