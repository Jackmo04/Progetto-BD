package porto.data.dao;

import java.util.Optional;

import porto.data.api.Cell;
import porto.data.api.Person;
import porto.data.api.Planet;

public record PersonImplTemp(String CUI , String name , String surname) implements Person{

    @Override
    public String getCUI() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCUI'");
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getName'");
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUsername'");
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPassword'");
    }

    @Override
    public String getSurname() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSurname'");
    }

    @Override
    public String getRazza() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRazza'");
    }

    @Override
    public String getBornDate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBornDate'");
    }

    @Override
    public Planet getBornPlace() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBornPlace'");
    }

    @Override
    public Boolean isWanted() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isWanted'");
    }

    @Override
    public void setWanted(Boolean wanted) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setWanted'");
    }

    @Override
    public String getIdeology() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getIdeology'");
    }

    @Override
    public String getRole() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRole'");
    }

    @Override
    public Optional<Cell> getCell() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCell'");
    }

    @Override
    public void setCell(Cell cell) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setCell'");
    }

}
