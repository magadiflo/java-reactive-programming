package dev.magadiflo.app.sec12.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class SlackRoom {
    private static final Logger log = LoggerFactory.getLogger(SlackRoom.class);
    private final String name;
    private final Sinks.Many<SlackMessage> sink;
    private final Flux<SlackMessage> flux;

    public SlackRoom(String name) {
        this.name = name;
        this.sink = Sinks.many().replay().all();
        this.flux = this.sink.asFlux();
    }

    public void addMember(SlackMember slackMember) {
        log.info("{} se unió al room {}", slackMember.getName(), this.name);
        this.subscribeToRoomMessages(slackMember);
        slackMember.setMessageConsumer(message -> this.postMessage(slackMember.getName(), message));
    }

    private void subscribeToRoomMessages(SlackMember slackMember) {
        this.flux
                .filter(slackMessage -> !slackMessage.sender().equals(slackMember.getName()))
                .map(slackMessage -> slackMessage.formatForDelivery(slackMember.getName()))
                .subscribe(slackMember::receives);
    }

    private void postMessage(String sender, String message) {
        this.sink.tryEmitNext(new SlackMessage(sender, message));
    }
}
