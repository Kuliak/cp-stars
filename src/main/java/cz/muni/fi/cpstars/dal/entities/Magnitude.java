package cz.muni.fi.cpstars.dal.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Star magnitude entity class.
 *
 * @author Ľuboslav Halama <lubo.halama@gmail.com>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "magnitudes")
public class Magnitude {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    private long id;

    @JoinColumn(name = "star_id")
    @ManyToOne
    @JsonIgnore
    private Star star;

    @JoinColumn(name = "datasource_id")
    @ManyToOne
    private DataSource datasource;

    @Column(name = "band")
    private String name;

    @Column(name = "value")
    private Double value;

    @Column(name = "error")
    private Double error;

    @Column(name = "quality")
    private Character quality;

    @Column(name = "uncertainty_flag")
    private Character uncertaintyFlag;

    public boolean isDefined() {
        return value != null
                || error != null;
    }
}
