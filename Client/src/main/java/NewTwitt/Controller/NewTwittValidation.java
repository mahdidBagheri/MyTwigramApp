package NewTwitt.Controller;

import NewTwitt.Events.NewTwittEvent;
import NewTwitt.Exceptions.NewTwittException;

public class NewTwittValidation {
    public void Validate(NewTwittEvent newTwittEvent) throws NewTwittException {
        ValidateTwittText(newTwittEvent);
    }

    public void ValidateTwittText(NewTwittEvent newTwittEvent) throws NewTwittException {
        if(newTwittEvent.getText().equals("")){
            throw new NewTwittException("Twitt Feild Can not be empty");
        }
        if(newTwittEvent.getText().length() > 400){
            throw new NewTwittException("You Can not write more than 400 characters");
        }


    }
}
