package org.example;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class BatchProcessor {
    private static final Logger logger = Logger.getLogger(BatchProcessor.class.getName());

    public List<String> processItems(List<String> items) {
        List<String> results = new ArrayList<>();
        if (items == null || items.isEmpty()) {
            logger.info("No items to process in batch.");
            return results;
        }
        for (String item : items) {
            results.add(processSingleItem(item));
        }
        logger.info("Batch processing completed. Processed " + results.size() + " item(s).");
        return results;
    }

    public String processSingleItem(String item) {
        if (item == null) {
            return "";
        }
        return StringUtils.strip(item).toUpperCase();
    }
}
