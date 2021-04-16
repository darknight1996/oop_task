package repository.impl;

import repository.ToyRepository;
import toy.AbstractToy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class FileToyRepository implements ToyRepository {

    private List<AbstractToy> toys;

    private final String FILE_PATH = "src/main/resources/toys.dat";

    public FileToyRepository() {
        init();
    }

    public void reload() {
        init();
    }

    private void init() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            toys = (List<AbstractToy>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<AbstractToy> getToys() {
        return toys;
    }

    @Override
    public void updateToys(List<AbstractToy> toys) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(toys);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
