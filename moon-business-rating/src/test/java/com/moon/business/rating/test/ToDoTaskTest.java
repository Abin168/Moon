package com.moon.business.rating.test;

import com.moon.business.rating.MoonBusinessRating;
import com.moon.business.rating.service.KafkaMsgSendTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {MoonBusinessRating.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ToDoTaskTest {
    @Autowired
    private KafkaMsgSendTest test;

    @Test
    public void test() throws InterruptedException {
        test.sendTsgTest();
    }


}
