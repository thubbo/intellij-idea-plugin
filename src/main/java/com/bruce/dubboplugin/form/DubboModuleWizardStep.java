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

package com.bruce.dubboplugin.form;

import com.bruce.dubboplugin.DubboPluginModuleBuilder;
import com.bruce.dubboplugin.dto.DependencyConstant;
import com.bruce.dubboplugin.dto.PreConfig;
import com.bruce.dubboplugin.dto.UserChooseDependency;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DubboModuleWizardStep extends ModuleWizardStep {

    private Project myProject;

    private WizardContext myContext;

    private DubboPluginModuleBuilder myBuilder;
    private JComboBox mavenGradleCombox;
    private JComboBox javaKotlinCombox;
    private JComboBox bootVersionCombox;
    private JTextField groupIdText;
    private JTextField artifactIdText;
    private JCheckBox mybatisCheckBox;
    private JCheckBox redisCheckBox;
    private JCheckBox mySqlCheckBox;
    private JCheckBox lombokCheckBox;
    private JCheckBox fastJsonCheckBox;
    private JCheckBox rabbitMqCheckBox;
    private JCheckBox commonsLang3CheckBox;
    private JCheckBox retryCheckBox;
    private JCheckBox zookeeperCheckBox;
    private JCheckBox hikariCheckBox;
    private JPanel myPanel;
    private JCheckBox cosumerCheckBox;
    private JCheckBox providerCheckBox;
    private JTextField demoApiTextField;
    private JTextField demoProviderTextField;
    private JCheckBox pageHelperCheckBox;
    private JCheckBox webTomcatCheckBox;


    private List<JCheckBox> myJCheckBoxList = Lists.newArrayList();
    private Map<String, JCheckBox> myDependecyToCheckBoxMap = Maps.newHashMap();
    private Map<JCheckBox, String> myJcheckBoxToDependencyMap = Maps.newHashMap();

    public DubboModuleWizardStep(DubboPluginModuleBuilder builder, WizardContext context, boolean includeArtifacts) {
        myProject = context.getProject();
        myBuilder = builder;
        myContext = context;
        System.out.println("start to init wizard step");
        initCheckBoxAndMap();
    }

    private void initCheckBoxAndMap() {
        addForOneCheckBox(mybatisCheckBox, DependencyConstant.MYBAITS);
        addForOneCheckBox(redisCheckBox, DependencyConstant.REDIS);
        addForOneCheckBox(mySqlCheckBox, DependencyConstant.MYSQL);
        addForOneCheckBox(lombokCheckBox, DependencyConstant.LOMBOK);
        addForOneCheckBox(fastJsonCheckBox, DependencyConstant.FASTJSON);
        addForOneCheckBox(rabbitMqCheckBox, DependencyConstant.RABBIT_MQ);
        addForOneCheckBox(commonsLang3CheckBox, DependencyConstant.COMMON_LANGS_3);
        addForOneCheckBox(retryCheckBox, DependencyConstant.SPRING_RETRY);
        addForOneCheckBox(zookeeperCheckBox, DependencyConstant.ZOOKEEPER);
        addForOneCheckBox(webTomcatCheckBox, DependencyConstant.WEB_TOMCAT);
        addForOneCheckBox(pageHelperCheckBox,DependencyConstant.PAGE_HELPER);
        addForOneCheckBox(hikariCheckBox,DependencyConstant.HIKARI);
//        addForOneCheckBox(notImplementedCheckBox, DependencyConstant.);
    }

    private void addForOneCheckBox(JCheckBox myCheckBox, String dependencyName) {
        myJCheckBoxList.add(myCheckBox);
        myDependecyToCheckBoxMap.put(dependencyName, myCheckBox);
        myJcheckBoxToDependencyMap.put(myCheckBox, dependencyName);
    }

    @Override
    public JComponent getComponent() {
        providerCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (providerCheckBox.isSelected()) {
                    demoApiTextField.setEnabled(true);
                    demoProviderTextField.setEnabled(true);
                } else {
                    demoApiTextField.setEnabled(false);
                    demoProviderTextField.setEnabled(false);
                }
            }
        });

        artifactIdText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                onArtifactIdChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                onArtifactIdChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                onArtifactIdChange();
            }
        });
        return myPanel;
    }

    private void onArtifactIdChange() {
        demoApiTextField.setText(artifactIdText.getText() + "Api");
        demoProviderTextField.setText(artifactIdText.getText() + "Provider");
    }

    @Override
    public void updateStep() {
        for (JCheckBox jCheckBox : myJCheckBoxList) {
            jCheckBox.setSelected(false);
        }
        List<String> dependencies = myBuilder.getPreConfig().getDependencies();
        if (!dependencies.isEmpty()) {
            for (String dependency : dependencies) {
                if (myDependecyToCheckBoxMap.containsKey(dependency)) {
                    myDependecyToCheckBoxMap.get(dependency).setSelected(true);
                }
            }
        }
    }

    @Override
    public void updateDataModel() {
        UserChooseDependency dependency = new UserChooseDependency();
        ArrayList<String> dependencyList = Lists.newArrayList();
        dependency.setDependencyList(dependencyList);
        if (mavenGradleCombox.getSelectedItem().equals("Maven")) {
            dependency.setUseMaven(true);
        } else {
            dependency.setUseGradle(true);
        }

        if (javaKotlinCombox.getSelectedItem().equals("Java")) {
            dependency.setUseJava(true);
        } else {
            dependency.setUseKotlin(true);
        }

        dependency.setBootVersion((String) bootVersionCombox.getSelectedItem());
        for (JCheckBox jCheckBox : myJCheckBoxList) {
            if (jCheckBox.isSelected()) {
                String s = myJcheckBoxToDependencyMap.get(jCheckBox);
                dependencyList.add(s);
            }
        }
        if (providerCheckBox.isSelected()) {
            dependency.setHasProvider(true);
            dependency.setApiArtifactId(demoApiTextField.getText());
            dependency.setProviderArtifactId(demoProviderTextField.getText());
        }

        if (webTomcatCheckBox.isSelected()) {
            dependency.setHasWebSupport(true);
        }
        dependency.setGroupId(groupIdText.getText());
        dependency.setArtifactId(artifactIdText.getText());
        myBuilder.setUserChooseDependency(dependency);
    }

    @Override
    public boolean validate() throws ConfigurationException {
        if (!providerCheckBox.isSelected() && !cosumerCheckBox.isSelected()) {
            throw new ConfigurationException("provider and consumer should selected at least one", "validate fail");
        }

//        if(providerCheckBox.isSelected()){
//        }
        return true;
    }
}
