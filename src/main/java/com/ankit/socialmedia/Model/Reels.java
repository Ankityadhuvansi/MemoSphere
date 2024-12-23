package com.ankit.socialmedia.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder // Enables the Builder pattern for this entity
public class Reels {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false) // Enforce not null constraint
    private String video;

    private String caption;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "reel_likes", // Name of the join table
            joinColumns = @JoinColumn(name = "reel_id"), // Foreign key to Reels
            inverseJoinColumns = @JoinColumn(name = "user_id") // Foreign key to User
    )
    @ToString.Exclude
//    @JsonIgnore // Prevent serialization of likes if needed
    private Set<User> likes = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reels)) return false;
        Reels reels = (Reels) o;
        return Objects.equals(id, reels.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}