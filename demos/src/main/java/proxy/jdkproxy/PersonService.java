package proxy.jdkproxy;

/**
 * Created by jinyancao on 4/14/16.
 */
public interface PersonService {
    public String getPersonName(Integer personId);
    public void save(String name);
    public void update(Integer personId, String name);
}


