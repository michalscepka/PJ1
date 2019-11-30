package bulanci;

public class Bush extends StaticGameObject {

    public Bush(String name) {
        super(name);
    }

    public Bush(String filename, String name) {
        super(filename, name);
    }

    public Bush(double positionX, double positionY) {
        super(positionX, positionY);
    }

    public Bush(String filename, double positionX, double positionY) {
        super(filename, positionX, positionY);
    }

    public Bush(String filename, String name, double positionX, double positionY) {
        super(filename, name, positionX, positionY);
    }
}
