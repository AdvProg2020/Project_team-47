import java.io.File;

public class ClearDatabase {
    public static void main(String[] args) {
        removeFolder();
    }

    private static void removeFolder() {
        for (Path value : Path.values()) {
            remove(value.getPath());
        }
        remove("src/main/resources/UsedUsernames.json");
        remove("src/main/resources/UsedProductId.json");
        remove("src/main/resources/UsedId.json");
    }

    private static void remove(String path) {
        File folder = new File(path);
        folder.deleteOnExit();
        try {
            for (File file : folder.listFiles()) {
                file.deleteOnExit();
            }
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
