package com.darkempire.anji.sql;

import com.darkempire.anji.log.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by siredvin on 07.12.13.
 */
@Deprecated
public class PreparedStatementListener extends APreparedSQLController {
    private PreparedStatement preparedStatement;

    public PreparedStatementListener(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }

    @Override
    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    @Override
    public void close() throws SQLException {
        Log.log(logIndex, "close");
        preparedStatement.close();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return preparedStatement.getConnection();
    }
}
