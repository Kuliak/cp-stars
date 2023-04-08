package cz.muni.fi.cpstars.dal.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.muni.fi.cpstars.dal.Constraints;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

/**
 * Star magnitude entity class.
 *
 * @author Ľuboslav Halama <lubo.halama@gmail.com>
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "magnitudes")
@Data
public class Magnitude {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    @NotNull
    private long id;

    @JoinColumn(name = "star_id")
    @ManyToOne
    @ToString.Exclude
    private Star star;

    @JoinColumn(name = "datasource_id")
    @ManyToOne
    @NonNull
    @NotNull
    private DataSource datasource;

    @Column(name = "band", length = Constraints.MAGNITUDE_NAME_MAX_LENGTH)
    @Size(max = Constraints.MAGNITUDE_NAME_MAX_LENGTH)
    @NonNull
    @NotNull
    private String name;

    @Column(name = "value")
    @NonNull
    @NotNull
    private Double value;

    @Column(name = "error")
    @PositiveOrZero
    private Double error;

    @Column(name = "quality", length = Constraints.MAGNITUDE_QUALITY_MAX_LENGTH)
    private Character quality;

    @Column(name = "uncertainty_flag")
    private Character uncertaintyFlag;
}
