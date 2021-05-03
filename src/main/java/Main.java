import playroom.Playroom;
import repository.PlayroomRepository;
import repository.impl.FilePlayroomRepository;
import toy.*;
import ui.MainFrame;

import java.io.*;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        //MainFrame mainFrame = new MainFrame();
        AbstractToy ball1 = new Ball(ToySize.MEDIUM, 80);
        AbstractToy ball2 = new Ball(ToySize.SMALL, 120);

        AbstractToy car1 = new Car(ToySize.MEDIUM, 100);
        AbstractToy car2 = new Car(ToySize.SMALL, 120);

        AbstractToy doll1 = new Doll(ToySize.MEDIUM, 140);
        AbstractToy doll2 = new Doll(ToySize.LARGE, 200);

        Playroom playroom1 = new Playroom("playroom1", 6, List.of(ball1, car1, doll1));
        Playroom playroom2 = new Playroom("playroom2", 4, List.of(ball2, car2, doll2));

        FileOutputStream fout = new FileOutputStream("src/main/resources/playrooms.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(List.of(playroom1, playroom2));

        PlayroomRepository playroomRepository = new FilePlayroomRepository();
        List<Playroom> playrooms = playroomRepository.getPlayrooms();

    }

}
