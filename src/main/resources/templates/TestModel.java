package {{mybatisModelPackage}};

{{#dep_lombok}}
import lombok.Data;
{{/dep_lombok}}
import java.util.Date;

{{#dep_lombok}}
@Data
{{/dep_lombok}}
public class TestModel {
    private Integer id;

    private Integer age;

    private String userName;

    private Date createdAt;

    private Date updatedAt;

    {{^dep_lombok}}
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    {{/dep_lombok}}
}