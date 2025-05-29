package porto.data.api;

import java.util.Optional;

public interface Person {

    public String getCUI();

    public String getName();

    public String getUsername();

    public String getPassword();

    public String getSurname();

    public String getRazza();

    public String getBornDate();

    public Planet getBornPlace();

    public Boolean isWanted();

    public void setWanted(Boolean wanted);

    public String getIdeology();

    public String getRole();

    public Optional<Cell> getCell();

    public void setCell(Cell cell);

}
