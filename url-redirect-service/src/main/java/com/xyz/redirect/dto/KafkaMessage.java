package com.xyz.redirect.dto;

import com.xyz.redirect.entity.URL;
import com.xyz.redirect.utility.KafkaMessageType;

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
