package DAO;

public interface DBApi<T1,T2> {
    T1 findById(T2 id);
    void save(T1 value);
    void update(T1 value);
    void delete(T1 value);

}
