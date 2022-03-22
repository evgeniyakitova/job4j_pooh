package ru.job4j.pooh;

import org.junit.Test;

import static org.junit.Assert.*;

public class QueueServiceTest {
    @Test
    public void whenPostThenGetMessage() {
        QueueService queueService = new QueueService();
        String paramForPostMethod = "temperature=18";
        queueService.process(
                new Req("POST", "queue", "weather", paramForPostMethod)
        );
        Resp result = queueService.process(
                new Req("GET", "queue", "weather", "")
        );
        assertEquals("temperature=18", result.text());
        assertEquals("200", result.status());
    }

    @Test
    public void whenPostFewMessagesThenOnlyOneSubscriberGetEachMessage() {
        QueueService queueService = new QueueService();
        String firstParam = "temperature=18";
        String secondParam = "south wind";
        queueService.process(
                new Req("POST", "queue", "weather", firstParam)
        );
        queueService.process(
                new Req("POST", "queue", "weather", secondParam)
        );
        Resp firstResult = queueService.process(
                new Req("GET", "queue", "weather", "")
        );
        Resp secondResult = queueService.process(
                new Req("GET", "queue", "weather", "")
        );
        assertEquals(firstResult.text(), firstParam);
        assertEquals(secondResult.text(), secondParam);
    }
}