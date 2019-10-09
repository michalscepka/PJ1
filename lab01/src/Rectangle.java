

/*******************************************************************************
 * Instance tridy {@code Obdelnik} predstavuji obdelniky urcene
 * pro praci na virtualnim platne pri prvnim seznameni s tridami a objekty.
 * Vychozi podoba tridy urcena pro prvni seznameni s tridami a objekty.
 *
 * @author Rudolf PECINOVSKY
 * @version 3.00.002
 */
public class Rectangle {

//== KONSTANTNI ATRIBUTY TRIDY =================================================

  /**
   * Pocatecni barva nakreslene instance v pripade,
   * kdy uzivatel zadnou pozadovanou barvu nezada -
   * pro obdelnik Barva.CERVENA.
   */
  public static final MyColor DEFAULT_COLOR = MyColor.RED;

  /** Maximalni povolena velikost kroku. */
  public static final int MAX_STEP = 100;

  /** Platno, na ktere se bude instance kreslit. */
  private static final Canvas CANVAS = Canvas.getInstance();

//== PROMENNE ATRIBUTY TRIDY ===================================================

  /**
   * Pocet pixelu, o nez se instance posune
   * po bezparametrickem posunovem povelu
   */
  private static int step = 50;

  /** Pocet vytvorenych instanci */
  private static int count = 0;

//== KONSTANTNI ATRIBUTY INSTANCI ==============================================

  /** Poradi vytvoreni dane instance v ramci tridy. */
  private final int order = ++count;

  /** Nazev sestavajici z nazvu tridy a poradi instance */
  private final String name = "Obdelnik_" + order;

//== PROMENNE ATRIBUTY INSTANCI ================================================

  private int xPos; //Bodova x-ova souradnice instance
  private int yPos; //Bodova y-ova souradnice instance
  private int width; //sirka v bodech
  private int height; //Vyska v bodech
  private MyColor color; //Barva instance

//== PRISTUPOVE METODY VLASTNOSTI TRIDY ========================================

  /***************************************************************************
   * Vrati velikost implicitniho kroku, o ktery se instance presune
   * pri volani bezparametrickych metod presunu.
   *
   * @return Velikost implicitniho kroku v bodech
   */
  public static int getStep() {
    return step;
  }

  /***************************************************************************
   * Nastavi velikost implicitniho kroku, o ktery se instance presune
   * pri volani bezparametrickych metod presunu.
   *
   * @param size
   *          Velikost implicitniho kroku v bodech;<br/>
   *          musi platit: 0 &lt;= velikost &lt;= {@link #MAX_STEP}
   */
  public static void setStep(int size) {
    if ((size < 0) || (size > MAX_STEP)) {
      throw new IllegalArgumentException(
          "Krok musi byt z intervalu <0;" + MAX_STEP + ">.");
    }
    step = size;
  }

//== OSTATNI METODY TRIDY ======================================================

//##############################################################################
//== KONSTRUKTORY A TOVARNI METODY =============================================

  /***************************************************************************
   * Pripravi novou instanci s implicitnimi rozmery, umistenim a barvou.
   * Instance bude umistena v levem hornim rohu platna
   * a bude mit implicitni barvu,
   * vysku rovnu kroku a sirku dvojnasobku kroku (tj. implicitne 50x100 bodu).
   */
  //constructor overloading
  public Rectangle() {
    this(0, 0, 2 * step, step);
  }

  /***************************************************************************
   * Pripravi novou instanci se zadanou polohou a rozmery
   * a implicitni barvou.
   *
   * @param x
   *          x-ova souradnice instance, x>=0, x=0 ma levy okraj platna
   * @param y
   *          y-ova souradnice instance, y>=0, y=0 ma horni okraj platna
   * @param width
   *          Sirka vytvarene instance, sirka > 0
   * @param height
   *          Vyska vytvarene instance, vyska > 0
   */
  public Rectangle(int x, int y, int width, int height) {
    this(x, y, width, height, DEFAULT_COLOR);
  }

  /***************************************************************************
   * Pripravi novou instanci se zadanymi rozmery, polohou a barvou.
   *
   * @param x
   *          x-ova souradnice instance, x>=0, x=0 ma levy okraj platna
   * @param y
   *          y-ova souradnice instance, y>=0, y=0 ma horni okraj platna
   * @param width
   *          Sirka vytvarene instance, sirka > 0
   * @param height
   *          Vyska vytvarene instance, vyska > 0
   * @param color
   *          Barva vytvarene instance
   */
  public Rectangle(int x, int y, int width, int height, MyColor color) {
    //Test platnosti parametru
    if ((x < 0) || (y < 0) || (width <= 0) || (height <= 0)) {
      throw new IllegalArgumentException(
          "\nParametry nemaji povolene hodnoty: x=" + x + ", y=" + y
              + ", sirka=" + width + ", vyska=" + height);
    }

    //Parametry akceptovany --> muzeme tvorit
    xPos = x;
    yPos = y;
    this.width = width;
    this.height = height;
    this.color = color;
    paint();
  }

  public Rectangle getInstance() {
    return this;
  }

//== PRISTUPOVE METODY ATRIBUTU INSTANCI =======================================

  /***************************************************************************
   * Vrati x-ovou souradnici pozice instance.
   *
   * @return x-ova souradnice.
   */
  public int getX() {
    return xPos;
  }

