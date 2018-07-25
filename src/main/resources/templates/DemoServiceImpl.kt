package {{dubboPackageName}};

import java.util.Date;

import com.alibaba.dubbo.config.annotation.Service;

@Service(version = "{{dubboServiceVersion}}")
class {{serviceSimpleName}}Impl : {{serviceSimpleName}} {

    override fun sayHello(name: String): String {
        return "Hello, " + name + ", " + Date()
    }

}