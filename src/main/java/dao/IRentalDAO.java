package dao;

import model.Rental;

import java.util.List;

public interface IRentalDAO {

    List<Rental> findAll();

    List<Rental> search(String keyword);

    void create(Rental rental);

    void deleteByIds(List<Integer> ids);

    Rental findById(int id);
}
