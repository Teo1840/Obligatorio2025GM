package um.edu.uy.tads;

import um.edu.uy.exeptions.ElementoYaExiste;

public interface MyHash<T> {
    void insertar (String clave, T valor) throws ElementoYaExiste;
    boolean pertenece (String clave);
    void borrar (String clave);
}
