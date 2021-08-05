package sample;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Drinks {
    //stores info of a drink
    private String name;
    private Ingredient base = new Ingredient();
    private Ingredient ice = new Ingredient();
    private Ingredient tapioca = new Ingredient();
    private Ingredient toppings = new Ingredient();
    private Ingredient straw = new Ingredient();

    //constructors
    public Drinks(){
    }
    public Drinks(String name, Ingredient base, Ingredient ice, Ingredient tapioca, Ingredient toppings, Ingredient straw){
        this.base = base;
        this.ice = ice;
        this.tapioca = tapioca;
        this.toppings = toppings;
        this.straw = straw;
    }

    //setters and getters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ingredient getBase() {
        return base;
    }

    public void setBase(Ingredient base) {
        this.base = base;
    }

    public Ingredient getIce() {
        return ice;
    }

    public void setIce(Ingredient ice) {
        this.ice = ice;
    }

    public Ingredient getTapioca() {
        return tapioca;
    }

    public void setTapioca(Ingredient tapioca) {
        this.tapioca = tapioca;
    }

    public Ingredient getToppings() {
        return toppings;
    }

    public void setToppings(Ingredient toppings) {
        this.toppings = toppings;
    }

    public Ingredient getStraw() {
        return straw;
    }

    public void setStraw(Ingredient straw) {
        this.straw = straw;
    }

    public int changeToUntitled(int numOfUntitled, String drinkName){
        //change drink name to untitled in case no name was provided
        if(drinkName.equals("") || drinkName.startsWith("untitled")){
            numOfUntitled++;
            this.name = "untitled" + numOfUntitled;
        }
        else{
            this.name = drinkName;
        }
        return numOfUntitled;
    }

    public void trimName(int maxNameChar){
        //make sure name isn't too long (won't fit on the display)
        if(this.name != null) {
            if (this.name.length() > maxNameChar) {
                String tempString = this.name.substring(0, maxNameChar);
                this.name = tempString;
            }
        }
    }

    public void addToFile(String fileName) throws IOException {
        //add drink to specified file
        FileWriter fw = new FileWriter(fileName, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(this.name + ":" + this.base.getName() + "," + this.ice.getName() + "," + this.tapioca.getName() + "," + this.toppings.getName() + "," + this.straw.getName() + "\n");
        bw.close();
    }
}