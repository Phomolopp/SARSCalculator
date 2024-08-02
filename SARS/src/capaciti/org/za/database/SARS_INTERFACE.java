package capaciti.org.za.database;
public interface SARS_INTERFACE
{
   String URL ="jdbc:derby://localhost:1527//SARS_DB";
   String userName ="SARS";
   String dbpassword = "123";
   String sql ="INSERT INTO SARS_DATABASE VALUES (?, ?, ?, ?, ?, ?, ?)";
}
