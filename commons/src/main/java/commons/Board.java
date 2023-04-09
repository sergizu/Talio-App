package commons;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "board_id")
    public long id;

    public String title;

    public long key;

    @OneToMany(mappedBy = "board", cascade = {CascadeType.PERSIST,
        CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    @JsonManagedReference
    public List<TDList> tdLists = new ArrayList<>();

    /**
     * Default constructor for JPA
     */
    public Board(){}

    /**
     * Constructor for creating a board with a title
     * @param title of the board
     */
    public Board(String title) {
        this.title = title;
    }

    /**
     * Equals method using EqualsBuilder from Apache Commons Lang
     * Beware that the equals method does not check for equality of keys and ids
     * @param obj other object
     * @return true iff the objects are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Board board = (Board) obj;

        return new EqualsBuilder()
                .append(title, board.title)
                .append(tdLists, board.tdLists)
                .isEquals();
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
     * @return string representation of the board
     */
    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", title=" + title +
                ", tdLists=" + tdLists +
                '}';
    }

    /**
     * Getter for id
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for id
     * @param id of the board
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter for title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for title
     * @param title of the board
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Method that adds a TDList to the board
     * @param l TDList to be added
     */
    public void addList(TDList l){ tdLists.add(l); }

    /**
     * Method that removes a TDList from the board
     * @param id of the TDList to be removed
     * @return true iff the TDList was removed
     */
    public boolean removeList(long id){
        return tdLists.removeIf(n -> (n.id == id));
    }
}