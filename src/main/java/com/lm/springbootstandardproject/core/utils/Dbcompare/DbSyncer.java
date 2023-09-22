package com.lm.springbootstandardproject.core.utils.Dbcompare;

import com.lm.springbootstandardproject.core.utils.Dbcompare.meta.MetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DbSyncer {
    @Autowired
    private DbProperties dbProperties;


    public  void sync() {
        String sourceHost = dbProperties.getSourceHost();
        String sourceUser = dbProperties.getSourceUser();
        String sourcePass = dbProperties.getSourcePass();
        String sourceSchema = dbProperties.getSourceSchema();
        String sourceCharset = dbProperties.getSourceCharset();
        String targetHost = dbProperties.getTargetHost();
        String targetUser = dbProperties.getTargetUser();
        String targetPass = dbProperties.getTargetPass();
        String targetSchema = dbProperties.getTargetSchema();
        String targetCharset = dbProperties.getTargetCharset();
        String autoExecute = dbProperties.getAutoExecute();

        if (!sourceCharset.equals(targetCharset)) {
            System.out.println("source target charset not equal");
            System.out.println(-1);
        }

        MetaData source = new MetaData();
        source.setJdbcUrl("jdbc:mysql://" + sourceHost + "/" + sourceSchema+"?characterEncoding="+sourceCharset);
        source.setUser(sourceUser);
        source.setPassword(sourcePass);
        source.setSchema(sourceSchema);
        source.init();

        MetaData target = new MetaData();
        target.setJdbcUrl("jdbc:mysql://" + targetHost + "/" + targetSchema+"?characterEncoding="+targetCharset);
        target.setUser(targetUser);
        target.setPassword(targetPass);
        target.setSchema(targetSchema);
        target.init();

        CompareUnits units = new CompareUnits(source, target);
        units.compare();
        System.out.println("对比目标数据库的不同  ");
        for (int i = 0; i < units.getContent().size(); i++) {
            System.out.println(units.getContent().get(i));
        }

        System.out.println("mysql语句  ");
        for (int i = 0; i < units.getChangeSql().size(); i++) {
            System.out.println(units.getChangeSql().get(i));
            if (autoExecute.equals("YES")) {
                SqlUtil.ddl(target.getConn(), units.getChangeSql().get(i));
            }
        }

    }

}
