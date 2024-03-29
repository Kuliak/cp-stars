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
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Entity class for star-datasource attribute, e.g. number of observed nights for APASS for specific star.
 *
 * @author Ľuboslav Halama <lubo.halama@gmail.com>
 */
@Entity
@Table(name = "star_datasource_attributes")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StarDatasourceAttribute {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    @NotNull
    private long id;

    @JoinColumn(name = "attribute_definition_id")
    @ManyToOne
    @NonNull
    @NotNull
    private AttributeDefinition attributeDefinition;

    @JoinColumn(name = "datasource_id")
    @ManyToOne
    @NonNull
    @NotNull
    private DataSource datasource;

    @JoinColumn(name = "star_id")
    @ManyToOne
    private Star star;

    @Column(name = "value", length = Constraints.STAR_DATASOURCE_VALUE_MAX_LENGTH)
    @Size(max = Constraints.STAR_DATASOURCE_VALUE_MAX_LENGTH)
    @NonNull
    @NotNull
    private String value;

    public boolean isDefined() {
        return !value.isEmpty();
    }
}
