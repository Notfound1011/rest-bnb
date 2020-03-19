package framework;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public class YamlUtils {
    // 首先声明一个Map存解析之后的内容:
    Map<String, Object> properties;

    // 空的构造函数
    public YamlUtils() {
    }
    // 以文件路径为条件的构造函数
    public YamlUtils(String filePath) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 调基础工具类的方法
        Yaml yaml = new Yaml();
        properties = yaml.loadAs(inputStream, Map.class);
    }

    // 从String 中获取配置的方法
    public void initWithString(String content) {
        // 这里的yaml和上面那个应该可以抽取成一个全局变量,不过平常用的时候
        // 也不会两个同时用
        Yaml yaml = new Yaml();
        properties = yaml.loadAs(content, Map.class);
    }


    /**
     * 从Map中获取配置的值
     * 传的key支持两种形式, 一种是单独的,如user.path.key
     * 一种是获取数组中的某一个,如 user.path.key[0]
     * @param key
     * @return
     */
    private  <T> T getValueByKey(String key) {
        String separator = ".";
        String[] separatorKeys = null;
        if (key.contains(separator)) {
            // 取下面配置项的情况, user.path.keys 这种
            separatorKeys = key.split("\\.");
        } else {
            // 直接取一个配置项的情况
            Object res = properties.get(key);
            return (T) res;
        }
        // 下面肯定是取多个的情况
        String finalValue = null;
        Object tempObject = properties;

        for (int i = 0; i < separatorKeys.length; i++) {
            //如果是user[0].path这种情况,则按list处理
            String innerKey = separatorKeys[i];
            if (innerKey.contains("[")) {
                // 如果是user[0]的形式,则index = 0 , innerKey=user
                innerKey = innerKey.substring(0, innerKey.indexOf("["));
            }
            Map<String, Object> mapTempObj = (Map) tempObject;
            Object object = mapTempObj.get(innerKey);

            Object targetObj = object;
            // 一次获取结束,继续获取后面的
            tempObject = targetObj;
            if (i == separatorKeys.length - 1) {
                //循环结束
                return (T) targetObj;
            }

        }
        return null;
    }

    public String getAuthFromYaml(){
        String filePath = this.getClass().getResource("Auth.yaml").getPath();
        YamlUtils yamlUtils = new YamlUtils(filePath);
        return yamlUtils.getValueByKey("auth.id");
    }

    public String getRequestUrlFromYaml(String env, String appid) {
        String filePath = this.getClass().getResource("Env.yaml").getPath();
        YamlUtils yamlUtils = new YamlUtils(filePath);
        return  yamlUtils.getValueByKey(env).toString() + yamlUtils.getValueByKey(appid) + "/";
    }

    public static void main(String[] args) {
        String url = new YamlUtils().getRequestUrlFromYaml("prd.url","vesta2.appId");
        System.out.println(url);
    }
}
