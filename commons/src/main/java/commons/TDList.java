package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class TDList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String title;

    //Maybe an arrayList isn't the best data structure
    @OneToMany
    public List<Card> list;

    private TDList() {

    }

    public TDList(String title) {
        this.title = title;
        list = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
        // I suggest we could use EqualsBuilder - reliable and shorter implementation
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
    } //added toString

    public boolean removeCard(long id) {
        return list.removeIf(p -> p.getId() == id);
    }

    public void addCard(Card card) {
        list.add(card);
    }

    //public boolean isEmpty() {
    //    return list.isEmpty();
    //}
}