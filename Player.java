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
    private float peso;
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
        peso = 0;
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

    public void coger(String descripcion){
        Item item = currentRoom.buscarItem(descripcion);
        if(item != null && peso+item.getPeso() < pesoMaxCarga && item.esPortable()== true ){

            equipo.add(item);
            pesoMaxCarga += item.getPeso();
            System.out.println("has cogido "+item.getDescripcion());
            currentRoom.elimnaItem(item);

        } 
        else{
            System.out.println("El objeto no se puede cargar");
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
            if(!encontrado) {
                System.out.println("No llevo  nada");
            }

        }
    }

    public void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("Notas que tu  arma elfica reluce, se acercan los orcos, cuidado! Muevete!!");
        }
        else {
            //anterior = currentRoom;
            recorrido.push(currentRoom);
            currentRoom = nextRoom;
            System.out.println( currentRoom.getDescription());
            System.out.println();

        }
    }

    public void irAtras(){
        if (!recorrido.empty()){
            currentRoom = recorrido.pop();
        }
        else{
            System.out.println("No puede ir atras");
        }

    }

    public void printLocationInfo(){
        System.out.println(currentRoom.getLongDescription());     

        

    }
}
