package com.lagab.capoeira.capoeiraportal.domain;

import com.lagab.capoeira.capoeiraportal.domain.enums.Style;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class School implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String name;

    private String founder;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] logo;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Style style;
}
