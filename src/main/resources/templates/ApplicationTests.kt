package {{packageName}}

import org.junit.Test
import org.junit.runner.RunWith
{{testImports}}
{{#newTestInfrastructure}}
@RunWith(SpringRunner::class)
@SpringBootTest
{{/newTestInfrastructure}}
{{^newTestInfrastructure}}
@RunWith(SpringJUnit4ClassRunner::class)
@SpringApplicationConfiguration(classes = arrayOf({{applicationName}}::class))
{{/newTestInfrastructure}}
{{testAnnotations}}class {{applicationName}}Tests {

	@Test
	fun contextLoads() {
	}

}
