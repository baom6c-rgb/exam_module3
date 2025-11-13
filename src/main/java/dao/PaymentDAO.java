package dao;

import model.PaymentMethod;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO implements IPaymentDAO {

    private static final String SELECT_ALL = "SELECT * FROM payment_method";
    private static final String SELECT_BY_ID = "SELECT * FROM payment_method WHERE payment_id = ?";

    @Override
    public List<PaymentMethod> findAll() {
        List<PaymentMethod> list = new ArrayList<>();

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ALL)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PaymentMethod pm = new PaymentMethod(
                        rs.getInt("payment_id"),
                        rs.getString("payment_name")
                );
                list.add(pm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public PaymentMethod findById(int id) {
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new PaymentMethod(
                        rs.getInt("payment_id"),
                        rs.getString("payment_name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
