package model;

import java.util.Date;

public class Rental {
    private int roomId;
    private String customerName;
    private String phone;
    private Date startDate;     // java.util.Date để dễ format khi hiển thị
    private int paymentId;
    private String paymentName; // JOIN từ payment_method
    private String note;

    public Rental() {
    }

    public Rental(int roomId, String customerName, String phone, Date startDate, int paymentId, String paymentName, String note) {
        this.roomId = roomId;
        this.customerName = customerName;
        this.phone = phone;
        this.startDate = startDate;
        this.paymentId = paymentId;
        this.paymentName = paymentName;
        this.note = note;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
