
/**
 * Enumeration class Option - write a description of the enum class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public enum Option
{
    GO("Ir"), QUIT("Salir"), HELP("Ayudar"), LOOK("Vigilar"), EAT("Comer"), BACK("Volver"), TAKE("Agarrar"), DROP("Dejar"), ITEM("Equipo"), UNKNOWN(" ");
    private String comando;

    private Option(String comandoIntro){
        comando = comandoIntro;
    }

    public String getComando(){
        return comando;
    }
}
