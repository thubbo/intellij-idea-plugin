package {{MybatisMapperPackage}};

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import {{MybatisModelQuatifiedName}};

@Mapper
public interface TestModelMapper {
    int insert(@Param("testModel") TestModel testModel);

    int insertSelective(@Param("testModel") TestModel testModel);

    int insertList(@Param("testModels") List<TestModel> testModels);

    int update(@Param("testModel") TestModel testModel);

    {{#dep_pagehelper}}
    List<TestModel> findByIdGreaterThan(@Param("minId")Integer minId);
    {{/dep_pagehelper}}
}