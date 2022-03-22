package ru.job4j.pooh;

import org.junit.Test;

import static org.junit.Assert.*;

public class TopicServiceTest {
    @Test
    public void whenPostToTopicThenGetMessage() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForSubscriber1 = "client407";
        String paramForSubscriber2 = "client6565";
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        topicService.process(
                new Req("POST", "topic", "weather", paramForPublisher)
        );
        Resp result1 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        Resp result2 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber2)
        );
        assertEquals(paramForPublisher, result1.text());
        assertEquals("", result2.text());
    }

    @Test
    public void whenPostOneMessageToTopicThenAllSubscribesGetIt() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "some information";
        String paramForSubscriber1 = "client1";
        String paramForSubscriber2 = "client2";
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber2)
        );
        topicService.process(
                new Req("POST", "topic", "weather", paramForPublisher)
        );
        Resp result1 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        Resp result2 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber2)
        );
        assertEquals(paramForPublisher, result1.text());
        assertEquals(paramForPublisher, result2.text());
    }

}