package sample;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.util.ArrayList;

public class Controller {
    public AnchorPane createDrinksPane;
    public AnchorPane displayDrinksPane;
    public Button startBtn;
    public Label selectionTitle;

    //menu buttons
    public ImageView milkTeaBtn;
    public ImageView fruitTeaBtn;
    public ImageView basicMilkTeaBtn;
    public ImageView brownSugarMilkTeaBtn;
    public ImageView greenTeaMilkTeaBtn;
    public ImageView passionFruitTeaBtn;
    public ImageView peachTeaBtn;
    public ImageView strawberryMatchaTeaBtn;
    public ImageView wantsIce;
    public ImageView wantsTapioca;
    public ImageView herbalJellyBtn;
    public ImageView redBeanBtn;
    public ImageView fruitsBtn;
    public ImageView milkFoamBtn;
    public ImageView poppingBobaBtn;
    public ImageView pinkStrawBtn;
    public ImageView blueStrawBtn;
    public ImageView greenStrawBtn;
    public ImageView yellowStrawBtn;
    public TextField newDrinkName;
    //confirm selection buttons
    public Button selectDrinkTypeBtn;
    public Button selectMilkTeaBtn;
    public Button selectFruitTeaBtn;
    public Button selectToppingsBtn;
    public Button selectIceBtn;
    public Button selectTapiocaBtn;
    public Button selectStrawBtn;
    public Button enterNameBtn;
    //menu
    public AnchorPane drinkTypeMenu;
    public AnchorPane milkTeaMenu;
    public AnchorPane fruitTeaMenu;
    public AnchorPane iceMenu;
    public AnchorPane tapiocaMenu;
    public AnchorPane toppingsMenu;
    public AnchorPane strawMenu;
    public AnchorPane nameMenu;
    //drink ingredients
    public ImageView drinkBase;
    public ImageView ice;
    public ImageView tapioca;
    public ImageView topping;
    public ImageView straw;
    public ArrayList<Drinks> drinks = new ArrayList<>();
    public Drinks newDrink = new Drinks();
    public Ingredient selectedIngredient = new Ingredient();
    public CurrentSelection currentSelection = new CurrentSelection();
    public ImageView highlightIngredient;
    public Label drinkNameLbl;
    public int numOfUntitled = 0;
    public int maxNameChar = 16;
    public int imageWidth = 100, imageHeight = 153, borderWidth = 45, imageTextWidth = 0, textHeight = 17, itemsPerRow = 4;

    //get previously made drinks from file
    public void initialize() throws IOException{
        //in case file doesn't exist
        FileWriter fw = new FileWriter("CreatedDrinks.txt", true);
        fw.close();
        //read from file
        //info format: name:'base','ice','tapioca','toppings','straw'  |||  each line is a new drinks
        FileReader fr = new FileReader("CreatedDrinks.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br.readLine()) != null){
            //separate drink name from drink components
            String array[] = line.split(":");
            Drinks tempDrink = new Drinks();
            tempDrink.setName(array[0]);
            //count how many drinks are untitled so untitled drinks can be assigned a number, ex: untitled1, untitled2, etc...
            if(array[0].startsWith("untitled")){
                numOfUntitled++;
            }
            //separate drink components from each other
            String drinkComponent[] = array[1].split(",");
            //find image of each component
            //drink base
            tempDrink.getBase().setName(drinkComponent[0]);
            String location = "Images/" + drinkComponent[0] + ".PNG";
            tempDrink.getBase().setIngredientImage(new ImageView(new Image(this.getClass().getResourceAsStream(location))));
            //ice
            if(drinkComponent[1].equals("no ice")){
                tempDrink.getIce().setName("no ice");
            }
            else {
                location = "Images/" + tempDrink.getBase().getName() + "Ice.PNG";
                tempDrink.getIce().setName(tempDrink.getBase().getName() + "Ice");
                tempDrink.getIce().setIngredientImage(new ImageView(new Image(this.getClass().getResourceAsStream(location))));
            }
            //tapioca
            if(drinkComponent[2].equals("no tapioca")){
                tempDrink.getTapioca().setName("no tapioca");
            }
            else {
                location = "Images/" + tempDrink.getBase().getName() + "Ice.PNG";
                tempDrink.getTapioca().setName(tempDrink.getBase().getName() + "Ice");
                tempDrink.getTapioca().setIngredientImage(new ImageView(new Image(this.getClass().getResourceAsStream(location))));
            }
            //toppings
            tempDrink.getToppings().setName(drinkComponent[3]);
            location = "Images/" + drinkComponent[3] + ".PNG";
            tempDrink.getToppings().setIngredientImage(new ImageView(new Image(this.getClass().getResourceAsStream(location))));
            //straws
            tempDrink.getStraw().setName(drinkComponent[4]);
            location = "Images/" + drinkComponent[4] + ".PNG";
            tempDrink.getStraw().setIngredientImage(new ImageView(new Image(this.getClass().getResourceAsStream(location))));
            //add drink to list
            this.drinks.add(tempDrink);
            //display drink
            displayDrinks(tempDrink);
        }
        br.close();
    }


