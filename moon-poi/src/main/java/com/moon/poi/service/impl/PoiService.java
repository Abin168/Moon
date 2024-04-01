package com.moon.poi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.moon.core.common.result.R;
import com.moon.poi.common.constants.PoiConstant;
import com.moon.poi.service.AbstractPoi;
import com.moon.poi.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class PoiService extends AbstractPoi {

    @Qualifier("poiThreadPoolTaskExecutor")
    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Value("${template.tempDir}")
    private String tempDir;

    @Override
    public R<String> filling(String tempPath, Map<String, Object> map) {
        String copyTemplateFile = String.format("%s%s%s", tempDir, "tempFile", PoiConstant.DOCX_SUFFIX);
        FileUtil.copyFile(tempPath, copyTemplateFile);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String finalFile = String.format("%s_%s%s", tempDir, uuid, PoiConstant.DOCX_SUFFIX);
        try (XWPFDocument doc = changWord(copyTemplateFile, map);
             FileOutputStream out = new FileOutputStream(finalFile)) {
            if (Objects.isNull(doc)) {
                // fixme
                return R.fail();
            }
            doc.write(out);
        } catch (IOException e) {
            //
        }
        // upload out file fixme
        FileUtil.deleteFile(copyTemplateFile);
        return R.success();
    }

    private XWPFDocument changWord(String inputFile, final Map<String, Object> textMap) {
        XWPFDocument document;
        try {
            document = new XWPFDocument(Files.newInputStream(Paths.get(inputFile)));
            long start = System.currentTimeMillis();
            replaceTxt(document, textMap);
            long end = System.currentTimeMillis();
            log.info("changWord consumer time {}", end - start);
        } catch (Exception e) {
            log.error("WordUtils changWord error inputFile={}, textMap={}", inputFile, JSONObject.toJSONString(textMap), e);
            return null;
        }
        return document;
    }

    private void replaceTxt(XWPFDocument document, final Map<String, Object> txtMap) {
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        paragraphs.forEach(paragraph -> {
            String text = paragraph.getText();
            if (!containSymBol(text)) {
                return;
            }
            paragraph.getRuns().forEach(run -> replaceRun(run, txtMap));
        });
    }

    private void replaceRun(XWPFRun run, final Map<String, Object> txtMap) {
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
    }

    private boolean containSymBol(String text) {
        return text.contains(PoiConstant.LEFT_SYMBOL) && text.contains(PoiConstant.RIGHT_SYMBOL);
    }
}
