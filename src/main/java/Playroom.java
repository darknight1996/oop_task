import toy.AbstractToy;

import java.util.List;
import java.util.stream.Collectors;

public class Playroom {

    private final List<AbstractToy> toys;

    private final int money;

    public Playroom(List<AbstractToy> toys, int money) {
        this.toys = toys;
        this.money = money;
    }

    private void init() {
        // check is enough money for all toys
        int totalCost = toys.stream().mapToInt(AbstractToy::getCost).sum();

    }
}
