import controllers.AuthManager;
import controllers.UIManager;

public class Main {
    public static void main(String[] args) {
        AuthManager authManager = new AuthManager();
        UIManager uiManager = new UIManager(authManager);
        uiManager.showMainMenu();
    }
}