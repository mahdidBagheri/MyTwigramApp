package ServerConfig.PathConfig;

import ServerConfig.ServerMainConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PathConfig {
    private String usersPicPath;
    private String twittsPicPath;

    public PathConfig() throws IOException {
        ServerMainConfig mainConfig = new ServerMainConfig();

        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfig.getPathConfigPath());
        properties.load(fileReader);

        usersPicPath = properties.getProperty("usersPicsPath");
        twittsPicPath = properties.getProperty("twittsPicsPath");
    }

    public String getUsersPicPath() {
        return usersPicPath;
    }

    public String getTwittsPicPath() {
        return twittsPicPath;
    }
}
