package DAOController;

import java.util.ArrayList;
import java.util.UUID;

public interface DAO<T> {
     boolean insert(T o);
     boolean update(T o, UUID id);   // CORRIGÉ : UUID au lieu de int (les classes utilisent UUID)
     boolean delete(UUID id);        // CORRIGÉ
     ArrayList<T> select(); 
}