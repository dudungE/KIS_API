import lombok.Getter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import java.io.FileInputStream;
import java.util.Properties;

@Getter
public class KisApiConfig {
    private String appKey;
    private String appSecret;
    private String accessToken;

    public KisApiConfig(String propFilePath) throws Exception {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(propFilePath)) {
            props.load(fis);
        }
        this.appKey = props.getProperty("appkey").trim();
        this.appSecret = props.getProperty("appsecret").trim();
        this.accessToken = props.getProperty("access_token").trim();
    }

    // getter
}
