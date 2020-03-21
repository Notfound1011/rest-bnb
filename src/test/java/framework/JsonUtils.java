package framework;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import service.user.testcase.TestGetPortalConfig;

import java.io.File;
import java.io.IOException;

public class JsonUtils {
    public JsonUtils() {
    }

    public Object addKVtoJsonFile(String jsonFilePath, String key, Object value) {
        File file = new File(jsonFilePath );
        String input = null;
        try {
            input = FileUtils.readFileToString(file,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject object = JSONObject.parseObject(input);
        object.put(key,value);
        return object;
    }

    public Object addKVtoJsonBody(String JsonBody, String key, Object value) {
        JSONObject object = JSONObject.parseObject(JsonBody);
        object.put(key,value);
        return object;
    }
}
