package com.yh.weatherpush.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * H2 代码生成
 *
 * @author hubin,lanjerry
 * @since 1.0
 */
public class H2CodeGeneratorTest {

    /**
     * 执行初始化数据库脚本
     */
    // @BeforeAll
    public static void before() throws SQLException {
        Connection conn = DATA_SOURCE_CONFIG.getConn();
        InputStream inputStream = H2CodeGeneratorTest.class.getResourceAsStream("/sql/init.sql");
        ScriptRunner scriptRunner = new ScriptRunner(conn);
        scriptRunner.setAutoCommit(true);
        scriptRunner.runScript(new InputStreamReader(inputStream));
        conn.close();
    }

    /**
     * 数据源配置
     */
    private static final DataSourceConfig DATA_SOURCE_CONFIG = new DataSourceConfig.Builder(
        "jdbc:mysql://127.0.0.1:3306/weather?characterEncoding=utf-8&serverTimezone=GMT%2B8&useSSL=false&autoReconnect=true&allowMultiQueries=true",
        "root", "root").build();

    /**
     * 策略配置
     */
    private StrategyConfig.Builder strategyConfig() {
        // 设置需要生成的表名
        return new StrategyConfig.Builder().addInclude("sys_admin", "sys_holiday", "sys_sch_task", "sys_tag",
                "sys_task_rel_tag").addTablePrefix("sys_");
    }

    /**
     * 全局配置
     */
    private GlobalConfig.Builder globalConfig() {
        return new GlobalConfig.Builder()
                .outputDir("/Users/yangh/Documents/project/weather-push/src/main/java").author("yh");
    }

    /**
     * 包配置
     */
    private PackageConfig.Builder packageConfig() {
        return new PackageConfig.Builder("com.yh", "weatherpush");
    }

    /**
     * 模板配置
     */
    private TemplateConfig.Builder templateConfig() {
        return new TemplateConfig.Builder();
    }

    /**
     * 注入配置
     */
    private InjectionConfig.Builder injectionConfig() {
        // 测试自定义输出文件之前注入操作，该操作再执行生成代码前 debug 查看
        return new InjectionConfig.Builder().beforeOutputFile((tableInfo, objectMap) -> {
            System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
        });
    }

    /**
     * 简单生成
     */
    @Test
    public void testSimple() {
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        generator.strategy(strategyConfig().entityBuilder().enableFileOverride().enableLombok().idType(IdType.ASSIGN_ID)
            .logicDeleteColumnName("is_delete").build());
        generator.global(globalConfig().build());
        generator.packageInfo(packageConfig().build());
        generator.template(templateConfig().build());
        generator.execute();
    }
}