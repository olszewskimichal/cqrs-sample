package com.example.cqrsui.channels;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {

  @Input("user")
  SubscribableChannel users();

  @Output("payments")
  MessageChannel payments();

  @Output("shipments")
  MessageChannel shipments();

  @Output("communcation")
  MessageChannel communication();

}
