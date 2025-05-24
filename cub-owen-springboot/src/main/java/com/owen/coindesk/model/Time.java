package com.owen.coindesk.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import com.owen.coindesk.util.UpdatedDeserializer;
import com.owen.coindesk.util.UpdatedUkDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Time {
  @JsonDeserialize(using = UpdatedDeserializer.class)
  private LocalDateTime updated;
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
  private LocalDateTime updatedISO;
  @JsonDeserialize(using = UpdatedUkDeserializer.class)
  private LocalDateTime updateduk;
}
