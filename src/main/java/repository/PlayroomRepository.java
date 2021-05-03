package repository;

import playroom.Playroom;

import java.util.List;

public interface PlayroomRepository {

    List<Playroom> getPlayrooms();

    void updatePlayrooms(List<Playroom> toys);

    void reload();

}
