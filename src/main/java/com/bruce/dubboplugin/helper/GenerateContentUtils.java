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
import com.bruce.dubboplugin.dto.GenerateContentContext;
import com.bruce.dubboplugin.dto.UserChooseDependency;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GenerateContentUtils {
    public static void generateFiles(GenerateContentContext contentContext) {
        //try to only use with file api, so that will be really easy to test
        String dir = contentContext.getRootPath();
        UserChooseDependency userChooseDependency = contentContext.getUserChooseDependency();
        Map<String, Object> model = resolveModel(userChooseDependency);
        File projectFile = new File(dir);
        if (!projectFile.exists()) {
            projectFile.mkdirs();
        }
        String language = "java";

        if (userChooseDependency.isUseKotlin()) {
            language = "kotlin";
        }

        write(new File(dir, ".gitIgnore"), ".gitignore.txt", model);

        if (!userChooseDependency.isHasProvider()) {
            generateFilesForOnlyCustomerCode(dir, userChooseDependency, language, model);
            return;
        } else {
            generateFilesForProviderCode(dir, userChooseDependency, language, model);
            return;
        }



//        generateGitIgnore();
//        root.refresh(false, true);

//        if (userChooseDependency.isUseMaven()) {
//            List<VirtualFile> pomFiles = MavenUtil.streamPomFiles(project, project.getBaseDir()).collect(Collectors.toList());
//            MavenProjectsManager.getInstance(project).addManagedFilesOrUnignore(pomFiles);
//        }

    }

    private static void generateFilesForProviderCode(String dir, UserChooseDependency userChooseDependency, String language, Map<String, Object> model) {
        String applicationName = userChooseDependency.getArtifactId() + "Application";

//        String pacakgeName = userChooseDependency.getGroupId() + "." + userChooseDependency.getArtifactId();

        String providerDir = dir + "/" + userChooseDependency.getProviderArtifactId();

        String apiDir = dir + "/" + userChooseDependency.getApiArtifactId();

        String apiPackageName = userChooseDependency.getGroupId() + "." + userChooseDependency.getApiArtifactId();

        String providerPackageName = userChooseDependency.getGroupId() + "." + userChooseDependency.getProviderArtifactId();

        //write files to parent pom
        boolean useGradle = userChooseDependency.isUseGradle();
        if (useGradle) {
            // TODO: 7/17/2018 should check the gralde file for multiple module
//            writeText(new File(dir, "build.gradle"), TemplateUtils.processToString("gradle.ftl", null));
        } else {
            String process = TemplateRenderer.INSTANCE.process("parent-pom.xml", model);
            writeText(new File(dir, "pom.xml"), process);
        }

        String codeLocation = language;


        String extension = ("kotlin".equals(language) ? "kt" : language);

        //generate files for api
        generateFilesForApiModule(model, apiDir, apiPackageName, useGradle, codeLocation, extension);


        //generate pom.xml for provider files

        genreateFilesForProviderModule(model, applicationName, providerDir, providerPackageName, useGradle, codeLocation, extension, userChooseDependency);
    }

    private static void genreateFilesForProviderModule(Map<String, Object> model, String applicationName, String providerDir, String providerPackageName, boolean useGradle, String codeLocation, String extension, UserChooseDependency userChooseDependency) {
        File resourceSrc = new File(providerDir + "/src/main/resources");
        resourceSrc.mkdirs();
        if (useGradle) {
            // TODO: 7/17/2018 need support gradle project
//            writeText(new File(dir, "build.gradle"), TemplateUtils.processToString("gradle.ftl", null));

        } else {
            String process = TemplateRenderer.INSTANCE.process("starter-pom-provider.xml", model);
            writeText(new File(providerDir, "pom.xml"), process);
        }

        File providerSrc = new File(new File(providerDir, "src/main/" + codeLocation),
                providerPackageName.replace(".", "/"));


        providerSrc.mkdirs();

        File testSrc = new File(new File(providerDir + "/src/test/" + codeLocation), providerPackageName.replace(".", "/"));
        testSrc.mkdirs();


        //create the main class for springboot application

        write(new File(providerSrc, applicationName + "." + extension),
                "Application." + extension, model);

        new File(providerSrc + "/provider").mkdirs();

        //将DemoService生成进去
        write(new File(providerSrc + "/provider", "DemoServiceImpl." + extension), "DemoServiceImpl." + extension, model);

        File resources = new File(providerDir, "src/main/resources");
        resources.mkdirs();
        write(new File(resources, "application.properties"), "application.properties", model);

        if (userChooseDependency.getDependencyList().contains(DependencyConstant.MYBAITS)) {
            File mapperSrc = new File(providerSrc + "/mapper");
            mapperSrc.mkdirs();
            write(new File(mapperSrc, "TestModelMapper.java"), "TestModelMapper.java", model);

            File modelSrc = new File(providerSrc + "/model");
            modelSrc.mkdirs();
            write(new File(modelSrc, "TestModel.java"), "TestModel.java", model);


            File resourceMapperSRc = new File(resourceSrc + "/mapper");
            resourceMapperSRc.mkdirs();

            write(new File(resourceMapperSRc, "TestModelMapper.xml"), "TestModelMapper.xml", model);

            write(new File(resourceSrc, "schema.sql"), "schema.sql", model);

            write(new File(testSrc, "MapperTest.java"), "MapperTest.java", model);

            if (userChooseDependency.getDependencyList().contains(DependencyConstant.PAGE_HELPER)) {
                File serviceSrc = new File(providerSrc + "/service");
                serviceSrc.mkdirs();
                write(new File(serviceSrc, "TestModelService.java"), "TestModelService.java", model);
            }
        }


    }

    private static void generateFilesForApiModule(Map<String, Object> model, String apiDir, String apiPackageName, boolean useGradle, String codeLocation, String extension) {
        new File(apiDir + "/src/main/java").mkdirs();
        new File(apiDir + "/src/main/resources").mkdirs();
        new File(apiDir + "/src/test/java");
        if (useGradle) {
            // TODO: 7/17/2018 need support gradle project
//            writeText(new File(dir, "build.gradle"), TemplateUtils.processToString("gradle.ftl", null));

        } else {
            String process = TemplateRenderer.INSTANCE.process("starter-pom-api.xml", model);
            writeText(new File(apiDir, "pom.xml"), process);
        }


        File apiSrc = new File(new File(apiDir, "src/main/" + codeLocation),
                apiPackageName.replace(".", "/"));
        apiSrc.mkdirs();

        //write DemoService to api folder
        write(new File(apiSrc, "DemoService." + extension), "DemoService." + extension, model);
    }

    private static void generateFilesForOnlyCustomerCode(String dir, UserChooseDependency userChooseDependency, String language, Map<String, Object> model) {
        String applicationName = userChooseDependency.getArtifactId() + "Application";

        String pacakgeName = userChooseDependency.getGroupId() + "." + userChooseDependency.getArtifactId();


        if (userChooseDependency.isUseGradle()) {
//            writeText(new File(dir, "build.gradle"), TemplateUtils.processToString("gradle.ftl", null));
        } else {
            String process = TemplateRenderer.INSTANCE.process("start-pom-customer.xml", model);
            writeText(new File(dir, "pom.xml"), process);
        }

        String codeLocation = language;

        try {
            new File(dir + "/src/main/java").mkdirs();
            new File(dir + "/src/main/resources").mkdirs();
            new File(dir + "/src/test/java").mkdirs();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        File src = new File(new File(dir, "src/main/" + codeLocation),
                pacakgeName.replace(".", "/"));
        src.mkdirs();

        //create the main class for springboot application
        String extension = ("kotlin".equals(language) ? "kt" : language);
        write(new File(src, applicationName + "." + extension),
                "Application." + extension, model);

        File resources = new File(dir, "src/main/resources");
        resources.mkdirs();
        write(new File(resources, "application.properties"), "application.properties", model);
    }

    private static Map<String, Object> resolveModel(UserChooseDependency userChooseDependency) {
        Map<String, Object> model = Maps.newHashMap();
        model.put("war", false);

        model.put("kotlinSupport", false);

        if (userChooseDependency.isHasWebSupport()) {
            model.put("hasWeb", true);
        }

        if (userChooseDependency.isHasProvider()) {
            model.put("apiArtifactId", userChooseDependency.getApiArtifactId());
            model.put("providerArtifactId", userChooseDependency.getProviderArtifactId());

            model.put("dubboPackageName", userChooseDependency.getGroupId() + "." + userChooseDependency.getApiArtifactId());

            model.put("serviceSimpleName", "DemoService");

            model.put("dubboProviderPackageName", userChooseDependency.getGroupId() + "." + userChooseDependency.getProviderArtifactId() + ".provider");


            if (userChooseDependency.isUseMaven()) {
                model.put("mavenBuild", true);
                model.put("mavenParentGroupId", userChooseDependency.getGroupId());
                model.put("mavenParentArtifactId", userChooseDependency.getArtifactId());
                // TODO: 7/14/2018 need config version from user input
                model.put("mavenParentVersion", "0.0.1-SNAPSHOT");
                model.put("includeSpringBootBom", false);
                model.put("packageName", userChooseDependency.getGroupId() + "." + userChooseDependency.getProviderArtifactId());
            }

            model.put("isDubboServer", true);
        } else {
            model.put("isDubboClient", true);
            model.put("packageName", userChooseDependency.getGroupId() + "." + userChooseDependency.getArtifactId());

        }

        if (userChooseDependency.getDependencyList().contains(DependencyConstant.MYBAITS)) {
            model.put("mybatisModelPackage", userChooseDependency.getGroupId() + "." + userChooseDependency.getProviderArtifactId() + ".model");
            model.put("MybatisMapperPackage", userChooseDependency.getGroupId() + "." + userChooseDependency.getProviderArtifactId() + ".mapper");
            model.put("MybatisModelQuatifiedName", userChooseDependency.getGroupId() + "." + userChooseDependency.getProviderArtifactId() + ".model.TestModel");
            model.put("MybatisMapperQuatifiedName", userChooseDependency.getGroupId() + "." + userChooseDependency.getProviderArtifactId() + ".mapper.TestModelMapper");

            if (userChooseDependency.getDependencyList().contains(DependencyConstant.PAGE_HELPER)) {
                model.put("providerServicePackage", userChooseDependency.getGroupId() + "." + userChooseDependency.getProviderArtifactId() + ".service");
                model.put("MybatisServiceQuatifiedName", userChooseDependency.getGroupId() + "." + userChooseDependency.getProviderArtifactId() + ".service.TestModelService");
            }
        }


        model.put("dubboSpringbootVersion", "0.2.0");


        List<Dependency> dependencies = extractDependencyFrom(userChooseDependency.getDependencyList());


        for (String s : userChooseDependency.getDependencyList()) {
            model.put("dep_" + s, true);
        }

        model.put("compileDependencies",
                filterDependencies(dependencies, Dependency.SCOPE_COMPILE));
        model.put("runtimeDependencies",
                filterDependencies(dependencies, Dependency.SCOPE_RUNTIME));
        model.put("compileOnlyDependencies",
                filterDependencies(dependencies, Dependency.SCOPE_COMPILE_ONLY));
        model.put("providedDependencies",
                filterDependencies(dependencies, Dependency.SCOPE_PROVIDED));
        model.put("testDependencies",
                filterDependencies(dependencies, Dependency.SCOPE_TEST));
        Map<String, String> buildPropertyJava = Maps.newLinkedHashMap();
        buildPropertyJava.put("java.version", "1.8");
        model.put("buildPropertiesVersions", buildPropertyJava.entrySet());

        Map<String, String> buildPropertyMaven = Maps.newLinkedHashMap();

        buildPropertyMaven.put("project.build.sourceEncoding", "UTF-8");
        buildPropertyMaven.put("project.reporting.outputEncoding", "UTF-8");

        model.put("buildPropertiesMaven", buildPropertyMaven.entrySet());

        model.put("dependencyManagementPluginVersion", "0.6.0.RELEASE");

        model.put("kotlinVersion", "1.2.20");

        model.put("isRelease", true);

        model.put("applicationImports", "import org.springframework.boot.autoconfigure.SpringBootApplication;");

        model.put("applicationAnnotations", "@SpringBootApplication");

        model.put("bootOneThreeAvailable", true);

        model.put("bootTwoZeroAvailable", false);

        model.put("springBootPluginName", "org.springframework.boot");

        model.put("newTestInfrastructure", true);

        model.put("servletInitializrImport", "import org.springframework.boot.web.support.SpringBootServletInitializer;");

        model.put("kotlinStdlibArtifactId", "kotlin-stdlib-jdk8");

        model.put("java8OrLater", "true");
        model.put("applicationName", userChooseDependency.getArtifactId() + "Application");
        model.put("artifactId", userChooseDependency.getArtifactId());
        model.put("baseDir", userChooseDependency.getArtifactId());
//        model.put("boms","{}");
        // TODO: 7/14/2018 need config them
        model.put("bootVersion", "2.0.3.RELEASE");
        model.put("build", "maven");
        model.put("buildProperties", "io.spring.initializr.generator.BuildProperties@62547c95");
        model.put("class", "class io.spring.initializr.generator.ProjectRequest");
        model.put("dependencies", "[]");
        model.put("description", "Demo project for Spring Boot");
        model.put("dubboServiceName", "com.example.HelloService");
        model.put("dubboServiceVersion", "1.0.0");
        model.put("dubboSide", "dubboServer");
        model.put("embeddedZookeeper", "true");
//        model.put("facets","[]");
        model.put("groupId", userChooseDependency.getGroupId());
        model.put("javaVersion", "1.8");
        if (userChooseDependency.isUseJava()) {
            model.put("language", "java");
        } else {
            model.put("language", "kotlin");
        }
        model.put("name", userChooseDependency.getArtifactId());
        model.put("packaging", "jar");
//        model.put("parameters","{host=localhost:9097, connection=keep-alive, upgrade-insecure-requests=1, user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36, accept=text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8, referer=http://localhost:9097/, accept-encoding=gzip, deflate, br, accept-language=en,zh-CN;q=0.9,zh;q=0.8,ja;q=0.7,zh-TW;q=0.6,nl;q=0.5}");
        model.put("qos", "false");
        model.put("repositories", "{}");
        model.put("resolvedDependencies", dependencies);
        //don't know the use for it.
//        model.put("style","[dubbo, netty4, fastjson, commons-lang3]");
        model.put("type", "maven-project");
        model.put("version", "0.0.1-SNAPSHOT");
        return model;
    }

    private static Object filterDependencies(List<Dependency> dependencies, String scopeCompile) {
        return dependencies.stream().filter((dep) -> scopeCompile.equals(dep.getScope())).collect(Collectors.toList());
    }

    private static List<Dependency> extractDependencyFrom(List<String> dependencyList) {
        List<Dependency> dependencies = Lists.newArrayList();
        dependencies.add(Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter").build());
        for (String s : dependencyList) {
            dependencies.addAll(DependencyHelper.findDependency(s));
        }
        return dependencies;
    }

    private static void write(File file, String s, Map<String, Object> o) {
        writeText(file, TemplateRenderer.INSTANCE.process(s, o));
    }


    private static void writeText(File target, String body) {
        try {
            IOUtils.write(body, new FileOutputStream(target), "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
