package fourth.lab.rmi;

import fourth.lab.dto.GenreDTO;
import fourth.lab.dto.MovieDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Backend extends Remote {
    public GenreDTO genreFindById(long id) throws RemoteException;
    public GenreDTO genreFindByName(String name) throws RemoteException;
    public boolean genreUpdate(GenreDTO genre) throws RemoteException;
    public boolean genreInsert(GenreDTO genre) throws RemoteException;
    public boolean genreDelete(GenreDTO genre) throws RemoteException;
    public List<GenreDTO> genreFindAll() throws RemoteException;
    public MovieDTO movieFindById(long id) throws RemoteException;
    public MovieDTO movieFindByName(String name) throws RemoteException;
    public boolean movieUpdate(MovieDTO movie) throws RemoteException;
    public boolean movieInsert(MovieDTO movie) throws RemoteException;
    public boolean movieDelete(MovieDTO movie) throws RemoteException;
    public List<MovieDTO> movieFindAll() throws RemoteException;
    public List<MovieDTO> movieFindByGenreId(Long id) throws RemoteException;
}
