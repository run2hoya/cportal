package com.castis.cportal.service;

import com.castis.commonLib.dto.TransactionID;
import com.castis.cportal.common.setting.Properties;
import com.castis.cportal.dto.wanted.WantedApplicantDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {

    private final Properties properties;


    public MimeMessage getMimeMessage(TransactionID trId, String subject, InternetAddress[] toAddr, String sender) {

        // SMTP 서버 정보를 설정한다. (ssl적용에따라 설정옵션이 달라진다. 아래는 ssl적용 안한버전이다.)
        java.util.Properties props = new java.util.Properties();
        props.put("mail.host", "mail.castis.com");
        props.put("mail.smtp.port", "25");
        props.put("mail.transport.protocol", "smtp");


        Session session = Session.getDefaultInstance(props, null);


        try {
            MimeMessage message = new MimeMessage(session);
            //받는사람 메일
            message.setFrom(new InternetAddress(sender, MimeUtility.encodeText(sender, "UTF-8", "B"))); // 한글의 경우 encoding 필요
            message.setRecipients(Message.RecipientType.TO, toAddr); //수신자 셋팅

            // 메일 제목
            message.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));
            // 메일 내용
            // 메일 내용을 설정을 위한 클래스를 설정합니다.
            message.setContent(new MimeMultipart());

            return message;
        } catch (Exception e) {
            log.error(trId + "", e);
        }

        return null;
    }

    public boolean sendMail(TransactionID trId, String body, String subject, InternetAddress[] toAddr, String sender) {

        try {
            MimeMessage message = getMimeMessage(trId, subject, toAddr, sender);

            // 메일 내용을 위한 Multipart클래스를 받아온다. (위 new MimeMultipart()로 넣은 클래스입니다.)
            Multipart mp = (Multipart) message.getContent();
            // html 형식으로 본문을 작성해서 바운더리에 넣습니다.
            mp.addBodyPart(getContents(body));
            // 메일을 보냅니다.
            Transport.send(message);

            log.info(trId + " mail 전송 완료");

            return true;
        } catch (Exception e) {
            log.error("", e);
            return false;
        }
    }

    public void sendMail(TransactionID trId, String pdfFilePath, String subject, List<File> imageList,
                         InternetAddress[] toAddr, String sender) {

        try {
            MimeMessage message = getMimeMessage(trId, subject, toAddr, sender);

            File pdfFile = new File(pdfFilePath);

            // 메일 내용을 위한 Multipart클래스를 받아온다. (위 new MimeMultipart()로 넣은 클래스입니다.)
            Multipart mp = (Multipart) message.getContent();
            // html 형식으로 본문을 작성해서 바운더리에 넣습니다.
            mp.addBodyPart(getContents("<html><head></head><body><p>보다 자세한 정보는 첨부파일을 확인해 주세요</p><br><img src=\"cid:image\" ></body></html>"));
            // 첨부 파일을 추가합니다.
            mp.addBodyPart(getFileAttachment(pdfFile));
            // 이미지 파일을 추가해서 contextId를 설정합니다. contextId는 위 본문 내용의 cid로 링크가 설정 가능합니다.
            mp.addBodyPart(getImage(imageList.get(0), "image"));
            // 메일을 보냅니다.
            Transport.send(message);

            for (File file : imageList) {
                file.deleteOnExit();
            }
            pdfFile.deleteOnExit();

            log.info(trId + " mail 전송 완료");
        } catch (Exception e) {
            log.error("", e);
        }
    }


    private static void wait(int mSecond) { //웹 자동화이기 때문에 버튼을 누르고 나면 조금 기다려주기 위해 만든 메소드. 10을 넣으면 1초를 기다린다.
        try {
            Thread.sleep(mSecond);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private BodyPart getContents(String html) throws MessagingException {
        BodyPart mbp = new MimeBodyPart();
        // setText를 이용할 경우 일반 텍스트 내용으로 설정된다.
        // mbp.setText(html);
        // html 형식으로 설정
        mbp.setContent(html, "text/html; charset=utf-8");
        return mbp;
    }

    // 파일을 로컬로 부터 읽어와서 BodyPart 클래스로 만든다. (바운더리 변환)
    private BodyPart getFileAttachment(File file) throws MessagingException {
        // BodyPart 생성
        BodyPart mbp = new MimeBodyPart();
        // 파일 읽어서 BodyPart에 설정(바운더리 변환)
        DataSource source = new FileDataSource(file);
        mbp.setDataHandler(new DataHandler(source));
        mbp.setDisposition(Part.ATTACHMENT);
        mbp.setFileName(file.getName());
        return mbp;
    }

    private BodyPart getImage(File file, String contextId) throws MessagingException {
        // 파일을 읽어와서 BodyPart 클래스로 받는다.
        BodyPart mbp = getFileAttachment(file);
        if (contextId != null) {
            // ContextId 설정
            mbp.setHeader("Content-ID", "<" + contextId + ">");
        }
        return mbp;
    }

    //너무나 귀찮다
    public StringBuilder generateHtml(WantedApplicantDto wantedApplicantDto) {
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
