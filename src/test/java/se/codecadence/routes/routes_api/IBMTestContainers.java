package se.codecadence.routes.routes_api;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.Db2Container;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class IBMTestContainers {

    @Container
    public static final Db2Container DB2_CONTAINER = new Db2Container(
        DockerImageName.parse("icr.io/db2_community/db2:11.5.9.0"))
            // The DB2 image requires accepting an EULA
            .acceptLicense();


    @Test
    void isDB2ContainerRunningAndConnects() {
        // Assert that the container is running
        assert(DB2_CONTAINER.isRunning());

        // Use the connection details provided by Testcontainers to connect to the database
         // Use the connection details provided by Testcontainers to connect
        try (Connection connection = DB2_CONTAINER.createConnection("")) {
            assertTrue("Connection to DB2 is not valid.", connection.isValid(5) );
            System.out.println("Successfully connected to DB2!");
        } catch (Exception e) {
            fail("Failed to connect to DB2 container: " + e.getMessage());
        }
    }

    @Test
    void testCanExecuteSimpleQuery() {
        // Test a simple query to ensure the database is functional
        String sql = "SELECT * FROM SYSCAT.SCHEMATA WHERE SCHEMANAME = 'SYSCAT'";

        try (Connection connection = DB2_CONTAINER.createConnection("");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            
            // Expect at least one row for the SYSCAT schema
            assertTrue("Query returned no results, indicating a problem.", resultSet.next());
            System.out.println("Simple query executed successfully.");

        } catch (Exception e) {
            fail("Failed to execute query or process result: " + e.getMessage());
        }
    }
}
