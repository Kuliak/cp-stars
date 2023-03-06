package cz.muni.fi.cpstars.dal.classes;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class for storing basic (general) information about CP Star.
 * Information are to be used in database overview table.
 *
 * @author Ľuboslav Halama <lubo.halama@gmail.com>
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StarBasicInfo {
    @NotNull
    public long id;
    private String id_2009_A_AND_A_498_961_R;
    private String consideredCategoryAffiliationProbabilityFlag;
    private String binarySystemComponent;
    public Double icrsRightAscension;
    public Double icrsDeclination;
    public Double galacticLongitude;
    public Double galacticLatitude;
}
