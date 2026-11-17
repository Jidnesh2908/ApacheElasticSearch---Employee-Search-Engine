
package com.employee.search.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import co.elastic.clients.elasticsearch.ElasticsearchClient;

@Component
public class EmployeeIndexInitializer implements CommandLineRunner {

    private static final String INDEX = "employees";

    private final ElasticsearchClient client;

    public EmployeeIndexInitializer(ElasticsearchClient client) {
        this.client = client;
    }

    @Override
    public void run(String... args) throws Exception {

        boolean exists = client.indices()
                .exists(e -> e.index(INDEX))
                .value();

        if (!exists) {

            client.indices().create(c -> c
                .index(INDEX)
                .settings(s -> s
                    .numberOfReplicas("0")
                )
                .mappings(m -> m
                    .properties("employee_id",
                        p -> p.keyword(k -> k))
                    .properties("name",
                        p -> p.text(t -> t
                            .fields("keyword",
                                f -> f.keyword(k -> k))))
                    .properties("email",
                        p -> p.keyword(k -> k))
                    .properties("department",
                        p -> p.keyword(k -> k))
                    .properties("skills",
                        p -> p.text(t -> t))
                    .properties("designation",
                        p -> p.text(t -> t))
                    .properties("experience",
                        p -> p.integer(i -> i))
                    .properties("location",
                        p -> p.keyword(k -> k))
                    .properties("salary",
                        p -> p.double_(d -> d))
                )
            );

            System.out.println(
                "Employees index created successfully!"
            );

        } else {

            client.indices().putSettings(s -> s
                .index(INDEX)
                .settings(settings -> settings
                    .numberOfReplicas("0")
                )
            );

            System.out.println(
                "Employees index already exists. Settings updated."
            );
        }
    }
}