    //display drinks
    private void displayDrinks(Drinks drink){
        //text width = image width, border width = border height
        //find which row the item should be on
        int row = 0;
        int numOfItems = this.drinks.size();
        if(numOfItems%itemsPerRow == 0){
            row = (numOfItems - 1)/itemsPerRow;
        }
        else {
            row = numOfItems / itemsPerRow;
        }
        //find which column
        int column = numOfItems%itemsPerRow - 1;
        if(column == -1){
            column = column + itemsPerRow;
        }
        //calculate coordinates of image
        double coordinates[] = new double[2];
        coordinates[0] = borderWidth + column*(imageWidth + borderWidth);
        coordinates[1] = borderWidth + row*(imageHeight + imageTextWidth + textHeight + borderWidth);
        //increase AnchorPane size if needed to encompass the item
        int paneHeight = (int)Math.round(coordinates[1] + imageHeight + imageTextWidth + textHeight + borderWidth);
        if(displayDrinksPane.getHeight() < paneHeight){
            displayDrinksPane.setPrefHeight(paneHeight);
        }
        //insert images of all ingredients
        setImageViewLayout(drink.getBase().getIngredientImage(), coordinates[0], coordinates[1], imageWidth, imageHeight, true);
        String location = "Images/CupOutline.PNG";
        ImageView tempImageView = new ImageView(new Image(this.getClass().getResourceAsStream(location)));
        setImageViewLayout(tempImageView, coordinates[0], coordinates[1], imageWidth, imageHeight, true);
        setImageViewLayout(drink.getIce().getIngredientImage(), coordinates[0], coordinates[1], imageWidth, imageHeight, true);
        setImageViewLayout(drink.getTapioca().getIngredientImage(), coordinates[0], coordinates[1], imageWidth, imageHeight, true);
        setImageViewLayout(drink.getToppings().getIngredientImage(), coordinates[0], coordinates[1], imageWidth, imageHeight, true);
        setImageViewLayout(drink.getStraw().getIngredientImage(), coordinates[0], coordinates[1], imageWidth, imageHeight, true);

        //create + inset label for drink name
        Label drinkName = new Label();
        drinkName.setLayoutX(coordinates[0]);
        drinkName.setLayoutY((coordinates[1] + imageHeight + imageTextWidth));
        drinkName.setPrefWidth(imageWidth);
        drinkName.setPrefHeight(textHeight);
        drinkName.setAlignment(Pos.CENTER);
        drinkName.setText(drink.getName());

        //add all to the anchorpane
        displayDrinksPane.getChildren().addAll(drink.getBase().getIngredientImage(), tempImageView, drink.getIce().getIngredientImage(), drink.getTapioca().getIngredientImage(), drink.getToppings().getIngredientImage(), drink.getStraw().getIngredientImage(), drinkName);
    }

    //move imageview to location (layout)
    public void setImageViewLayout(ImageView imageView, double x, double y, int width, int height, boolean preserveRatio){
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        if(preserveRatio){
            imageView.setPreserveRatio(true);
        }
    }

