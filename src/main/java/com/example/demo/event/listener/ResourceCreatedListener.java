package com.example.demo.event.listener;

import com.example.demo.event.ResourceCreateEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

/**
 * @author KarlsCode.
 */

@Component
public class ResourceCreatedListener implements ApplicationListener<ResourceCreateEvent> {

    @Override
    public void onApplicationEvent(ResourceCreateEvent resourceCreateEvent) {
        HttpServletResponse response = resourceCreateEvent.getResponse();
        Long id = resourceCreateEvent.getId();

        addHeaderLocation(response, id);
    }

    private void addHeaderLocation(HttpServletResponse response, Long id) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{code}").buildAndExpand(id).toUri();

        response.addHeader("Location", uri.toASCIIString());
    }
}
