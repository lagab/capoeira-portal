package com.lagab.capoeira.capoeiraportal.domain;

import com.lagab.capoeira.capoeiraportal.domain.enums.Style;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class School extends LocalizableEntity implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String name;

    private String founder;

    @Column(name = "logo_url")
    private String logoUrl;


    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Style style;
}
