package com.ruchi.engine.configurations;

import java.io.File;

/**
 * Created by Thamayanthy on 12/13/2014.
 */
public class RuchiDatabaseConfigurations {

    private String dataSourceName;

    private String localhostURL;

    private String jdbcDriver;

    private boolean showSql = false;

    private String daoConnectionFactoryClass;

    private String uname;

    private String pword;

    public RuchiDatabaseConfigurations(File confFile) {

        this.dataSourceName = "sample";
        this.localhostURL = "jdbc:mysql://localhost:3306/";
        jdbcDriver = "com.mysql.jdbc.Driver";

    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public String getLocalhostURL() {
        return localhostURL;
    }

    public void setLocalhostURL(String localhostURL) {
        this.localhostURL = localhostURL;
    }

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public boolean isShowSql() {
        return showSql;
    }

    public void setShowSql(boolean showSql) {
        this.showSql = showSql;
    }

    public String getDaoConnectionFactoryClass() {
        return daoConnectionFactoryClass;
    }

    public void setDaoConnectionFactoryClass(String daoConnectionFactoryClass) {
        this.daoConnectionFactoryClass = daoConnectionFactoryClass;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPword() {
        return pword;
    }

    public void setPword(String pword) {
        this.pword = pword;
    }
}