package bulanci;

public class Shotgun extends Gun {

    public Shotgun(String name, String filename, int defaultAmmo) {
        super(name, filename, defaultAmmo);
    }

    public Shotgun(String name, String filename, Player player, int defaultAmmo) {
        super(name, filename, player, defaultAmmo);
    }
}
