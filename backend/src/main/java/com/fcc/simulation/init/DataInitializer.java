package com.fcc.simulation.init;

import com.fcc.simulation.entity.WhiskyInventory;
import com.fcc.simulation.repository.WhiskyInventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final WhiskyInventoryRepository repository;

    @Value("${app.table-md-path:../table.md}")
    private String tableMdPath;

    public DataInitializer(WhiskyInventoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (repository.count() > 0) {
            log.info("whisky_inventory already seeded, skipping.");
            return;
        }

        Path path = Paths.get(tableMdPath).toAbsolutePath();
        if (!Files.exists(path)) {
            log.warn("table.md not found at {}, skipping seed.", path);
            return;
        }

        List<String> lines = Files.readAllLines(path).stream()
                .map(String::trim)
                .filter(l -> l.startsWith("|"))
                .toList();

        if (lines.size() < 3) return;

        // header row
        String[] headers = splitRow(lines.get(0));
        // lines.get(1) is the separator row — skip
        List<String[]> dataRows = new ArrayList<>();
        for (int i = 2; i < lines.size(); i++) {
            dataRows.add(splitRow(lines.get(i)));
        }

        List<WhiskyInventory> entities = new ArrayList<>();
        for (int ri = 0; ri < dataRows.size(); ri++) {
            String[] cells = dataRows.get(ri);
            WhiskyInventory entity = new WhiskyInventory();
            entity.setAgeLabel(cellAt(cells, 0));
            entity.setDistYear(cellAt(cells, 1));
            entity.setTtl(cellAt(cells, 2));
            entity.setSortOrder(ri);

            Map<String, String> yearValues = new HashMap<>();
            for (int ci = 3; ci < headers.length; ci++) {
                String year = headers[ci];
                String value = cellAt(cells, ci);
                yearValues.put(year, value);
            }
            entity.setYearValues(yearValues);
            entities.add(entity);
        }

        repository.saveAll(entities);
        log.info("Seeded {} rows from {}", entities.size(), path);
    }

    private String[] splitRow(String line) {
        String[] parts = line.split("\\|", -1);
        // remove first and last empty elements from leading/trailing |
        if (parts.length >= 2) {
            String[] result = new String[parts.length - 2];
            for (int i = 0; i < result.length; i++) {
                result[i] = parts[i + 1].trim();
            }
            return result;
        }
        return new String[0];
    }

    private String cellAt(String[] cells, int index) {
        if (index >= cells.length) return "";
        return cells[index];
    }
}
