package repository.impl;

import playroom.Playroom;
import repository.PlayroomRepository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class FilePlayroomRepository implements PlayroomRepository {

    private List<Playroom> playrooms;

    private final String FILE_PATH = "src/main/resources/playrooms.dat";

    public FilePlayroomRepository() {
        init();
    }

    public void reload() {
        init();
    }

    private void init() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            playrooms = (List<Playroom>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Playroom> getPlayrooms() {
        return playrooms;
    }

    @Override
    public void updatePlayrooms(List<Playroom> playrooms) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(playrooms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
