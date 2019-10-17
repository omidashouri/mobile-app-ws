package ir.omidashouri.mobileappws.jacocotest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageBuilderTest {

    @Test
    void getMessage() {
        MessageBuilder obj = new MessageBuilder();
        assertEquals("Hello omid", obj.getMessage("omid"));
    }
}