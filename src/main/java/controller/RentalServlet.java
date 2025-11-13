package controller;

import model.Rental;
import service.IRentalService;
import service.RentalService;
import service.IPaymentService;
import service.PaymentService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "RentalServlet", urlPatterns = "/rental")
public class RentalServlet extends HttpServlet {

    private final IRentalService rentalService = new RentalService();
    private final IPaymentService paymentService = new PaymentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        setUTF8(request, response);

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "search":
                search(request, response);
                break;
            case "confirmDelete":
                showConfirmDelete(request, response);
                break;
            default:
                list(request, response);
        }
    }

    private void list(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("rentals", rentalService.findAll());
        request.setAttribute("payments", paymentService.findAll());
        request.getRequestDispatcher("rental/list.jsp").forward(request, response);
    }

    private void search(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        request.setAttribute("rentals", rentalService.search(keyword));
        request.setAttribute("payments", paymentService.findAll());
        request.getRequestDispatcher("rental/list.jsp").forward(request, response);
    }

    private void showConfirmDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("ids", request.getParameterValues("ids"));
        request.getRequestDispatcher("rental/confirmDelete.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        setUTF8(request, response);

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "create":
                create(request, response);
                break;
            case "delete":
                delete(request, response);
                break;
        }
    }

    private void create(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        List<String> errors = new ArrayList<>();
        Rental r = new Rental();

        String name = request.getParameter("customer_name");
        if (name == null || !name.trim().matches("^[\\p{L}\\s]{5,50}$"))
            errors.add("Tên người thuê phải 5–50 ký tự và không chứa số / ký tự đặc biệt.");
        else r.setCustomerName(name.trim());

        String phone = request.getParameter("phone");
        if (phone == null || !phone.matches("^\\d{10}$"))
            errors.add("Số điện thoại phải gồm 10 ký tự số.");
        else r.setPhone(phone);

        try {
            LocalDate date = LocalDate.parse(request.getParameter("start_date"));
            if (date.isBefore(LocalDate.now()))
                errors.add("Ngày bắt đầu thuê không được nhỏ hơn hôm nay.");
            else r.setStartDate(Date.valueOf(date));
        } catch (Exception e) {
            errors.add("Ngày bắt đầu thuê không hợp lệ.");
        }

        try {
            r.setPaymentId(Integer.parseInt(request.getParameter("payment_id")));
        } catch (Exception e) {
            errors.add("Vui lòng chọn hình thức thanh toán.");
        }

        String note = request.getParameter("note");
        if (note != null && note.length() > 200)
            errors.add("Ghi chú không được quá 200 ký tự.");
        else r.setNote(note);

        if (!errors.isEmpty() || !rentalService.create(r, errors)) {
            request.setAttribute("errors", errors);
            request.setAttribute("payments", paymentService.findAll());
            request.setAttribute("openModalCreate", true);
            request.getRequestDispatcher("rental/list.jsp").forward(request, response);
            return;
        }

        response.sendRedirect("/rental?action=list");
    }

    private void delete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String[] idArr = request.getParameterValues("ids");
        List<Integer> ids = new ArrayList<>();

        if (idArr != null)
            for (String id : idArr) ids.add(Integer.parseInt(id));

        rentalService.deleteByIds(ids);
        response.sendRedirect("/rental?action=list");
    }

    private void setUTF8(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
    }
}
