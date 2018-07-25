package {{packageName}};

import org.springframework.boot.SpringApplication;

{{#isDubboClient}}
import javax.annotation.PostConstruct;
import com.alibaba.dubbo.config.annotation.Reference;
import {{dubboServiceName}};
{{/isDubboClient}}
{{^hasWeb}}
import java.util.concurrent.CountDownLatch;
{{/hasWeb}}


{{applicationImports}}
{{applicationAnnotations}}
public class {{applicationName}} {

	public static void main(String[] args) {{^hasWeb}}throws InterruptedException{{/hasWeb}}{

		SpringApplication.run({{applicationName}}.class, args);

		{{^hasWeb}}
		new CountDownLatch(1).await(); //hold住应用，防止provider退出
		{{/hasWeb}}
	}
	
}
