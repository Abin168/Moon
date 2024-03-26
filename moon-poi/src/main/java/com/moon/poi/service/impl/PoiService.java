package com.moon.poi.service.impl;

import com.moon.core.common.result.R;
import com.moon.poi.common.constants.PoiConstant;
import com.moon.poi.service.AbstractPoi;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PoiService extends AbstractPoi {
    @Override
    public R<?> wordFilling(String tempPath, Map<String, Object> map) {
        return R.success();
    }

    public static void replaceTxt(XWPFDocument document, final Map<String, Object> txtMap) {
        if (MapUtils.isEmpty(txtMap)) return;
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

    public static boolean containSymBol(String text) {
        return text.contains(PoiConstant.LEFT_SYMBOL) && text.contains(PoiConstant.RIGHT_SYMBOL);
    }
}
