/*
 * Copyright 2004-2006 H2 Group. Licensed under the H2 License, Version 1.0 (http://h2database.com/html/license.html).
 * Initial Developer: H2 Group
 */
package org.h2.command.ddl;

import java.sql.SQLException;

import org.h2.engine.Right;
import org.h2.engine.Session;
import org.h2.message.Message;
import org.h2.table.Table;

public class TruncateTable extends DefineCommand {

    private Table table;

    public TruncateTable(Session session) {
        super(session);
    }
    
    public void setTable(Table table) {
        this.table = table;
    }
    
    public int update() throws SQLException {
        session.commit(true);
        if(!table.canTruncate()) {
            throw Message.getSQLException(Message.CANNOT_TRUNCATE_1, table.getSQL());
        } else {
            session.getUser().checkRight(table, Right.DELETE);
            table.lock(session, true);
            table.truncate(session);
        }
        return 0;
    }

}
