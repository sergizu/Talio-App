package commons;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "board_id")
    public long id;

    public String title;

    public long key;

    @OneToMany(mappedBy = "board", cascade = {CascadeType.PERSIST,
        CascadeType.MERGE, CascadeType.REFRESH})
    @JsonManagedReference
    public List<TDList> tdLists = new ArrayList<>();

    public Board(){}

    public Board(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, tdLists);
    }
    // To string method does not work, because class TDList doesn't have toString method
    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", title=" + title +
                ", tdLists=" + tdLists +
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

    public void addList(TDList l){ tdLists.add(l); }

    public boolean removeList(long id){
        return tdLists.removeIf(n -> (n.id == id));
    }
}