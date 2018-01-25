package com.example.cqrsui;

import com.example.cqrs.events.DomainEvent;
import com.example.cqrs.events.UserDeactivated;
import com.example.cqrsui.channels.Channels;
import com.example.cqrsui.channels.CommandPublisher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@SpringBootApplication
@EnableBinding(Channels.class)
public class CqrsUiApplication {

  private final CommandPublisher commandPublisher;

  public CqrsUiApplication(CommandPublisher commandPublisher) {
    this.commandPublisher = commandPublisher;
  }

  public static void main(String[] args) {
    SpringApplication.run(CqrsUiApplication.class, args);
  }

  @StreamListener("user")
  public void handleEvent(DomainEvent event) {
    System.out.println("received =" + event);
    if (event instanceof UserDeactivated) {
      commandPublisher.sendCmdToPayment("cancelPayment");
    }
  }
}
