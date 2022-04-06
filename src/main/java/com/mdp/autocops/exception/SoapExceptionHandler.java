package com.mdp.autocops.exception;

import lombok.extern.log4j.Log4j2;
import org.w3c.dom.DOMException;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.ByteArrayOutputStream;
import java.util.Set;

@Log4j2
public class SoapExceptionHandler implements SOAPHandler<SOAPMessageContext> {
    @Override
    public Set<QName> getHeaders() {
        return null;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext smc) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            SOAPMessage msg = smc.getMessage();
            msg.writeTo(out);
            logSoupMsg(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        try {
            String msg = null;
            if (context != null && context.getMessage() != null && context.getMessage().getSOAPBody() != null
                    && context.getMessage().getSOAPBody().getFault() != null) {
                if (context.getMessage().getSOAPBody().getFault().getDetail() != null
                        && context.getMessage().getSOAPBody().getFault().getDetail().getTextContent() != null) {
                    msg = context.getMessage().getSOAPBody().getFault().getDetail().getTextContent();
                } else if (context.getMessage().getSOAPBody().getFault().getFaultString() != null) {
                    msg = context.getMessage().getSOAPBody().getFault().getFaultString();
                }
            }
            if (msg != null && (msg.toLowerCase().contains("not found") || msg.toUpperCase().contains("NOT_FOUND")
                    || msg.toUpperCase().contains("WSA001") || msg.toUpperCase().contains("INCORRECT"))) {
                throw new NotFoundException(msg);
            } else if (msg != null && (msg.toUpperCase().contains("WSA003") || msg.contains("already exists") || msg.contains("doesn't belong to"))) {
                throw new InvalidRequestException(msg);
            }
            throw new WebServiceException(msg);
        } catch (DOMException | SOAPException e) {
            return true;
        }
    }

    @Override
    public void close(MessageContext messageContext) {

    }

    private void logSoupMsg(ByteArrayOutputStream out) {
        String soapMsg = out.toString();
        String cardNumberStart = null;
        String cardNumberEnd = null;
        if (soapMsg.contains("<card_number>")) {
            cardNumberStart = "<card_number>";
            cardNumberEnd = "</card_number>";
        } else if (soapMsg.contains("<cardNumber>")) {
            cardNumberStart = "<cardNumber>";
            cardNumberEnd = "</cardNumber>";
        }
        if (cardNumberStart != null && soapMsg.contains(cardNumberStart)) {
            int index = soapMsg.indexOf(cardNumberStart) + 6;
            int endIndex = soapMsg.indexOf(cardNumberEnd) - 4;
            soapMsg = soapMsg.substring(0, index + cardNumberStart.length()) + "******" + soapMsg.substring(endIndex);
        }
        log.info(soapMsg);
    }
}
