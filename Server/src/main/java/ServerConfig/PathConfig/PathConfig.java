package ServerConfig.PathConfig;

import ServerConfig.ServerMainConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PathConfig {
    private String usersPicPath;

    public PathConfig() throws IOException {
        ServerMainConfig mainConfig = new ServerMainConfig();

        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfig.getPathConfigPath());
        properties.load(fileReader);

        usersPicPath = properties.getProperty("usersPicsPath");
    }

    public String getUsersPicPath() {
        return usersPicPath;
    }
}
