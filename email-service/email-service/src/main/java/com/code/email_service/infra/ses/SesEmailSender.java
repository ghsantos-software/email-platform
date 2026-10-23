package com.code.email_service.infra.ses;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Body;

import com.code.email_service.adapters.EmailSenderGateway;
import com.code.email_service.core.exceptions.EmailServiceException;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class SesEmailSender implements EmailSenderGateway {

    private final AmazonSimpleEmailService sesClient;

    public SesEmailSender(AmazonSimpleEmailService sesClient) {
        this.sesClient = sesClient;
    }

    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        SendEmailRequest request = new SendEmailRequest()
                .withSource("guihenrique.s@outlook.com")
                .withDestination(
                        new Destination().withToAddresses(toEmail)
                )
                .withMessage(
                        new Message()
                                .withSubject(new Content(subject))
                                .withBody(
                                        new Body().withText(new Content(body))
                                )
                );

        try {
            sesClient.sendEmail(request);
            log.info("E-mail enviado com sucesso para {}", toEmail);

        } catch (AmazonServiceException ex) {
            log.error(
                    "Erro retornado pela AWS SES: código={}, mensagem={}",
                    ex.getErrorCode(),
                    ex.getErrorMessage(),
                    ex
            );

            throw new EmailServiceException(
                    "Falha ao enviar e-mail pelo AWS SES.",
                    ex
            );

        } catch (Exception ex) {
            log.error("Erro inesperado ao enviar e-mail", ex);

            throw new EmailServiceException(
                    "Erro inesperado ao enviar e-mail.",
                    ex
            );
        }
    }
}

