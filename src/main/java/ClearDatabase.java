import java.io.File;
import java.util.Objects;

public class ClearDatabase {
    public static void main(String[] args) {
        cleanDatabase();
    }

    private static void cleanDatabase() {
        for (Path value : Path.values()) {
            removeFolder(value.getPath());
        }
        removeFile("src/main/resources/UsedUsernames.json");
        removeFile("src/main/resources/UsedProductId.json");
        removeFile("src/main/resources/UsedId.json");
    }

    private static void removeFile(String path) {
        File file = new File(path);
        file.deleteOnExit();
    }

    private static void removeFolder(String path) {
        removeFolder(new File(path));
    }

    private static void removeFolder(File folder) {
        try {
            for (File file : Objects.requireNonNull(folder.listFiles())) {
                if (file.isDirectory()) {
                    removeFolder(file);
                } else if (file.isFile()) {
                    file.deleteOnExit();
                }
            }
            folder.delete();
        } catch (NullPointerException ignored) {
        }
    }

    public enum Path {
        USERS_FOLDER("src/main/resources/Users/"),
        NOT_VERIFIED_USERS_FOLDER("src/main/resources/NotVerifiedUser/"),
        CATEGORIES_FOLDER("src/main/resources/Categories/"),
        REQUESTS_FOLDER("src/main/resources/Requests/"),
        PRODUCT_FOLDER("src/main/resources/Products/"),
        CODES_FOLDER("src/main/resources/Codes/"),
        OFFS_FOLDER("src/main/resources/Offs/"),
        LOGS_FOLDER("src/main/resources/Logs/");


        private final String path;

        Path(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }
}
