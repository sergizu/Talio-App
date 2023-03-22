package commons;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
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

    public TDList() {}

    public TDList(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, cards);
    }

    public String toString() {
        String toReturn = "TO DO List:\n" +
                "id: " + id + "\n"
                + "title: " + title + "\n" +
                "Cards:\n";
        for (Card card : cards)
            toReturn += card.toString();
        return toReturn;
    }

    public boolean removeCard(long id) {
        return cards.removeIf(p -> p.getId() == id);
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public boolean empty() {
        return cards.size() == 0;
    }

    public String getTitle() {
        return this.title;
    }

    public long getId() {
        return id;
    }

    public Board getBoard() {
        return board;
    }
}