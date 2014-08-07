package scallop.core;

public interface Subject {

    Observer registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}
