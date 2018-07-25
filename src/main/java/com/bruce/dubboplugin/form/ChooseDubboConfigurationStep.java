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
import com.bruce.dubboplugin.dto.Dependency;
import com.bruce.dubboplugin.dto.DependencyConstant;
import com.google.common.collect.Lists;

import com.bruce.dubboplugin.DubboPluginModuleBuilder;
import com.bruce.dubboplugin.dto.PreConfig;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.wizard.StepAdapter;
import com.intellij.openapi.Disposable;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChooseDubboConfigurationStep extends ModuleWizardStep implements Disposable {

    private DubboPluginModuleBuilder myBuilder;
    private JPanel panel1;
    private JRadioButton dubboSpringBootMybatisMysqlRadioButton;
    private JRadioButton createNewRadioButton;


    public ChooseDubboConfigurationStep(DubboPluginModuleBuilder builder, @Nullable StepAdapter stepAdapter) {
        this.myBuilder = builder;
        initComponents();
    }

    private void initComponents() {
        ButtonGroup group = new ButtonGroup();
        group.add(dubboSpringBootMybatisMysqlRadioButton);
        group.add(createNewRadioButton);
        final PreConfig preConfig = new PreConfig();
        preConfig.setDependencies(Lists.newArrayList(DependencyConstant.MYBAITS,DependencyConstant.MYSQL
                ,DependencyConstant.HIKARI,DependencyConstant.ZOOKEEPER,DependencyConstant.PAGE_HELPER));
        myBuilder.setPreConfig(preConfig);
        dubboSpringBootMybatisMysqlRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                preConfig.setDependencies(Lists.newArrayList(DependencyConstant.MYBAITS,DependencyConstant.MYSQL
                ,DependencyConstant.HIKARI,DependencyConstant.ZOOKEEPER,DependencyConstant.PAGE_HELPER));
                myBuilder.setPreConfig(preConfig);
            }
        });

        createNewRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                preConfig.setDependencies(Lists.newArrayList());
                myBuilder.setPreConfig(preConfig);
            }
        });
    }

    @Override
    public JComponent getComponent() {
        return panel1;
    }

    @Override
    public void updateDataModel() {

    }

    @Override
    public void dispose() {

    }
}
