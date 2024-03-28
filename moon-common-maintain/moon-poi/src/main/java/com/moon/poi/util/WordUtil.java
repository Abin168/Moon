package com.moon.poi.util;

import org.apache.commons.collections4.MapUtils;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.util.List;
import java.util.Map;

public class WordUtil {

    private static final String LEFT_SYMBOL = "«";
    private static final String RIGHT_SYMBOL = "»";

    /**
     * replace txt in word template
     *
     * @param document
     * @param txtMap
     */
    public static void replaceTxt(XWPFDocument document, final Map<String, Object> txtMap) {
        if (MapUtils.isEmpty(txtMap)) return;
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        paragraphs.forEach(paragraph -> {
            String text = paragraph.getText();
            if (!checkText(text)) {
                return;
            }
            paragraph.getRuns().forEach(run -> {
                String runStr = run.toString();
                if (!checkText(runStr)) {
                    return;
                }
                Object val = replaceVal(runStr, txtMap);
//                run.setText(val);
            });

        });
    }

    public static boolean checkText(String text) {
        return text.contains(LEFT_SYMBOL) && text.contains(RIGHT_SYMBOL);
    }

    public static Object replaceVal(String value, Map<String, Object> textMap) {
        String runStrip = value.replace(LEFT_SYMBOL, "").replace(RIGHT_SYMBOL, "");
        return textMap.getOrDefault(runStrip, "");
    }
}
