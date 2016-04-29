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

        puerta.addItem(new Item(2.0F, "Dardo"));
        gondor.addItem(new Item(0.7F, "Estrella"));    
        monte.addItem(new Item(0.2F, "Anillo "));
        ojo.addItem(new Item(0.8F, "Silmarilion"));
        atalaya.addItem(new Item(5.0F, "Espada Orca"));
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
        System.out.println("Type 'help' if you need help.");
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

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            System.out.println(currentRoom.getLongDescription());
            wantToQuit = false;
        }
        else if (commandWord.equals("eat")) {
            System.out.println("Has comido y  ahora no tienes hambre");
            wantToQuit = false;
        }
        else if (commandWord.equals("back")) {
            irAtras();
        }
         else if (commandWord.equals("take"+currentRoom.buscarItem(command.getSecondWord()))) {
           hobbit.coger(command. getSecondWord());
        }
        else if (commandWord.equals("drop")) {
           hobbit.dejar(command. getSecondWord());
        }
        else if (commandWord.equals("item")) {
           hobbit. verEquipo();
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
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        /**if(direction.equals("north")) {
        nextRoom = currentRoom.northExit;
        System.out.println("Estas en el monte del destinoa");
        System.out.println("Notas que tu  arma elfica reluce, se acercan los orcos, cuidado!");
        if(direction!="north") {
        System.out.println("Te rodean los orcos, oyes un sonido estridente, miras al cielo....Nazgul!");
        System.out.println("GAME OVER!");
        }
        }
        if(direction.equals("east")) {
        nextRoom = currentRoom.eastExit;
        System.out.println("Estas en la puerta negra");
        System.out.println("Notas que tu  arma elfica reluce, se acercan los orcos, cuidado!");
        if(direction !="east") {
        System.out.println("Miras al cielo, ves una enorme figura alada, te asustas pensando en los Nazgul, pero te fijas bien....Aguilas?");
        System.out.println("Enorabuena, has conseguido salir, estas en GONDOR!!!");
        }

        }
        if(direction.equals("south")) {
        nextRoom = currentRoom.southExit;
        System.out.println("Estas la atalaya de los orcos");
        System.out.println("Notas que tu  arma elfica reluce, se acercan los orcos, cuidado!");
        if(direction !="south") {
        System.out.println("Te rodean los orcos, oyes un sonido estridente, miras al cielo....Nazgul!");
        System.out.println("GAME OVER!");
        }
        }
        if(direction.equals("west")) {
        nextRoom = currentRoom.westExit;
        System.out.println("Estas en Barad thur");
        System.out.println("Notas que tu  arma elfica reluce, se acercan los orcos, cuidado!");
        if(direction !="west") {
        System.out.println("Te rodean los orcos, oyes un sonido estridente, miras al cielo....Nazgul!");
        System.out.println("GAME OVER!");
        }
        }

        if(direction.equals("surEast")) {
        nextRoom = currentRoom.surEastExit;
        System.out.println("Notas que tu  arma elfica reluce, se acercan los orcos, cuidado!");
        if(direction != "surEast") {
        System.out.println("Te rodean los orcos, oyes un sonido estridente, miras al cielo....Nazgul!");
        System.out.println("GAME OVER!");
        }
        }*/

        if (nextRoom == null) {
            System.out.println("Notas que tu  arma elfica reluce, se acercan los orcos, cuidado! Muevete!!");
        }
        else {
            //anterior = currentRoom;
            anteriores.push(currentRoom);
            currentRoom = nextRoom;
            System.out.println("You are " + currentRoom.getDescription());
            System.out.print("Exits: ");

            // System.out.println("Ves los ejercitos desde el cielo");

            System.out.println();
        }
    }

    private void printLocationInfo(){
        System.out.println(currentRoom.getLongDescription());     

        /**if(currentRoom.northExit != null) {
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
        }*/

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

    private void irAtras(){
        if (!anteriores.empty()){
            currentRoom = anteriores.pop();
        }
        else{
            System.out.println("No puede ir atras");
        }
        printLocationInfo();

    }


}
