package service;

public interface Compatible<T> {

    //this is a universal contract for checking compatibility (comparing two objects)
    boolean isCompatibleWith(T target);
}


