package world.skytale.model2;

public interface Comment {

    ID getSenderID();
    long getTime();

    ID getPostID();
    long getPosTime();
}
