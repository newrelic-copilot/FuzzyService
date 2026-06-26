package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BatchProcessorTest {

    private final BatchProcessor processor = new BatchProcessor();

    @Test
    public void testProcessItems_normalInput() {
        List<String> input = Arrays.asList("  hello  ", "world", "  foo  ");
        List<String> result = processor.processItems(input);
        assertEquals(3, result.size());
        assertEquals("HELLO", result.get(0));
        assertEquals("WORLD", result.get(1));
        assertEquals("FOO", result.get(2));
    }

    @Test
    public void testProcessItems_emptyList() {
        List<String> result = processor.processItems(Collections.emptyList());
        assertTrue(result.isEmpty());
    }

    @Test
    public void testProcessItems_nullList() {
        List<String> result = processor.processItems(null);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testProcessSingleItem_stripsAndUppercases() {
        assertEquals("TEST", processor.processSingleItem("  test  "));
        assertEquals("HELLO", processor.processSingleItem("hello"));
    }

    @Test
    public void testProcessSingleItem_nullInput() {
        assertEquals("", processor.processSingleItem(null));
    }
}
