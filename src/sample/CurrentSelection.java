package sample;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class CurrentSelection {
    private AnchorPane currentMenu = new AnchorPane();
    private Button currentBtn;

    //constructors
    CurrentSelection(){
    }
    CurrentSelection(AnchorPane currentMenu, Button currentBtn){
        this.currentMenu = currentMenu;
        this.currentBtn = currentBtn;
    }

    //setters and getters
    public AnchorPane getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(AnchorPane currentMenu) {
        this.currentMenu = currentMenu;
    }

    public Button getCurrentBtn() {
        return currentBtn;
    }

    public void setCurrentBtn(Button currentBtn) {
        this.currentBtn = currentBtn;
    }
}