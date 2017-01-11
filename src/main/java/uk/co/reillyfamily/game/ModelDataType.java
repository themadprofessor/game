package uk.co.reillyfamily.game;

/**
 * Created by stuart on 09/01/17.
 */
public enum ModelDataType {
    VERTEX("position"), NORMAL("normal"), COLOUR("colour");

    private String id;

    ModelDataType(String id) {
        this.id = id;
    }

    protected String getId() {
        return id;
    }
}
