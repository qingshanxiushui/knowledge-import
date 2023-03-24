package com.study.regexp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neusoft.deepcogni.mrqcs.regex.DFARegexEngine;
import com.neusoft.deepcogni.mrqcs.regex.DFARegexEngineBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class TemplateParamgraphEngine {
    private static Logger log = LoggerFactory.getLogger(TemplateParamgraphEngine.class);

    private static final Map<String, DFARegexEngine> TEMPLATE_ENGINES = new HashMap<String, DFARegexEngine>();

    static {
        try {

            StringBuilder sbuilder = new StringBuilder();
            try(BufferedReader br = new BufferedReader(new InputStreamReader(TemplateParamgraphEngine.class.getResourceAsStream("/regex/paragraph.json"), Charset.forName("utf-8")))){

                br.lines().forEach(new Consumer<String>() {
                    @Override
                    public void accept(String t) {
                        sbuilder.append(t);
                    }
                });
            }

            JSONArray templates = JSONArray.parseArray(sbuilder.toString());
            JSONObject template = null;
            for(int i = 0 , l = templates.size(); i < l; i++) {
                template = templates.getJSONObject(i);
                String id = template.getString("template_id");
                JSONArray regexs = template.getJSONArray("paragraphs");

                TEMPLATE_ENGINES.put(id, DFARegexEngineBuilder.build(regexs));
            }

        }catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public static DFARegexEngine getParagraphEngine(String id) {
        return TEMPLATE_ENGINES.get(id);
    }
}
