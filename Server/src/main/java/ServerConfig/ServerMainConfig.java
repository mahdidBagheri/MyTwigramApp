package ServerConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ServerMainConfig {
    String mainPath ="..\\MyTwitterApp_Networks\\Server\\src\\main\\resources\\Config\\ServerMainConfig";

    String pathConfigPath;


    public ServerMainConfig() throws IOException {
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainPath);
        properties.load(fileReader);
        pathConfigPath = properties.getProperty("pathConfigPath");
    }

    public String getPathConfigPath() {
        return pathConfigPath;
    }

    public void setPathConfigPath(String pathConfigPath) {
        this.pathConfigPath = pathConfigPath;
    }
}
