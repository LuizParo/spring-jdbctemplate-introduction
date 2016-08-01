package br.com.devmedia.editora.util;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

//@Configuration
public class PopulateTables {

    @Autowired
    public void setDataSource(DataSource dataSource) {
        DatabasePopulatorUtils.execute(this.createAddressPopulator(), dataSource);
    }

    private DatabasePopulator createAddressPopulator() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setContinueOnError(true);
        populator.addScript(new ClassPathResource("script-address.sql"));
        
        return populator;
    }
}