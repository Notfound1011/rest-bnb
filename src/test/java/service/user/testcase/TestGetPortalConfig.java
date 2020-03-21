package service.user.testcase;

import org.junit.jupiter.api.Test;
import service.user.api.getPortalConfigApi;

import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;

public class TestGetPortalConfig {
    getPortalConfigApi getPortalConfig = new getPortalConfigApi();

    @Test
    public void getPortalConfig() {
        getPortalConfig.getPortalConfig("上海",2,"2020-3-19",
                "2020-3-19","oversea",true)
                .then().body("resultCode",equalTo(0)
                ,"hotDestination.code",equalTo("hotdestinations"));
    }
}
