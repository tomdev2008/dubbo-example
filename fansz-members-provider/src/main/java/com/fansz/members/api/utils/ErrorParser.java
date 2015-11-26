package com.fansz.members.api.utils;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Vector;

/**
 * 错误消息解析
 *
 * Created by xuzhen on 15/7/26.
 */
@AllArgsConstructor
@Log4j
@Service
public class ErrorParser {

    private MessageSource messageSource = null;

    public List<ErrorMessage> phase(Set<ConstraintViolation<?>> constraintViolations) {
        Vector<ErrorMessage> errorMessages = new Vector<>();
        String errorMessage;
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            if (messageSource == null)
                errorMessage = constraintViolation.getMessage();
            else
                errorMessage = messageSource.getMessage(constraintViolation.getMessageTemplate(), null, Locale.getDefault());
            try {
                errorMessages.add(new ErrorMessage(errorMessage));
            } catch (MessageFormatException mfe) {
                log.error("Phase ErrorMessage Error：" + errorMessage);
            }
        }
        return errorMessages;
    }

    public ErrorMessage phase (String message) {
        String errorMessageStr;
        ErrorMessage errorMessage = null;
        if (messageSource == null)
            errorMessageStr = message;
        else
            errorMessageStr = messageSource.getMessage(message, null, Locale.getDefault());
        try {
            errorMessage = new ErrorMessage(errorMessageStr);
        } catch (MessageFormatException mfe) {
            log.error("Phase ErrorMessage Error：" + errorMessageStr);
        }
        return errorMessage;
    }
}
