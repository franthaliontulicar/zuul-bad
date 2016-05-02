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
    private  float pesoMaxCarga;
    /**
     * Constructor for objects of class Player
     */
    public Player(Room zona, float pesoCarga)
    {
        // initialise instance variables
        currentRoom = zona;
        equipo = new ArrayList<>();
        recorrido = new Stack<>();
        pesoMaxCarga = pesoCarga;
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

    public void verEquipo(){
        if(equipo.size() != 0){
            System.out.println("Ver equipo");
            for(Item item: equipo){
                System.out.println(item.getDescripcion()+"peso: "+item.getPeso()+" Kg ");
            }
        }
        else{
            System.out.println("No tiene nada equipado, pide ayuda a Smeagol");
        }

    }

    public float getPesoMaxCarga(){
        return pesoMaxCarga;
    }

    public boolean portable(){
        boolean equipar = false;
        for(Item item : equipo){
            if(item.getPeso() <= pesoMaxCarga){
                equipar = true;
            }

        }

        return equipar;
    }

    public void coger(String descripcion){
        Item item = currentRoom.buscarItem(descripcion);
        if(item != null){
            if(portable()== true){
                equipo.add(item);
                pesoMaxCarga += item.getPeso();
                System.out.println("has cogido "+item.getDescripcion());
            }
            else{
                System.out.println("El objeto no se puede cargar");
            }
        } 
        else{
            System.out.println("No existe el objeto");
        }

    }

    public void dejar(String descripcion){
        int index = 0;
        boolean encontrado = false;
        while(index < equipo.size() && !encontrado){
            if(equipo.get(index).getDescripcion().equals(descripcion)){
                currentRoom.addItem(equipo.get(index));
                pesoMaxCarga-= equipo.get(index).getPeso();
                equipo.remove(equipo.get(index));
                encontrado = true;
                System.out.println("he tirado el objeto");
            }
            index++;
        }
    }
}
