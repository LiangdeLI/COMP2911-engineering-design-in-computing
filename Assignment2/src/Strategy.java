import java.util.ArrayList;

/**
 * The strategy interface
 * @author Liangde Li z5077896
 */
public interface Strategy {
    public int calcHeuritic(City curr, ArrayList<Path> jobList);
}
