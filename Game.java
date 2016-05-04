import java.util.Stack;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Player hobbit;
    private Room currentRoom;
    //private Room  anterior;
    //private int contAtras;
    private Stack <Room> anteriores;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        anteriores = new Stack<>();
        hobbit = new Player(currentRoom, 50.0F);

    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room puerta, gondor, monte, ojo, atalaya;

        // create the rooms
        puerta = new Room("  la puerta negra");
        gondor = new Room(" MInas Tirith, estas a salvo");
        monte = new Room(" monte del destino");
        ojo = new Room(" cerca de Barad thur, cuidado con el ojo que todo lo  ve");
        atalaya = new Room(" en la atalaya de los orcos, precaucion");

        // initialise room exits
        puerta.setExits("east", gondor);
        puerta.setExits("west", ojo);
        puerta.setExits("northWest", monte);
        puerta.setExits("north", monte);
        gondor.setExits("west", puerta);
        monte.setExits("east", puerta);
        monte.setExits("south", atalaya);
        monte.setExits("west", ojo);
        monte.setExits("surEast", puerta);
        ojo.setExits("north", monte);
        ojo.setExits("east", puerta);
        ojo.setExits("south", atalaya);
        ojo.setExits("surEast", atalaya);
        atalaya.setExits("north", monte);
        atalaya.setExits("east", puerta);
        atalaya.setExits("west", ojo);
        atalaya.setExits("northWest", ojo);

        puerta.addItem(new Item(2.0F, "Dardo", true));
        gondor.addItem(new Item(0.7F, "Estrella", false));    
        monte.addItem(new Item(0.2F, "Anillo ", true));
        ojo.addItem(new Item(0.8F, "Silmarilion", false));
        atalaya.addItem(new Item(5.0F, "Espada ", true));
        //anterior = null;

        currentRoom = monte;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Escribe "+Option.HELP.getComando() +" si necesitas ayuda.");
        System.out.println();
        System.out.println("You are " + currentRoom.getDescription());
        System.out.print("Exits: ");
        if(currentRoom.northExit != null) {
            System.out.print("north ");
        }
        if(currentRoom.eastExit != null) {
            System.out.print("east ");
        }
        if(currentRoom.southExit != null) {
            System.out.print("south ");
        }
        if(currentRoom.westExit != null) {
            System.out.print("west ");
        }
        if(currentRoom.surEastExit != null) {
            System.out.print("surEast ");
        }
        if(currentRoom.northWestExit != null) {
            System.out.print("northWest ");
        }

        System.out.println();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        Option comando = command.getCommandWord();
        switch(comando){
            case HELP:
            printHelp();
            break;
            case QUIT:
            wantToQuit = quit(command);
            break;
            case GO:
            hobbit.goRoom(command);
            break;
            case LOOK:
            hobbit.printLocationInfo();
            break;
            case EAT:
            System.out.println("Has comido hasta saciarte, ya no estas hambriento");
            break;
            case TAKE:
            hobbit.coger(command.getSecondWord());
            break;
            case DROP:
            hobbit.dejar(command.getSecondWord());
            break;
            case ITEM:
            hobbit.verEquipo();
            break;
            case BACK:
            hobbit.irAtras();
            hobbit.printLocationInfo();
            break;        
        }


        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("Estas perdido  en Mordor.");
        System.out.println();
        //System.out.println("Your command words are:");
        //System.out.println("   go quit help look");

        CommandWords comando= parser.getComando();
        comando.showAll();
        parser.mostrarComandos();
    }


    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }


}
