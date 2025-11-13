<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Xác nhận xóa</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/assets/style.css">

</head>

<body class="bg-light">

<div class="container mt-5">

    <div class="card shadow-sm">

        <div class="card-header bg-danger text-white text-center">
            <h5 class="mb-0">Xác nhận</h5>
        </div>

        <div class="card-body text-center">

            <c:if test="${empty ids}">
                <p class="text-danger">Không có phòng trọ nào được chọn.</p>
                <a href="/rental?action=list" class="btn btn-secondary mt-2">Quay lại</a>
            </c:if>

            <c:if test="${not empty ids}">
                <p class="fs-5">Bạn có muốn xóa thông tin thuê trọ</p>

                <p class="fw-bold">
                    <c:forEach var="id" items="${ids}" varStatus="st">
                        PT-${id < 10 ? '00' : id < 100 ? '0' : ''}${id}
                        <c:if test="${!st.last}">, </c:if>
                    </c:forEach>
                    ?
                </p>

                <form action="/rental" method="post" class="mt-3">
                    <input type="hidden" name="action" value="delete">

                    <c:forEach var="id" items="${ids}">
                        <input type="hidden" name="ids" value="${id}">
                    </c:forEach>

                    <div class="d-flex justify-content-around mt-4">
                        <a href="/rental?action=list" class="btn btn-secondary px-4">Không</a>
                        <button class="btn btn-danger px-4">Có</button>
                    </div>
                </form>
            </c:if>

        </div>
    </div>

</div>

</body>
</html>
