package com.lm.springbootstandardproject.core.config;



import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;
import java.util.Collections;

public class MybatisPlusGenerator {
    public static void main(String[] args) {
        var tables="t_book_class,t_category,t_claim,t_claim_permission,t_comments,t_config,t_ebook,t_files,t_hot_word,t_identity,t_item_in_category,t_links,t_log_keys,t_module,t_more,t_posts,t_resource,t_resource_library,t_resource_word,t_type,t_unit,t_user_claim,t_users,t_view_log";
        var url="jdbc:mysql://lz:3306/lingman?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&serverTimezone=Asia/Shanghai";
        FastAutoGenerator.create(url, "lingman", "lingman")
                .globalConfig(builder -> {
                    builder.author("lingman") // 设置作者
                           // .enableSwagger(false) // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D://"); // 指定输出目录
                })
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);

                }))
                .packageConfig(builder -> {
                    builder.parent("com.dev.lib") // 设置父包名
                            .moduleName("system") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D:\\com\\dev\\lib\\system\\mapperxml\\")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables)
                            .mapperBuilder()
                            .enableMapperAnnotation(); // 设置需要生成的表
                    // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

}
