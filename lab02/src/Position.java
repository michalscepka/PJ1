

/*******************************************************************************
 * Třída {@code Pozice} slouží jako přepravka uchovávající informace
 * o pozici objektu. 
 * Proto jsou její atributy deklarovány jako veřejné konstanty.
 *
 * @author     Rudolf Pecinovský
 * @version    2.02, 18.2.2005
 */
public class Position
{
//== KONSTANTNÍ ATRIBUTY TŘÍDY =================================================
//== PROMĚNNÉ ATRIBUTY TŘÍDY ===================================================
//== KONSTANTNÍ ATRIBUTY INSTANCÍ ==============================================

    /** Vodorovná souřadnice dané pozice. */
    public final int x;

    /** Svislá souřadnice dané pozice. */
    public final int y;



//== PROMĚNNÉ ATRIBUTY INSTANCÍ ================================================
//== PŘÍSTUPOVÉ METODY VLASTNOSTÍ TŘÍDY ========================================
//== OSTATNÍ NESOUKROMÉ METODY TŘÍDY ===========================================

//##############################################################################
//== KONSTRUKTORY A TOVÁRNÍ METODY =============================================

    /***************************************************************************
     * Vytvoří instanci se zadanými souřadnicemi.
     *
     * @param x  Vodorovná souřadnice.
     * @param y  Svislá souřadnice.
     */
    public Position( int x, int y )
    { 
        this.x  = x;
        this.y  = y;
    }



//== ABSTRAKTNÍ METODY =========================================================
//== PŘÍSTUPOVÉ METODY VLASTNOSTÍ INSTANCÍ =====================================

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


//== OSTATNÍ NESOUKROMÉ METODY INSTANCÍ ========================================

    /***************************************************************************
     * Vrátí informaci o tom, reprezentuje-li zadaná instance stejnou pozici
     * jako objekt zadaný jako parametr.
     *
     * @param  o Objekt, s nímž je daná instance porovnávána
     * @return {@code true} reprezentuje-li objekt stejnou pozici, 
     *         jinak {@code false}
     */
    @Override
    public boolean equals( Object o )
    {
        return (o instanceof Position )   &&
               (((Position)o).x  ==  x)   && 
               (((Position)o).y  ==  y);
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
        return "Pozice[x=" + x + ",y=" + y + "]";
    }



//== SOUKROMÉ A POMOCNÉ METODY TŘÍDY ===========================================
//== SOUKROMÉ A POMOCNÉ METODY INSTANCÍ ========================================
//== VNOŘENÉ A VNITŘNÍ TŘÍDY ===================================================
//== TESTY A METODA MAIN =======================================================
}

