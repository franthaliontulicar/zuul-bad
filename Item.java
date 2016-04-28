
/**
 * Write a description of class Item here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Item
{
    // instance variables - replace the example below with your own
    private float peso;
    private String descripcion;

    /**
     * Constructor for objects of class Item
     */
    public Item(float pesoItem, String descripcionItem)
    {
        // initialise instance variables
        peso = pesoItem;
        descripcion = descripcionItem;
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public float getPeso()
    {
        // put your code here
        return peso;
    }
    
    public String getDescripcion(){
        return descripcion;
    }
    
    public boolean portable(){
        boolean equipar = false;
        float pesoMax =20.0F;
        if(peso <= pesoMax){
            equipar = true;
        }
        return equipar;
    }
}
