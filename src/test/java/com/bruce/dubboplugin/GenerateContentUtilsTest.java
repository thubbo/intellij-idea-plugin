package com.bruce.dubboplugin;

import com.bruce.dubboplugin.dto.DependencyConstant;
import com.bruce.dubboplugin.generator.ProjectAssert;
import com.google.common.collect.Lists;
import com.bruce.dubboplugin.dto.UserChooseDependency;

import com.bruce.dubboplugin.dto.GenerateContentContext;
import com.bruce.dubboplugin.helper.GenerateContentUtils;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class GenerateContentUtilsTest {

    private String projectRootPath = "src/test/resources/testFiles";

    @Before
    public void setUp(){
        deleteDirectory(new File(projectRootPath));
    }
    @Test
    public void testGenerateFilesAsProviderWithNoDependency() {
        GenerateContentContext contentContext = new GenerateContentContext();
        UserChooseDependency userChooseDependency = new UserChooseDependency();
        userChooseDependency.setUseMaven(true);
        userChooseDependency.setUseGradle(false);
        userChooseDependency.setBootVersion("2.0.3");
        userChooseDependency.setGroupId("com.example.lala");
        userChooseDependency.setArtifactId("demo");
        userChooseDependency.setUseJava(true);
        userChooseDependency.setUseKotlin(false);
        userChooseDependency.setDependencyList(Lists.newArrayList());
        userChooseDependency.setHasProvider(true);
        userChooseDependency.setApiArtifactId("demo-Api");
        userChooseDependency.setProviderArtifactId("demo-Provider");
        userChooseDependency.setHasWebSupport(false);
        contentContext.setRootPath(projectRootPath);
        contentContext.setUserChooseDependency(
                userChooseDependency);

        File file = GenerateContentUtils.generateFiles(
                contentContext);
        ProjectAssert projectAssert = new ProjectAssert(file);
        assertThat(projectAssert.isJavaProject("com.example.lala","Application"));
    }


    @Test
    public void testGenerateFilesAsProviderWithAllDepedency() {
        GenerateContentContext contentContext = new GenerateContentContext();
        UserChooseDependency userChooseDependency = new UserChooseDependency();
        userChooseDependency.setUseMaven(true);
        userChooseDependency.setUseGradle(false);
        userChooseDependency.setBootVersion("2.0.3");
        userChooseDependency.setGroupId("com.example.lala");
        userChooseDependency.setArtifactId("demo");
        userChooseDependency.setUseJava(true);
        userChooseDependency.setUseKotlin(false);
        userChooseDependency.setDependencyList(Lists.newArrayList(DependencyConstant.MYBAITS, DependencyConstant.MYSQL, DependencyConstant.ZOOKEEPER, DependencyConstant.COMMON_LANGS_3,
                DependencyConstant.FASTJSON, DependencyConstant.LOMBOK, DependencyConstant.PAGE_HELPER, DependencyConstant.REDIS, DependencyConstant.SPRING_RETRY, DependencyConstant.WEB_TOMCAT,
                DependencyConstant.HIKARI, DependencyConstant.RABBIT_MQ));
        userChooseDependency.setHasProvider(true);
        userChooseDependency.setApiArtifactId("demo-Api");
        userChooseDependency.setProviderArtifactId("demo-Provider");
        userChooseDependency.setHasWebSupport(false);
        contentContext.setUserChooseDependency(
                userChooseDependency);
        contentContext.setRootPath(projectRootPath);
        GenerateContentUtils.generateFiles(
                contentContext);
    }


    @Test
    public void testGenerateFilesAsProviderWithNoDependencyWithZookeeperDependency() {
        GenerateContentContext contentContext = new GenerateContentContext();
        UserChooseDependency userChooseDependency = new UserChooseDependency();
        userChooseDependency.setUseMaven(true);
        userChooseDependency.setUseGradle(false);
        userChooseDependency.setBootVersion("2.0.3");
        userChooseDependency.setGroupId("com.example.lala");
        userChooseDependency.setArtifactId("demo");
        userChooseDependency.setUseJava(true);
        userChooseDependency.setUseKotlin(false);
        userChooseDependency.setDependencyList(Lists.newArrayList(DependencyConstant.ZOOKEEPER));
        userChooseDependency.setHasProvider(true);
        userChooseDependency.setApiArtifactId("demoApi");
        userChooseDependency.setProviderArtifactId("demoProvider");
        userChooseDependency.setHasWebSupport(false);
        contentContext.setUserChooseDependency(
                userChooseDependency);
        contentContext.setRootPath(projectRootPath);
        GenerateContentUtils.generateFiles(
                contentContext);
    }

    boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }


    @Test
    public void testGenerateFileAsConsumerWihtNoDependencyTest() {
        GenerateContentContext contentContext = new GenerateContentContext();
        UserChooseDependency userChooseDependency = new UserChooseDependency();
        userChooseDependency.setUseMaven(true);
        userChooseDependency.setUseGradle(false);
        userChooseDependency.setBootVersion("2.0.3");
        userChooseDependency.setGroupId("com.example.lala");
        userChooseDependency.setArtifactId("demo");
        userChooseDependency.setUseJava(true);
        userChooseDependency.setUseKotlin(false);
        userChooseDependency.setDependencyList(Lists.newArrayList());
        userChooseDependency.setHasProvider(false);
//        userChooseDependency.setApiArtifactId("demoApi");
//        userChooseDependency.setProviderArtifactId("demoProvider");
        userChooseDependency.setHasWebSupport(false);
        contentContext.setUserChooseDependency(
                userChooseDependency);
        contentContext.setRootPath(projectRootPath);
        GenerateContentUtils.generateFiles(
                contentContext);
    }

}
