package second.module.test.ui;

import second.module.test.models.Actor;
import second.module.test.models.Movie;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class UI {
    Scanner in = new Scanner(System.in);
    Requests requests;

    public UI(Requests act) {
        super();
        this.requests = act;
    }

    public void interactWithUser(String[] args) throws RemoteException, ParseException {
        int choice = 100000;
        long id;
        String str;
        int year;
        while(choice > 0) {
            System.out.println("Choose option:\n"
                    + "1 - create Actor\n"
                    + "2 - read Actor\n"
                    + "3 - update Actor\n"
                    + "4 - delete Actor\n"

                    + "5 - create Movie\n"
                    + "6 - read Movie\n"
                    + "7 - update Movie\n"
                    + "8 - delete Movie\n"


                    + "9 - create Containing\n"
                    + "10 - read Containing\n"
                    + "11 - update Containing\n"
                    + "12 - delete Containing\n"


                    + "13 - get all actors\n"
                    + "14 - get all actors by movie id\n"
                    + "15 - get all movies\n"
                    + "16 - get all movies by current and last year\n"
                    + "17 - delete movies with release year less than X\n"
                    + "anything else - FINALLY EXIT"
            );
            choice = in.nextInt();
            switch (choice) {
                case 1:
                    Actor actor = new Actor();
                    System.out.println("Enter a name:");
                    str = in.next();
                    actor.setName(str);
                    System.out.println("Enter a surname");
                    str = in.next();
                    actor.setSurname(str);
                    System.out.println("Enter a date (dd-MM-yyyy)");
                    str = in.next();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    Date date = dateFormat.parse(str);
                    actor.setBirthDate(date);
                    requests.createActor(actor);
                    System.out.println("Created new actor!");
                    break;
                case 2:
                    System.out.println("Enter id:");
                    id = in.nextLong();
                    actor = requests.readActor(id);
                    System.out.println(actor);
                    break;
                case 3:
                    System.out.println("Enter id:");
                    id = in.nextLong();
                    actor = new Actor();
                    System.out.println("Enter a name:");
                    str = in.next();
                    actor.setName(str);
                    System.out.println("Enter a surname");
                    str = in.next();
                    actor.setSurname(str);
                    System.out.println("Enter a date (dd-MM-yyyy)");
                    str = in.next();
                    dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    date = dateFormat.parse(str);
                    actor.setBirthDate(date);
                    requests.updateActor(actor);
                    System.out.println("Updated this actor!");
                    break;
                case 4:
                    System.out.println("Enter id:");
                    id = in.nextLong();
                    requests.deleteActor(id);
                    System.out.println("Deleted actor");
                    break;
                case 5:
                    Movie movie = new Movie();
                    System.out.println("Enter name:");
                    str = in.next();
                    movie.setName(str);
                    System.out.println("Enter release year:");
                    year = in.nextInt();
                    movie.setReleaseYear(year);
                    System.out.println("Enter country:");
                    str = in.next();
                    movie.setCountry(str);
                    requests.createMovie(movie);
                    System.out.println("Created new movie!");
                    break;
                case 6:
                    System.out.println("Enter id:");
                    id = in.nextLong();
                    movie = requests.readMovie(id);
                    System.out.println(movie);
                    break;
                case 7:
                    System.out.println("Enter id:");
                    movie = new Movie();
                    id = in.nextLong();
                    movie.setId(id);
                    System.out.println("Enter name:");
                    str = in.next();
                    movie.setName(str);
                    System.out.println("Enter release year:");
                    year = in.nextInt();
                    movie.setReleaseYear(year);
                    System.out.println("Enter country:");
                    str = in.next();
                    movie.setCountry(str);
                    requests.updateMovie(movie);
                    System.out.println("Updated movie!");
                    break;
                case 8:
                    System.out.println("Enter id:");
                    id = in.nextLong();
                    requests.deleteMovie(id);
                    System.out.println("Deleted movie!");
                    break;

                case 13:
                    List<Actor> list1 = requests.findAllActors();
                    System.out.println(list1);
                    break;
                case 14:
                    System.out.println("Enter positive number to find all actors:");
                    id = in.nextLong();
                    List<Actor> list2 = requests.finaAllActorsByMovieId(id);
                    System.out.println(list2);
                    break;
                case 15:
                    List<Movie> list3 = requests.findAllMovies();
                    System.out.println(list3);
                    break;
                case 16:
                    List<Movie> list4 = requests.findAllMoviesByCurrentAndLastYear();
                    System.out.println(list4);
                    break;
                case 17:
                    System.out.println("Enter release year: ");
                    id = in.nextInt();
                    requests.deleteMoviesByReleaseYearLessThan(id);
                    System.out.println("Deleted!");
                    System.out.println(requests.findAllMovies());
                    break;
                default:
                    return;
            }
        }
    }

}
