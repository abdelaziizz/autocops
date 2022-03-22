package com.mdp.autocops.config;

import com.mdp.autocops.exception.SoapExceptionHandler;
import com.mdp.autocops.exception.TechnicalException;
import com.mdp.autocops.model.constant.ServicePortProperties;
import generatedSources.cxf.ru.bpc.svxp.omnichannels.OmniChannelsPort;
import generatedSources.cxf.ru.bpc.svxp.omnichannels.OmniChannelsWS;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.namespace.QName;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import java.net.URL;
import java.util.List;
import java.util.Locale;

@Configuration
@RequiredArgsConstructor
public class ServicePortConfig {
    private final ServicePortProperties servicePortProperties;
    private final MessageSource messageSource;



    @Bean
    public OmniChannelsPort createOmniChannelsWS() {
        OmniChannelsPort port;
        try {

            OmniChannelsWS service = new OmniChannelsWS(new URL(servicePortProperties.getOmniChannelsApi()));
            port = service.getOmniChannelsSOAP();
            Binding binding = ((BindingProvider) port).getBinding();
            List<Handler> handlerList = binding.getHandlerChain();
            handlerList.add(new SoapExceptionHandler());
            binding.setHandlerChain(handlerList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new TechnicalException(messageSource.getMessage("UNKNOWN_ERROR", new Object[]{}, Locale.getDefault()));
        }
        return port;
    }


}
