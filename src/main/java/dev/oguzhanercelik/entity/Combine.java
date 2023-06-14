package dev.oguzhanercelik.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "combine", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"top_id", "bottom_id", "shoes_id"})
})
public class Combine {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "top_id", nullable = false)
    private Integer topId;

    @Column(name = "bottom_id", nullable = false)
    private Integer bottomId;

    @Column(name = "shoes_id", nullable = false)
    private Integer shoesId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "top_id", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Top top;

    @ManyToOne
    @JoinColumn(name = "bottom_id", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Bottom bottom;

    @ManyToOne
    @JoinColumn(name = "shoes_id", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Shoes shoes;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
