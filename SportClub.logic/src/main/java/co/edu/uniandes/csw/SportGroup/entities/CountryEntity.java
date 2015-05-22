package co.edu.uniandes.csw.SportGroup.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import org.eclipse.persistence.annotations.JoinFetch;

@Entity
@NamedQueries({
    @NamedQuery(name = "Country.leastPopulated", query = "select u from CountryEntity u WHERE u.population = (SELECT MIN(v.population) from CountryEntity v)"),
    @NamedQuery(name = "Country.mostPopulated", query = "select u from CountryEntity u WHERE u.population = (SELECT MAX(v.population) from CountryEntity v)")
})
public class CountryEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "Country")
    private Long id;
    private String name;
    private Integer population;

    @OneToMany
    @JoinFetch
    private List<SportEntity> sports;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinFetch
    private List<SportEntity> ownedSports;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public List<SportEntity> getSports() {
        return sports;
    }

    public void setSports(List<SportEntity> sports) {
        this.sports = sports;
    }

    public List<SportEntity> getOwnedSports() {
        return ownedSports;
    }

    public void setOwnedSports(List<SportEntity> ownedSports) {
        this.ownedSports = ownedSports;
    }
}