  public void setX(int x) {
    if ((x < 0) || ((x + width) > CANVAS.getWidth())) {
      throw new IllegalArgumentException("X :" + x + " is out of range.");
    }
    xPos = x;
  }

  /***************************************************************************
   * Vrati y-ovou souradnici pozice instance.
   *
   * @return y-ova souradnice.
   */
  public int getY() {
    return yPos;
  }

  /***************************************************************************
   * Nastavi novou pozici instance.
   *
   * @param x
   *          Nova x-ova pozice instance
   * @param y
   *          Nova y-ova pozice instance
   */
  public void setPosition(int x, int y) {
    erase();
    xPos = x;
    yPos = y;
    paint();
  }

  /***************************************************************************
   * Vrati sirku instance.
   *
   * @return Sirka instance v bodech
   */
  public int getWidth() {
    return width;
  }

  /***************************************************************************
   * Vrati vysku instance.
   *
   * @return Vyska instance v bodech
   */
  public int getHeight() {
    return height;
  }

  /***************************************************************************
   * Nastavi novy "ctvercovy" rozmer instance -
   * na zadany rozmer se nastavi vyska i sirka.
   *
   * @param size
   *          Nove nastavovany rozmer v obou smerech; rozmer>0
   */
  public void setDimension(int size) {
    setDimension(size, size);
  }

  /***************************************************************************
   * Nastavi nove rozmery instance.
   *
   * @param width
   *          Nove nastavovana sirka; sirka>=0
   * @param height
   *          Nove nastavovana vyska; vyska>=0
   */
  public void setDimension(int width, int height) {
    if ((width < 0) || (height < 0)) {
      throw new IllegalArgumentException(
          "Rozmery musi byt nezaporne: sirka=" + width + ", vyska=" + height);
    }
    erase();
    this.width = width;
    this.height = height;
    paint();
  }

  /***************************************************************************
   * Vrati barvu instance.
   *
   * @return Instance tridy Barva definujici nastavenou barvu.
   */
  public MyColor getColor() {
    return color;
  }

  /***************************************************************************
   * Nastavi novou barvu instance.
   *
   * @param newColor
   *          Pozadovana nova barva.
   */
  public void setColor(MyColor newColor) {
    color = newColor;
    paint();
  }

  /***************************************************************************
   * Vrati nazev instance, tj. nazev jeji tridy nasledovany poradim.
   *
   * @return Retezec s nazvem instance.
   */
  public String getName() {
    return name;
  }

//== PREKRYTE METODY IMPLEMENTOVANYCH ROZHRANI =================================
//== PREKRYTE ABSTRAKTNI METODY RODICOVSKE TRIDY ===============================
//== PREKRYTE KONKRETNI METODY RODICOVSKE TRIDY ================================

  /***************************************************************************
   * Prevede instanci na retezec. Pouziva se predevsim pri ladeni.
   *
   * @return Retezcova reprezentace dane instance.
   */
  @Override
  public String toString() {
    return name + "_(x=" + xPos + ",y=" + yPos + ",sirka=" + width + ",vyska="
        + height + ",barva=" + color + ")";
  }

//== NOVE ZAVEDENE METODY INSTANCI =============================================

  /***************************************************************************
   * Vykresli obraz sve instance na platno.
   */
  public void paint() {
    CANVAS.setColorOfForeground(color);
    CANVAS
        .fill(new java.awt.geom.Rectangle2D.Double(xPos, yPos, width, height));
  }

  /***************************************************************************
   * Smaze obraz sve instance z platna (nakresli ji barvou pozadi platna).
   */
  public void erase() {
    CANVAS.setColorOfForeground(CANVAS.getColorOfBackground());
    CANVAS
        .fill(new java.awt.geom.Rectangle2D.Double(xPos, yPos, width, height));
  }

  /***************************************************************************
   * Presune instanci o zadany pocet bodu vpravo,
   * pri zaporne hodnote parametru vlevo.
   *
   * @param distance
   *          Vzdalenost, o kterou se instance presune.
   */
  public void moveRight(int distance) {
    setPosition(xPos + distance, yPos);
  }

  /***************************************************************************
   * Presune instanci o krok bodu vpravo.
   */
  public void moveRight() {
    moveRight(step);
  }

  /***************************************************************************
   * Presune instanci o krok bodu vlevo.
   */
  public void moveLeft() {
    moveRight(-step);
  }

  /***************************************************************************
   * Presune instanci o zadany pocet bodu dolu,
   * pri zaporne hodnote parametru nahoru.
   *
   * @param vzdalenost
   *          Pocet bodu, o ktere se instance presune.
   */
  public void moveDown(int distance) {
    setPosition(xPos, yPos + distance);
  }

  /***************************************************************************
   * Presune instanci o krok bodu dolu.
   */
  public void moveDown() {
    moveDown(step);
  }

  /***************************************************************************
   * Presune instanci o krok bodu nahoru.
   */
  public void moveUp() {
    moveDown(-step);
  }

//== SOUKROME A POMOCNE METODY TRIDY ===========================================
//== SOUKROME A POMOCNE METODY INSTANCI ========================================
//== VNORENE A VNITRNI TRIDY ===================================================
//== TESTY A METODA MAIN =======================================================
}
