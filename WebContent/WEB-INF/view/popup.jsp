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
        <link rel="apple-touch-icon" href="/app-assets/images/ico/apple-icon-120.png">
        <link rel="shortcut icon" type="image/x-icon" href="/app-assets/images/ico/favicon.ico">
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,300;0,400;0,500;0,600;1,400;1,500;1,600"
              rel="stylesheet">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">

        <!-- BEGIN: Vendor CSS-->
        <link rel="stylesheet" type="text/css" href="/app-assets/vendors/css/vendors.min.css">
        <link rel="stylesheet" type="text/css" href="/app-assets/vendors/css/forms/select/select2.min.css">
        <!-- END: Vendor CSS-->

        <!-- BEGIN: Theme CSS-->
        <link rel="stylesheet" type="text/css" href="/app-assets/css/bootstrap.css">
        <link rel="stylesheet" type="text/css" href="/app-assets/css/bootstrap-extended.css">
        <link rel="stylesheet" type="text/css" href="/app-assets/css/colors.css">
        <link rel="stylesheet" type="text/css" href="/app-assets/css/components.css">
        <link rel="stylesheet" type="text/css" href="/app-assets/css/themes/dark-layout.css">
        <link rel="stylesheet" type="text/css" href="/app-assets/css/themes/bordered-layout.css">
        <link rel="stylesheet" type="text/css" href="/app-assets/css/themes/semi-dark-layout.css">

        <!-- BEGIN: Page CSS-->
        <link rel="stylesheet" type="text/css" href="/app-assets/css/core/menu/menu-types/horizontal-menu.css">
        <link rel="stylesheet" type="text/css" href="/assets/summernote/summernote-lite.min.css">
        <!-- END: Page CSS-->

        <!-- BEGIN: Custom CSS-->
        <link rel="stylesheet" type="text/css" href="/assets/css/style.css">
        <link rel="stylesheet" type="text/css" href="/app-assets/css/plugins/extensions/ext-component-sweet-alerts.css">
        <!-- END: Custom CSS-->

        <style>
            @font-face {
                font-family: 'NotoSansKR';
                src: url('/app-assets/fonts/NotoSansKR-Regular.otf') format('opentype')
            }
        </style>

    </head>

    <body class="horizontal-layout horizontal-menu 1-column navbar-floating footer-static  " data-open="hover" data-menu="horizontal-menu" data-col="1-column">


        <%@ include file="/WEB-INF/view/common/util.jsp" %>
        <sec:authentication property="authorities" var="auth"/>
        <sec:authentication property="Details.id" var="id"/>

        <!-- BEGIN: Header-->
        <nav class="header-navbar navbar-expand-lg navbar navbar-fixed align-items-center navbar-shadow navbar-brand-center" data-nav="brand-center">
            <div class="navbar-header d-xl-block d-none">
                <ul class="nav navbar-nav">
                    <li class="nav-item"><a class="navbar-brand" href="/index"><span class="brand-logo">
                            <svg viewbox="0 0 139 95" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" height="24">
                                <defs>
                                    <lineargradient id="linearGradient-1" x1="100%" y1="10.5120544%" x2="50%" y2="89.4879456%">
                                        <stop stop-color="#000000" offset="0%"></stop>
                                        <stop stop-color="#FFFFFF" offset="100%"></stop>
                                    </lineargradient>
                                    <lineargradient id="linearGradient-2" x1="64.0437835%" y1="46.3276743%" x2="37.373316%" y2="100%">
                                        <stop stop-color="#EEEEEE" stop-opacity="0" offset="0%"></stop>
                                        <stop stop-color="#FFFFFF" offset="100%"></stop>
                                    </lineargradient>
                                </defs>
                                <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                    <g id="Artboard" transform="translate(-400.000000, -178.000000)">
                                        <g id="Group" transform="translate(400.000000, 178.000000)">
                                            <path class="text-primary" id="Path" d="M-5.68434189e-14,2.84217094e-14 L39.1816085,2.84217094e-14 L69.3453773,32.2519224 L101.428699,2.84217094e-14 L138.784583,2.84217094e-14 L138.784199,29.8015838 C137.958931,37.3510206 135.784352,42.5567762 132.260463,45.4188507 C128.736573,48.2809251 112.33867,64.5239941 83.0667527,94.1480575 L56.2750821,94.1480575 L6.71554594,44.4188507 C2.46876683,39.9813776 0.345377275,35.1089553 0.345377275,29.8015838 C0.345377275,24.4942122 0.230251516,14.560351 -5.68434189e-14,2.84217094e-14 Z" style="fill:currentColor"></path>
                                            <path id="Path1" d="M69.3453773,32.2519224 L101.428699,1.42108547e-14 L138.784583,1.42108547e-14 L138.784199,29.8015838 C137.958931,37.3510206 135.784352,42.5567762 132.260463,45.4188507 C128.736573,48.2809251 112.33867,64.5239941 83.0667527,94.1480575 L56.2750821,94.1480575 L32.8435758,70.5039241 L69.3453773,32.2519224 Z" fill="url(#linearGradient-1)" opacity="0.2"></path>
                                            <polygon id="Path-2" fill="#000000" opacity="0.049999997" points="69.3922914 32.4202615 32.8435758 70.5039241 54.0490008 16.1851325"></polygon>
                                            <polygon id="Path-21" fill="#000000" opacity="0.099999994" points="69.3922914 32.4202615 32.8435758 70.5039241 58.3683556 20.7402338"></polygon>
                                            <polygon id="Path-3" fill="url(#linearGradient-2)" opacity="0.099999994" points="101.428699 0 83.0667527 94.1480575 130.378721 47.0740288"></polygon>
                                        </g>
                                    </g>
                                </g>
                            </svg></span>
                        <h2 class="brand-text mb-0">Cportal</h2>
                    </a></li>
                </ul>
            </div>
            <div class="navbar-container d-flex content">
                <div class="bookmark-wrapper d-flex align-items-center">
                    <ul class="nav navbar-nav">
                        <li class="nav-item d-none d-lg-block"><a class="nav-link nav-link-style"><i class="ficon" data-feather="moon"></i></a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <!-- END: Header-->

        <!-- BEGIN: Content-->
        <div class="app-content content ">
            <div class="content-overlay"></div>
            <div class="header-navbar-shadow"></div>
            <div class="content-wrapper container-xxl p-0">
                <div class="content-body" id="contentBody">


                </div>
            </div>
        </div>


        <button class="btn btn-primary btn-icon scroll-top" type="button"><i data-feather="arrow-up"></i></button>
        <!-- END: Footer-->

        <!-- BEGIN: Vendor JS-->
        <script src="/app-assets/vendors/js/vendors.min.js"></script>
        <script src="/app-assets/vendors/js/forms/validation/jquery.validate.min.js"></script>
        <script src="/app-assets/vendors/js/extensions/sweetalert2.all.min.js"></script>
        <script src="/app-assets/vendors/js/forms/select/select2.full.min.js"></script>
        <!-- BEGIN Vendor JS-->

        <!-- BEGIN: Theme JS-->
        <script src="/app-assets/js/core/app-menu.js"></script>
        <script src="/app-assets/js/core/app.js"></script>
        <!-- END: Theme JS-->

        <!-- BEGIN: Page JS-->
        <script src="/app-assets/js/scripts/forms/form-select2.js"></script>
        <script type="text/javascript" src="/assets/js/lib/ejs_production.js"></script>
        <script type="text/javascript" src="/assets/js/lib/ejs.js"></script>
        <script src="/assets/js/clipboard.min.js"></script>


        <script src="/assets/summernote/summernote-lite.min.js"></script>
        <script src="/assets/summernote/summernote-ko-KR.min.js"></script>
        <script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>

        <script data-main="${target}" src="/assets/js/lib/require.js"></script>
        <!-- END: Page JS-->

        <script>
            window.targetId = "${targetId}";
        </script>
    </body>
</html>
