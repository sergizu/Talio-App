package server.api;

import commons.Board;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.BoardRepository;

import java.util.*;
import java.util.function.Function;

public class TestBoardRepository implements BoardRepository {
    List<String> calls = new LinkedList<>();
    HashMap<Long, Board> map = new HashMap<>();


    //TODO Add implementations for the methods below
    @Override
    public List<Board> findAll() {
        calls.add("findAll");
        List<Board> list = new ArrayList<>();
        for (Map.Entry<Long, Board> entry: map.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

    @Override
    public List<Board> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Board> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Board> findAllById(Iterable<Long> longs) {
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
    public void delete(Board entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Board> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Board> S save(S entity) {
        calls.add("save");
        map.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public <S extends Board> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Board> findById(Long id) {
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
    public <S extends Board> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Board> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Board> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Board getOne(Long aLong) {
        return null;
    }

    @Override
    public Board getById(Long id) {
        calls.add("getById");
        if(!map.containsKey(id))
            return null;
        return map.get(id);
    }

    @Override
    public <S extends Board> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Board> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Board> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Board> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Board> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Board> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Board, R> R findBy(Example<S> example,
                                         Function<FluentQuery.FetchableFluentQuery<S>,
                                                 R> queryFunction) {
        return null;
    }
}
