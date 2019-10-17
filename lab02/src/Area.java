

public class Area {
	  /** Šířka objektu. */
    public final int width;

    /** Výška objektu. */
    public final int height;
    
    /** Vodorovná souřadnice dané pozice. */
    public final int x;

    /** Svislá souřadnice dané pozice. */
    public final int y;
    
    /***************************************************************************
     * Vytvoří instanci se zadanými rozměry.
     *
     * @param width  Šířka objektu.
     * @param height  Výška objektu.
     */
    public Area(int x, int y,  int width, int height )
    {
    	this.x  = x;
        this.y  = y;
    	this.width = width;
        this.height = height;
    }
    
    /***************************************************************************
     * Vrátí hodnotu vodorovné souřadnice dané pozice.
     *
     * @return  Požadovaná souřadnice.
     */
    public int getX()
    {
        return x;
    }


    /***************************************************************************
     * Vrátí hodnotu svislé souřadnice dané pozice.
     *
     * @return  Požadovaná souřadnice.
     */
    public int getY()
    {
        return y;
    }
    
    /***************************************************************************
     * Vrátí velikost šířky objektu.
     *
     * @return  Šířka objektu.
     */
    public int getWidth()
    {
        return width;
    }


    /***************************************************************************
     * Vrátí velikost výšky objektu.
     *
     * @return  Výška objektu.
     */
    public int getHeight()
    {
        return height;
    }
    
    
    /***************************************************************************
     * Vrátí informaci o tom, reprezentuje-li zadaná instance stejný rozměr 
     * jako objekt zadaný jako parametr.
     *
     * @param  o Objekt, s nímž je daná instance porovnávána
     * @return {@code true} reprezentuje-li objekt stejný rozměr, 
     *         jinak {@code false}
     */
    @Override
    public boolean equals( Object o )
    {
        return (o instanceof Area)            &&
               (((Area)o).width  ==  width)   && 
               (((Area)o).height  ==  height)  &&
               (((Area)o).x  ==  x)   && 
               (((Area)o).y  ==  y);
    }
    
}
