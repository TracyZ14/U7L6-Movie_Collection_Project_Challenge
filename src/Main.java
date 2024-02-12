import java.util.ArrayList;
import java.util.Scanner;
public class Main
{
    public static void main(String[] args)
    {
        ArrayList<SimpleMovie> movies = MovieDatabaseBuilder.getMovieDB("src/movie_data");
        /*
        for(SimpleMovie movie : movies)
        {
            System.out.println(movie);
        }
        System.out.println("Number of movies: " + movies.size());
        */
        System.out.println("Enter an actor's name or (q) to quit.");
        Scanner scan = new Scanner(System.in);
        String name = scan.nextLine();
    }
}