package com.example.cqrsui.channels;

import org.springframework.integration.annotation.Publisher;
import org.springframework.stereotype.Component;

@Component
public class CommandPublisher {

  @Publisher(channel = "payments")
  public String sendCmdToPayment(String event) {
    System.out.println("Send: " + event);
    return event;
  }
}
