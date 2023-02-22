package com.xyz.shortener.dto;

import com.xyz.shortener.entity.URL;
import com.xyz.shortener.utility.KafkaMessageType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaMessage {
    private KafkaMessageType type;
    private URL url;
}
