package {{packageName}}

{{^kotlinSupport}}
import org.springframework.boot.SpringApplication
{{/kotlinSupport}}
{{applicationImports}}
{{#kotlinSupport}}
import org.springframework.boot.runApplication
{{/kotlinSupport}}

{{#isDubboClient}}
import javax.annotation.PostConstruct;
import com.alibaba.dubbo.config.annotation.Reference;
import {{dubboServiceName}};
{{/isDubboClient}}

{{applicationAnnotations}}
class {{applicationName}} {
    {{#isDubboClient}}
    @Reference(version = "{{dubboServiceVersion}}"{{^embeddedZookeeper}}, url = "dubbo://localhost:20880"{{/embeddedZookeeper}})
    var demoService: {{serviceSimpleName}}? = null;
    {{/isDubboClient}}

    {{#isDubboClient}}
    @PostConstruct
    fun init() {
        val sayHello = demoService!!.sayHello("world")
        System.err.println(sayHello)
    }
    {{/isDubboClient}}
}

fun main(args: Array<String>) {
{{^kotlinSupport}}
    {{#isDubboServer}}{{#embeddedZookeeper}}
    // start embedded zookeeper server
    EmbeddedZooKeeper(2181, false).start();

    {{/embeddedZookeeper}}{{/isDubboServer}}
    SpringApplication.run({{applicationName}}::class.java, *args)
{{/kotlinSupport}}
{{#kotlinSupport}}
    runApplication<{{applicationName}}>(*args)
{{/kotlinSupport}}
}