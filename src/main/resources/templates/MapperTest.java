package {{packageName}};

import {{MybatisModelQuatifiedName}};
import {{MybatisMapperQuatifiedName}};
{{#dep_pagehelper}}
import {{MybatisServiceQuatifiedName}};
{{/dep_pagehelper}}
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

{{#dep_pagehelper}}
import com.github.pagehelper.Page;
import org.assertj.core.api.Assertions;
{{/dep_pagehelper}}

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTest {

    @Autowired
    private TestModelMapper testModelMapper;

    {{#dep_pagehelper}}
    @Autowired
    private TestModelService testModelService;
    {{/dep_pagehelper}}
    @Test
    public void testMapper(){
        TestModel testModel = new TestModel();
        testModel.setAge(1);
        testModel.setCreatedAt(new Date());
        testModel.setUpdatedAt(new Date());
        testModel.setUserName("lalal");
        testModelMapper.insert(testModel);
    }

    {{#dep_pagehelper}}
    @Test
    public void testMapperWithPage(){
        for (int i = 0; i < 100; i++) {
            TestModel testModel = new TestModel();
            testModel.setAge(1);
            testModel.setCreatedAt(new Date());
            testModel.setUpdatedAt(new Date());
            testModel.setUserName("lalal");
            testModelMapper.insert(testModel);
        }

        Page<TestModel> byIdGreaterThanWithPage = testModelService.findByIdGreaterThanWithPage(0, 1, 20);
        Assertions.assertThat(byIdGreaterThanWithPage.getPageSize()).isGreaterThanOrEqualTo(5);
        Assertions.assertThat(byIdGreaterThanWithPage.size()).isEqualTo(20);
    }
    {{/dep_pagehelper}}
}