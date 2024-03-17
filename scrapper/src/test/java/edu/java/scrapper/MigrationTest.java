package edu.java.scrapper;

import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class MigrationTest extends IntegrationTest {
    @Test
    public void chatTableTest() throws SQLException {
        try (Connection connection = POSTGRES.createConnection("")){
            PreparedStatement sqlQuery = connection.prepareStatement("SELECT * FROM public.chat");
            String actualResult = sqlQuery.executeQuery().getMetaData().getColumnName(1);
            assertThat(actualResult).isEqualTo("idchat");
        }
    }
   @Test
    public void linkTableTest() throws SQLException {
        try (Connection connection = POSTGRES.createConnection("")){
            PreparedStatement sqlQuery = connection.prepareStatement("SELECT * FROM link");
            String firstColumn = sqlQuery.executeQuery().getMetaData().getColumnName(1);
            String secondColumn = sqlQuery.executeQuery().getMetaData().getColumnName(2);
            String thirdColumn = sqlQuery.executeQuery().getMetaData().getColumnName(3);
            assertThat(firstColumn).isEqualTo("idlink");
            assertThat(secondColumn).isEqualTo("url");
            assertThat(thirdColumn).isEqualTo("update_at");
        }
    }
    @Test
    public void connectionTableTest() throws SQLException {
        try (Connection connection = POSTGRES.createConnection("")) {
            PreparedStatement sqlQuery = connection.prepareStatement("SELECT * FROM chatlink");
            String firstColumn = sqlQuery.executeQuery().getMetaData().getColumnName(1);
            String secondColumn = sqlQuery.executeQuery().getMetaData().getColumnName( 2);
            assertThat(firstColumn).isEqualTo("idchat");
            assertThat(secondColumn).isEqualTo("idlink");
        }
    }
}
