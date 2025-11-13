package dao;

import model.PaymentMethod;

import java.util.List;

public interface IPaymentDAO {

    List<PaymentMethod> findAll();

    PaymentMethod findById(int id);
}
