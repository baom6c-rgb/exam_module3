package service;

import model.Rental;

import java.util.List;

public interface IRentalService {

    List<Rental> findAll();

    List<Rental> search(String keyword);

    boolean create(Rental rental, List<String> errors);

    void deleteByIds(List<Integer> ids);

    Rental findById(int id);
}
