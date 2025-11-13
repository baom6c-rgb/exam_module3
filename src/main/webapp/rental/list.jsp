<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh sách phòng trọ</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .table th, .table td { vertical-align: middle; }
    </style>
    <link rel="stylesheet" href="/assets/style.css">

</head>

<body class="bg-light">
<div class="container mt-4">

    <h3 class="mb-3">Danh sách phòng trọ</h3>

    <form action="/rental" method="get" class="d-flex mb-3" style="max-width: 400px;">
        <input type="hidden" name="action" value="search">
        <input name="keyword" class="form-control me-2" placeholder="Tìm kiếm...">
        <button class="btn btn-primary">Tìm</button>
    </form>

    <button class="btn btn-success mb-3" data-bs-toggle="modal" data-bs-target="#createModal">Tạo mới</button>

    <form action="/rental" method="get">
        <input type="hidden" name="action" value="confirmDelete">

        <table class="table table-bordered table-striped bg-white">
            <thead class="table-primary">
            <tr>
                <th>Chọn</th>
                <th>STT</th>
                <th>Mã phòng</th>
                <th>Tên người thuê</th>
                <th>SĐT</th>
                <th>Ngày bắt đầu</th>
                <th>Thanh toán</th>
                <th>Ghi chú</th>
            </tr>
            </thead>

            <tbody>
            <c:if test="${empty rentals}">
                <tr><td colspan="8" class="text-center text-muted">Không có dữ liệu</td></tr>
            </c:if>

            <c:forEach var="r" items="${rentals}" varStatus="st">
                <tr>
                    <td><input type="checkbox" name="ids" value="${r.roomId}"></td>
                    <td>${st.index + 1}</td>

                    <td>PT-${r.roomId < 10 ? '00' : r.roomId < 100 ? '0' : ''}${r.roomId}</td>

                    <td>${r.customerName}</td>
                    <td>${r.phone}</td>
                    <td><fmt:formatDate value="${r.startDate}" pattern="dd-MM-yyyy"/></td>
                    <td>${r.paymentName}</td>
                    <td>${r.note}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <button class="btn btn-danger">Xóa</button>
    </form>
</div>


<div class="modal fade" id="createModal">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">

            <form method="post" action="/rental">
                <input type="hidden" name="action" value="create">

                <div class="modal-header">
                    <h5 class="modal-title">Tạo thông tin thuê trọ</h5>
                </div>

                <div class="modal-body">

                    <c:if test="${not empty errors}">
                        <div class="alert alert-danger">
                            <ul>
                                <c:forEach var="e" items="${errors}">
                                    <li>${e}</li>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:if>

                    <div class="mb-3">
                        <label>Mã phòng</label>
                        <input class="form-control" value="Tự động tăng" disabled>
                    </div>

                    <div class="mb-3">
                        <label>Tên người thuê</label>
                        <input name="customer_name" class="form-control" value="${param.customer_name}">
                    </div>

                    <div class="mb-3">
                        <label>Số điện thoại</label>
                        <input name="phone" class="form-control" value="${param.phone}">
                    </div>

                    <div class="mb-3">
                        <label>Ngày bắt đầu thuê</label>
                        <input type="date" name="start_date" class="form-control" value="${param.start_date}">
                    </div>

                    <div class="mb-3">
                        <label>Hình thức thanh toán</label>
                        <select class="form-select" name="payment_id">
                            <option value="">-- Chọn --</option>
                            <c:forEach var="p" items="${payments}">
                                <option value="${p.paymentId}"
                                    ${param.payment_id == p.paymentId ? 'selected' : ''}>
                                        ${p.paymentName}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label>Ghi chú</label>
                        <textarea name="note" class="form-control" rows="3">${param.note}</textarea>
                    </div>

                </div>

                <div class="modal-footer">
                    <button class="btn btn-primary">Tạo mới</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                </div>
            </form>

        </div>
    </div>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<c:if test="${openModalCreate}">
    <script>
        new bootstrap.Modal(document.getElementById('createModal')).show();
    </script>
</c:if>

</body>
</html>
