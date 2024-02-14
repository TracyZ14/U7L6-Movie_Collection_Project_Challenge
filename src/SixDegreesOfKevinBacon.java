import java.util.ArrayList;
public class SixDegreesOfKevinBacon
{
    private String actor;
    private ArrayList<SimpleMovie> movies;
    private ArrayList<SimpleMovie> moviesSearched;
    private ArrayList<String> actorsSearched;
    private ArrayList<String> link;
    private ArrayList<ArrayList<SimpleMovie>> moviesLink;
    private ArrayList<ArrayList<String>> actorsLink;
    private int baconNumber;

    public SixDegreesOfKevinBacon(String actor, ArrayList<SimpleMovie> movies)
    {
        this.actor = actor;
        this.movies = movies;
        this.moviesSearched = new ArrayList<SimpleMovie>();
        this.actorsSearched = new ArrayList<String>();
        this.link = new ArrayList<String>();
        this.moviesLink = new ArrayList<ArrayList<SimpleMovie>>();
        this.actorsLink = new ArrayList<ArrayList<String>>();
        this.baconNumber = 0;
    }

    public void printAnswer()
    {
        String answer = "";
        for(int i = 0; i < link.size(); i++)
        {
            answer += link.get(i);
            if(i != (link.size() - 1))
            {
                answer += " --> ";
            }
        }
        System.out.println(answer);
        System.out.println("Bacon Number of " + baconNumber);
    }

    public void findSixDegreesOfKevinBacon()
    {
        link.add(actor);
        ArrayList<String> movieActors = new ArrayList<String>();
        movieActors.add("Kevin Bacon");
        ArrayList<SimpleMovie> kevinBaconMovies = organizeMovies(moviesOf(movieActors));
        movieActors.set(0, actor);
        ArrayList<SimpleMovie> actorMovies = organizeMovies(moviesOf(movieActors));
        moviesLink.add(actorMovies);
        moviesSearched = actorMovies;
        actorsSearched = movieActors;
        boolean isFound = foundKevinBacon(actorMovies, kevinBaconMovies);
        while(!isFound)
        {
            movieActors = organizeActors(actorsOf(actorMovies));
            movieActors = removeRepetitiveActors(movieActors);
            movieActors = removeActors(movieActors, actorsSearched);
            actorsLink.add(movieActors);
            actorsSearched = addActors(actorsSearched, movieActors);
            actorMovies = organizeMovies(moviesOf(movieActors));
            actorMovies = removeRepetitiveMovies(actorMovies);
            actorMovies = removeMovies(actorMovies, moviesSearched);
            moviesLink.add(actorMovies);
            moviesSearched = addMovies(moviesSearched, actorMovies);
            isFound = foundKevinBacon(actorMovies, kevinBaconMovies);
        }
        findLink();
        findBaconNumber();
        printAnswer();
    }

