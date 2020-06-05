package cn.erick.aiomp.utils.file;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 读取配置文件工具类
 */
public class ReadConfigUtil {

    private static Properties properties;

    //初始化变量
    protected static Properties init() {
        if (properties == null) {
            properties = new Properties();
        }
        return properties;
    }

    /**
     * 原生ClassLoader读取配置文件
     */
    //原生调用  给定key
    public static String readConfigFileByJava(String key) {
        init();
        try {
            InputStream inputStream = ReadConfigUtil.class.getResourceAsStream("/config/configuration.properties");
            properties.load(new InputStreamReader(inputStream, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }
    //返回Porperties
    public static Properties readConfigFileByJava() {
        init();
        try {
            InputStream inputStream = ReadConfigUtil.class.getResourceAsStream("/config/configuration.properties");
            properties.load(new InputStreamReader(inputStream, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
    /**
     * 通过Spring框架中的ClassPathResource获取配置文件内容
     *
     * @param key 传入得指定key，获取对应的val
     * @return
     */
    //通过给定key获取value
    public static String readConfigFileBySpring(String key) {
        init();
        try {
            //spring 框架 读取配置文件流
            ClassPathResource pathResource = new ClassPathResource("/config/configuration.properties");
            properties.load(new InputStreamReader(pathResource.getInputStream(), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();

        }
        return properties.getProperty(key);
    }

    //通过获取Properties对象自定义获取配置文件内容
    public static Properties readConfigFileBySpring() {
        init();
        try {
            //spring 框架 读取配置文件流
            ClassPathResource pathResource = new ClassPathResource("/config/configuration.properties");
            properties.load(new InputStreamReader(pathResource.getInputStream(), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static void main(String[] args) {

    }
}
