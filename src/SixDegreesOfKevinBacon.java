import java.util.ArrayList;
import java.util.Scanner;
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

    public SixDegreesOfKevinBacon()
    {
        this.actor = "";
        this.movies = MovieDatabaseBuilder.getMovieDB("src/movie_data");
        this.moviesSearched = new ArrayList<SimpleMovie>();
        this.actorsSearched = new ArrayList<String>();
        this.link = new ArrayList<String>();
        this.moviesLink = new ArrayList<ArrayList<SimpleMovie>>();
        this.actorsLink = new ArrayList<ArrayList<String>>();
        this.baconNumber = 0;
    }

    public String findActor(String name)
    {
        Scanner scan = new Scanner(System.in);
        ArrayList<String> correctActor = new ArrayList<String>();
        name = name.toLowerCase();
        for(int i = 0; i < movies.size(); i++)
        {
            ArrayList<String> actors = movies.get(i).getActors();
            for(String actor : actors)
            {
                if(actor.toLowerCase().contains(name))
                {
                    correctActor.add(actor);
                }
            }
        }
        removeRepetitiveActors(correctActor);
        for(int i = 1; i < correctActor.size(); i++)
        {
            String tempActor = correctActor.get(i);
            while((i > 0) && (tempActor.compareTo(correctActor.get(i - 1)) < 0))
            {
                correctActor.set(i, correctActor.get(i - 1));
                i--;
            }
            correctActor.set(i, tempActor);
        }
        for(int i = 0; i < correctActor.size(); i++)
        {
            String actorName = correctActor.get(i);
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + actorName);
        }
        System.out.println("Which actor do you want to pick?");
        System.out.print("Enter number: ");
        int choice = scan.nextInt();
        scan.nextLine();
        String actorSelected = correctActor.get(choice - 1);
        return actorSelected;
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
        System.out.println("Test");
        ArrayList<SimpleMovie> kevinBaconMovies = moviesOf(movieActors);
        System.out.println("Test2");
        movieActors.set(0, actor);
        ArrayList<SimpleMovie> actorMovies = moviesOf(movieActors);
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
            actorMovies = moviesOf(movieActors);
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

    public ArrayList<String> findLink()
    {
        int totalLinks = moviesLink.size() + actorsLink.size();
        for(SimpleMovie movieLink1 : moviesLink.get(0))
        {
            if(hasRelationship(movieLink1, actor))
            {
                link.set(1, movieLink1.getTitle());
                if(1 != totalLinks)
                {
                    for(String actorLink1 : actorsLink.get(0))
                    {
                        if(hasRelationship(movieLink1, actorLink1))
                        {
                            link.set(2, actorLink1);
                            for(SimpleMovie movieLink2 : moviesLink.get(1))
                            {
                                if(hasRelationship(movieLink2, actorLink1))
                                {
                                    link.set(3, movieLink2.getTitle());
                                    if(3 != totalLinks)
                                    {
                                        for(String actorLink2 : actorsLink.get(1))
                                        {
                                            if(hasRelationship(movieLink2, actorLink2))
                                            {
                                                link.set(4, actorLink2);
                                                for(SimpleMovie movieLink3 : moviesLink.get(2))
                                                {
                                                    if(hasRelationship(movieLink3, actorLink2))
                                                    {
                                                        link.set(5, movieLink3.getTitle());
                                                        if(5 != totalLinks)
                                                        {
                                                            for(String actorLink3 : actorsLink.get(2))
                                                            {
                                                                if(hasRelationship(movieLink3, actorLink3))
                                                                {
                                                                    link.set(6, actorLink3);
                                                                    for(SimpleMovie movieLink4 : moviesLink.get(3))
                                                                    {
                                                                        if(hasRelationship(movieLink4, actorLink3))
                                                                        {
                                                                            link.set(7, movieLink4.getTitle());
                                                                            if(7 != totalLinks)
                                                                            {
                                                                                for(String actorLink4 : actorsLink.get(3))
                                                                                {
                                                                                    if(hasRelationship(movieLink4, actorLink4))
                                                                                    {
                                                                                        link.set(8, actorLink4);
                                                                                        for(SimpleMovie movieLink5 : moviesLink.get(4))
                                                                                        {
                                                                                            if(hasRelationship(movieLink5, actorLink4))
                                                                                            {
                                                                                                link.set(9, movieLink5.getTitle());
                                                                                                if(9 != totalLinks)
                                                                                                {
                                                                                                    for(String actorLink5 : actorsLink.get(4))
                                                                                                    {
                                                                                                        if(hasRelationship(movieLink5, actorLink5))
                                                                                                        {
                                                                                                            link.set(10, actorLink5);
                                                                                                            for(SimpleMovie movieLink6 : moviesLink.get(5))
                                                                                                            {
                                                                                                                if(hasRelationship(movieLink6, actorLink5))
                                                                                                                {
                                                                                                                    link.set(11, movieLink6.getTitle());
                                                                                                                    if(hasRelationship(movieLink6, "Kevin Bacon"))
                                                                                                                    {
                                                                                                                        link.add("Kevin Bacon");
                                                                                                                        return link;
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                                else
                                                                                                {
                                                                                                    if(hasRelationship(movieLink5, "Kevin Bacon"));
                                                                                                    {
                                                                                                        link.add("Kevin Bacon");
                                                                                                        return link;
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                            else
                                                                            {
                                                                                if(hasRelationship(movieLink4, "Kevin Bacon"))
                                                                                {
                                                                                    link.add("Kevin Bacon");
                                                                                    return link;
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        else
                                                        {
                                                            if(hasRelationship(movieLink3, "Kevin Bacon"))
                                                            {
                                                                link.add("Kevin Bacon");
                                                                return link;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    else
                                    {
                                        if(hasRelationship(movieLink2, "Kevin Bacon"))
                                        {
                                            link.add("Kevin Bacon");
                                            return link;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                else
                {
                    if(hasRelationship(movieLink1, "Kevin Bacon"))
                    {
                        link.add("Kevin Bacon");
                        return link;
                    }
                }
            }
        }
        link.add("Kevin Bacon");
        return link;
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