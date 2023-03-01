package server.api;

import commons.Card;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.CardRepository;

import java.util.*;
import java.util.function.Function;

public class TestCardRepository implements CardRepository {
    List<String> calls = new LinkedList<>();
    HashMap<Long, Card> map = new HashMap<>();


    //TODO Add implementations for the methods below
    @Override
    public List<Card> findAll() {
        calls.add("findAll");
        List<Card> list = new ArrayList<>();
        for (Map.Entry<Long, Card> entry: map.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

    @Override
    public List<Card> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Card> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Card> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        calls.add("count");
        return map.entrySet().size();
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Card entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Card> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Card> S save(S entity) {
        calls.add("save");
        map.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public <S extends Card> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Card> findById(Long id) {
        calls.add("findById");
        if(!map.containsKey(id))
            return Optional.empty();
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public boolean existsById(Long id) {
        calls.add("existsById");
        return map.containsKey(id);
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Card> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Card> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Card> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Card getOne(Long aLong) {
        return null;
    }

    @Override
    public Card getById(Long id) {
        calls.add("getById");
        if(!map.containsKey(id))
            return null;
        return map.get(id);
    }

    @Override
    public <S extends Card> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Card> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Card> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Card> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Card> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Card> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Card, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
