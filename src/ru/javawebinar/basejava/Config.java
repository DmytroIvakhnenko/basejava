package ru.javawebinar.basejava;

import ru.javawebinar.basejava.storage.SqlStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class Config {
    private static Config instance;
    private static final String configurationFile = "resumes.properties";
    private static String configurationPath = "./config/";

    private final File storageDir;
    private final SqlStorage sqlStorage;

    private Config() {
        File configFile = new File(configurationPath + configurationFile);
        try (InputStream is = new FileInputStream(configFile)) {
            Properties properties = new Properties();
            properties.load(is);
            storageDir = new File(properties.getProperty("storage.dir"));
            sqlStorage = new SqlStorage(properties.getProperty("db.url"), properties.getProperty("db.name"), properties.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file" + configFile.getAbsolutePath());
        }
    }

    public static void setConfigurationPath(String confPath) {
        configurationPath = confPath;
    }

    public static Config getInstance() {
        if (Objects.isNull(instance)) {
            instance = new Config();
        }
        return instance;
    }

    public SqlStorage getSqlStorage() {
        return sqlStorage;
    }

    public File getStorageDir() {
        return storageDir;
    }
}
