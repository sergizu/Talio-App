package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Table(name = "Card")
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String title;

    @ManyToOne
    @JoinColumn(name = "list_id")
    public TDList list;
    //empty constructor for ObjectMapper
    public Card() {

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
        return "Card{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
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
