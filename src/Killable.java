public interface Killable extends Entity{
    int getHealth();
    void setHealth(int health);
    void harm(int amt);

}
