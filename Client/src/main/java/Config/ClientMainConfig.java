package Config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ClientMainConfig {
    String mainPath ="..\\MyTwitterApp_Networks\\Client\\src\\main\\resources\\Config\\ClientMainConfig";

    String frameConfigPath;
    String colorConfigPath;
    String fontConfigPath;

    public ClientMainConfig() throws IOException {
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainPath);
        properties.load(fileReader);
        frameConfigPath = properties.getProperty("frameConfigPath");
        colorConfigPath = properties.getProperty("colorConfigPath");
        fontConfigPath = properties.getProperty("fontConfigPath");

    }

    public String getMainPath() {
        return mainPath;
    }

    public void setMainPath(String mainPath) {
        this.mainPath = mainPath;
    }

    public String getFrameConfigPath() {
        return frameConfigPath;
    }

    public void setFrameConfigPath(String frameConfigPath) {
        this.frameConfigPath = frameConfigPath;
    }

    public String getColorConfigPath() {
        return colorConfigPath;
    }

    public void setColorConfigPath(String colorConfigPath) {
        this.colorConfigPath = colorConfigPath;
    }

    public String getFontConfigPath() {
        return fontConfigPath;
    }

    public void setFontConfigPath(String fontConfigPath) {
        this.fontConfigPath = fontConfigPath;
    }
}
