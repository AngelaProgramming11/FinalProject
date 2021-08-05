import org.junit.Before;
import org.junit.Test;
import sample.Drinks;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class CafeTests {
    Drinks drink;
    @Before
    public void setup(){
        drink = new Drinks();
    }
    @Test
    public void changeToUntitledTest(){
        //no name provided
        drink.changeToUntitled(3, new String());
        assertEquals(drink.getName(), "untitled4");
        //name is provided
        drink.changeToUntitled(10, "testing");
        assertEquals(drink.getName(), "testing");
        //untitled is given
        drink.changeToUntitled(10, "untitled5");
        assertEquals(drink.getName(), "untitled11");
    }

    @Test
    public void trimNameTest(){
        //name is too long
        drink.setName("abcdefghijklmnopqrstuvw");
        drink.trimName(18);
        assertEquals(drink.getName(), "abcdefghijklmnopqr");
        //less than maximum
        drink.setName("testing");
        drink.trimName(15);
        assertEquals(drink.getName(), "testing");
        //exactly the right length
        drink.setName("PassionFruitTea1");
        assertEquals(drink.getName(), "PassionFruitTea1");
        //no name
        drink.setName("");
        drink.trimName(5);
        assertEquals(drink.getName(), "");
    }
    @Test
    public void addToFileTest() throws IOException {
        //no names
        drink.addToFile("Testing.txt");
        //create drinks
        drink.setName("Milk Tea");
        drink.getBase().setName("BrownSugarMilkTea");
        drink.getIce().setName("Ice");
        drink.getTapioca().setName("Tapioca");
        drink.getToppings().setName("Fruits");
        drink.getStraw().setName("BlueStraw");
        drink.addToFile("Testing.txt");

        //no ice, no tapioca
        drink.setName("Fruit Tea");
        drink.getBase().setName("PeachTea");
        drink.getIce().setName("no ice");
        drink.getTapioca().setName("no tapioca");
        drink.getToppings().setName("MilkFoam");
        drink.getStraw().setName("PinkStraw");
        drink.addToFile("Testing.txt");

        //read file
        FileReader fr = new FileReader("Testing.txt");
        BufferedReader br = new BufferedReader(fr);
        String tempString;
        assertEquals((tempString = br.readLine()), "null:Ingredient Name,Ingredient Name,Ingredient Name,Ingredient Name,Ingredient Name");
        assertEquals((tempString = br.readLine()), "Milk Tea:BrownSugarMilkTea,Ice,Tapioca,Fruits,BlueStraw");
        assertEquals((tempString = br.readLine()), "Fruit Tea:PeachTea,no ice,no tapioca,MilkFoam,PinkStraw");
        br.close();
        //clear file
        FileWriter fw = new FileWriter("Testing.txt");
        fw.write("");
        fw.close();
    }
}
