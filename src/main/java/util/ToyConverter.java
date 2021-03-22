package util;

import toy.*;

import java.util.List;

public class ToyConverter {

    private final int FIELDS_COUNT = 3;
    private final String INVALID_FIELDS_COUNT_EXCEPTION = "invalid fields count, expected " + FIELDS_COUNT;

    private final int TYPE_INDEX = 0;
    private final int SIZE_INDEX = 1;
    private final int COST_INDEX = 2;


    public AbstractToy convert(List<String> fields) throws Exception {

        if (fields.size() != FIELDS_COUNT) {
            throw new Exception(INVALID_FIELDS_COUNT_EXCEPTION);
        }

        AbstractToy abstractToy;

        switch (fields.get(TYPE_INDEX)) {
            case ("Ball"):
                abstractToy = new Ball(ToySize.valueOf(fields.get(SIZE_INDEX)),
                        Integer.parseInt(fields.get(COST_INDEX)));
                break;
            case ("Car"):
                abstractToy = new Car(ToySize.valueOf(fields.get(SIZE_INDEX)),
                        Integer.parseInt(fields.get(COST_INDEX)));
                break;
            case ("Cubes"):
                abstractToy = new Cubes(ToySize.valueOf(fields.get(SIZE_INDEX)),
                        Integer.parseInt(fields.get(COST_INDEX)));
                break;
            case ("Doll"):
                abstractToy = new Doll(ToySize.valueOf(fields.get(SIZE_INDEX)),
                        Integer.parseInt(fields.get(COST_INDEX)));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + fields.get(TYPE_INDEX));
        }
        
        return abstractToy;

    }

}
