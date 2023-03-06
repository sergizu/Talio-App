package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;

    //empty constructor for ObjectMapper
    private Card() {

    }

    public Card(String title) {
        this.title = title;
    }

    //Equals method, decided to include both the ID and title(maybe we should rethink this later)
    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this,o);
        // I suggest we could use EqualsBuilder - reliable and shorter implementation
    }

    //Hashcode method, decided to include both the ID and title(maybe we should rethink this later)
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this); ///also reliable and less code
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,MULTI_LINE_STYLE); ///shorter implementation
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
