package service;

import model.PaymentMethod;
import java.util.List;

public interface IPaymentService {
    List<PaymentMethod> findAll();
}
