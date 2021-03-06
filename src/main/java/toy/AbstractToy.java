package toy;

import java.io.Serializable;

public abstract class AbstractToy implements Serializable {

    private final ToySize toySize;

    private final int cost;

    public AbstractToy(ToySize toySize, int cost) {
        this.toySize = toySize;
        this.cost = cost;
    }

    public ToySize getToySize() {
        return toySize;
    }

    public int getCost() {
        return cost;
    }

    public String getType() {
        return this.getClass().getName().split("\\.")[1];
    }

    @Override
    public String toString() {
        return "Toy type: " + this.getType()
                + "\nToy cost: " + this.getCost()
                + "\nToy size: " + this.getToySize() + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractToy that = (AbstractToy) o;
        return cost == that.cost && toySize == that.toySize;
    }

}
