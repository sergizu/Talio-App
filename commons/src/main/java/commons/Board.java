package commons;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private List<TDList> lists;
    public Board(String title) {
        this.title = title;
        lists = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board b = (Board) o;
        return Objects.equals(id, b.id) && Objects.equals(lists, b.lists);
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

    public void addList(String name){ lists.add(new TDList(name)); }

    public boolean removeList(long id){
        List<TDList> r = new ArrayList<>();
        boolean a = false;
        for(TDList l:lists){
            if(l.getId() != id){
                r.add(l);
                a = true;
            }
        }
        lists = r;
        return a;
    }
}
