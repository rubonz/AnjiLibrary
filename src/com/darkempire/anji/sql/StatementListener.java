package com.darkempire.anji.sql;

import com.darkempire.anji.log.Log;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by siredvin on 07.12.13.
 */
@Deprecated
public class StatementListener extends ASQLContoller {
    private Statement statement;

    public StatementListener(Statement statement) {
        this.statement = statement;
    }

    @Override
    public Statement getStatement() {
        return statement;
    }

    @Override
    public void close() throws SQLException {
        Log.log(logIndex, "close");
        statement.close();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return statement.getConnection();
    }
}
