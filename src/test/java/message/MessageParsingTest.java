package message;

import com.dsidak.utils.Message;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageParsingTest {
    @Test
    public void testMessageParsing() {
        String data = "username: content";
        Message message = Message.of(data);
        assertEquals("username", message.getUsername());
        assertEquals("content", message.getContent());
    }

    @Test
    public void testWithoutSemicolon() {
        String data = "messageWithContent";
        Message message = Message.of(data);
        assertEquals("", message.getUsername());
        assertEquals("messageWithContent", message.getContent());
    }

    @Test
    public void testManySemicolons() {
        String data = "username: one:two:many:semicolons";
        Message message = Message.of(data);
        assertEquals("username", message.getUsername());
        assertEquals("one:two:many:semicolons", message.getContent());
    }
}
