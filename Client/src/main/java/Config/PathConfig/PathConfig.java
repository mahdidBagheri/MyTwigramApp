package Config.PathConfig;

import Config.ClientMainConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PathConfig {

    String pathConfigPath;

    private String usersPicsPath;


    public PathConfig() throws IOException {
        ClientMainConfig mainConfig = new ClientMainConfig();
        pathConfigPath = mainConfig.getPathConfigPath();

        Properties properties = new Properties();
        FileReader fileReader = new FileReader(pathConfigPath);
        properties.load(fileReader);

        usersPicsPath = properties.getProperty("usersPics");

    }

    public String getUsersPicsPath() {
        return usersPicsPath;
    }
}
