package com.odin.coreupdate.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DataSourceLogger implements CommandLineRunner {

    private final DataSource dataSource;

    public DataSourceLogger(DataSource dataSource) {
        System.out.println("[DATASOURCE-PROOF] DataSourceLogger bean instantiated!");
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        Thread.sleep(100); // Small delay to ensure logging is flushed
        System.out.println("\n[DATASOURCE-PROOF] ============ APPLICATION STARTED - DATASOURCE INFO ============");
        
        try {
            if (dataSource instanceof HikariDataSource) {
                HikariDataSource hikariDataSource = (HikariDataSource) dataSource;
                String url = hikariDataSource.getJdbcUrl();
                String username = hikariDataSource.getUsername();
                
                System.out.println("[DATASOURCE-PROOF] ✓ HikariDataSource detected");
                System.out.println("[DATASOURCE-PROOF] JDBC URL: " + url);
                System.out.println("[DATASOURCE-PROOF] Username: " + username);
                
                // Extract database name from URL
                String dbName = "UNKNOWN";
                if (url != null && url.contains("/")) {
                    String[] parts = url.split("/");
                    dbName = parts[parts.length - 1].split("\\?")[0]; // Remove query params
                }
                
                System.out.println("[DATASOURCE-PROOF] Database Name: " + dbName);
                System.out.println("[DATASOURCE-PROOF] Expected: core");
                System.out.println("[DATASOURCE-PROOF] Result: " + (dbName.equals("core") ? "✅ CORRECT" : "❌ WRONG - ' got '" + dbName + "'"));
                
            } else {
                System.out.println("[DATASOURCE-PROOF] ⚠️ DataSource type: " + dataSource.getClass().getName());
                System.out.println("[DATASOURCE-PROOF] Note: Only HikariDataSource provides JDBC URL inspection");
            }
        } catch (Exception e) {
            System.out.println("[DATASOURCE-PROOF] ❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("[DATASOURCE-PROOF] ======================================================================\n");
    }
}
