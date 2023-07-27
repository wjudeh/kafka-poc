package com.stc.kafkapoc.service;

import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.List;

public interface TopicService {

    short DEFAULT_REPLICATION_FACTOR_NUMBER = 1;

    int DEFAULT_PARTITIONS_NUMBER = 1;

    List<String> getAll();

    CreateTopicsResult create(NewTopic newTopic);
}
