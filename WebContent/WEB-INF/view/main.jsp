<!DOCTYPE html>
<%@page import="org.springframework.web.servlet.LocaleResolver" %>
<%@page import="javax.naming.spi.Resolver" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html class="loading" lang="en" data-textdirection="ltr">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">

        <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=0,minimal-ui">
        <meta name="description" content="Cportal &amp; castis">
        <meta name="keywords" content="dashboard template, cportal, web app">
        <meta name="author" content="castis">
        <title>Cportal</title>
        <link rel="apple-touch-icon" href="app-assets/images/ico/apple-icon-120.png">
        <link rel="shortcut icon" type="image/x-icon" href="app-assets/images/ico/favicon.ico">
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,300;0,400;0,500;0,600;1,400;1,500;1,600"
              rel="stylesheet">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">

        <!-- BEGIN: Vendor CSS-->
        <link rel="stylesheet" type="text/css" href="app-assets/vendors/css/vendors.min.css">
        <link rel="stylesheet" type="text/css" href="app-assets/vendors/css/tables/datatable/dataTables.bootstrap5.min.css">
        <link rel="stylesheet" type="text/css" href="app-assets/vendors/css/forms/wizard/bs-stepper.min.css">
        <link rel="stylesheet" type="text/css" href="app-assets/vendors/css/forms/select/select2.min.css">
        <!-- END: Vendor CSS-->

        <!-- BEGIN: Theme CSS-->
        <link rel="stylesheet" type="text/css" href="app-assets/css/bootstrap.css">
        <link rel="stylesheet" type="text/css" href="app-assets/css/bootstrap-extended.css">
        <link rel="stylesheet" type="text/css" href="app-assets/css/colors.css">
        <link rel="stylesheet" type="text/css" href="app-assets/css/components.css">
        <link rel="stylesheet" type="text/css" href="app-assets/css/themes/dark-layout.css">
        <link rel="stylesheet" type="text/css" href="app-assets/css/themes/bordered-layout.css">
        <link rel="stylesheet" type="text/css" href="app-assets/css/themes/semi-dark-layout.css">

        <!-- BEGIN: Page CSS-->
        <link rel="stylesheet" type="text/css" href="app-assets/css/core/menu/menu-types/vertical-menu.css">
        <link rel="stylesheet" type="text/css" href="app-assets/css/plugins/forms/form-validation.css">
        <link rel="stylesheet" type="text/css" href="assets/summernote/summernote-lite.min.css">
        <link rel="stylesheet" type="text/css" href="app-assets/css/plugins/charts/chart-apex.css">
        <link rel="stylesheet" type="text/css" href="app-assets/css/plugins/forms/form-validation.css">
        <link rel="stylesheet" type="text/css" href="app-assets/css/plugins/forms/form-wizard.css">
        <link rel="stylesheet" type="text/css" href="app-assets/vendors/css/pickers/flatpickr/flatpickr.min.css">
        <link rel="stylesheet" type="text/css" href="https://npmcdn.com/flatpickr/dist/themes/confetti.css">
        <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css">
        <link rel="stylesheet" type="text/css" href="app-assets/css/plugins/extensions/ext-component-sweet-alerts.css">
        <!-- END: Page CSS-->

        <!-- BEGIN: Custom CSS-->
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">
        <link rel="stylesheet" type="text/css" href="assets/css/pre.css">
        <link rel="stylesheet" type="text/css" href="assets/css/cardStyle.css">
        <!-- END: Custom CSS-->

        <style>
            @font-face {
                font-family: 'NotoSansKR';
                src: url('/app-assets/fonts/NotoSansKR-Regular.otf') format('opentype')
            }
        </style>

    </head>

    <body class="vertical-layout vertical-menu-modern  navbar-floating footer-static   menu-collapsed" data-open="click"
          data-menu="vertical-menu-modern" data-col="">


        <%@ include file="/WEB-INF/view/common/menu.jsp" %>
        <%@ include file="/WEB-INF/view/common/util.jsp" %>
        <sec:authentication property="authorities" var="auth"/>
        <sec:authentication property="Details.id" var="id"/>

        <!-- BEGIN: Content-->
        <div class="app-content content ">
            <div class="content-overlay"></div>
            <div class="header-navbar-shadow"></div>
            <div class="content-wrapper container-xxl p-0">

                <div class="content-body" id="contentBody">

                </div>

            </div>
        </div>

        <%@ include file="/WEB-INF/view/common/footer.jsp" %>

        <%--        <script src="https://cdn.ckeditor.com/ckeditor5/34.1.0/classic/ckeditor.js"></script>--%>
        <script src="app-assets/vendors/js/charts/apexcharts.min.js"></script>
        <script src="app-assets/vendors/js/forms/wizard/bs-stepper.min.js"></script>
        <script src="app-assets/vendors/js/forms/select/select2.full.min.js"></script>
        <script src="assets/summernote/summernote-lite.min.js"></script>
        <script src="assets/summernote/summernote-ko-KR.min.js"></script>

        <script src="app-assets/vendors/js/tables/datatable/jquery.dataTables.min.js"></script>
        <script src="app-assets/vendors/js/tables/datatable/dataTables.bootstrap5.min.js"></script>


        <script src="app-assets/vendors/js/pickers/flatpickr/flatpickr.min.js"></script>
        <script src="https://npmcdn.com/flatpickr/dist/l10n/ko.js"></script>
        <script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
        <script data-main="cportalJS/main" src="assets/js/lib/require.js"></script>
        <!-- END: Page JS-->

        <script>
            window.id = "${id}";
            window.page = "${page}";
        </script>
    </body>
</html>
