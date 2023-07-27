package com.stc.kafkapoc.endpoint.mapper;

import com.stc.kafkapoc.endpoint.dto.TopicDTO;
import com.stc.kafkapoc.endpoint.wrapper.TopicWrapper;
import org.apache.kafka.clients.admin.NewTopic;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import static com.stc.kafkapoc.service.TopicService.DEFAULT_PARTITIONS_NUMBER;
import static com.stc.kafkapoc.service.TopicService.DEFAULT_REPLICATION_FACTOR_NUMBER;

@Mapper(componentModel = "spring")
public interface TopicMapper {
    TopicMapper MAPPER = Mappers.getMapper(TopicMapper.class);

    TopicWrapper map(Boolean isDone);

    default NewTopic map(TopicDTO topicDTO) {

        if (topicDTO.getPartitions() == null || topicDTO.getPartitions() == 0) {
            topicDTO.setPartitions(DEFAULT_PARTITIONS_NUMBER);
        }

        if (topicDTO.getReplicationFactor() == null || topicDTO.getReplicationFactor() == 0) {
            topicDTO.setReplicationFactor(DEFAULT_REPLICATION_FACTOR_NUMBER);
        }

        return new NewTopic(topicDTO.getName(), topicDTO.getPartitions(), topicDTO.getReplicationFactor());
    }
}