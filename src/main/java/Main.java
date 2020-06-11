import database.Database;

public class Main {
    public static void main(String[] args) {
        new Database().startDatabaseLoading();
        graphicView.Main.getInstance().run(args);
    }
}
