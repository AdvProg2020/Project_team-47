package database;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.Controller;
import model.log.BuyLog;
import model.log.Log;
import model.log.SellLog;
import model.others.Email;
import model.others.Product;
import model.others.request.Request;
import model.user.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;
import java.util.TreeSet;

public class Database {
    private static final Gson gson;
    private static OutputStream outputStream;

    static {
        gson = Controller.getGson();
    }

    private final ArrayList<CategoryData> categories;
    private final ArrayList<DiscountCodeData> codes;
    private final ArrayList<BuyLog> buyLogs;
    private final ArrayList<SellLog> sellLogs;
    private final ArrayList<OffData> offs;
    private final ArrayList<ProductData> products;
    private final ArrayList<UserData> users;
    private final ArrayList<UserData> notVerifiedUsers;
    private final ArrayList<Request> requests;
    private TreeSet<String> usedUsernames;
    private TreeSet<String> usedProductId;


    public Database() {
        categories = new ArrayList<>();
        codes = new ArrayList<>();
        offs = new ArrayList<>();
        buyLogs = new ArrayList<>();
        sellLogs = new ArrayList<>();
        products = new ArrayList<>();
        users = new ArrayList<>();
        requests = new ArrayList<>();
        notVerifiedUsers = new ArrayList<>();
        usedUsernames = new TreeSet<>();
        usedProductId = new TreeSet<>();
    }

    private static String readFile(File file) {
        try {
            InputStream inputStream = new FileInputStream(file);
            Scanner scanner = new Scanner(inputStream);
            StringBuilder fileContent = new StringBuilder();
            while (scanner.hasNextLine()) {
                fileContent.append(scanner.nextLine());
            }
            inputStream.close();
            return fileContent.toString();
        } catch (IOException ignored) {
        }
        return null;
    }

    public static void updateUsedUsernames(TreeSet<String> usedUsernames) {
        saveInFile(usedUsernames, Path.RESOURCE.getPath() + "UsedUsernames.json");
    }

    public static void updateUsedProductId(TreeSet<String> usedId) {
        saveInFile(usedId, Path.RESOURCE.getPath() + "UsedProductId.json");
    }

    public static void updateUsedId(TreeSet<String> usedId) {
        saveInFile(usedId, Path.RESOURCE.getPath() + "UsedId.json");
    }

    static void addProduct(ProductData productData, String productId) {
        saveInFile(productData, Path.PRODUCT_FOLDER.getPath() + productId + ".json");
    }

    static void addUser(UserData userData, String username) {
        String path = Path.USERS_FOLDER.getPath() + username;
        saveInFile(userData, path + ".json");
    }

    private static void createFolder(String path) {
        File folder = new File(path);
        folder.mkdirs();
    }

    private static boolean isThereFolder(String path) {
        File folder = new File(path);
        return folder.exists();
    }

    static void addNotVerifiedUser(UserData userData, String username) {
        saveInFile(userData, Path.NOT_VERIFIED_USERS_FOLDER.getPath() + username + ".json");
    }

    static void addCategory(CategoryData category) {
        saveInFile(category, Path.CATEGORIES_FOLDER.getPath() + category.getId() + ".json");
    }

    public static void addRequestToDatabase(RequestData request, String requestId) {
        saveInFile(request, Path.REQUESTS_FOLDER.getPath() + requestId + ".json");
    }

    static void addDiscountCode(DiscountCodeData codeData, String code) {
        saveInFile(codeData, Path.CODES_FOLDER.getPath() + code + ".json");
    }

    static void addOff(OffData offData, String id) {
        saveInFile(offData, Path.OFFS_FOLDER.getPath() + id + ".json");
    }

    public static void addBuyLog(BuyLog log, String logId) {
        saveInFile(log, Path.BUY_LOGS_FOLDER.getPath() + logId + ".json");
    }

    public static void addSellLog(SellLog log, String logId) {
        saveInFile(log, Path.SELL_LOGS_FOLDER.getPath() + logId + ".json");
    }

