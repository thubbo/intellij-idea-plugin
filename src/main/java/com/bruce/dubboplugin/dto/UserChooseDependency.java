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

import lombok.Data;

import java.util.List;

@Data
public class UserChooseDependency {
    private boolean useMaven;

    private boolean useGradle;

    private String bootVersion;

    private String groupId;

    private String artifactId;

    private boolean useJava;

    private boolean useKotlin;

    private List<String> dependencyList;

    private boolean hasProvider;

    private String apiArtifactId;

    private String providerArtifactId;

    private boolean hasWebSupport;

}
