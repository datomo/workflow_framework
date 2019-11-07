package ch.gangoffour.workflow.message;

public class Message<H, B> {
    public final H header;
    public final B body;

    public Message(H header, B body) {
        this.header = header;
        this.body = body;
    }

    public Message<H,B> changeHeader(H header){
        return new Message<>(header, this.body);
    }

    public Message<H,B> changeBody(B body){
        return new Message<>(this.header, body);
    }
}
