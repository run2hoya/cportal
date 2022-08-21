package com.castis.cportal.service.createMail;

import com.castis.cportal.dto.wanted.WantedApplicantDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApplicantMailService {

    //너무나 귀찮다
    public static StringBuilder generateHtml(WantedApplicantDto wantedApplicantDto) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<!-- Complete Email template -->\n" +
                        "   \n" +
                        "<body style=\"background-color:grey\">\n" +
                        "    <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                        "           width=\"550\" bgcolor=\"white\" style=\"border:2px solid black\">\n" +
                        "        <tbody>\n" +
                        "            <tr>\n" +
                        "                <td align=\"center\">\n" +
                        "                    <table align=\"center\" border=\"0\" cellpadding=\"0\"\n" +
                        "                           cellspacing=\"0\" class=\"col-550\" width=\"550\">\n" +
                        "                        <tbody>\n" +
                        "                            <tr>\n" +
                        "                                <td align=\"center\" style=\"background-color: #4cb96b;\n" +
                        "                                           height: 50px;\">\n" +
                        "   \n" +
                        "                                    <a href=\"#\" style=\"text-decoration: none;\">\n" +
                        "                                        <p style=\"color:white;\n" +
                        "                                                  font-weight:bold;\">\n" +
                        "                                            Jobcast\n" +
                        "                                        </p>\n" +
                        "                                    </a>\n" +
                        "                                </td>\n" +
                        "                            </tr>\n" +
                        "                        </tbody>\n" +
                        "                    </table>\n" +
                        "                </td>\n" +
                        "            </tr>\n" +
                        "            <tr style=\"height: 150px;\">\n" +
                        "                <td align=\"center\" style=\"border: none;\n" +
                        "                           border-bottom: 2px solid #4cb96b; \n" +
                        "                           padding-right: 20px;padding-left:20px\">\n" +
                        "   <h2>공고 Title</h2>\n" +
                        "                    <p style=\"font-weight: bolder;font-size: 42px;\n" +
                        "                              letter-spacing: 0.025em;\n" +
                        "                              color:black;\">\n");
        stringBuilder.append(wantedApplicantDto.getWantedTitle() +
                        "                    </p>\n" +
                        "                </td>\n" +
                        "            </tr>\n" +
                        "   \n" +
                        "            <tr style=\"display: inline-block;\">\n" +
                        "                <td style=\"height: 150px;\n" +
                        "                           padding: 20px;\n" +
                        "                           border: none; \n" +
                        "                           border-bottom: 2px solid #361B0E;\n" +
                        "                           background-color: white;\">\n" +
                        "                     \n" +
                        "                    <h2 style=\"text-align: left;\n" +
                        "                               align-items: center;\">\n" +
                        "                        지원자 \n" +
                        "                   </h2>\n" +
                        "                    <p class=\"data\"\n" +
                        "                       style=\"text-align: justify-all;\n" +
                        "                              align-items: center; \n" +
                        "                              font-size: 15px;\n" +
                        "                              padding-bottom: 12px;\">\n");
        stringBuilder.append("이름 : ").append(wantedApplicantDto.getName()).append("<br>\n");
        stringBuilder.append("email : ").append(wantedApplicantDto.getEmail()).append("<br>\n");
        stringBuilder.append("phone : ").append(wantedApplicantDto.getPhone()).append("<br>\n");

        stringBuilder.append(
                        "                    </p>\n" +
                        "                </td>\n" +
                        "            </tr>\n" +
                        "            <tr style=\"border: none; \n" +
                        "            background-color: #4cb96b; \n" +
                        "            height: 40px; \n" +
                        "            color:white; \n" +
                        "            padding-bottom: 20px; \n" +
                        "            text-align: center;\">\n" +
                        "                  \n" +
                        "<td height=\"40px\" align=\"center\">\n" +

                                "                  <a href=\"#\"\n" +
                                "                  target=\"_blank\"\n" +
                                "                  style=\"text-decoration:none; \n" +
                                "                         color:#999999;\">\n" +

                        "    <p style=\"color:white; \n" +
                        "    line-height: 1.5em;\">\n" +
                        "    jobcast\n" +
                        "    </p>\n" +
                                "</a>"+
                        "</td>\n" +
                        "</tr>\n" +
                        "<tr>\n" +
                        "<td style=\"font-family:'Open Sans', Arial, sans-serif;\n" +
                        "           font-size:11px; line-height:18px; \n" +
                        "           color:#999999;\" \n" +
                        "    valign=\"top\"\n" +
                        "    align=\"center\">\n" +
                        "                  © 2022 jobcast. All Rights Reserved.<br>\n" +
                        "                  이 메일주소는 발신전용 주소입니다. 회신이 불가능합니다. \n" +
                        "            </td>\n" +
                        "              </tr>\n" +
                        "            </tbody></table></td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "          <td class=\"em_hide\"\n" +
                        "          style=\"line-height:1px;\n" +
                        "                 min-width:700px;\n" +
                        "                 background-color:#ffffff;\">\n" +
                        "              <img alt=\"\" \n" +
                        "              src=\"images/spacer.gif\" \n" +
                        "              style=\"max-height:1px; \n" +
                        "              min-height:1px; \n" +
                        "              display:block; \n" +
                        "              width:700px; \n" +
                        "              min-width:700px;\" \n" +
                        "              width=\"700\"\n" +
                        "              border=\"0\" \n" +
                        "              height=\"1\">\n" +
                        "              </td>\n" +
                        "        </tr>\n" +
                        "        </tbody>\n" +
                        "    </table>\n" +
                        "</body>");
        return stringBuilder;
    }

}
