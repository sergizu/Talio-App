package commons;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@JsonIgnoreProperties(ignoreUnknown = true)

public class TDList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "list_id")
    public long id;

    public String title;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "board_id")
    @JsonBackReference
    public Board board;

    @OneToMany(mappedBy = "list", cascade = {CascadeType.PERSIST,
        CascadeType.MERGE, CascadeType.REFRESH})
    @JsonManagedReference
    public List<Card> cards = new ArrayList<>();

    /**
     * Default constructor for JPA
     */
    public TDList() {}

    /**
     * Constructor for creating a list with a title
     * @param title of the list
     */
    public TDList(String title) {
        this.title = title;
    }

    /**
     * Equals method using EqualsBuilder from Apache Commons Lang
     * Beware that the equals method does not check for equality ids
     * @param o other object
     * @return true iff the objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TDList tdList = (TDList) o;

        return new EqualsBuilder()
                .append(title, tdList.title)
                .append(cards, tdList.cards)
                .isEquals();
    }

    /**
     * HashCode method using HashCodeBuilder from Apache Commons Lang
     * @return hashcode of the object
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * toString method for printing the list
     * @return string representation of the list
     */
    public String toString() {
        String toReturn = "TO DO List:\n" +
                "id: " + id + "\n"
                + "title: " + title + "\n" +
                "Cards:\n";
        for (Card card : cards)
            toReturn += card.toString();
        return toReturn;
    }

    /**
     * Get the card with the given id
     * @param id of the card
     * @return true iff a card was found and removed
     */
    public boolean removeCard(long id) {
        return cards.removeIf(p -> p.getId() == id);
    }

    /**
     * Add a card to the list
     * @param card to be added
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * Method that checks if the TDList is empty
     * @return true iff the list is empty
     */
    public boolean isEmpty() {
        return cards.size() == 0;
    }

    /**
     * Getter for the title of the TDList
     * @return title of the TDList
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Getter for the id of the TDList
     * @return id of the TDList
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for the title of the TDList
     * @param newName of the TDList
     */
    public void setTitle(String newName) {
        this.title = newName;
    }

    /**
     * Getter for the board of the TDList
     * @return board of the TDList
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Setter for the board of the TDList
     * @param board of the TDList
     */
    public void setBoard(Board board) {
        this.board = board;
    }
}