package co.edu.uniandes.csw.sportclub.dtos;

import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
public class CountryDTO {

    private Long id;

    private String name;

    private Integer population;

    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date foundation;

    private List<SportDTO> sports;

    private List<SportDTO> ownedSports;

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

    public List<SportDTO> getSports() {
        return sports;
    }

    public void setSports(List<SportDTO> sports) {
        this.sports = sports;
    }

    public List<SportDTO> getOwnedSports() {
        return ownedSports;
    }

    public void setOwnedSports(List<SportDTO> ownedSports) {
        this.ownedSports = ownedSports;
    }

    public Date getFoundation() {
        return foundation;
    }

    public void setFoundation(Date foundation) {
        this.foundation = foundation;
    }
}
