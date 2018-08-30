# Dubbo Intellij Idea Plugin

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Jetbrains Plugins](https://img.shields.io/jetbrains/plugin/v/10946-a8translate.svg)][plugin]
[![Downloads](https://img.shields.io/jetbrains/plugin/d/10946.svg?style=flat-square)][plugin]

<div align="right">
<a href="README_CN.md">中文文档</a>
</div>


## Features

- Quickly build a Dubbo Spring Boot project include ApiModule and providerModule.
- You can choose other dependencies like mybatis pageHelper hikari, and the plugin will auto generate code sample and unit test.


## Supported dependencies
| dependency name            |  supported version        |
|----------------------|-------------------  |
| MYBAITS   |   v1.0              |
| MYSQL       |   v1.0            |
|LOMBOK      |   v1.0             |
| REDIS    |   v1.0           |
| FASTJSON   |   v1.0            |
| RABBIT_MQ       |   v1.0          |
| COMMON_LANGS_3     |  v1.0          |
| SPRING_RETRY |   v1.0            |
| ZOOKEEPER      |   v1.0             |
|WEB_TOMCAT| v1.0 |
|PAGE_HELPER | v1.0 |
|HIKARI | v1.0 |


## Screen Shot

- first step  
![](http://ogyxv3y5w.bkt.clouddn.com/start.png)

- second step   
![](http://ogyxv3y5w.bkt.clouddn.com/second.png)


- third step  
![](http://ogyxv3y5w.bkt.clouddn.com/third.png)

- generated project structure    
![](http://ogyxv3y5w.bkt.clouddn.com/projectStructure.png)

- generated main class  
![](http://ogyxv3y5w.bkt.clouddn.com/demoApplication.png)

- generated dubboProvider  
![](http://ogyxv3y5w.bkt.clouddn.com/generateProvider.png)

 
- generated mybatis interface  
![](http://ogyxv3y5w.bkt.clouddn.com/mybatisExample.png)


- generated service, pageHelper is used    
![](http://ogyxv3y5w.bkt.clouddn.com/generateService.png)


- generated testcase  
![](http://ogyxv3y5w.bkt.clouddn.com/generateTestCase.png)



## Install 

the plugin support Intellij version more than or equal to 2018.1

**use IDEA plugin system:**
- <kbd>Preferences(Settings)</kbd> > <kbd>Plugins</kbd> > <kbd>Browse repositories...</kbd> > <kbd>search and find"Dubbo"</kbd> > <kbd>Install Plugin</kbd>


**directly download**
- download[`lastest plugin zip`](http://ogyxv3y5w.bkt.clouddn.com/dubboPlugin-1.0.zip) -> <kbd>Preferences(Settings)</kbd> > <kbd>Plugins</kbd> > <kbd>Install plugin from disk...</kbd>


## Requirement

- if you has used with zookeeper, please set up zookeeper server first
- before start, please config the database config in application.properties to your own config
- if you want to run the testcase in project, please run schema.sql in project resource folder


## Contribute to project
- import project to your Intellij
- add a gradle task **gradle runIde** 
- modify project source code and run the task
- more you can view on  https://github.com/JetBrains/gradle-intellij-plugin


[plugin]: https://plugins.jetbrains.com/plugin/10946







