package repository;

import toy.AbstractToy;

import java.util.List;

public interface ToyRepository {

    List<AbstractToy> getToys();

    void updateToys(List<AbstractToy> toys);

    void reload();
}
