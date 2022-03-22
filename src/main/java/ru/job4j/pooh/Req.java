package ru.job4j.pooh;

public class Req {
    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String ls = System.lineSeparator();
        String[] startLine = content.split(ls)[0].split(" ");
        String[] source = startLine[1].substring(1).split("/");
        String param = "";
        if ("GET".equals(startLine[0]) && "topic".equals(source[0])) {
            if (source.length < 3) {
                throw new IllegalArgumentException();
            }
            param = source[2];
        } else if ("POST".equals(startLine[0])) {
            param = content.split(ls + ls)[1].trim();
        }
        return new Req(startLine[0], source[0], source[1], param);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}
