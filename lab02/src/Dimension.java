

/*******************************************************************************
 * Třída {@code Rozměr} slouží jako přepravka k uchovávání informací o rozměru 
 * objektů. Proto jsou její atributy deklarovány jako veřejné konstanty.
 *
 * @author     Rudolf Pecinovský
 * @version    2.01, duben 2004
 */
public class Dimension
{
//== KONSTANTNÍ ATRIBUTY TŘÍDY =================================================
//== PROMĚNNÉ ATRIBUTY TŘÍDY ===================================================
//== KONSTANTNÍ ATRIBUTY INSTANCÍ ==============================================

    /** Šířka objektu. */
    public final int width;

    /** Výška objektu. */
    public final int height;



//== PROMĚNNÉ ATRIBUTY INSTANCÍ ================================================
//== PŘÍSTUPOVÉ METODY VLASTNOSTÍ TŘÍDY ========================================
//== OSTATNÍ NESOUKROMÉ METODY TŘÍDY ===========================================

//##############################################################################
//== KONSTRUKTORY A TOVÁRNÍ METODY =============================================

    /***************************************************************************
     * Vytvoří instanci se zadanými rozměry.
     *
     * @param width  Šířka objektu.
     * @param height  Výška objektu.
     */
    public Dimension( int width, int height )
    {
        this.width = width;
        this.height = height;
    }



//== ABSTRAKTNÍ METODY =========================================================
//== PŘÍSTUPOVÉ METODY VLASTNOSTÍ INSTANCÍ =====================================

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


//== OSTATNÍ NESOUKROMÉ METODY INSTANCÍ ========================================

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
        return (o instanceof Dimension)            &&
               (((Dimension)o).width  ==  width)   && 
               (((Dimension)o).height  ==  height);
    }


    /***************************************************************************
     * Vrací textovou reprezentaci dané instance
     * používanou především k ladícím účelům.
     *
     * @return Požadovaná textová reprezentace.
     */
    @Override
    public String toString()
    {
        return "Rozměr[šířka=" + width + ", výška=" + height + "]";
    }


//== NOVĚ ZAVEDENÉ METODY INSTANCÍ =============================================
//== SOUKROMÉ A POMOCNÉ METODY TŘÍDY ===========================================
//== SOUKROMÉ A POMOCNÉ METODY INSTANCÍ ========================================
//== VNOŘENÉ A VNITŘNÍ TŘÍDY ===================================================
//== TESTY A METODA MAIN =======================================================
}

