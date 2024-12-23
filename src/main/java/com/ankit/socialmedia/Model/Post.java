package com.ankit.socialmedia.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String caption;
    private String image;
    private String video;

    // Many-to-Many relationship for likes
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "post_likes", // Name of the join table
            joinColumns = @JoinColumn(name = "post_id"), // Foreign key to Post
            inverseJoinColumns = @JoinColumn(name = "user_id") // Foreign key to User
    )
    @ToString.Exclude
//    @JsonIgnore // Prevent serialization of likes
    private Set<User> likes = new HashSet<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
//    @JsonManagedReference // Manages the relationship with comments
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
//    @JsonBackReference // Prevent serialization of user in post
    private User user;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        Post post = (Post) o;
        return id != null && id.equals(post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}