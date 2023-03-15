package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name ="TDList")
@Entity
public class TDList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "list_id")
    public long id;

    public String title;

    @ManyToOne
    @JoinColumn(name = "board_id")
    public Board board;

    @OneToMany(mappedBy = "list",cascade = CascadeType.ALL,orphanRemoval = true)
    public List<Card> list = new ArrayList<>();

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
        return Objects.hash(id, title, list);
    }

    public String toString() {
        String toReturn = "TO DO List:\n" +
                "id: " + id + "\n"
                + "title: " + title + "\n" +
                "Cards:\n";
        for (Card card : list)
            toReturn += card.toString();
        return toReturn;
    }

    public boolean removeCard(long id) {
        return list.removeIf(p -> p.getId() == id);
    }

    public void addCard(Card card) {
        list.add(card);
    }

    public boolean empty() {
        return list.size() == 0;
    }
}