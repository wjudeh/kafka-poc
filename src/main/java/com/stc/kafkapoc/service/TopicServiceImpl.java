package com.stc.kafkapoc.service;

import com.stc.kafkapoc.exception.NcWebServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.errors.InvalidTopicException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.stc.kafkapoc.exception.dto.ErrorCode.TOPIC_NOT_CREATED;
import static org.springframework.http.HttpStatus.CONFLICT;

@AllArgsConstructor
@Slf4j
@Service
public class TopicServiceImpl implements TopicService {

    private final KafkaAdminClient adminClient;

    @Override
    public List<String> getAll() {

        try {
            ListTopicsResult listTopicsResult = adminClient.listTopics();

            Collection<TopicListing> topicListings = listTopicsResult.listings().get();

            return topicListings.stream().map(TopicListing::name).toList();

        } catch (InterruptedException e) {

            log.warn("Callback response Interrupted" + e.getMessage());

            Thread.currentThread().interrupt();

        } catch (ExecutionException e) {

            throw new InvalidTopicException("Error while fetching topics", e);
        }

        return List.of();
    }

    @Override
    public CreateTopicsResult create(NewTopic newTopic) {

        CreateTopicsResult result = adminClient.createTopics(Collections.singleton(newTopic));


        try {

            KafkaFuture<Void> future = result.values().get(newTopic.name());

            future.get();

        } catch (InterruptedException e) {

            log.warn("Callback response InterruptedException. " + e.getMessage());

            Thread.currentThread().interrupt();

        } catch (ExecutionException e) {

            throw NcWebServiceException.build(CONFLICT, e)
                    .addMessage(TOPIC_NOT_CREATED, e.getMessage());
        }


        return result;
    }
}