    //start
    public void start(MouseEvent mouseEvent){
        //reset variables + cup
        String relativeLocation = "Images/EmptyCup.PNG";
        Image tempImage = new Image(this.getClass().getResourceAsStream(relativeLocation));
        drinkBase.setImage(tempImage);
        ice.setVisible(false);
        tapioca.setVisible(false);
        topping.setVisible(false);
        straw.setVisible(false);
        newDrink = new Drinks();
        drinkNameLbl.setText("");

        //change graphics to next menu
        startBtn.setVisible(false);
        drinkTypeMenu.setVisible(true);
        selectedIngredient = null;
        currentSelection.setCurrentBtn(selectDrinkTypeBtn);
        currentSelection.setCurrentMenu(drinkTypeMenu);
        selectionTitle.setText("Choose drink type:");
    }

    //select a drink type (milk or tea)
    public void selectDrinkType(MouseEvent mouseEvent) {
        //if milk tea is selected
        if(selectedIngredient.getIngredientImage().equals(milkTeaBtn)){
            //change graphics to milk tea menu
            changeSelection(drinkTypeMenu, milkTeaMenu, selectDrinkTypeBtn, true, selectMilkTeaBtn, null, null,"Choose a milk tea:");
        }
        //if fruit tea is selected
        else if(selectedIngredient.getIngredientImage().equals(fruitTeaBtn)){
            //change graphics to fruit tea menu
            changeSelection(drinkTypeMenu, fruitTeaMenu, selectDrinkTypeBtn, true, selectFruitTeaBtn, null, null,"Choose a fruit tea:");
        }
    }

    //drink base is selected
    public void selectDrinkBase(MouseEvent mouseEvent){
        //show selected ingredient
        String imageLocation = "Images/" + selectedIngredient.getName() + ".PNG";
        drinkBase.setImage(new Image(this.getClass().getResourceAsStream(imageLocation)));
        //add selected ingredient
        newDrink.getBase().setName(selectedIngredient.getName());
        newDrink.getBase().setIngredientImage(new ImageView(new Image(this.getClass().getResourceAsStream(imageLocation))));
        //change graphics to next menu (ice)
        changeSelection(milkTeaMenu, iceMenu, selectMilkTeaBtn, true, selectIceBtn, drinkBase, imageLocation,"Choose to add ice:");
        fruitTeaMenu.setVisible(false);
        selectFruitTeaBtn.setDisable(true);
    }

    //ice option
    public void selectIce(MouseEvent mouseEvent){
        //wants ice
        if(selectedIngredient != null) {
            //find specific ice (image of ice for each drink base is different b/c of color)
            String imageLocation = "Images/" + newDrink.getBase().getName() + "Ice.PNG";
            Image tempImage = new Image(this.getClass().getResourceAsStream(imageLocation));
            newDrink.getIce().setName(selectedIngredient.getName());
            newDrink.getIce().getIngredientImage().setImage(tempImage);
            //change graphics (to tapioca menu)
            changeSelection(iceMenu, tapiocaMenu, selectIceBtn, false, selectTapiocaBtn, ice, imageLocation,"Choose to add tapioca:");
        }
        //no ice
        else{
            newDrink.getIce().setName("no ice");
            //change graphics (to tapioca menu)
            changeSelection(iceMenu, tapiocaMenu, selectIceBtn, false, selectTapiocaBtn, null, null,"Choose to add tapioca:");
        }
    }

    //tapioca option
    public void selectTapioca(MouseEvent mouseEvent){
        //wants tapioca
        if(selectedIngredient != null) {
            //find specific ice (image of ice for each drink base is different b/c of color)
            String imageLocation = "Images/" + newDrink.getBase().getName() + "Tapioca.PNG";
            Image tempImage = new Image(this.getClass().getResourceAsStream(imageLocation));
            newDrink.getTapioca().setName(selectedIngredient.getName());
            newDrink.getTapioca().getIngredientImage().setImage(tempImage);
            //change graphics (to toppings)
            changeSelection(tapiocaMenu, toppingsMenu, selectTapiocaBtn, false, selectToppingsBtn, tapioca, imageLocation, "Choose a topping:");
        }
        //doesn't want tapioca
        else{
            newDrink.getTapioca().setName("no tapioca");
            //change graphics (to toppings)
            changeSelection(tapiocaMenu, toppingsMenu, selectTapiocaBtn, false, selectToppingsBtn, null, null, "Choose a topping:");
        }
    }

