package bus_app.repositories.interfaces;

import bus_app.exceptions.IncorrectBodyException;
import bus_app.exceptions.NoDataException;

import java.util.List;

public interface DefaultDBRepository<T> {

    T createItem(T item) throws IncorrectBodyException;

    T findItemById(Integer id) throws NoDataException;

    List<T> findAllItems();

    T updateItemById(Integer id, T item) throws IncorrectBodyException;

    void deleteItemById(Integer id) throws NoDataException;

    void deleteAllItems();
}