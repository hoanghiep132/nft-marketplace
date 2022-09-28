package com.hiepnh.nftmarket.coresvc.service;

import com.hiepnh.nftmarket.coresvc.Test;
import com.hiepnh.nftmarket.coresvc.domain.response.BaseResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl extends BaseService implements MailService {


    private final JavaMailSender mailSender;

    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    @Value("${spring.mail.username}")
    private String emailSender;


    public BaseResponse sendMail(String username, String userMail) {
        BaseResponse response = new BaseResponse();
        try {
            MimeMessage mailMessage = mailSender.createMimeMessage();

            String header = PRE_HEADER + "  ";
            mailMessage.setSubject(header, "utf-8");

            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, "UTF-8");
            helper.setFrom(emailSender);
            helper.setTo(userMail);
            helper.setSubject(header);

            String contentSb = "Gửi " +
                    username +
                    "<br/>" +
                    "<p>Bạn đã tạo tài khoản trên trang “Nền tảng tích hợp, chia sẻ dữ liệu” của Bộ KH&ĐT thành công.</p>" +
                    "<p>Vui lòng bấm vào " +
                    "<a href=\"" +
//                    loginUrl +
                    "\">đây</a>" +
                    " để đăng nhập sử dụng dịch vụ của chúng tôi<p>" +
                    "<br/>" +
                    "<p>Trân trọng,</p>" +
                    "<p>Admin</p>" + NOTE;

            helper.setText(contentSb, true);
            mailSender.send(mailMessage);


            response.setSuccess();
        } catch (Exception ex) {
            logger.error("sendMailCreateAccountSuccess error", ex);
            response.setResult(-1, "Gửi mail thông báo tạo tài khoản không thành công");
        }
        return response;
    }

    private static final String PRE_HEADER = "[NFT_Market]";
    private static final String NOTE = "<i>Chú ý: Đây là email tự động, vui lòng không reply lại.</i>";

}
