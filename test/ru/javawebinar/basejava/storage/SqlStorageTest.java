package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.Config;

public class SqlStorageTest extends AbstractStorageTest {
    private final static Config CONFIG = Config.getInstance();

    public SqlStorageTest() {
        super(new SqlStorage(CONFIG.getDbPath(), CONFIG.getDbUserName(), CONFIG.getDbUserPass()));
    }
}
