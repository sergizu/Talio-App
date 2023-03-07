package server.api;

import commons.TDList;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.ListRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class TestListRepository implements ListRepository {


    @Override
    public List<TDList> findAll() {
        return null;

    }

    @Override
    public List<TDList> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<TDList> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<TDList> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(TDList entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends TDList> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends TDList> S save(S entity) {
        return null;
    }

    @Override
    public <S extends TDList> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<TDList> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends TDList> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends TDList> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<TDList> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public TDList getOne(Long aLong) {
        return null;
    }

    @Override
    public TDList getById(Long aLong) {
        return null;
    }

    @Override
    public <S extends TDList> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends TDList> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends TDList> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends TDList> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends TDList> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends TDList> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends TDList, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
