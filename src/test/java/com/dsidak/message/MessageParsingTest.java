package com.dsidak.message;

import com.dsidak.utils.Message;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MessageParsingTest {
    @Test
    public void testMessageParsing() {
        String data = "username: content";
        Message message = Message.of(data);
        assertEquals("username", message.getUsername());
        assertEquals("content", message.getContent());
        assertEquals("[username]: content", message.toString());
    }

    @Test
    public void testWithoutSemicolon() {
        String data = "messageWithoutUsername";
        assertThrows(IllegalArgumentException.class, () -> Message.of(data));
    }

    @Test
    public void testManySemicolons() {
        String data = "username: one:two:many:semicolons";
        Message message = Message.of(data);
        assertEquals("username", message.getUsername());
        assertEquals("one:two:many:semicolons", message.getContent());
        assertEquals("[username]: one:two:many:semicolons", message.toString());
    }

    @Test
    public void testStringToMessageAndBack() {
        String data = "username: content";
        Message message = Message.of(data);
        assertEquals("[username]: content", message.toString());
        String messageToString = message.toString();
        assertEquals("[username]: content", messageToString);
        Message fromString = Message.of(messageToString);
        assertEquals("username", fromString.getUsername());
        assertEquals("content", fromString.getContent());
        assertEquals("[username]: content", fromString.toString());
    }
}
