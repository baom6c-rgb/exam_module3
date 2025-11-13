package service;

import dao.IPaymentDAO;
import dao.PaymentDAO;
import model.PaymentMethod;

import java.util.List;

public class PaymentService implements IPaymentService {

    private final IPaymentDAO paymentDAO = new PaymentDAO();

    @Override
    public List<PaymentMethod> findAll() {
        return paymentDAO.findAll();
    }
}
