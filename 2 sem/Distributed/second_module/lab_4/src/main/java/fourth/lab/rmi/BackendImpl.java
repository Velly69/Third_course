package fourth.lab.rmi;

import fourth.lab.dao.GenreDAO;
import fourth.lab.dao.MovieDAO;
import fourth.lab.dto.GenreDTO;
import fourth.lab.dto.MovieDTO;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class BackendImpl extends UnicastRemoteObject implements Backend {
    protected BackendImpl() throws RemoteException {
        super();
    }

    @Override
    public GenreDTO genreFindById(long id) throws RemoteException {
        return GenreDAO.findById(id);
    }

    @Override
    public GenreDTO genreFindByName(String name) throws RemoteException {
        return GenreDAO.findByName(name);
    }

    @Override
    public boolean genreUpdate(GenreDTO genre) throws RemoteException {
        return GenreDAO.update(genre);
    }

    @Override
    public boolean genreInsert(GenreDTO genre) throws RemoteException {
        return GenreDAO.insert(genre);
    }

    @Override
    public boolean genreDelete(GenreDTO genre) throws RemoteException {
        return GenreDAO.delete(genre);
    }

    @Override
    public List<GenreDTO> genreFindAll() throws RemoteException {
        return GenreDAO.findAll();
    }

    @Override
    public MovieDTO movieFindById(long id) throws RemoteException {
        return MovieDAO.findById(id);
    }

    @Override
    public MovieDTO movieFindByName(String name) throws RemoteException {
        return MovieDAO.findByName(name);
    }

    @Override
    public boolean movieUpdate(MovieDTO movie) throws RemoteException {
        return MovieDAO.update(movie);
    }

    @Override
    public boolean movieInsert(MovieDTO movie) throws RemoteException {
        return MovieDAO.insert(movie);
    }

    @Override
    public boolean movieDelete(MovieDTO movie) throws RemoteException {
        return MovieDAO.delete(movie);
    }

    @Override
    public List<MovieDTO> movieFindAll() throws RemoteException {
        return MovieDAO.findAll();
    }

    @Override
    public List<MovieDTO> movieFindByGenreId(Long id) throws RemoteException {
        return MovieDAO.findByGenreId(id);
    }

    public static void main(String[] args) throws RemoteException {
        BackendImpl bck = new BackendImpl();
        Registry registry = LocateRegistry.createRegistry(1234);
        registry.rebind("videoshop", bck);
        System.out.println("Server started!");
    }
}
