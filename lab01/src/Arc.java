import java.awt.geom.Arc2D;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jan Kožusznik
 * @version 0.1
 */
public class Arc {

  private static final Canvas CANVAS = Canvas.getInstance();

  private MyColor color = MyColor.AZURE;

  private Direction8 direction = Direction8.SOUTH;

  private double angle = 45;

  private int xPos = 0;

  private int yPos = 0;

  private int width = 150;

  private int height = 150;

  private String name;

  private final int step = 50;

  private static Map<Direction8, Double> directionMap =
      new HashMap<Direction8, Double>();
  static {
    directionMap.put(Direction8.EAST, (double) 0);
    directionMap.put(Direction8.NORTHEAST, (double) 45);
    directionMap.put(Direction8.NORTH, (double) 90);
    directionMap.put(Direction8.NORTHWEST, (double) 135);
    directionMap.put(Direction8.WEST, (double) 180);
    directionMap.put(Direction8.SOUTHWEST, (double) 225);
    directionMap.put(Direction8.SOUTH, (double) 270);
    directionMap.put(Direction8.SOUTHEAST, (double) 315);
  }

  /**
   *
   */
  public Arc() {
    this(0, 0, 150, 150, MyColor.AZURE, Direction8.NORTH, 45);
  }

  /**
   * @param x
   * @param y
   * @param width
   * @param height
   * @param color
   * @param direction8
   * @param angle
   */
  public Arc(int x, int y, int width, int height, MyColor color,
      Direction8 direction8, double angle) {
    xPos = x;
    yPos = y;
    this.width = width;
    this.height = height;
    this.color = color;
    direction = direction8;
    this.angle = angle;
    paint();

  }

  public void paint() {
    repaint(color);
  }

  public void erase() {
    repaint(CANVAS.getColorOfBackground());
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
    erase();
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
    erase();
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

  /***************************************************************************
   * Smaze obraz sve instance z platna (nakresli ji barvou pozadi platna).
   */

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

  private void repaint(MyColor color2) {
    double initialAngle = getInitialAngle();
    CANVAS.setColorOfForeground(color2);
    CANVAS.fill(new java.awt.geom.Arc2D.Double(xPos, yPos, width, height,
        initialAngle - (angle / 2), angle, Arc2D.PIE));

  }

  private double getInitialAngle() {
    return directionMap.get(direction);
  }

  public void setAngle(int i) {
    angle = i;
  }

}