    public boolean foundKevinBacon(ArrayList<SimpleMovie> actorMovies, ArrayList<SimpleMovie> kevinBaconMovies)
    {
        for(int i = 0; i < actorMovies.size(); i++)
        {
            for(int j = 0; j < kevinBaconMovies.size(); j++)
            {
                if(actorMovies.get(i).equals(kevinBaconMovies.get(j)))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<SimpleMovie> moviesOf(ArrayList<String> actorList)
    {
        ArrayList<SimpleMovie> moviesOfActor = new ArrayList<SimpleMovie>();
        for(String actor : actorList)
        {
            for(SimpleMovie movie : this.movies)
            {
                if(movie.getActors().contains(actor))
                {
                    moviesOfActor.add(movie);
                }
            }
        }
        return movies;
    }

    public ArrayList<String> actorsOf(ArrayList<SimpleMovie> movieList)
    {
        ArrayList<String> allActors = new ArrayList<String>();
        for(SimpleMovie movie : movieList)
        {
            ArrayList<String> actors = movie.getActors();
            for(String actor : actors)
            {
                allActors.add(actor);
            }
        }
        return allActors;
    }

    public ArrayList<SimpleMovie> organizeMovies(ArrayList<SimpleMovie> movieList)
    {
        for(int i = 1; i < movieList.size(); i++)
        {
            SimpleMovie tempMovie = movieList.get(i);
            String tempTitle = tempMovie.getTitle();
            while((i > 0) && (tempTitle.compareTo(movieList.get(i - 1).getTitle()) < 0))
            {
                movieList.set(i, movieList.get(i - 1));
                i--;
            }
            movieList.set(i, tempMovie);
        }
        return movieList;
    }

    public ArrayList<String> organizeActors(ArrayList<String> actorList)
    {
        for(int i = 1; i < actorList.size(); i++)
        {
            String tempActor = actorList.get(i);
            while((i > 1) && (tempActor.compareTo(actorList.get(i - 1)) < 0))
            {
                actorList.set(i, actorList.get(i - 1));
                i--;
            }
        }
        return actorList;
    }

    public ArrayList<SimpleMovie> removeRepetitiveMovies(ArrayList<SimpleMovie> movieList)
    {
        for(int i = (movieList.size() - 1); i >= 0; i--)
        {
            for(int j = 0; j < i; j++)
            {
                if(movieList.get(i).equals(movieList.get(j)))
                {
                    movieList.remove(i);
                    j = i;
                }
            }
        }
        return movieList;
    }

    public ArrayList<String> removeRepetitiveActors(ArrayList<String> actorList)
    {
        for(int i = (actorList.size() - 1); i >= 0; i--)
        {
            for(int j = 0; j < i; j++)
            {
                if(actorList.get(i).equals(actorList.get(j)))
                {
                    actorList.remove(i);
                    j = i;
                }
            }
        }
        return actorList;
    }

    public ArrayList<SimpleMovie> removeMovies(ArrayList<SimpleMovie> newMovieList, ArrayList<SimpleMovie> moviesSearched)
    {
        for(int i = 0; i < moviesSearched.size(); i++)
        {
            int leftIndex = 0;
            int rightIndex = newMovieList.size() - 1;
            while(leftIndex <= rightIndex)
            {
                int middleIndex = (leftIndex + rightIndex) / 2;
                int target = moviesSearched.get(i).getTitle().compareTo(newMovieList.get(middleIndex).getTitle());
                if(target == 0)
                {
                    newMovieList.remove(middleIndex);
                    leftIndex = rightIndex + 1;
                }
                else
                {
                    if(target < 0)
                    {
                        rightIndex = middleIndex - 1;
                    }
                    if(target > 0)
                    {
                        leftIndex = middleIndex + 1;
                    }
                }
            }
        }
        return newMovieList;
    }

    public ArrayList<String> removeActors(ArrayList<String> newActorList, ArrayList<String> actorsSearched)
    {
        for(int i = 0; i < actorsSearched.size(); i++)
        {
            int leftIndex = 0;
            int rightIndex = newActorList.size() - 1;
            while(leftIndex <= rightIndex)
            {
                int middleIndex = (leftIndex + rightIndex) / 2;
                int target = actorsSearched.get(i).compareTo(newActorList.get(middleIndex));
                if(target == 0)
                {
                    newActorList.remove(middleIndex);
                    leftIndex = rightIndex + 1;
                }
                else
                {
                    if(target < 0)
                    {
                        rightIndex = middleIndex - 1;
                    }
                    if(target > 0)
                    {
                        leftIndex = middleIndex + 1;
                    }
                }
            }
        }
        return newActorList;
    }

    public ArrayList<SimpleMovie> addMovies(ArrayList<SimpleMovie> movieList, ArrayList<SimpleMovie> newMovieList)
    {
        for(SimpleMovie movie : newMovieList)
        {
            movieList.add(movie);
        }
        return movieList;
    }

    public ArrayList<String> addActors(ArrayList<String> actorList, ArrayList<String> newActorList)
    {
        for(String actor : newActorList)
        {
            actorList.add(actor);
        }
        return actorList;
    }

    public void findLink()
    {
        boolean isCorrectLink = false;
        while(!isCorrectLink)
        {

        }
        link.add("Kevin Bacon");
    }

    public void findBaconNumber()
    {
        baconNumber = actorsLink.size() + 1;
    }

    public boolean hasRelationship(SimpleMovie movie, String actor)
    {
        if(movie.getActors().contains(actor))
        {
            return true;
        }
        return false;
    }
}