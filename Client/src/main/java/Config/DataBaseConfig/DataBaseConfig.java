package Config.DataBaseConfig;

import Config.ClientMainConfig;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class DataBaseConfig {
    String dataBaseConfigPath;

    String dataBaseAddress;

    public DataBaseConfig() throws IOException {
        ClientMainConfig mainConfig = new ClientMainConfig();
        dataBaseConfigPath = mainConfig.getDataBaseConfigPath();

        Properties properties = new Properties();
        FileReader fileReader = new FileReader(dataBaseConfigPath);
        properties.load(fileReader);

        dataBaseAddress = properties.getProperty("dataBaseAddress");
    }

    public void saveDataBaseName(String DBname) throws IOException {
        ClientMainConfig mainConfig = new ClientMainConfig();
        dataBaseConfigPath = mainConfig.getDataBaseConfigPath();

        Properties properties = new Properties();
        FileWriter fileWriter = new FileWriter(dataBaseConfigPath);

        properties.setProperty("dataBaseAddress",DBname);
        properties.store(fileWriter,"");
        int a = 0;
    }

    public String getDataBaseAddress() {
        return dataBaseAddress;
    }
}
