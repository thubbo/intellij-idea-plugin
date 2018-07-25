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

package com.bruce.dubboplugin.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
@Builder
public class Dependency {

    /**
     * Compile Scope.
     */
    public static final String SCOPE_COMPILE = "compile";

    /**
     * Compile Only Scope.
     */
    public static final String SCOPE_COMPILE_ONLY = "compileOnly";

    /**
     * Runtime Scope.
     */
    public static final String SCOPE_RUNTIME = "runtime";

    /**
     * Provided Scope.
     */
    public static final String SCOPE_PROVIDED = "provided";

    /**
     * Test Scope.
     */
    public static final String SCOPE_TEST = "test";

    /**
     * All scope types.
     */
    public static final List<String> SCOPE_ALL = Collections
            .unmodifiableList(Arrays.asList(SCOPE_COMPILE, SCOPE_RUNTIME,
                    SCOPE_COMPILE_ONLY, SCOPE_PROVIDED, SCOPE_TEST));

    private String groupId;

    private String artifactId;

    private String version;

    @Builder.Default
    private String scope = SCOPE_COMPILE;

    private String type;
}
