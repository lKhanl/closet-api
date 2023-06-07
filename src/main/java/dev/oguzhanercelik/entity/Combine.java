package dev.oguzhanercelik.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shoes")
public class Combine {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "top_id", nullable = false)
    private Integer topId;

    @Column(name = "bottom_id", nullable = false)
    private Integer bottomId;

    @Column(name = "shoes_id", nullable = false)
    private Integer shoesId;

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