    //topping is selected
    public void selectToppings(MouseEvent mouseEvent) {
        String imageLocation = "Images/" + selectedIngredient.getName() + ".PNG";
        newDrink.getToppings().setName(selectedIngredient.getName());
        newDrink.getToppings().setIngredientImage(new ImageView(new Image(this.getClass().getResourceAsStream(imageLocation))));
        //change graphics to straw menu
        changeSelection(toppingsMenu, strawMenu, selectToppingsBtn, true, selectStrawBtn, topping, imageLocation, "Choose a straw:");
    }

    //straw is selected
    public void selectStraw(MouseEvent mouseEvent){
        String imageLocation = "Images/" + selectedIngredient.getName() + ".PNG";
        newDrink.getStraw().setName(selectedIngredient.getName());
        newDrink.getStraw().setIngredientImage(new ImageView(new Image(this.getClass().getResourceAsStream(imageLocation))));
        //change graphics
        changeSelection(strawMenu, nameMenu, selectToppingsBtn, true, selectStrawBtn, straw, imageLocation, "Give your drink a name:");
    }

    //name is selected
    public void finishDrink(MouseEvent mouseEvent) throws IOException{
        //trim name if needed
        newDrink.trimName(maxNameChar);
        //in case no name was inputed
        numOfUntitled = newDrink.changeToUntitled(numOfUntitled, newDrinkName.getText());
        drinkNameLbl.setText(newDrink.getName());
        newDrinkName.setText("");
        //reset game
        nameMenu.setVisible(false);
        startBtn.setVisible(true);
        selectionTitle.setText("");
        //add drink to list
        drinks.add(newDrink);
        //add drink to display
        displayDrinks(newDrink);
        //add to file
        newDrink.addToFile("CreatedDrinks.txt");
    }

    public void changeSelection(AnchorPane previousMenu, AnchorPane nextMenu, Button previousBtn, boolean disablePreviousBtn, Button nextButton, ImageView displayedIngredient, String ingredientImgLocation, String selectionTitleText){
        //clear previous graphics and make visible next menu
        previousMenu.setVisible(false);
        if(disablePreviousBtn) {
            previousBtn.setDisable(true);
        }
        nextMenu.setVisible(true);
        //nextButton.setDisable(false);
        selectionTitle.setText(selectionTitleText);
        currentSelection.setCurrentMenu(nextMenu);
        currentSelection.setCurrentBtn(nextButton);
        highlightIngredient.setVisible(false);
        //display ingredient selected in cup
        if(displayedIngredient != null) {
            displayedIngredient.setImage(new Image(this.getClass().getResourceAsStream(ingredientImgLocation)));
            displayedIngredient.setVisible(true);
        }
        //clear selected ingredient in case it is accidentally accessed
        selectedIngredient = null;
    }

    //record what ingredient was chosen in case any changes are made before pressing 'next'
    public void selectIngredient(MouseEvent mouseEvent) {
        //make sure what is clicked is an imageview
        if(mouseEvent.getSource() instanceof ImageView && ((ImageView) mouseEvent.getSource()).getImage() != null){
            selectedIngredient = new Ingredient();
            selectedIngredient.pointToImageView((ImageView)mouseEvent.getSource());
            selectedIngredient.setName(((ImageView) mouseEvent.getSource()).getId());
            currentSelection.getCurrentBtn().setDisable(false);
            //highlight selected ingredient
            highlightIngredient.setLayoutX((currentSelection.getCurrentMenu().getLayoutX() + selectedIngredient.getIngredientImage().getLayoutX()));
            highlightIngredient.setLayoutY((currentSelection.getCurrentMenu().getLayoutY() + selectedIngredient.getIngredientImage().getLayoutY()));
            highlightIngredient.setFitWidth(selectedIngredient.getIngredientImage().getFitWidth());
            highlightIngredient.setFitHeight(selectedIngredient.getIngredientImage().getFitHeight());
            highlightIngredient.setVisible(true);
        }
    }

    //unselect ingredient
    public void unselectIngredient(MouseEvent mouseEvent) {
        selectedIngredient = null;
        highlightIngredient.setVisible(false);
    }
}