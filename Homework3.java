import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Homework3 {
    public static void main(String[] args) throws InvalidDataException
    {
        User first = new User("1", "first@example.com", 13), second = new User("2", "second@example.com", 14);
        System.out.println(first.equals(second));
        System.out.println(first.equals(first));
        System.out.println(first.toString());
        System.out.println(second.toString());
        System.out.println();

        Friendship friendship = new Friendship(first,second);
        System.out.println(friendship.toString());

        System.out.println(Friendship.areUsersInFriendship(first,second));

        friendship.deleteFriendship();

        System.out.println(Friendship.areUsersInFriendship(first,second));

        //exceptions tests

        try {
            User InvalidUser1 = new User("Invalid1", "email@main.ru", -1); //invalid age
        }catch (InvalidDataException ex)
        {
            System.out.println(ex.getMessage());
        }
        try {
               User InvalidUser1 = new User("Invalid1", "incorrect_email", 14); //invalid email
        }   catch (InvalidDataException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}

class InvalidDataException extends Exception
{
    public InvalidDataException(String message)
    {
        super(message);
    }
}

class MyJSON
{
    public static String getKeyAndValue(String key, Object value)
    {
        if(value instanceof String)  return "\"" + key +"\"" + " : \"" + value.toString() + "\"";
        return "\"" + key +"\"" + " : " + value.toString();
    }
    public static String getJsonByValues(String ... args)
    {
        String res = "{\n";
        for (int i = 0;i < args.length - 1; i++)
        {
            res += "    " + args[i] + ",\n";
        }
        res += "    " + args[args.length - 1] + "\n}";
        return res;
    }

    private MyJSON(){}
}
class User
{
    public static int countOfUsers = 0;
    int id = 0;
    String name = "";
    String email = "email@example.com";
    int age = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    User(String name, String email, int age) throws InvalidDataException
    {
        if(!email.contains("@")) throw new InvalidDataException("Invalid user id!");
        if(age < 0) throw  new InvalidDataException("Invalid age!");
        Date date = new Date();
        this.id = (date.getSeconds() * date.getMinutes() - date.getDay() * date.getHours() + date.getMonth() - date.getMonth()) * date.getYear() - countOfUsers;
        this.name = name;
        this.email = email;
        this.age = age;
        countOfUsers++;
    }

    @Override
    public boolean equals(Object user)
    {
        return user instanceof User &&  id == ((User)user).id && name.equals(((User) user).name) && email.equals(((User) user).email) && age == ((User) user).age;
    }

    @Override
    public String toString()
    {
        return MyJSON.getJsonByValues( MyJSON.getKeyAndValue("id",id),  MyJSON.getKeyAndValue("name",name),MyJSON.getKeyAndValue("email",email), MyJSON.getKeyAndValue("age",age));
    }
}

class Friendship
{
    private static final ArrayList<Friendship> FRIENDSHIPS = new ArrayList<Friendship>();
    boolean isAvailable;

    User first;
    User second;
    public Friendship(User first, User second)
    {
          this.first = first;
          this.second = second;
          this.isAvailable = true;
          FRIENDSHIPS.add(this);
    }
    @Override
    public String toString()
    {
        return MyJSON.getJsonByValues( MyJSON.getKeyAndValue("is_available",isAvailable),  MyJSON.getKeyAndValue("first_user", first),  MyJSON.getKeyAndValue("second user",second));
    }

    public void deleteFriendship()
    {
        this.isAvailable = false;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public User getFirst() {
        return first;
    }

    public User getSecond() {
        return second;
    }

    public static boolean areUsersInFriendship(User first, User second)
    {
        for (Friendship fr : FRIENDSHIPS)
        {
            if (fr.getFirst().equals(first) && fr.getSecond().equals(second) && fr.isAvailable()) return true;
        }
        return false;
    }
}
