import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
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
        try
        {
            for(int i = 1; i < movies.size(); i++)
            {
                SimpleMovie tempMovie = movies.get(i);
                String tempTitle = tempMovie.getTitle();
                while((i > 0) && (tempTitle.compareTo(movies.get(i - 1).getTitle()) < 0))
                {
                    movies.set(i, movies.get(i - 1));
                    i--;
                }
                movies.set(i, tempMovie);
            }
            File f = new File("src/sorted_movies");
            f.createNewFile();
            FileWriter fw = new FileWriter(f);
            for(SimpleMovie movie : movies)
            {
                fw.write(movie + "\n");
            }
            fw.close();

            ArrayList<SimpleMovie> sortedMovies = MovieDatabaseBuilder.getMovieDB("src/sorted_movies");
            String name = "";
            while(!name.equals("q"))
            {
                System.out.println("Enter an actor's name or (q) to quit.");
                Scanner scan = new Scanner(System.in);
                name = scan.nextLine();
                System.out.println("one");
                SixDegreesOfKevinBacon empty = new SixDegreesOfKevinBacon();
                System.out.println("two");
                String actor = empty.findActor(name);
                System.out.println("three");
                SixDegreesOfKevinBacon game = new SixDegreesOfKevinBacon(actor, sortedMovies);
                System.out.println("four");
                game.findSixDegreesOfKevinBacon();
            }
        }
        catch(IOException ioe)
        {
            System.out.println("Writing file failed");
            System.out.println(ioe);
        }
    }
}