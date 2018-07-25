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

package com.bruce.dubboplugin.helper;

import com.bruce.dubboplugin.dto.Dependency;
import com.bruce.dubboplugin.dto.DependencyConstant;
import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DependencyHelper {
    private static Map<String, List<Dependency>> dependencyMap = new HashMap<String, List<Dependency>>() {{
        put(DependencyConstant.MYBAITS, Lists.newArrayList(Dependency.builder().groupId("org.mybatis.spring.boot").artifactId("mybatis-spring-boot-starter").version("1.3.2").build()));
        put(DependencyConstant.DUBBO, Lists.newArrayList(Dependency.builder().groupId("com.alibaba.boot").artifactId("dubbo-spring-boot-starter").version("0.1.0").build()));
        put(DependencyConstant.FASTJSON, Lists.newArrayList(Dependency.builder().groupId("com.alibaba").artifactId("fastjson").version("1.2.47").build()));
        put(DependencyConstant.MYSQL, Lists.newArrayList(Dependency.builder().groupId("mysql").artifactId("mysql-connector-java").scope(Dependency.SCOPE_RUNTIME).build()));
        put(DependencyConstant.LOMBOK, Lists.newArrayList(Dependency.builder().groupId("org.projectlombok").artifactId("lombok").scope(Dependency.SCOPE_COMPILE_ONLY).version("1.16.6").build()));
        put(DependencyConstant.REDIS, Lists.newArrayList(Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-data-redis").build()));
        put(DependencyConstant.RABBIT_MQ, Lists.newArrayList(Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-amqp").build()));
        put(DependencyConstant.SPRING_RETRY, Lists.newArrayList(Dependency.builder().groupId("org.springframework.retry").artifactId("spring-retry").version("1.3.0.RELEASE").build()));
        put(DependencyConstant.ZOOKEEPER, Lists.newArrayList());
        put(DependencyConstant.WEB_TOMCAT, Lists.newArrayList(Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-web").build()));
        put(DependencyConstant.PAGE_HELPER, Lists.newArrayList(Dependency.builder().groupId("com.github.pagehelper").artifactId("pagehelper-spring-boot-starter").version("1.2.5").build()));
        put(DependencyConstant.COMMON_LANGS_3, Lists.newArrayList(Dependency.builder().groupId("org.apache.commons").artifactId("commons-lang3").version("3.7").build()));
        put(DependencyConstant.HIKARI, Lists.newArrayList(Dependency.builder().groupId("com.zaxxer").artifactId("HikariCP").version("2.6.0").build()));
    }};

    public static List<Dependency> findDependency(String dependency) {
        return dependencyMap.get(dependency);
    }

}
