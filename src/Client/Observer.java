package Client;

public interface Observer<Subject>
{
    /**
     *
     * @param subject
     */
    void update(Subject subject);
}

