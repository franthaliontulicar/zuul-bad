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

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExits(Room north, Room east, Room south, Room west, Room surEast, Room northWest) 
    {
        if(north != null)
            northExit = north;
        if(east != null)
            eastExit = east;
        if(south != null)
            southExit = south;
        if(west != null)
            westExit = west;
        if(surEast != null)
            surEastExit = surEast;   
        if(northWest != null)
            northWestExit = northWest;      
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
            siguienteArea = northExit ;
        }
        if(direccion.equals("east")){
            siguienteArea = eastExit ;
        }
        if(direccion.equals("south")){
            siguienteArea = southExit ;
        }
        if(direccion.equals("west")){
            siguienteArea = westExit ;
        }
        if(direccion.equals("surEast")){
            siguienteArea = surEastExit ;
        }
        if(direccion.equals("northWest")){
            siguienteArea = northWestExit ;
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
        String salida = null;
        if(northExit != null){ 
            salida = "north" ;
        }
        if(eastExit != null){
            salida = "east" ;
        }
        if(southExit != null){
            salida = "south" ;
        }
        if( westExit != null){
            salida = "west" ;
        }
        if(surEastExit != null){
           salida = "surEast" ;
        }
        if(northWestExit != null){
           salida = "northWest" ;
        }
        
        return salida;
        
    }
}
