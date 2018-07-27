/*
 *
 *  * Copyright 2017-2018 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.bruce.dubboplugin.generator;


import com.bruce.dubboplugin.utils.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Source code assertions.
 *
 * @author Stephane Nicoll
 */
public class SourceCodeAssert {

    private final String name;

    private final String content;

    public SourceCodeAssert(String name, String content) {
        this.name = name;
        this.content = trimSpaceAndEmptyLines(content.replaceAll("\r\n", "\n").replaceAll("(?m)^[ \t]*\r?\n", ""));
    }

    public SourceCodeAssert equalsTo(InputStream stream) throws IOException {
        String expectedContent = StreamUtils.copyToString(stream,
                Charset.forName("UTF-8")).replaceAll("\r\n", "\n").replaceAll("(?m)^[ \t]*\r?\n", "");
        expectedContent = trimSpaceAndEmptyLines(expectedContent);
        assertThat(content).describedAs("Content for %s", this.name)
                .isEqualTo(expectedContent);

        return this;
    }

    public SourceCodeAssert hasImports(String... classNames) {
        for (String className : classNames) {
            contains("import " + className);
        }
        return this;
    }

    public SourceCodeAssert doesNotHaveImports(String... classNames) {
        for (String className : classNames) {
            doesNotContain("import " + className);
        }
        return this;
    }

    public SourceCodeAssert contains(String... expressions) {
        assertThat(this.content).describedAs("Content for %s", this.name)
                .contains(expressions);
        return this;
    }

    public SourceCodeAssert doesNotContain(String... expressions) {
        assertThat(this.content).describedAs("Content for %s", this.name)
                .doesNotContain(expressions);
        return this;
    }

    private static String trimSpaceAndEmptyLines(String content) {
        char[] value = content.toCharArray();
        int len = value.length;
        int st = 0;
        char[] val = value; /* avoid getfield opcode */

        while ((st < len) && (val[st] <= ' ' || val[st] == '\r' || val[st] == '\n')) {
            st++;
        }
        while ((st < len) && (val[len - 1] == ' ' || val[len - 1] == '\r' || val[len - 1] == '\n')) {
            len--;
        }
        return ((st > 0) || (len < value.length)) ? content.substring(st, len) : content;
    }

}
