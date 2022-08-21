package com.castis.cportal.service.createMail;

import com.castis.cportal.common.enumeration.ProductType;
import com.castis.cportal.dto.wanted.WantedMailDto;
import com.castis.cportal.repository.WantedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class JobcastMailService {

    private final WantedRepository wantedRepository;

    public String generateHtml() {

        List<WantedMailDto> jobList = wantedRepository.getWantedMailList("일자리", LocalDate.now(), ProductType.PREMIER);
        List<WantedMailDto> businessList = wantedRepository.getWantedMailList("일거리", LocalDate.now(), ProductType.PREMIER);
        List<WantedMailDto> albaList = wantedRepository.getWantedMailList("알바", LocalDate.now(), ProductType.PREMIER);
        List<WantedMailDto> sellCompanyList = wantedRepository.getWantedMailList("사업부 매각", LocalDate.now(), ProductType.PREMIER);

        StringBuilder html = new StringBuilder();
        html.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
                .append("<head>")
                .append("	<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">")
                .append("  	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0;\">")
                .append(" 	<meta name=\"format-detection\" content=\"telephone=no\"/>")
                .append("	<style>")
                .append("/* Reset styles */ ")
                .append("body { margin: 0; padding: 0; min-width: 100%; width: 100% !important; height: 100% !important;}")
                .append("body, table, td, div, p, a { -webkit-font-smoothing: antialiased; text-size-adjust: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; line-height: 100%; }")
                .append("table, td { mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-collapse: collapse !important; border-spacing: 0; }")
                .append("img { border: 0; line-height: 100%; outline: none; text-decoration: none; -ms-interpolation-mode: bicubic; }")
                .append("#outlook a { padding: 0; }")
                .append(".ReadMsgBody { width: 100%; } .ExternalClass { width: 100%; }")
                .append(".ExternalClass, .ExternalClass p, .ExternalClass span, .ExternalClass font, .ExternalClass td, .ExternalClass div { line-height: 100%; }")
                .append("")
                .append("/* Extra floater space for advanced mail clients only */ ")
                .append("@media all and (max-width: 600px) {")
                .append("	.floater { width: 320px; }")
                .append("}")
                .append("")
                .append("/* Set color for auto links (addresses, dates, etc.) */ ")
                .append("a, a:hover {")
                .append("	color: #127DB3;")
                .append("}")
                .append(".footer a, .footer a:hover {")
                .append("	color: #999999;")
                .append("}")
                .append("")
                .append(" 	</style>")
                .append("")
                .append("	<!-- MESSAGE SUBJECT -->")
                .append("	<title>Jobcast</title>")
                .append("")
                .append("</head>")
                .append("")
                .append("<!-- BODY -->")
                .append("<!-- Set message background color (twice) and text color (twice) -->")
                .append("<body topmargin=\"0\" rightmargin=\"0\" bottommargin=\"0\" leftmargin=\"0\" marginwidth=\"0\" marginheight=\"0\" width=\"100%\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; width: 100%; height: 100%; -webkit-font-smoothing: antialiased; text-size-adjust: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; line-height: 100%;")
                .append("	background-color: #FFFFFF;")
                .append("	color: #000000;\"")
                .append("	bgcolor=\"#FFFFFF\"")
                .append("	text=\"#000000\">")
                .append("")
                .append("<!-- SECTION / BACKGROUND -->")
                .append("<!-- Set section background color -->")
                .append("<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; width: 100%;\" class=\"background\"><tr><td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0;\"")
                .append("	bgcolor=\"#127DB3\">")
                .append("")
                .append("<!-- WRAPPER -->")
                .append("<!-- Set wrapper width (twice) -->")
                .append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\"")
                .append("	width=\"600\" style=\"border-collapse: collapse; border-spacing: 0; padding: 0; width: inherit;")
                .append("	max-width: 600px;\" class=\"wrapper\">")

                .append("	<!-- HEADER -->")
                .append("	<!-- Set text color and font family (\"sans-serif\" or \"Georgia, serif\") -->")
                .append("	<tr>")
                .append("		<td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; padding-left: 6.25%; padding-right: 6.25%; width: 87.5%; font-size: 24px; font-weight: bold; line-height: 130%;")
                .append("			padding-top: 20px;")
                .append("			color: #FFFFFF;")
                .append("			font-family: sans-serif;\" class=\"header\">")
                .append("				당신을 위한 일자리 일거리 정보가 왔습니다")
                .append("		</td>")
                .append("	</tr>")
                .append("")
                .append("	<!-- SUBHEADER -->")
                .append("	<!-- Set text color and font family (\"sans-serif\" or \"Georgia, serif\") -->")
                .append("	<tr>")
                .append("		<td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; padding-bottom: 3px; padding-left: 6.25%; padding-right: 6.25%; width: 87.5%; font-size: 18px; font-weight: 300; line-height: 150%;")
                .append("			padding-top: 5px;")
                .append("			color: #FFFFFF;")
                .append("			font-family: sans-serif;\" class=\"subheader\">")
                .append("				회원님의 구직활동을 분석하여 채용정보를 추천해 드립니다")
                .append("		</td>")
                .append("	</tr>")
                .append("")
                .append("	<!-- HERO IMAGE -->")
                .append("	<!-- Image text color should be opposite to background color. Set your url, image src, alt and title. Alt text should fit the image size. Real image size should be x2 (wrapper x2). Do not set height for flexible images (including \"auto\"). URL format: http://domain.com/?utm_source={{Campaign-Source}}&utm_medium=email&utm_content={{ÃŒmage-Name}}&utm_campaign={{Campaign-Name}} -->")
                .append("	<tr>")
                .append("		<td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0;")
                .append("			padding-top: 20px;\" class=\"hero\"><a target=\"_blank\" style=\"text-decoration: none;\"")
                .append("			href=\"http://www.cportal.world\"><img border=\"0\" vspace=\"0\" hspace=\"0\"")
                .append("			src=\"cid:image\"")
                .append("			alt=\"Please enable images to view this content\" title=\"Hero Image\"")
                .append("			width=\"530\" style=\"")
                .append("			width: 88.33%;")
                .append("			max-width: 530px;")
                .append("			color: #FFFFFF; font-size: 13px; margin: 0; padding: 0; outline: none; text-decoration: none; -ms-interpolation-mode: bicubic; border: none; display: block;\"/></a></td>")
                .append("	</tr>")
                .append("")
                .append("	<!-- PARAGRAPH -->")
                .append("	<!-- Set text color and font family (\"sans-serif\" or \"Georgia, serif\"). Duplicate all text styles in links, including line-height -->")
                .append("	<tr>")
                .append("		<td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; padding-left: 6.25%; padding-right: 6.25%; width: 87.5%; font-size: 17px; font-weight: 400; line-height: 160%;")
                .append("			padding-top: 25px; ")
                .append("			color: #FFFFFF;")
                .append("			font-family: sans-serif;\" class=\"paragraph\">")
                .append("				더 많은 정보를 원하시면 아래의 버튼을 클릭해 주세요")
                .append("		</td>")
                .append("	</tr>")
                .append("")
                .append("	<!-- BUTTON -->")
                .append("	<!-- Set button background color at TD, link/text color at A and TD, font family (\"sans-serif\" or \"Georgia, serif\") at TD. For verification codes add \"letter-spacing: 5px;\". Link format: http://domain.com/?utm_source={{Campaign-Source}}&utm_medium=email&utm_content={{Button-Name}}&utm_campaign={{Campaign-Name}} -->")
                .append("	<tr>")
                .append("		<td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; padding-left: 6.25%; padding-right: 6.25%; width: 87.5%;")
                .append("			padding-top: 25px;")
                .append("			padding-bottom: 35px;\" class=\"button\"><a")
                .append("			href=\"http://www.cportal.world/\" target=\"_blank\" style=\"text-decoration: underline;\">")
                .append("				<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"max-width: 240px; min-width: 120px; border-collapse: collapse; border-spacing: 0; padding: 0;\"><tr><td align=\"center\" valign=\"middle\" style=\"padding: 12px 24px; margin: 0; text-decoration: underline; border-collapse: collapse; border-spacing: 0; border-radius: 4px; -webkit-border-radius: 4px; -moz-border-radius: 4px; -khtml-border-radius: 4px;\"")
                .append("					bgcolor=\"#0B5073\"><a target=\"_blank\" style=\"text-decoration: underline;")
                .append("					color: #FFFFFF; font-family: sans-serif; font-size: 17px; font-weight: 400; line-height: 120%;\"")
                .append("					href=\"http://www.cportal.world/\">")
                .append("						CPORTAL")
                .append("					</a>")
                .append("			</td></tr></table></a>")
                .append("		</td>")
                .append("	</tr>")
                .append("")
                .append("<!-- End of WRAPPER -->")
                .append("</table>")
                .append("")
                .append("<!-- SECTION / BACKGROUND -->")
                .append("<!-- Set section background color -->")
                .append("</td></tr><tr><td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0;")
                .append("	padding-top: 5px;\"")
                .append("	bgcolor=\"#FFFFFF\">")
                .append("")
                .append("<!-- WRAPPER -->")
                .append("<!-- Set conteiner background color -->")
                .append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\"")
                .append("	width=\"800\" style=\"border-collapse: collapse; border-spacing: 0; padding: 0; width: inherit;")
                .append("	max-width: 800px;\">");

        generateWanted(html, jobList, "job", "일자리");
        generateWanted(html, businessList, "business", "일거리");
        generateWanted(html, albaList, "alba", "알바");
        generateWanted(html, sellCompanyList, "sellCompany", "사업부 매각");






                html.append("<!-- End of WRAPPER -->")
                .append("</table>")
                .append("")
                .append("<!-- SECTION / BACKGROUND -->")
                .append("<!-- Set section background color -->")
                .append("</td></tr><tr><td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0;\"")
                .append("	bgcolor=\"#F0F0F0\">")
                .append("")
                .append("<!-- WRAPPER -->")
                .append("<!-- Set wrapper width (twice) -->")
                .append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\"")
                .append("	width=\"600\" style=\"border-collapse: collapse; border-spacing: 0; padding: 0; width: inherit;")
                .append("	max-width: 600px;\" class=\"wrapper\">")

                .append("	<!-- FOOTER -->")
                .append("	<!-- Set text color and font family (\"sans-serif\" or \"Georgia, serif\"). Duplicate all text styles in links, including line-height -->")
                .append("	<tr>")
                .append("		<td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; padding-left: 6.25%; padding-right: 6.25%; width: 87.5%; font-size: 13px; font-weight: 400; line-height: 150%;")
                .append("			padding-top: 20px;")
                .append("			padding-bottom: 20px;")
                .append("			color: #999999;")
                .append("			font-family: sans-serif;\" class=\"footer\">")
                .append("")
                .append("© 2022 jobcast. All Rights Reserved.<br/> 이 메일주소는 발신전용 주소입니다. 회신이 불가능합니다.")
                .append("		</td>")
                .append("	</tr>")
                .append("")
                .append("<!-- End of WRAPPER -->")
                .append("</table>")
                .append("")
                .append("<!-- End of SECTION / BACKGROUND -->")
                .append("</td></tr></table>")
                .append("")
                .append("</body>")
                .append("</html>");


        return html.toString();
    }


    private void generateWantedItem(StringBuilder html, List<WantedMailDto> mailList) {

        if(mailList == null || mailList.size() == 0) return;

        if(mailList.size() == 1) {
            html.append("")
                    .append("	<tr>")
                    .append("		<td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; padding-left: 10px; padding-right: 10px;\" class=\"floaters\"><table width=\"380\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"left\" valign=\"top\" style=\"border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-spacing: 0; margin: 0; padding: 0; display: inline-table; float: none;\" class=\"floater\"><tr><td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; padding-left: 15px; padding-right: 15px; font-size: 17px; font-weight: 400; line-height: 160%;")
                    .append("			padding-top: 30px; ")
                    .append("			font-family: sans-serif;")
                    .append("			color: #000000;\"><a target=\"_blank\" style=\"text-decoration: none;")
                    .append("			font-size: 17px; line-height: 160%;\"")
                    .append("			href=\"http://www.cportal.world/popup/view/wanted/" + mailList.get(0).getId() + "\">")
                    .append("				<b style=\"color:#0B5073; text-decoration: underline;\">" + mailList.get(0).getTitle() + "</b></a><br/>")
                    .append(				mailList.get(0).getNickName())
                    .append("		</td></tr></table></td>")
                    .append("	</tr>");
        } else if(mailList.size() == 2) {
            html.append("	<tr>")
                    .append("		<td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; padding-left: 10px; padding-right: 10px;\" class=\"floaters\"><table width=\"380\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"left\" valign=\"top\" style=\"border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-spacing: 0; margin: 0; padding: 0; display: inline-table; float: none;\" class=\"floater\"><tr><td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; padding-left: 15px; padding-right: 15px; font-size: 17px; font-weight: 400; line-height: 160%;")
                    .append("			padding-top: 30px; ")
                    .append("			font-family: sans-serif;")
                    .append("			color: #000000;\"><a target=\"_blank\" style=\"text-decoration: none;")
                    .append("			font-size: 17px; line-height: 160%;\"")
                    .append("			href=\"http://www.cportal.world/popup/view/wanted/" + mailList.get(0).getId() + "\">")
                    .append("				<b style=\"color:#0B5073; text-decoration: underline;\">" + mailList.get(0).getTitle() + "</b></a><br/>")
                    .append(				mailList.get(0).getNickName())
                    .append("		</td></tr></table><table width=\"380\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"right\" valign=\"top\" style=\"border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-spacing: 0; margin: 0; padding: 0; display: inline-table; float: none;\" class=\"floater\"><tr><td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; padding-left: 15px; padding-right: 15px; font-size: 17px; font-weight: 400; line-height: 160%;")
                    .append("			padding-top: 30px; ")
                    .append("			font-family: sans-serif;")
                    .append("			color: #000000;\"><a target=\"_blank\" style=\"text-decoration: none;")
                    .append("			font-size: 17px; line-height: 160%;\"")
                    .append("			href=\"http://www.cportal.world/popup/view/wanted/" + mailList.get(1).getId() + "\">")
                    .append("				<b style=\"color:#0B5073; text-decoration: underline;\">" + mailList.get(1).getTitle() + "</b></a><br/>")
                    .append(				mailList.get(1).getNickName())
                    .append("		</td></tr></table></td>")
                    .append("	</tr>");
        }

    }


    private void generateWanted(StringBuilder html, List<WantedMailDto> mailList, String type, String typeName) {

        if(mailList == null || mailList.size() == 0) return;

        html.append("	<!-- BUTTON -->")
                .append(" 	<tr>")
                .append("		<td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; border-spacing: 0; margin: 0; padding: 0; padding-left: 6.25%; padding-right: 6.25%; width: 87.5%;")
                .append("			padding-top: 30px;")
                .append("			padding-bottom: 35px;\" class=\"button\"><a")
                .append("			href=\"http://www.cportal.world?page=" + type + "\" target=\"_blank\" style=\"text-decoration: underline;\">")
                .append("				<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"max-width: 240px; min-width: 120px; border-collapse: collapse; border-spacing: 0; padding: 0;\"><tr><td align=\"center\" valign=\"middle\" style=\"padding: 12px 24px; margin: 0; text-decoration: underline; border-collapse: collapse; border-spacing: 0; border-radius: 4px; -webkit-border-radius: 4px; -moz-border-radius: 4px; -khtml-border-radius: 4px;\"")
                .append("					bgcolor=\"#127DB3\"><a target=\"_blank\" style=\"text-decoration: underline;")
                .append("					color: #FFFFFF; font-family: sans-serif; font-size: 17px; font-weight: 400; line-height: 120%;\"")
                .append("					href=\"http://www.cportal.world?page=" + type + "\">")
                .append(typeName)
                .append("					</a>")
                .append("			</td></tr></table></a>")
                .append("		</td>")
                .append("	</tr>");

        for(int i = 0; i < mailList.size(); i+=2) {
            int last = i + 2;
            if(last > mailList.size())
                last = last - 1;
            List<WantedMailDto> subList = mailList.subList(i, last);
            generateWantedItem(html, subList);
        }

    }


}
