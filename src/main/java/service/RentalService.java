package service;

import dao.IRentalDAO;
import dao.RentalDAO;
import model.Rental;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RentalService implements IRentalService {

    private final IRentalDAO rentalDAO = new RentalDAO();

    @Override
    public List<Rental> findAll() {
        return rentalDAO.findAll();
    }

    @Override
    public List<Rental> search(String keyword) {
        return rentalDAO.search(keyword);
    }

    @Override
    public boolean create(Rental rental, List<String> errors) {
        validate(rental, errors);

        if (!errors.isEmpty()) {
            return false;
        }

        rentalDAO.create(rental);
        return true;
    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        rentalDAO.deleteByIds(ids);
    }

    @Override
    public Rental findById(int id) {
        return rentalDAO.findById(id);
    }

    // -------- VALIDATE INPUT --------
    private void validate(Rental r, List<String> errors) {

        // tên người thuê
        if (r.getCustomerName() == null || r.getCustomerName().trim().isEmpty()) {
            errors.add("Tên người thuê không được để trống.");
        } else if (!r.getCustomerName().matches("^[a-zA-ZÀ-ỹ\\s]{5,50}$")) {
            errors.add("Tên người thuê phải 5–50 ký tự và không chứa số / ký tự đặc biệt.");
        }

        // phone
        if (r.getPhone() == null || !r.getPhone().matches("^\\d{10}$")) {
            errors.add("Số điện thoại phải là 10 ký tự số.");
        }

        // start date
        if (r.getStartDate() == null) {
            errors.add("Ngày bắt đầu thuê không hợp lệ.");
        } else {
            Date now = new Date();
            if (r.getStartDate().before(now)) {
                errors.add("Ngày bắt đầu thuê không được là quá khứ.");
            }
        }

        // payment
        if (r.getPaymentId() <= 0) {
            errors.add("Vui lòng chọn hình thức thanh toán.");
        }

        // note
        if (r.getNote() != null && r.getNote().length() > 200) {
            errors.add("Ghi chú không được vượt quá 200 ký tự.");
        }
    }
}
