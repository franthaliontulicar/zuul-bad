import java.util.HashMap;
import java.util.ArrayList;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public class Room 
{
    public String description;
    public Room northExit;
    public Room southExit;
    public Room eastExit;
    public Room westExit;
    public Room surEastExit;
    public Room northWestExit;
    public HashMap<String, Room> salidas; 
    
    private ArrayList<Item> objetos;
    

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        salidas = new HashMap<>();
        objetos = new ArrayList<>();

    }

    
    public void addItem(Item item){

        objetos.add(item);       

    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExits(String nombre, Room salida) 
    {
        salidas.put(nombre,salida);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    public Room getExit(String direccion){
        Room siguienteArea = null; 

        if(direccion.equals("north")){
            siguienteArea = salidas.get("north") ;
        }
        if(direccion.equals("east")){
            siguienteArea = salidas.get("east") ;
        }
        if(direccion.equals("south")){
            siguienteArea = salidas.get("south") ;
        }
        if(direccion.equals("west")){
            siguienteArea = salidas.get("west") ;
        }
        if(direccion.equals("surEast")){
            siguienteArea = salidas.get("surEast") ;
        }
        if(direccion.equals("northWest")){
            siguienteArea = salidas.get("northWest") ;
        }
        return siguienteArea;

    }

    /**
     * Return a description of the room's exits.
     * For example: "Exits: north east west"
     *
     * @ return A description of the available exits.
     */
    public String getExitString(){
        String salida = "north, east, south, west, surEast, northWest";
        for(String clave : salidas.keySet()){
            clave += salida;
        }

        /**if(salidas.get("north") != null){ 
        salida = "north, east, south, west, surEast, northWest";
        }
        if(salidas.get("east") != null){
        salida = "east, south, west, surEast, northWest" ;
        }
        if(salidas.get("south") != null){
        salida = "south, west, sureast, northWest" ;
        }
        if(salidas.get("west") != null){
        salida = "west, sureast, northWest" ;
        }
        if(salidas.get("surEast") != null){
        salida = "surEast, northWest" ;
        }
        if(salidas.get("northWest") != null){
        salida = "northWest, south, surEast" ;
        }*/

        return salida;

    }

    /**
     * Return a long description of this room, of the form:
     *     You are in the 'name of room'
     *     Exits: north west southwest
     * @return A description of the room, including exits.
     */
    public String getLongDescription(){
        int i = 0;
        String info =""; 
        while(i < objetos.size()){
          for(Item item: objetos){
              objetos.get(i);
              info = "Tu estas en " + getDescription() + " Salidas: " + getExitString()+ "Objeto: "+item.getDescripcion()+ " peso: "+ item.getPeso();
            }

        }
        return info;
    }

    public Item buscarItem(String descripcion){
        boolean encontrado = false;
        Item  objeto= null;
        int index = 0;
        while (index < objetos.size()){

            if(!encontrado){
                objeto = objetos.get(index);
            }
            else{
                System.out.println("Buscas por los alrededores");
            }
            index++; 
        }
        return objeto;
    }
    
     public void elimnaItem(Item item){
         int index = 0;
         boolean encontrado = false;
         while(index < objetos.size() && !encontrado){
            if(objetos.get(index).getDescripcion().equals(item.getDescripcion())){
                 objetos.remove(objetos.get(index));
                 encontrado = true;
            }
             index++;
         }
    }
}
