package commons;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String title;

    public String description;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "list_id")
    @JsonBackReference
    public TDList list;

    public ArrayList<Subtask> nestedList;

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
        nestedList = new ArrayList<>();
    }

    public Card(String title, String description) {
        this.title = title;
        this.description = description;
        nestedList = new ArrayList<>();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void addSubTask(Subtask subtask) {
        nestedList.add(subtask);
    }

    public void removeSubTask(Subtask subtask) {
        nestedList.remove(subtask);
    }

    public ArrayList<Subtask> getNestedList() {
        return nestedList;
    }

    public void setNestedList(ArrayList<Subtask> updatedList) {
        this.nestedList = updatedList;
    }

    public int amountSelected() {
        int i = 0;
        for(Subtask subtask : this.nestedList) {
            if(subtask.checked) {
                i++;
            }
        }
        return i;
    }

    public String cellFactory() {
        String titleString = this.title;
        String descriptionIndicator = "";
        String amountSelected = "";
        if(description != null && !description.isEmpty()) {
            descriptionIndicator = "\n~";
        }
        if(!nestedList.isEmpty()) {
            amountSelected = amountSelected() + "/" + nestedList.size();
        }
        return titleString + "\n" +  amountSelected + descriptionIndicator;
    }
}