    private static void saveInFile(Object objectToSave, String path) {
        try {
            File file = new File(path);
            outputStream = new FileOutputStream(file);
            String object = Controller.getGson().toJson(objectToSave);
            Formatter formatter = new Formatter(outputStream);
            formatter.format(object);
            formatter.close();
        } catch (FileNotFoundException ignored) {
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    public static void removeCode(String code) {
        removeFile(Path.CODES_FOLDER.getPath() + code + ".json");
    }

    public static void removeOff(String offId) {
        removeFile(Path.OFFS_FOLDER.getPath() + offId + ".json");
    }

    public static void removeUser(String username) {
        removeFile(Path.USERS_FOLDER.getPath() + username + ".json");
    }

    public static void removeNotVerifiedUser(String username) {
        removeFile(Path.NOT_VERIFIED_USERS_FOLDER.getPath() + username + ".json");
    }

    public static void removeRequest(String id) {
        removeFile(Path.RESOURCE.getPath() + id + ".json");
    }

    public static void removeProduct(String productId) {
        removeFile(Path.PRODUCT_FOLDER.getPath() + productId + ".json");
    }

    public static void removeCategory(String categoryId) {
        removeFile(Path.CATEGORIES_FOLDER.getPath() + categoryId + ".json");
    }

    private static void removeFile(String path) {
        try {
            Files.deleteIfExists(Paths.get(path));
        } catch (IOException ignored) {
        }
    }

    public void startDatabaseLoading() {
        this.loadDatabase();
        this.connectRelations();
    }

    private void connectRelations() {
        CategoryData.connectRelations(this.categories);
        DiscountCodeData.connectRelations(this.codes);
        OffData.connectRelations(this.offs);
        ProductData.connectRelations(this.products);
        UserData.connectRelations(this.users, this.sellLogs, this.buyLogs);
    }

    private void loadDatabase() {
        this.creatingFolders();
        this.loadEmail();
        this.loadUsedUsernames();
        this.loadUsedProductId();
        this.loadUsedId();
        this.loadSellLogs();
        this.loadBuyLogs();
        this.loadCategories();
        this.loadCodes();
        this.loadOffs();
        this.loadProducts();
        this.loadUsers();
        this.loadNotVerifiedUsers();
        this.loadRequests();
    }

    private void creatingFolders() {
        for (Path pa : Path.values()) {
            createFolder(pa.getPath());
        }
    }

    private void loadEmail() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter email password!!");
        //amir
        //Email.setPassword(scanner.nextLine());
        Email.setPassword("apshop1818901");
        Email.checkPassword();
        scanner.close();
        File htmlPage = new File(Path.RESOURCE.getPath() + "Others/HtmlPage.html");
        Email.setHtmlPage(readFile(htmlPage));
    }

    private void loadUsedProductId() {
        File file = getFile(Path.RESOURCE.getPath() + "UsedProductId.json");
        String usedProductId = readFile(file);
        this.usedProductId = gson.fromJson(usedProductId, new TypeToken<TreeSet<String>>() {
        }.getType());
        if (usedProductId == null)
            return;
        Product.setUsedId(this.usedProductId);
    }

    private void loadUsedId() {
        File file = getFile(Path.RESOURCE.getPath() + "UsedId.json");
        String usedId = readFile(file);
        TreeSet<String> usedIdTreeSet = gson.fromJson(usedId, new TypeToken<TreeSet<String>>() {
        }.getType());
        if (usedId == null)
            return;
        Log.setUsedId(usedIdTreeSet);
    }

    private void loadUsedUsernames() {
        File file = getFile(Path.RESOURCE.getPath() + "UsedUsernames.json");
        String usedUsernames = readFile(file);
        this.usedUsernames = gson.fromJson(usedUsernames, new TypeToken<TreeSet<String>>() {
        }.getType());
        if (usedUsernames == null)
            return;
        User.setUsedUsernames(this.usedUsernames);
    }

    private void loadNotVerifiedUsers() {
        File[] files = new File(Path.NOT_VERIFIED_USERS_FOLDER.getPath()).listFiles();
        if (files == null)
            return;
        for (File file : files) {
            if (file.isDirectory())
                continue;
            addNotVerifiedUser(readFile(file));
        }
        UserData.addNotVerifiedUsers(this.notVerifiedUsers);
    }

    private void addNotVerifiedUser(String user) {
        notVerifiedUsers.add(gson.fromJson(user, UserData.class));
    }

    private void loadUsers() {
        File[] files = new File(Path.USERS_FOLDER.getPath()).listFiles();
        if (files == null)
            return;
        for (File file : files) {
            if (file.isDirectory())
                continue;
            addUser(readFile(file));
        }
        UserData.addUsers(this.users);
    }

    private void addUser(String user) {
        users.add(gson.fromJson(user, UserData.class));
    }

    private void loadProducts() {
        File[] files = new File(Path.PRODUCT_FOLDER.getPath()).listFiles();
        if (files == null)
            return;
        for (File file : files) {
            if (file.isDirectory())
                continue;
            addProduct(readFile(file));
        }
        ProductData.addProducts(this.products);
    }

    private void addProduct(String product) {
        products.add(gson.fromJson(product, ProductData.class));
    }

    private void loadRequests() {
        File[] files = new File(Path.REQUESTS_FOLDER.getPath()).listFiles();
        if (files == null)
            return;
        for (File file : files) {
            if (file.isDirectory())
                continue;
            addRequest(readFile(file));
        }
        Request.setAllNewRequests(requests);
    }

    private void addRequest(String requestString) {
        RequestData requestData = gson.fromJson(requestString, RequestData.class);
        requestData.addToRequests();
        requests.add(requestData.getRequest());
    }

    private void loadBuyLogs() {
        File[] files = new File(Path.BUY_LOGS_FOLDER.getPath()).listFiles();
        if (files == null)
            return;
        for (File file : files) {
            if (file.isDirectory())
                continue;
            addBuyLog(readFile(file));
        }
    }

    private void loadSellLogs() {
        File[] files = new File(Path.SELL_LOGS_FOLDER.getPath()).listFiles();
        if (files == null)
            return;
        for (File file : files) {
            if (file.isDirectory())
                continue;
            addSellLog(readFile(file));
        }
    }

    private void addBuyLog(String log) {
        buyLogs.add(gson.fromJson(log, BuyLog.class));
    }

    private void addSellLog(String log) {
        sellLogs.add(gson.fromJson(log, SellLog.class));
    }

    private void loadCodes() {
        File[] files = new File(Path.CODES_FOLDER.getPath()).listFiles();
        if (files == null)
            return;
        for (File file : files) {
            if (file.isDirectory())
                continue;
            addCode(readFile(file));
        }
        DiscountCodeData.addCodes(this.codes);
    }

    private void addCode(String code) {
        codes.add(gson.fromJson(code, DiscountCodeData.class));
    }

    private void loadOffs() {
        File[] files = new File(Path.OFFS_FOLDER.getPath()).listFiles();
        if (files == null)
            return;
        for (File file : files) {
            if (file.isDirectory())
                continue;
            addOff(readFile(file));
        }
        OffData.addOffs(this.offs);
    }

    private void addOff(String off) {
        offs.add(gson.fromJson(off, OffData.class));
    }

    private void loadCategories() {
        File[] files = new File(Path.CATEGORIES_FOLDER.getPath()).listFiles();
        if (files == null)
            return;
        for (File file : files) {
            if (file.isDirectory())
                continue;
            addCategory(readFile(file));
        }
        CategoryData.addCategories(this.categories);
    }

    private void addCategory(String category) {
        categories.add(gson.fromJson(category, CategoryData.class));
    }

    private File getFile(String path) {
        return new File(path);
    }

    public enum Path {
        RESOURCE("src/main/resources/"),
        USERS_FOLDER("src/main/resources/Users/"),
        NOT_VERIFIED_USERS_FOLDER("src/main/resources/NotVerifiedUser/"),
        CATEGORIES_FOLDER("src/main/resources/Categories/"),
        REQUESTS_FOLDER("src/main/resources/Requests/"),
        PRODUCT_FOLDER("src/main/resources/Products/"),
        CODES_FOLDER("src/main/resources/Codes/"),
        OFFS_FOLDER("src/main/resources/Offs/"),
        BUY_LOGS_FOLDER("src/main/resources/Logs/BuyLogs/"),
        SELL_LOGS_FOLDER("src/main/resources/Logs/SellLogs");


        private final String path;

        Path(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }
}
