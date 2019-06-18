package com.github.stoton.timetablebackend.domain.school;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long popularity = 0L;
    private String href;
    private String icon;
    @JsonIgnore
    @Lob
    private byte[] iconContent;
    @JsonIgnore
    private String mediaType;
    private String timetableHref;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timestamp;

    @JsonIgnore
    BigInteger schoolCallsNumber = BigInteger.ZERO;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

}
