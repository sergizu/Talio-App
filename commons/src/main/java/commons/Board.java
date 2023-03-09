package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
    public String title;
    @OneToMany
    public List<TDList> lists;
    public Board() {
        lists = new ArrayList<>();
    }
    public Board(String title) {
        this.title = title;
        lists = new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, lists);
    }
    // To string method does not work, because class TDList doesn't have toString method
    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", title=" + title +
                ", lists=" + lists +
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

    public void addList(TDList l){ lists.add(l); }

    public boolean removeList(long id){
        return lists.removeIf(n -> (n.id == id));
    }
}
