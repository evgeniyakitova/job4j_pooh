package ru.job4j.pooh;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    Map<String, Map<String, Queue<String>>> topics = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String text = "";
        String status = "204";
        if ("GET".equals(req.httpRequestType())) {
            var topic = topics.computeIfAbsent(req.getSourceName(), key ->  new ConcurrentHashMap<>());
            var userQueue = topic.computeIfAbsent(req.getParam(), key -> new ConcurrentLinkedQueue<>());
            String data = userQueue.poll();
            if (data != null) {
                text = data;
                status = "200";
            }
        } else if ("POST".equals(req.httpRequestType())) {
            var topic = topics.get(req.getSourceName());
            if (topic != null && !req.getParam().isEmpty()) {
                topic.values().forEach(queue -> queue.add(req.getParam()));
                status = "200";
            }
        }
        return new Resp(text, status);
    }
}
