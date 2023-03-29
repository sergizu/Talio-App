package commons;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;


@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String title;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "list_id")
    @JsonBackReference
    public TDList list;

    /**
     * Default constructor for JPA
     */
    public Card() {}

    /**
     * Constructor for creating a card with a title
     * @param title of the card
     */
    public Card(String title) {
        this.title = title;
    }

    /**
     * Equals method using EqualsBuilder from Apache Commons Lang
     * @param o other object
     * @return true iff the objects are equal
     */
    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this,o);
    }

    /**
     * Hashcode method using HashCodeBuilder from Apache Commons Lang
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * toString method
     * @return string representation of the card
     */
    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

    /**
     * Getter for id
     * @return id of this card
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for id
     * @param id for this card
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter for title
     * @return title of this card
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for title
     * @param title for this card
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for list
     * @return list of this card
     */
    public TDList getList() {
        return list;
    }

    /**
     * Setter for list
     * @param list for this card
     */
    public void setList(TDList list) {
        this.list = list;
    }
}
