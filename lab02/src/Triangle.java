/*******************************************************************************
 * Instance tridy {@code Trojuhelnik} predstavuji trojuhelniky urcene
 * pro praci na virtualnim platne pri prvnim seznameni s tridami a objekty.
 * Vychozi podoba tridy urcena pro prvni seznameni s tridami a objekty.
 *
 * @author Rudolf PECINOVSKY
 * @version 3.00.002
 */
public class Triangle {
//== KONSTANTNI ATRIBUTY TRIDY =================================================

  /**
   * Pocatecni barva nakreslene instance v pripade,
   * kdy uzivatel zadnou pozadovanou barvu nezada -
   * pro trojuhelnik Barva.ZELENA.
   */
  public static final MyColor DEFAULT_COLOR = MyColor.GREEN;

  /**
   * Smer, kam bude ukazovat vrcholt trojuhelniku v pripade,
   * kdy uzivatel zadny preferovny smer nezada.
   */
  public static final Direction8 DEFAULT_DIRECTION = Direction8.NORTH;

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
  private String name = "Trojuhelnik_" + order;

//== PROMENNE ATRIBUTY INSTANCI ================================================

  private int xPos; //Bodova x-ova souradnice instance
  private int yPos; //Bodova y-ova souradnice instance
  private int width; //sirka v bodech
  private int height; //Vyska v bodech
  private MyColor color; //Barva instance
  private Direction8 direction; //Smer, do nejz je otocen vrchol trojuhelniku

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
   * Pripravi novou instanci s implicitnimi rozmery, umistenim, barvou
   * a natocenim.
   * Instance bude umistena v levem hornim rohu platna
   * a bude mit implicitni barvu,
   * vysku rovnu kroku a sirku dvojnasobku kroku (tj. implicitne 50x100 bodu)
   * a bude natocena vrocholem na sever.
   */
  public Triangle() {
    this(0, 0, 2 * step, step);
  }

  /***************************************************************************
   * Pripravi novou instanci se zadanou polohou a rozmery
   * a implicitni barvou a smerem natoceni.
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
  public Triangle(int x, int y, int width, int height) {
    this(x, y, width, height, DEFAULT_COLOR, DEFAULT_DIRECTION);
  }

  /***************************************************************************
   * Pripravi novou instanci se zadanou polohou, rozmery a smerem natoceni
   * a s implicitni barvou.
   *
   * @param x
   *          x-ova souradnice, x>=0, x=0 ma levy okraj platna
   * @param y
   *          y-ova souradnice, y>=0, y=0 ma horni okraj platna
   * @param width
   *          Sirka instance, sirka > 0
   * @param height
   *          Vyska instance, vyska > 0
   * @param direction
   *          Smer, do nejz bude natocen vrchol trojuhelniku -
   *          je treba zadat nekterou z instanci tridy Smer8
   */
  public Triangle(int x, int y, int width, int height, Direction8 direction) {
    this(x, y, width, height, DEFAULT_COLOR, direction);
  }

  /***************************************************************************
   * Pripravi novou instanci se zadanymi rozmery, polohou a barvou.
   * Smer natoceni bude implicitni, tj. na sever.
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
  public Triangle(int x, int y, int width, int height, MyColor color) {
    this(x, y, width, height, color, DEFAULT_DIRECTION);
  }

  /***************************************************************************
   * Pripravi novou instanci se zadanymi rozmery, polohou, barvou,
   * i smerem natoceni.
   *
   * @param x
   *          x-ova souradnice instance, x>=0, x=0 ma levy okraj platna
   * @param y
   *          y-ova souradnice instance, y>=0, y=0 ma horni okraj platna
   * @param sirka
   *          Sirka vytvarene instance, sirka > 0
   * @param vyska
   *          Vyska vytvarene instance, vyska > 0
   * @param barva
   *          Barva vytvarene instance
   * @param direction
   *          Smer, do nejz bude natocen vrchol trojuhelniku -
   *          je treba zadat nekterou z instanci tridy Smer8
   */
  public Triangle(int x, int y, int width, int height, MyColor color,
      Direction8 direction) {
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
    this.direction = direction;
    paint();
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
   * Vrati smer instance. tj. smer, co nejz je otocen vrchol.
   *
   * @return Instance tridy Smer8 definujici nastaveny smer.
   */
  public Direction8 getDirection() {
    return direction;
  }

  /***************************************************************************
   * Nastavi novy smer instance.
   *
   * @param newDirection
   *          Pozadovany novy smer.
   */
  public void setDirection(Direction8 newDirection) {
    direction = newDirection;
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
    int[][] points = getVertices();
    CANVAS.fill(new java.awt.Polygon(points[0], points[1], 3));
  }

  /***************************************************************************
   * Smaze obraz sve instance z platna (nakresli ji barvou pozadi platna).
   */
  public void erase() {
    CANVAS.setColorOfForeground(CANVAS.getColorOfBackground());
    int[][] points = getVertices();
    CANVAS.fill(new java.awt.Polygon(points[0], points[1], 3));
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
   * @param distance
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

  /***************************************************************************
   * Vrati matici se souradnicemi vrcholu daneho trojuhelniku.
   *
   * @return Pozadovana matice
   */
  private int[][] getVertices() {
    int[] xpoints = null;
    int[] ypoints = null;

    switch (direction) {
      case EAST:
        xpoints = new int[]{xPos, xPos + (width), xPos};
        ypoints = new int[]{yPos, yPos + (height / 2), yPos + height};
        break;

      case NORTHEAST:
        xpoints = new int[]{xPos, xPos + width, xPos + width};
        ypoints = new int[]{yPos, yPos, yPos + height};
        break;

      case NORTH:
        xpoints = new int[]{xPos, xPos + (width / 2), xPos + width};
        ypoints = new int[]{yPos + height, yPos, yPos + height};
        break;

      case NORTHWEST:
        xpoints = new int[]{xPos, xPos, xPos + width};
        ypoints = new int[]{yPos + height, yPos, yPos};
        break;

      case WEST:
        xpoints = new int[]{xPos, xPos + width, xPos + width};
        ypoints = new int[]{yPos + (height / 2), yPos, yPos + height};
        break;

      case SOUTHWEST:
        xpoints = new int[]{xPos, xPos, xPos + width};
        ypoints = new int[]{yPos, yPos + height, yPos + height};
        break;

      case SOUTH:
        xpoints = new int[]{xPos, xPos + (width / 2), xPos + width};
        ypoints = new int[]{yPos, yPos + height, yPos,};
        break;

      case SOUTHEAST:
        xpoints = new int[]{xPos, xPos + width, xPos + width};
        ypoints = new int[]{yPos + height, yPos + height, yPos};
        break;

      default:
        throw new IllegalStateException(
            "Instance ukazuje do nedefinovaneho smeru");
    }
    return new int[][]{xpoints, ypoints};
  }

//== VNORENE A VNITRNI TRIDY ===================================================
//== TESTY A METODA MAIN =======================================================
}
