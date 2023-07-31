package com.stc.kafkapoc.endpoint.mapper;

import com.stc.kafkapoc.endpoint.dto.ProducerDTO;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import static com.stc.kafkapoc.service.ProducerService.DEFAULT_TOPIC_NAME;

@Mapper(componentModel = "spring")
public interface ProducerMapper {

    ProducerMapper MAPPER = Mappers.getMapper(ProducerMapper.class);

    default  ProducerRecord<String, String> map(ProducerDTO producerDTO) {

        if (producerDTO.getTopic() == null) {
            producerDTO.setTopic(DEFAULT_TOPIC_NAME);
        }

        return  new ProducerRecord<>(producerDTO.getTopic(), producerDTO.getKey(), producerDTO.getValue());
     }
}
