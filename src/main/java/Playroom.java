import toy.AbstractToy;

import java.util.List;

public class Playroom {

    private List<AbstractToy> toys;

    private final int money;

    private final String NOT_ENOUGH_MONEY_EXCEPTION = "Not enough money!";

    public Playroom(List<AbstractToy> toys, int money) throws Exception {
        this.toys = toys;
        this.money = money;
        init();
    }

    private void init() throws Exception {
        // check is enough money for all toys
        int totalCost = toys.stream().mapToInt(AbstractToy::getCost).sum();
        if (money < totalCost) {
            throw new Exception(NOT_ENOUGH_MONEY_EXCEPTION);
        }
    }

    public List<AbstractToy> getToys() {
        return toys;
    }

    public void setToys(List<AbstractToy> toys) {
        this.toys = toys;
    }
}
