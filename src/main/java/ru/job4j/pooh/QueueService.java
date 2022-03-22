package ru.job4j.pooh;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    Map<String, Queue<String>> queues = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String text = "";
        String status = "204";
        if ("GET".equals(req.httpRequestType())) {
            var queue = queues.get(req.getSourceName());
            if (queue != null) {
                String data = queue.poll();
                if (data != null) {
                    text = data;
                    status = "200";
                }
            }
        } else if ("POST".equals(req.httpRequestType()) && !req.getParam().isEmpty()) {
            var queue = queues.computeIfAbsent(req.getSourceName(), key -> new ConcurrentLinkedQueue<>());
            queue.add(req.getParam());
            status = "200";
        }
        return new Resp(text, status);
    }
}
