package com.example.cqrsui;

import com.example.cqrs.events.UserDeactivated;
import com.example.cqrsui.channels.Channels;
import java.time.Instant;
import java.util.concurrent.BlockingQueue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class E2ETest {

  @Autowired
  Channels channels;

  @Autowired
  MessageCollector messageCollector;

  BlockingQueue<Message<?>> payments;

  @Before
  public void setUp() {
    payments = messageCollector.forChannel(channels.payments());
  }

  @Test
  public void whenUserIsDeactivatedThen3CommandsAreThrown() {
    channels.users().send(new GenericMessage<Object>(new UserDeactivated(Instant.now())));
    ((String) payments.poll().getPayload()).contains("cancelPayment");
  }

}
