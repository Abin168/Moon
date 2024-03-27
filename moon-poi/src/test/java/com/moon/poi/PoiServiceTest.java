package com.moon.poi;

import com.moon.core.common.result.R;
import com.moon.poi.service.impl.PoiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {MoonPoi.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PoiServiceTest {

    @Autowired
    private PoiService poiService;

    @Value("${template.dir}")
    private String tempDir;

    @Test
    public void wordFilling() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "bob");
        map.put("age", 20);
        map.put("msg", "apache poi");
        R<String> stringR = poiService.wordFilling(tempDir, map);
    }

}
