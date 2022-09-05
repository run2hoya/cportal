package com.castis.cportal.service.createMail;

import com.castis.cportal.common.enumeration.ProductType;
import com.castis.cportal.dto.company.CompanyTitleDto;
import com.castis.cportal.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class Company114MailService {

    private final CompanyRepository companyRepository;

    public String generateHtml() {

        List<CompanyTitleDto> premierList =  companyRepository.findByProductType(ProductType.PREMIER);
        List<CompanyTitleDto> normalList =  companyRepository.findByProductType(ProductType.NORMAL);

        StringBuilder myvar = new StringBuilder();
        myvar.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
                .append("    <head>")
                .append("        <meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">")
                .append("        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0;\">")
                .append("        <meta name=\"format-detection\" content=\"telephone=no\"/>")
                .append("")
                .append("        <style>")
                .append("            /* Reset styles */")
                .append("            body {")
                .append("                margin: 0;")
                .append("                padding: 0;")
                .append("                min-width: 100%;")
                .append("                width: 100% !important;")
                .append("                height: 100% !important;")
                .append("            }")
                .append("")
                .append("            body, table, td, div, p, a {")
                .append("                -webkit-font-smoothing: antialiased;")
                .append("                text-size-adjust: 100%;")
                .append("                -ms-text-size-adjust: 100%;")
                .append("                -webkit-text-size-adjust: 100%;")
                .append("                line-height: 100%;")
                .append("            }")
                .append("")
                .append("            table, td {")
                .append("                mso-table-lspace: 0pt;")
                .append("                mso-table-rspace: 0pt;")
                .append("                border-collapse: collapse !important;")
                .append("                border-spacing: 0;")
                .append("            }")
                .append("")
                .append("            img {")
                .append("                border: 0;")
                .append("                line-height: 100%;")
                .append("                outline: none;")
                .append("                text-decoration: none;")
                .append("                -ms-interpolation-mode: bicubic;")
                .append("            }")
                .append("")
                .append("            #outlook a {")
                .append("                padding: 0;")
                .append("            }")
                .append("")
                .append("            .ReadMsgBody {")
                .append("                width: 100%;")
                .append("            }")
                .append("")
                .append("            .ExternalClass {")
                .append("                width: 100%;")
                .append("            }")
                .append("")
                .append("            .ExternalClass, .ExternalClass p, .ExternalClass span, .ExternalClass font, .ExternalClass td, .ExternalClass div {")
                .append("                line-height: 100%;")
                .append("            }")
                .append("")
                .append("            /* Rounded corners for advanced mail clients only */")
                .append("            @media all and (min-width: 560px) {")
                .append("                .container {")
                .append("                    border-radius: 8px;")
                .append("                    -webkit-border-radius: 8px;")
                .append("                    -moz-border-radius: 8px;")
                .append("                    -khtml-border-radius: 8px;")
                .append("                }")
                .append("            }")
                .append("")
                .append("            a, a:hover {")
                .append("                color: #127DB3;")
                .append("            }")
                .append("")
                .append("            .footer a, .footer a:hover {")
                .append("                color: #999999;")
                .append("            }")
                .append("")
                .append("        </style>")
                .append("")
                .append("        <!-- MESSAGE SUBJECT -->")
                .append("        <title>사업부 114</title>")
                .append("")
                .append("    </head>")
                .append("")
                .append("    <body topmargin=\"0\" rightmargin=\"0\" bottommargin=\"0\" leftmargin=\"0\" marginwidth=\"0\" marginheight=\"0\" width=\"100%\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; width: 100%; height: 100%; -webkit-font-smoothing: antialiased; text-size-adjust: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; line-height: 100%;")
                .append("	background-color: #F0F0F0;")
                .append("	color: #000000;\"")
                .append("          bgcolor=\"#F0F0F0\"")
                .append("          text=\"#000000\">")
                .append("")
                .append("        <table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; width: 100%;\" class=\"background\">")
                .append("            <tr>")
                .append("                <td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0;\"")
                .append("                    bgcolor=\"#F0F0F0\">")
                .append("")
                .append("")
                .append("")
                .append("                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\"")
                .append("                           bgcolor=\"#FFFFFF\"")
                .append("                           width=\"560\" style=\"border-collapse: collapse; border-spacing: 0; padding: 0; width: inherit;")
                .append("	max-width: 560px;\" class=\"container\">")
                .append("")
                .append("")
                .append("                        <tr>")
                .append("                            <td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; padding-left: 6.25%; padding-right: 6.25%; width: 87.5%; font-size: 24px; font-weight: bold; line-height: 130%;")
                .append("			padding-top: 25px;")
                .append("			color: #000000;")
                .append("			font-family: sans-serif;\" class=\"header\">")
                .append("사업부 114")
                .append("                            </td>")
                .append("                        </tr>")
                .append("")
                .append("                        <!-- SUBHEADER -->")
                .append("                        <!-- Set text color and font family (\"sans-serif\" or \"Georgia, serif\") -->")
                .append("                        <tr>")
                .append("                            <td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; padding-bottom: 3px; padding-left: 6.25%; padding-right: 6.25%; width: 87.5%; font-size: 18px; font-weight: 300; line-height: 150%;")
                .append("			padding-top: 5px;")
                .append("			color: #000000;")
                .append("			font-family: sans-serif;\" class=\"subheader\">")
                .append("사업부를 소개 해드립니다")
                .append("                            </td>")
                .append("                        </tr>")
                .append("")
                .append("                        <!-- HERO IMAGE -->")
                .append("                        <!-- Image text color should be opposite to background color. Set your url, image src, alt and title. Alt text should fit the image size. Real image size should be x2 (wrapper x2). Do not set height for flexible images (including \"auto\"). URL format: http://domain.com/?utm_source={{Campaign-Source}}&utm_medium=email&utm_content={{ÃŒmage-Name}}&utm_campaign={{Campaign-Name}} -->")
                .append("                        <tr>")
                .append("                            <td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0;")
                .append("			padding-top: 20px;\" class=\"hero\"><a target=\"_blank\" style=\"text-decoration: none;\"")
                .append("                                                href=\"http://www.cportal.world\">")
                .append("                                <img border=\"0\" vspace=\"0\" hspace=\"0\" src=\"cid:image\"")
                .append("                                     alt=\"Please enable images to view this content\" title=\"Hero Image\"")
                .append("                                     width=\"560\" style=\"width: 100%;max-width: 560px; color: #000000; font-size: 13px; margin: 0; padding: 0; outline: none; text-decoration: none;")
                .append("			-ms-interpolation-mode: bicubic; border: none; display: block;\"/></a></td>")
                .append("                        </tr>")
                .append("")
                .append("")
                .append("")
                .append("                        <!-- BUTTON -->")
                .append("                        <!-- Set button background color at TD, link/text color at A and TD, font family (\"sans-serif\" or \"Georgia, serif\") at TD. For verification codes add \"letter-spacing: 5px;\". Link format: http://domain.com/?utm_source={{Campaign-Source}}&utm_medium=email&utm_content={{Button-Name}}&utm_campaign={{Campaign-Name}} -->")
                .append("                        <tr>")
                .append("                            <td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; padding-left: 6.25%; padding-right: 6.25%; width: 87.5%;")
                .append("			padding-top: 25px;")
                .append("			padding-bottom: 5px;\" class=\"button\"><a")
                .append("                                    href=\"https://github.com/konsav/email-templates/\" target=\"_blank\" style=\"text-decoration: underline;\">")
                .append("                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"max-width: 240px; min-width: 120px; border-collapse: collapse; border-spacing: 0; padding: 0;\">")
                .append("                                    <tr>")
                .append("                                        <td align=\"center\" valign=\"middle\" style=\"padding: 12px 24px; margin: 0; text-decoration: underline; border-collapse: collapse; border-spacing: 0; border-radius: 4px; -webkit-border-radius: 4px; -moz-border-radius: 4px; -khtml-border-radius: 4px;\"")
                .append("                                            bgcolor=\"#E9703E\"><a target=\"_blank\" style=\"text-decoration: underline;")
                .append("					color: #FFFFFF; font-family: sans-serif; font-size: 17px; font-weight: 400; line-height: 120%;\"")
                .append("                                                href=\"http://www.cportal.world\">")
                .append("브라우저에서 보기")
                .append("                                        </a>")
                .append("                                        </td>")
                .append("                                    </tr>")
                .append("                                </table>")
                .append("                            </a>")
                .append("                            </td>")
                .append("                        </tr>")
                .append("")
                .append("                        <!-- LINE -->")
                .append("                        <!-- Set line color -->")
                .append("                        <tr>")
                .append("                            <td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; padding-left: 6.25%; padding-right: 6.25%; width: 87.5%;")
                .append("			padding-top: 25px;\" class=\"line\">")
                .append("                                <hr")
                .append("                                        color=\"#E0E0E0\" align=\"center\" width=\"100%\" size=\"1\" noshade style=\"margin: 0; padding: 0;\"/>")
                .append("                            </td>")
                .append("                        </tr>")
                .append("")
                .append("                        <!-- LIST -->")
                .append("                        <tr>")
                .append("                            <td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; padding-left: 6.25%; padding-right: 6.25%;\" class=\"list-item\">")
                .append("                                <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"width: inherit; margin: 0; padding: 0; border-collapse: collapse; border-spacing: 0;\">");

        addItem(myvar, premierList);
        myvar.append(" <hr>");
        addNormalItem(myvar, normalList);




        myvar.append("                                </table>")
                .append("                            </td>")
                .append("                        </tr>")
                .append("")
                .append("                        <!-- LINE -->")
                .append("                        <!-- Set line color -->")
                .append("                        <tr>")
                .append("                            <td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; padding-left: 6.25%; padding-right: 6.25%; width: 87.5%;")
                .append("			padding-top: 25px;\" class=\"line\">")
                .append("                                <hr")
                .append("                                        color=\"#E0E0E0\" align=\"center\" width=\"100%\" size=\"1\" noshade style=\"margin: 0; padding: 0;\"/>")
                .append("                            </td>")
                .append("                        </tr>")
                .append("")
                .append("                        <!-- PARAGRAPH -->")
                .append("                        <!-- Set text color and font family (\"sans-serif\" or \"Georgia, serif\"). Duplicate all text styles in links, including line-height -->")
                .append("                        <tr>")
                .append("                            <td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; padding-left: 6.25%; padding-right: 6.25%; width: 87.5%; font-size: 17px; font-weight: 400; line-height: 160%;")
                .append("			padding-top: 20px;")
                .append("			padding-bottom: 25px;")
                .append("			color: #000000;")
                .append("			font-family: sans-serif;\" class=\"paragraph\">")
                .append("                                더 많은 정보를 알고 싶으면? <a href=\"http://www.cportal.world\" target=\"_blank\" style=\"color: #127DB3; font-family: sans-serif; font-size: 17px; font-weight: 400; line-height: 160%;\">cportal</a>")
                .append("                            </td>")
                .append("                        </tr>")
                .append("")
                .append("                        <!-- End of WRAPPER -->")
                .append("                    </table>")
                .append("")
                .append("                    <!-- WRAPPER -->")
                .append("                    <!-- Set wrapper width (twice) -->")
                .append("                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\"")
                .append("                           width=\"560\" style=\"border-collapse: collapse; border-spacing: 0; padding: 0; width: inherit;")
                .append("	max-width: 560px;\" class=\"wrapper\">")
                .append("")
                .append("")
                .append("                        <!-- FOOTER -->")
                .append("                        <!-- Set text color and font family (\"sans-serif\" or \"Georgia, serif\"). Duplicate all text styles in links, including line-height -->")
                .append("                        <tr>")
                .append("                            <td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; padding-left: 6.25%; padding-right: 6.25%; width: 87.5%; font-size: 13px; font-weight: 400; line-height: 150%;")
                .append("			padding-top: 20px;")
                .append("			padding-bottom: 20px;")
                .append("			color: #999999;")
                .append("			font-family: sans-serif;\" class=\"footer\">")
                .append("")
                .append("                                © 2022 company114. All Rights Reserved.")
                .append("                            </td>")
                .append("                        </tr>")
                .append("")
                .append("                        <!-- End of WRAPPER -->")
                .append("                    </table>")
                .append("")
                .append("                    <!-- End of SECTION / BACKGROUND -->")
                .append("                </td>")
                .append("            </tr>")
                .append("        </table>")
                .append("")
                .append("    </body>")
                .append("</html>");




        return myvar.toString();
    }

    public void addItem(StringBuilder builder, List<CompanyTitleDto> companyList) {

        for(CompanyTitleDto company : companyList) {
            builder.append("                                    <!-- LIST ITEM -->")
                    .append("                                    <tr>")
                    .append("<td align=\"left\" valign=\"top\" style=\"font-size: 17px; font-weight: 400; line-height: 160%; border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0;")
                    .append("					padding-top: 25px;")
                    .append("					color: #000000;")
                    .append("					font-family: sans-serif;\" class=\"paragraph\"> <a target=\"_blank\" style=\"text-decoration: none; font-size: 17px; line-height: 160%;\"")
                    .append("			href=\"http://www.cportal.world/popup/view/company/" + company.getId() + "\">")
                    .append("				<b style=\"color:#0B5073; text-decoration: underline;\">" + company.getCompanyName() + "</b></a><br/>");

                builder.append(company.getCompanyDes());

            builder.append("                                        </td>")
                    .append("                                    </tr>")    ;
        }
    }

    public void generateItem(StringBuilder builder, CompanyTitleDto companyTitleDto) {
        builder.append("<td align=\"left\" valign=\"top\" style=\"font-size: 17px; font-weight: 400; line-height: 160%; border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0;")
                .append("					padding-top: 25px;")
                .append("					color: #000000;")
                .append("					font-family: sans-serif;\" class=\"paragraph\"> <a target=\"_blank\" style=\"text-decoration: none; font-size: 17px; line-height: 160%;\"")
                .append("			href=\"http://www.cportal.world/popup/view/company/" + companyTitleDto.getId() + "\">")
                .append("				<b style=\"color:#0B5073; text-decoration: underline;\">" + companyTitleDto.getCompanyName() + "</b></a><br/>");
        builder.append("                                        </td>");

    }
    public void addNormalItem(StringBuilder builder, List<CompanyTitleDto> companyList) {

        for(int i = 0; i < companyList.size(); i+=2) {
            builder.append("<tr>");
            int last = i + 2;
            if(last > companyList.size())
                last = last - 1;
            List<CompanyTitleDto> subList = companyList.subList(i, last);
            generateItem(builder, subList.get(0));
            if(subList.size() == 2) {
                generateItem(builder, subList.get(1));
            }
            builder.append("                                    </tr>")    ;
        }
    }



}
