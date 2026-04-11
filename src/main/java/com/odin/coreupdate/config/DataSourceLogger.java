package com.odin.coreupdate.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DataSourceLogger implements CommandLineRunner {

    private final DataSource dataSource;

    public DataSourceLogger(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) {
        try {
            if (dataSource instanceof HikariDataSource) {
                HikariDataSource hikariDataSource = (HikariDataSource) dataSource;
                String url = hikariDataSource.getJdbcUrl();
                
                // Extract database name from URL
                String dbName = "UNKNOWN";
                if (url != null && url.contains("/")) {
                    String[] parts = url.split("/");
                    dbName = parts[parts.length - 1].split("\\?")[0]; // Remove query params
                }
                
                System.out.println("\n========================================");
                System.out.println("[DATASOURCE-PROOF] core-update DataSource Configuration");
                System.out.println("[DATASOURCE-PROOF] JDBC URL: " + url);
                System.out.println("[DATASOURCE-PROOF] Database Name: " + dbName);
                System.out.println("[DATASOURCE-PROOF] Expected Database: core");
                System.out.println("[DATASOURCE-PROOF] Status: " + (dbName.equals("core") ? "✓ CORRECT" : "✗ WRONG - Should be 'core', got '" + dbName + "'"));
                System.out.println("========================================\n");
            } else {
                System.out.println("[DATASOURCE-PROOF] DataSource is not HikariDataSource: " + dataSource.getClass().getName());
            }
        } catch (Exception e) {
            System.out.println("[DATASOURCE-PROOF] Error extracting datasource info: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
