package org.third.hibernate.migrate;

import java.util.EnumSet;

import javax.persistence.Entity;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.hibernate.tool.schema.TargetType;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.third.hibernate.migrate.domain.Account;

public class MigrationTest {
    public static void main(String[] args) {
        MetadataSources metadataSources = new MetadataSources(new StandardServiceRegistryBuilder()
                .applySetting(AvailableSettings.DIALECT, "org.hibernate.dialect.PostgreSQLDialect")
                .applySetting(AvailableSettings.DRIVER, "org.postgresql.Driver")
                .applySetting(AvailableSettings.URL,
                        "jdbc:postgresql://shcGeorgeCTs1w1.hpeswlab.net:5432/dan?user=postgres&password=postgres&schema=adsf")
                .applySetting(AvailableSettings.USER, "postgres").applySetting(AvailableSettings.PASS, "postgres")
                .build());

        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
        // use Deployment's package as basePackage
        for (BeanDefinition bd : scanner.findCandidateComponents(Account.class.getPackage().getName())) {
            String name = bd.getBeanClassName();
            try {
                metadataSources.addAnnotatedClassName(name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Metadata metadata = metadataSources.buildMetadata();

        // 5.4.3
        SchemaUpdate su = new SchemaUpdate();
        EnumSet<TargetType> targets = EnumSet.of(TargetType.DATABASE);
        su.execute(targets, metadata);
    }
}
