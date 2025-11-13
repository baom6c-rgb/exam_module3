package dao;

import model.Rental;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalDAO implements IRentalDAO {

    private static final String SELECT_ALL =
            "SELECT r.room_id, r.customer_name, r.phone, r.start_date, r.note, " +
                    "p.payment_name, p.payment_id " +
                    "FROM rental r JOIN payment_method p ON r.payment_id = p.payment_id " +
                    "ORDER BY r.room_id";

    private static final String SELECT_SEARCH =
            "SELECT r.room_id, r.customer_name, r.phone, r.start_date, r.note, " +
                    "p.payment_name, p.payment_id " +
                    "FROM rental r JOIN payment_method p ON r.payment_id = p.payment_id " +
                    "WHERE CAST(r.room_id AS CHAR) LIKE ? " +
                    "OR r.customer_name LIKE ? " +
                    "OR r.phone LIKE ? " +
                    "ORDER BY r.room_id";

    private static final String INSERT_SQL =
            "INSERT INTO rental (customer_name, phone, start_date, payment_id, note) VALUES (?, ?, ?, ?, ?)";

    private static final String DELETE_IDS_SQL =
            "DELETE FROM rental WHERE room_id IN (%s)";

    private static final String SELECT_BY_ID =
            "SELECT r.room_id, r.customer_name, r.phone, r.start_date, r.note, " +
                    "p.payment_name, p.payment_id " +
                    "FROM rental r JOIN payment_method p ON r.payment_id = p.payment_id " +
                    "WHERE r.room_id = ?";

    @Override
    public List<Rental> findAll() {
        List<Rental> list = new ArrayList<>();

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ALL)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToRental(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Rental> search(String keyword) {
        List<Rental> list = new ArrayList<>();

        keyword = "%" + keyword + "%";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_SEARCH)) {

            ps.setString(1, keyword);
            ps.setString(2, keyword);
            ps.setString(3, keyword);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToRental(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void create(Rental rental) {
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_SQL)) {

            ps.setString(1, rental.getCustomerName());
            ps.setString(2, rental.getPhone());
            ps.setDate(3, new java.sql.Date(rental.getStartDate().getTime()));
            ps.setInt(4, rental.getPaymentId());
            ps.setString(5, rental.getNote());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) return;

        String placeholders = String.join(",", ids.stream().map(id -> "?").toArray(String[]::new));
        String sql = String.format(DELETE_IDS_SQL, placeholders);

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            for (int i = 0; i < ids.size(); i++) {
                ps.setInt(i + 1, ids.get(i));
            }

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Rental findById(int id) {
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToRental(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Rental mapResultSetToRental(ResultSet rs) throws SQLException {
        Rental rental = new Rental();

        rental.setRoomId(rs.getInt("room_id"));
        rental.setCustomerName(rs.getString("customer_name"));
        rental.setPhone(rs.getString("phone"));
        rental.setStartDate(rs.getDate("start_date"));
        rental.setPaymentId(rs.getInt("payment_id"));
        rental.setPaymentName(rs.getString("payment_name"));
        rental.setNote(rs.getString("note"));

        return rental;
    }
}
