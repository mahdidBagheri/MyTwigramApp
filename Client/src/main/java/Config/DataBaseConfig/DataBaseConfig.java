package Config.DataBaseConfig;

import Config.ClientMainConfig;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class DataBaseConfig {
    String dataBaseConfigPath;

    String dataBaseName;

    public DataBaseConfig() throws IOException {
        ClientMainConfig mainConfig = new ClientMainConfig();
        dataBaseConfigPath = mainConfig.getDataBaseConfigPath();

        Properties properties = new Properties();
        FileReader fileReader = new FileReader(dataBaseConfigPath);
        properties.load(fileReader);

        dataBaseName = properties.getProperty("dataBaseName");
    }

    public void saveDataBaseName(String DBname) throws IOException {
        ClientMainConfig mainConfig = new ClientMainConfig();
        dataBaseConfigPath = mainConfig.getDataBaseConfigPath();

        Properties properties = new Properties();
        FileWriter fileWriter = new FileWriter(dataBaseConfigPath);

        properties.setProperty("dataBaseName",DBname);
        properties.store(fileWriter,"");
        int a = 0;
    }

    public String getDataBaseName() {
        return dataBaseName;
    }
}
