package service.user.testcase;

import org.junit.jupiter.api.Test;
import service.user.api.UserApi;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class TestUserApi {
    @Test
    public void get(){
        UserApi user=new UserApi();
//        user.get("seveniruby").then().body("errcode", equalTo(0));
        HashMap<String, Object> data = new HashMap<>();
        user.getPortalConfig("上海",2,"2020-03-15","2020-03-16",data);
    }
}
