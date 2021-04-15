import toy.AbstractToy;

import java.util.List;

public class Playroom {

    private final String name;

    private final int minAge;

    private List<AbstractToy> toys;

    private Child child;

    public Playroom(String name, int minAge, List<AbstractToy> toys) throws Exception {
        this.name = name;
        this.minAge = minAge;
        this.toys = toys;
    }

    private int getAvgToysPrice() {
        return toys.stream().mapToInt(AbstractToy::getCost).sum() / toys.size();
    }

    private boolean isEnoughMoney(int money) {
        return (money >= getAvgToysPrice());
    }

    private boolean isValidAge(int age) {
        return (age >= minAge);
    }


    public String getName() {
        return name;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) throws Exception {
        String NOT_ENOUGH_MONEY_EXCEPTION = "Not enough money!";
        String NOT_ENOUGH_AGE_EXCEPTION = "Not enough age!";
        String SUCCESS = "You have successfully entered the room";

        if (!isEnoughMoney(child.getMoney())) {
            throw new Exception(NOT_ENOUGH_MONEY_EXCEPTION);
        }

        if (!isValidAge(child.getAge())) {
            throw new Exception(NOT_ENOUGH_AGE_EXCEPTION);
        }

        System.out.println(SUCCESS);
        this.child = child;
    }

    public List<AbstractToy> getToys() {
        return toys;
    }

    public void setToys(List<AbstractToy> toys) {
        this.toys = toys;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Playroom: ").append(name).append("\n");
        stringBuffer.append("Min age: ").append(minAge).append("\n");
        stringBuffer.append("Avg toys price: ").append(getAvgToysPrice()).append("\n");
        stringBuffer.append("Toys: ").append("\n");
        toys.forEach(toy -> {
            stringBuffer.append("----------").append("\n");
            stringBuffer.append(toy);
        });
        return stringBuffer.toString();

    }
}
