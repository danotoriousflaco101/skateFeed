package com.flaco.skatefeed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "user")
@Entity
@Table(name = "keywords")
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "keyword_value", nullable = false)
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public Keyword(String value, User user) {
        this.value = value;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Keyword keyword = (Keyword) o;
        return id != null && Objects.equals(id, keyword.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
