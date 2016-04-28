import java.util.Stack;
import java.util.ArrayList;
/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player
{
    // instance variables - replace the example below with your own
    private Room currentRoom;
    private ArrayList<Item> equipo;
    private Stack<Room> recorrido;

    /**
     * Constructor for objects of class Player
     */
    public Player(Room zona)
    {
        // initialise instance variables
        currentRoom = zona;
        equipo = new ArrayList<>();
        recorrido = new Stack<>();
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public Room getInicio()
    {
        // put your code here
        return currentRoom;
    }

    public int getNumItems(){
        return equipo.size();
    }

    public int getNumZonas(){
        return recorrido.size();

    }   

    

    public void coger(String descripcion){
        Item item = currentRoom.buscarItem(descripcion);
        if(currentRoom != null){
            equipo.add(item);

        } 
        else{
            System.out.println("No puedes coger el objeto");
        }

    }

    public void dejar(String descripcion){
        Item item = currentRoom.buscarItem(descripcion);
        if(currentRoom != null){
            equipo.remove(item);

        }
    }
}