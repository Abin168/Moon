package com.moon.poi.service.impl;

import com.moon.core.common.result.R;
import com.moon.poi.common.constants.PoiConstant;
import com.moon.poi.service.AbstractPoi;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class PoiService extends AbstractPoi {

    @Qualifier("poiThreadPoolTaskExecutor")
    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Override
    public R<?> wordFilling(String tempPath, Map<String, Object> map) {
        final int batchSize = 2;
//        return R.success();
        Path path = Paths.get(tempPath);
        return R.success();
    }

    public void replaceTxt(XWPFDocument document, final Map<String, Object> txtMap) {
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        paragraphs.forEach(paragraph -> {
            String text = paragraph.getText();
            if (!containSymBol(text)) {
                return;
            }
            paragraph.getRuns().forEach(run -> {
                //替换模板原来位置
                String runStr = run.toString();
                if (!containSymBol(runStr)) {
                    return;
                }
                Object replaceVale = txtMap.getOrDefault(StringUtils.strip(runStr, PoiConstant.SYMBOL), StringUtils.EMPTY);
                String[] breakArr = replaceVale.toString().split("\r\n");
                if (breakArr.length > 0) {
                    for (int i = 0; i < breakArr.length; i++) {
                        run.setText(breakArr[i], i);
                        if (i < breakArr.length - 1) {
                            run.addBreak();
                        }
                    }
                } else {
                    run.setText(StringUtils.EMPTY, 0);
                }
            });

        });
    }

    public void replaceTxtFuture(XWPFDocument document, final Map<String, Object> txtMap) {
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(paragraphs.stream()
                .map(paragraph -> CompletableFuture.runAsync(() -> {
                    String text = paragraph.getText();
                    if (!containSymBol(text)) {
                        return;
                    }
                    paragraph.getRuns().forEach(run -> {
                        //替换模板原来位置
                        String runStr = run.toString();
                        if (!containSymBol(runStr)) {
                            return;
                        }
                        Object replaceVale = txtMap.getOrDefault(StringUtils.strip(runStr, PoiConstant.SYMBOL), StringUtils.EMPTY);
                        String[] breakArr = replaceVale.toString().split("\r\n");
                        if (breakArr.length > 0) {
                            for (int i = 0; i < breakArr.length; i++) {
                                run.setText(breakArr[i], i);
                                if (i < breakArr.length - 1) {
                                    run.addBreak();
                                }
                            }
                        } else {
                            run.setText(StringUtils.EMPTY, 0);
                        }
                    });
                }, executor)).toArray(CompletableFuture[]::new));
    }

    public static boolean containSymBol(String text) {
        return text.contains(PoiConstant.LEFT_SYMBOL) && text.contains(PoiConstant.RIGHT_SYMBOL);
    }
}
