package commons;

import javax.persistence.*;

@Entity
public class CardChange {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @ManyToOne
    public Card card;

    public Change change;

    public CardChange() {

    }

    public CardChange(Card card, Change change) {
        this.card = card;
        this.change = change;
    }
}
