package com.bruce.dubboplugin;

import com.bruce.dubboplugin.helper.TemplateRenderer;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

public class TemplateRenderTest {
    @Test
    public void testTemplateRender(){
        Map<String,Object> model = Maps.newHashMap();
        model.put("groupId","com.lalala");
        String process = TemplateRenderer.INSTANCE.process("starter-pom.xml", model);
        System.out.println(process);
    }
}
