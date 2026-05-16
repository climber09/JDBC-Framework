package net.sourceforge.hunterj.jdbc;

import java.sql.Connection;

/**
 * <code>ConnectionAdapter</code> must be implemented by the user to supply the
 * instructions needed to open the application specific
 * <code>java.sql.Connection</code>.
 * 
 * @author Jim Hunter
 * @version 1.0
 */
public interface ConnectionAdapter {

    /**
     * Implemented by the user. Must encapsulate the instructions necessary to open
     * the application specific <code>java.sql.Connection</code>.
     * 
     * @return java.sql.Connection
     * @throws java.sql.SQLException
     */
    public Connection getTargetConnection() throws java.sql.SQLException;

}