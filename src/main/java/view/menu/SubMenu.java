package view.menu;

public abstract class SubMenu extends Menu{
    private Menu parentMenu;
    public SubMenu(String name, Menu previousMenu, Menu parentMenu) {
        super(name, previousMenu);
        this.parentMenu = parentMenu;
    }

}
