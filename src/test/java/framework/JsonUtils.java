package framework;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import service.user.testcase.getPortalConfig;

import java.io.File;
import java.io.IOException;

public class JsonUtils {
    private Object addKVtoJsonData(Object obj,String FileName,String key,Object value) {
        String jsonFilePath = obj.getClass().getResource(FileName).getPath();
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


    public static void main(String[] args) {
        JsonUtils ju = new JsonUtils();
//        Object  data = ju.addKVtoJsonData(new getPortalConfig(),"getPortalConfig.json","ov",true);
//        System.out.println(data);
    }
}
