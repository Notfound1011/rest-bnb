package service.user.api;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import service.CtripBnb;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class UserApi extends BaseApi {
    public Response get(String userid) {
        HashMap<String, Object> params=new HashMap<>();
        params.put("userid", userid);
        setParams(params);

        return parseSteps();


//        return given()
//                .queryParam("access_token", CtripBnb.getInstance().getToken())
//                .queryParam("userid", userid)
//                .when().log().all().get("https://qyapi.weixin.qq.com/cgi-bin/user/get")
//        .then().log().all().extract().response();

    }

    public Response update(String userid, HashMap<String, Object> data) {
        data.put("userid", userid);

        return given()
                .queryParam("access_token", CtripBnb.getInstance().getToken())
                .body(data)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/user/update")
                .then().extract().response();
    }


    public Response create(String userid, HashMap<String, Object> data) {
        data.put("userid", userid);

        return given()
                .queryParam("access_token", CtripBnb.getInstance().getToken())
                .body(data)
                .when().log().all().post("https://qyapi.weixin.qq.com/cgi-bin/user/create")
                .then().log().all().extract().response();
    }

    public Response getPortalConfig(String cityName, int cityId, String checkInDate, String checkOutDate, HashMap<String, Object> data) {
        String getPortalConfigUrl = requestUrl +  Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(getPortalConfigUrl);
        data.put("cityName", cityName);
        data.put("cityId", cityId);
        data.put("checkInDate", checkInDate);
        data.put("checkOutDate", checkOutDate);
        //todo: 使用模板技术

        String body=template("/service/user/api/getPortalConfig.json", data);

        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .when().log().all().post(getPortalConfigUrl)
                .then().log().all().extract().response();
    }


    public String template(String templatePath, HashMap<String, Object> data){
        Writer writer = new StringWriter();
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(this.getClass().getResource(templatePath).getPath());
        mustache.execute(writer, data);
        try {
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }


}
