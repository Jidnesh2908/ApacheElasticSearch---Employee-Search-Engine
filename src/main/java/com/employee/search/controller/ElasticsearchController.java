
package com.employee.search.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import co.elastic.clients.elasticsearch.ElasticsearchClient;

@RestController
public class ElasticsearchController {

    private final ElasticsearchClient client;

    public ElasticsearchController(ElasticsearchClient client) {
        this.client = client;
    }

    @GetMapping("/api/elasticsearch/health")
    public String checkConnection() throws Exception {

        var health = client.cluster().health();

        return "Connected to Elasticsearch! Cluster status: "
                + health.status();
    }
}
