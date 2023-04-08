package cz.muni.fi.cpstars.rest.controllers;

import cz.muni.fi.cpstars.bl.implementation.IdentifiersBlManagerImpl;
import cz.muni.fi.cpstars.bl.implementation.MagnitudesBlManagerImpl;
import cz.muni.fi.cpstars.bl.implementation.MotionsBlManagerImpl;
import cz.muni.fi.cpstars.bl.implementation.RadialVelocitiesBlManagerImpl;
import cz.muni.fi.cpstars.bl.implementation.StarDatasourceAttributeBlManagerImpl;
import cz.muni.fi.cpstars.bl.implementation.StarsBlManagerImpl;
import cz.muni.fi.cpstars.bl.interfaces.IdentifiersBlManager;
import cz.muni.fi.cpstars.bl.interfaces.MagnitudesBlManager;
import cz.muni.fi.cpstars.bl.interfaces.MotionsBlManager;
import cz.muni.fi.cpstars.bl.interfaces.RadialVelocitiesBlManager;
import cz.muni.fi.cpstars.bl.interfaces.StarDatasourceAttributeBlManager;
import cz.muni.fi.cpstars.bl.interfaces.StarsBlManager;
import cz.muni.fi.cpstars.dal.classes.ExtendedStar;
import cz.muni.fi.cpstars.dal.classes.StarBasicInfo;
import cz.muni.fi.cpstars.dal.entities.Identifier;
import cz.muni.fi.cpstars.dal.entities.Magnitude;
import cz.muni.fi.cpstars.dal.entities.Motion;
import cz.muni.fi.cpstars.dal.entities.RadialVelocity;
import cz.muni.fi.cpstars.dal.entities.Star;
import cz.muni.fi.cpstars.dal.entities.StarDatasourceAttribute;
import cz.muni.fi.cpstars.dal.implementation.initialization.DataInitializer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller for CP-Stars database access to information about stars.
 *
 * @author Ľuboslav Halama <lubo.halama@gmail.com>
 */
@RequestMapping(Paths.CP_STARS_DATABASE)
@RestController
public class StarsController {

    private final DataInitializer dataInitializer;
    private final IdentifiersBlManager identifiersBlManager;
    private final MagnitudesBlManager magnitudesBlManager;
    private final MotionsBlManager motionsBlManager;
    private final RadialVelocitiesBlManager radialVelocitiesBlManager;
    private final StarDatasourceAttributeBlManager starDatasourceAttributeBlManager;
    private final StarsBlManager starsBlManager;

    @Autowired
    public StarsController(
            DataInitializer dataInitializer,
            IdentifiersBlManagerImpl identifiersBlManagerImpl,
            MagnitudesBlManagerImpl magnitudesBlManagerImpl,
            MotionsBlManagerImpl motionsBlManagerImpl,
            RadialVelocitiesBlManagerImpl radialVelocitiesBlManagerImpl,
            StarDatasourceAttributeBlManagerImpl starDatasourceAttributeBlManagerImpl,
            StarsBlManagerImpl starsBlManagerImpl) {
        this.dataInitializer = dataInitializer;
        this.identifiersBlManager = identifiersBlManagerImpl;
        this.magnitudesBlManager = magnitudesBlManagerImpl;
        this.motionsBlManager = motionsBlManagerImpl;
        this.radialVelocitiesBlManager = radialVelocitiesBlManagerImpl;
        this.starDatasourceAttributeBlManager = starDatasourceAttributeBlManagerImpl;
        this.starsBlManager = starsBlManagerImpl;
    }

    /**
     * Return basic information about all CP stars.
     *
     * @return list of stars with basic information
     */
    @Operation(
            summary = "Get basic (general) information about all stars.",
            description = "Return list of all stars in the database. Each star has only basic information assigned (coordinates)."
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of stars with basic information returned."
    )
    @GetMapping
    public List<StarBasicInfo> getBasicInfoStarsList() {
//        reload();
        return starsBlManager.getAllStarsBasicInfo();
    }

    @Operation(
            summary = "Get all star attributes paired with corresponding datasource.",
            description = "Response contains list of all star-datasource attributes belonging to given star."
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of star's attributes (with assigned datasource) found and returned."
    )
    @GetMapping("/{starId}" + Paths.CP_STARS_DATABASE_STAR_DATASOURCE_ATTRIBUTES_PARTIAL)
    public List<StarDatasourceAttribute> getStarDatasourceAttributes(@PathVariable long starId) {
        return starDatasourceAttributeBlManager.getAllAttributesForStarId(starId);
    }

    @Operation(
            summary = "Get all information about specified star.",
            description = """
                    Response contains exhaustive information ONLY from the appliaction database.
                    Information obtained include:
                    - coordinates
                    - identifier
                    - photometry
                    - proper motions and parallaxes"""
    )
    @ApiResponse(
            responseCode = "200",
            description = "Extended Star details found and returned."
    )
    @GetMapping("/{id}")
    public Star getStar(@PathVariable long id) {
        return starsBlManager.getStar(id);
    }

    @Operation(
            summary = "Get all information about specified star including data fetched from external sources.",
            description = """
                    Response contains exhaustive information about the star both from the database and external services.
                    IMPORTANT: Querying external sources may take some time.
                    Information obtained include:
                    - external data
                    - coordinates
                    - identifier
                    - photometry
                    - proper motions and parallaxes"""
    )
    @ApiResponse(
            responseCode = "200",
            description = "Extended Star details found and returned."
    )
    @GetMapping("/{id}" + Paths.CP_STARS_DATABASE_EXTENDED)
    public ExtendedStar getExtendedStar(@PathVariable long id) {
        return starsBlManager.getExtendedStar(id);
    }

    @Operation(
            summary = "Get all magnitudes corresponding to specified star.",
            description = "Response contains list of all magnitudes belonging to given star."
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of star's magnitudes found and returned."
    )
    @GetMapping("/{starId}" + Paths.CP_STARS_DATABASE_MAGNITUDES_PARTIAL)
    public List<Magnitude> getStarMagnitudes(@PathVariable long starId) {
        return magnitudesBlManager.getAllMagnitudesForStarId(starId);
    }

    @Operation(
            summary = "Get all motions corresponding to specified star.",
            description = """
                    Response contains list of all motions belonging to given star. Information contain:
                    - proper motion information
                    - parallax information"""
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of star's motions found and returned."
    )
    @GetMapping("/{starId}" + Paths.CP_STARS_DATABASE_MOTIONS_PARTIAL)
    public List<Motion> getStarMotions(@PathVariable long starId) {
        return motionsBlManager.getAllMotionsForStarId(starId);
    }

    @Operation(
            summary = "Get all radial velocities corresponding to specified star.",
            description = "Response contains list of all radial velocities belonging to given star."
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of star's radial velocities found and returned."
    )
    @GetMapping("/{starId}" + Paths.CP_STARS_DATABASE_RADIAL_VELOCITIES_PARTIAL)
    public List<RadialVelocity> getStarRadialVelocities(@PathVariable long starId) {
        return radialVelocitiesBlManager.getAllRadialVelocitiesForStarId(starId);
    }

    @Operation(
            summary = "Get all identifier corresponding to specified star.",
            description = "Response contains identifier belonging to given star."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Star's identifier found and returned."
    )
    @GetMapping("/{starId}" + Paths.CP_STARS_DATABASE_IDENTIFIERS_PARTIAL)
    public List<Identifier> getStarIdentifiers(@PathVariable long starId) {
        return identifiersBlManager.getIdentifiersForStarId(starId);
    }

    private void reload() {
        this.dataInitializer.initializeData();
    }
}
