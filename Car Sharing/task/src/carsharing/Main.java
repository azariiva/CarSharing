package carsharing;

import carsharing.controller.console.ConsoleController;
import carsharing.persistance.JdbcConnectionContainer;
import carsharing.persistance.repository.JdbcCarRepository;
import carsharing.persistance.repository.JdbcCompanyRepository;
import carsharing.persistance.repository.JdbcCustomerRepository;

import java.io.File;
import java.util.Objects;

public class Main {

    private static final String DB_FOLDER_NAME = "src/carsharing/db";
    private static final String DB_FILE_NAME_ARGS_FLAG = "-databaseFileName";
    private static final String DB_FILE_DEFAULT_NAME = "carsharing";

    public static void main(String[] args) {
        final var dbFileName = extractDatabaseFileName(args);
        final var dbFolder = new File(DB_FOLDER_NAME);
        final var dbFile = new File(dbFolder, dbFileName);
        final var dbFileWithExt = new File(dbFolder, String.format("%s.mv.db", dbFileName));
        final var dbUri = String.format("jdbc:h2:%s", dbFile.getAbsolutePath());

        dbFolder.mkdirs();
        try {
            dbFileWithExt.createNewFile();
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
        JdbcConnectionContainer.setDbUri(dbUri);
        JdbcCompanyRepository.getInstance();
        JdbcCarRepository.getInstance();
        JdbcCustomerRepository.getInstance();
        new ConsoleController().run();
        JdbcConnectionContainer.closeConnection();
    }

    private static String extractDatabaseFileName(String[] args) {
        int i = 0;

        for (; i < args.length; i++) {
            if (Objects.equals(args[i], DB_FILE_NAME_ARGS_FLAG) && i != args.length - 1) {
                return args[i + 1];
            }
        }
        return DB_FILE_DEFAULT_NAME;
    }
}
