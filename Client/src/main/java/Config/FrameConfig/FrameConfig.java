package Config.FrameConfig;

import Config.ClientMainConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class FrameConfig {

    String frameConfigPath;

    private int width;
    private int height;
    private int Xcoordinate;
    private int Ycoordinate;

    public FrameConfig() throws IOException {
        ClientMainConfig mainConfig = new ClientMainConfig();
        frameConfigPath = mainConfig.getFrameConfigPath();

        Properties properties = new Properties();
        FileReader fileReader = new FileReader(frameConfigPath);
        properties.load(fileReader);

        width = Integer.parseInt(properties.getProperty("width"));
        height = Integer.parseInt(properties.getProperty("height"));
        Xcoordinate = Integer.parseInt(properties.getProperty("Xcoordinate"));
        Ycoordinate = Integer.parseInt(properties.getProperty("Ycoordinate"));

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getFrameXLoc() {
        return Xcoordinate;
    }

    public int getFrameYLoc() {
        return Ycoordinate;
    }
}
